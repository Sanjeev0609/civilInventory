package com.example.builderstool.ui.purchase

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import com.example.builderstool.model.Product
import com.example.builderstool.model.Purchase
import com.example.builderstool.model.PurchaseProducts
import com.example.builderstool.model.Supplier
import kotlinx.android.synthetic.main.fragment_purchase.*
import java.util.function.Predicate
import javax.sql.StatementEvent

class PurchaseFragment:BaseFragment(),PurchaseInterface {

    var suppliersList=ArrayList<Supplier>()
    var productsList=ArrayList<Product>()
    var supplierNames=ArrayList<String>()
    var productNames=ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_purchase,container,false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListener()
        observeViewModel()
    }

    private val purchaseViewModel by lazy {
        ViewModelProvider(this).get(PurchaseViewModel::class.java)
    }
    fun init(){
        purchaseViewModel.listProducts()
        purchaseViewModel.listSuppliers()
        btn_add_product.isEnabled=false
        rv_suppliers.adapter=SelectedSuppliersAdapter(currentContext,purchaseViewModel.purchaseData,this)
        rv_suppliers.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)


    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun initListener(){
      btn_select_supplier.setOnClickListener {
          var selectedPos=sp_supplier.selectedItemPosition
          if(selectedPos!=0){
              var purchase=Purchase()
              btn_add_product.isEnabled=true
              purchase.supplier=sp_supplier.adapter.getItem(selectedPos) as Supplier
             purchase.supplierId=(sp_supplier.adapter.getItem(selectedPos) as Supplier).id
              purchaseViewModel.purchaseData.add(purchase)
              rv_suppliers.adapter!!.notifyDataSetChanged()
              btn_select_supplier.isEnabled=false
          }else{
              showMessage("Select Supplier to continue")
              btn_add_product.isEnabled=false
          }
      }

        btn_purchase.setOnClickListener {
            purchaseViewModel!!.purchaseData[0].paid=et_paid.text.toString().toInt()
            purchaseViewModel!!.purchaseData[0].balance= purchaseViewModel!!.purchaseData[0].total!!.minus(purchaseViewModel!!.purchaseData[0].paid!!)
            purchaseViewModel!!.createPurchase()
        }
        btn_add_product.setOnClickListener {
            try {
                var selectedPos = sp_product.selectedItemPosition
                if (selectedPos != 0) {
                    btn_add_product.isEnabled = true
                    var product = sp_product.adapter.getItem(selectedPos) as Product

                    purchaseViewModel.purchaseData.get(0).products.add(PurchaseProducts().apply {
                        id = product.id
                        name = product.name
                        quantity = tie_product_quantity.text.toString().toInt()
                        price = product.price
                    })

                    productsList.removeIf(@RequiresApi(Build.VERSION_CODES.N)
                    object :Predicate<Product>{
                        override fun test(p0: Product): Boolean {
                            var boolean=p0.id==product.id
                            return boolean
                        }
                    })
                    btn_select_supplier.isEnabled = false
                    sp_product.setSelection(0)
                    tie_product_quantity.setText("0")
                    rv_suppliers.adapter!!.notifyDataSetChanged()
                    rv_bill.adapter=BillItemAdapter(currentContext,purchaseViewModel.purchaseData[0].products)
                    rv_bill.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
                    dataChanged()
                    rv_bill.adapter!!.notifyDataSetChanged()

                } else {
                    showMessage("Select Product to continue")
                    btn_add_product.isEnabled = false
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun removeProduct(product_id: Int) {
        var product=purchaseViewModel.products.value!!.stream().filter(object :Predicate<Product>{
            override fun test(p0: Product): Boolean {
                var boolean=p0.id==product_id!!
                return boolean

            }
        }).findAny().orElse(null)

        purchaseViewModel.purchaseData[0].products.removeIf(@RequiresApi(Build.VERSION_CODES.N)
        object :Predicate<PurchaseProducts>{
            override fun test(p0: PurchaseProducts): Boolean {
                var boolean=p0.id==product.id
                return boolean
            }
        })



        productsList.add(product)
        sp_product.setSelection(0)
        rv_suppliers.adapter!!.notifyDataSetChanged()
        dataChanged()
    }

    override fun quantityChanged() {
       dataChanged()
    }

   fun dataChanged(){
        var total=0;
        for(product in purchaseViewModel!!.purchaseData[0].products){
            total+=product.quantity!!.times(product.price!!.toInt())
        }
        tv_total.text=total.toString()!!
       purchaseViewModel!!.purchaseData[0].total=total
        rv_bill.adapter!!.notifyDataSetChanged()

    }

    fun observeViewModel(){
        with(purchaseViewModel){

            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                showMessage(it)
            })
            suppliers.observe(viewLifecycleOwner, Observer {
                suppliersList.clear()
                suppliersList.add(Supplier().apply {
                    name="Supplier"
                })
                suppliersList.addAll(it)
                supplierNames.clear()
                for(supplier in suppliersList){
                    supplierNames.add(supplier.name!!)
                }
                sp_supplier.adapter=CustomSupplierAdapter(currentContext,suppliersList)
            })
            products.observe(viewLifecycleOwner, Observer {
                productsList.clear()
                productsList.add(Product().apply { name="Product" })
                productsList.addAll(it)
                sp_product.adapter=CustomProductsAdapter(currentContext,productsList)
            })
        }
    }
}