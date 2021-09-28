package com.example.builderstool.ui.orders

import com.example.builderstool.ui.purchase.*


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import com.example.builderstool.model.Product
import com.example.builderstool.model.PurchaseProducts
import com.example.builderstool.model.Supplier
import kotlinx.android.synthetic.main.fragment_create_order.*
import kotlinx.android.synthetic.main.fragment_create_order.btn_add_product
import kotlinx.android.synthetic.main.fragment_create_order.et_paid
import kotlinx.android.synthetic.main.fragment_create_order.rv_bill
import kotlinx.android.synthetic.main.fragment_create_order.sp_product
import kotlinx.android.synthetic.main.fragment_create_order.tie_product_quantity
import kotlinx.android.synthetic.main.fragment_create_order.tv_total

import java.util.function.Predicate

class OrderFragment:BaseFragment(), PurchaseInterface {

    var suppliersList=ArrayList<Supplier>()
    var productsList=ArrayList<Product>()
    var supplierNames=ArrayList<String>()
    var productNames=ArrayList<String>()
    var siteId:Int?=null
    var siteName:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_order,container,false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var args=OrderFragmentArgs.fromBundle(it)
            siteId=args.id
            siteName=args.name
        }
        init()
        initListener()
        observeViewModel()
    }

    private val orderViewModel by lazy {
        ViewModelProvider(this).get(OrderViewModel::class.java)
    }
    fun init(){
        orderViewModel.listProducts()
        rv_products.adapter=SelectedProductsAdapter(currentContext,orderViewModel!!.orderData!!.products,this)
        rv_products!!.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun initListener(){

        btn_order.setOnClickListener {
            orderViewModel.orderData.customerId=siteId
            orderViewModel.orderData.customerName=siteName
            orderViewModel.orderData.billType="purchase_bill"
            orderViewModel!!.orderData.paid=et_paid.text.toString().toInt()
            orderViewModel!!.orderData.balance= orderViewModel!!.orderData.total!!.minus(orderViewModel!!.orderData.paid!!)
            orderViewModel!!.createOrder()
        }
        btn_add_product.setOnClickListener {
            try {
                var selectedPos = sp_product.selectedItemPosition
                if (selectedPos != 0) {
                    btn_add_product.isEnabled = true
                    var product = sp_product.adapter.getItem(selectedPos) as Product

                    orderViewModel.orderData.products.add(PurchaseProducts().apply {
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
                    sp_product.setSelection(0)
                    tie_product_quantity.setText("0")
                    rv_products.adapter!!.notifyDataSetChanged()
                    rv_bill.adapter= BillItemAdapter(currentContext,orderViewModel.orderData.products)
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
        var product=orderViewModel.products.value!!.stream().filter(object :Predicate<Product>{
            override fun test(p0: Product): Boolean {
                var boolean=p0.id==product_id!!
                return boolean

            }
        }).findAny().orElse(null)
        orderViewModel.orderData.products.removeIf(@RequiresApi(Build.VERSION_CODES.N)
        object :Predicate<PurchaseProducts>{
            override fun test(p0: PurchaseProducts): Boolean {
                var boolean=p0.id==product.id
                return boolean
            }
        })



        productsList.add(product)
        sp_product.setSelection(0)


        dataChanged()
    }

    override fun quantityChanged() {
        dataChanged()
    }

    fun dataChanged(){
        var total=0;
        for(product in orderViewModel!!.orderData.products){
            total+=product.quantity!!.times(product.price!!.toInt())
        }
        tv_total.text=total.toString()!!
        orderViewModel!!.orderData.total=total
        rv_bill.adapter!!.notifyDataSetChanged()
        rv_products.adapter!!.notifyDataSetChanged()
        sp_product.adapter= CustomProductsAdapter(currentContext,productsList)

    }

    fun observeViewModel(){
        with(orderViewModel){

            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                showMessage(it)
            })

            products.observe(viewLifecycleOwner, Observer {
                productsList.clear()
                productsList.add(Product().apply { name="Product" })
                productsList.addAll(it)
                sp_product.adapter= CustomProductsAdapter(currentContext,productsList)
            })
        }
    }
}