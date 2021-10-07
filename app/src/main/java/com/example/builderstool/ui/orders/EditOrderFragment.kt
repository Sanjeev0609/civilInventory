package com.example.builderstool.ui.orders

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.builderstool.network.request.EditOrderProducts
import com.example.builderstool.network.request.EditOrderRequest
import com.example.builderstool.network.response.ListOrderProductsResponse
import com.example.builderstool.ui.purchase.PurchaseInterface
import kotlinx.android.synthetic.main.fragment_edit_order.*
import java.util.function.Predicate

class EditOrderFragment:BaseFragment(),EditOrderInterface {
    var orderId=0
    var paid=0
    var balance=0
    var total=0
    var products=ArrayList<PurchaseProducts>()
    var excessProducts=ArrayList<PurchaseProducts>()
    var returnProducts=ArrayList<PurchaseProducts>()
    var inUseProducts=ArrayList<PurchaseProducts>()
    var list=ListOrderProductsResponse()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_order,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var args=EditOrderFragmentArgs.fromBundle(it)
            orderId=args.id
            paid=args.paid
            balance=args.balance
            total=args.total
        }
        init()
        observeViewModel()
    }
    val editOrderViewModel by lazy {
        ViewModelProvider(this).get(EditOrderViewModel::class.java)
    }
    fun init(){
        editOrderViewModel.listProducts(orderId)
        tv_total.text="₹ "+total
        et_paid.setText(paid!!.toString())
        btn_save.setOnClickListener {
            var products=ArrayList<EditOrderProducts>()
            var returningProducts=ArrayList<ReturnProduct>()
            for( p in list!!.excess!!){
                var product=list.inUse!!.indexOf(p)
                list.inUse!!.remove(list!!.inUse!![product])
                list.inUse!!.add(p);
            }
            var returnTotal=0
            for( p in list!!.returned!!){
                returningProducts.add(ReturnProduct().apply {
                    productId=p.id
                    quantity=p.quantity
                    returnQuantity=p.returnQuantity
                })
                returnTotal+=p.price!!.toInt()* p.returnQuantity!!
            }
            total-=returnTotal
            for(p in list!!.inUse!!){
                products.add(EditOrderProducts().apply {
                    productId=p.id
                    inUseQuantity=p.inUseQuantity
                    excessQuantity=p.excessQuantity
                })
            }
            var request=EditOrderRequest()
            request.products=products
            request.paid=et_paid.text.toString().toInt()
            request.balance=total-et_paid.text.toString().toInt()
            request.total=total
            request.returningProducts=returningProducts
            editOrderViewModel.editOrder(orderId,request)

//            list!!.inUse!!.contains()

        }
    }

    override fun dataChanged() {

//        for (p in list!!.inUse!!){
//            if(p.excessQuantity!=null){
//                list.excess!!.add(p)
//            }
//        }
        for( p in list!!.excess!!){
            var product=list.inUse!!.indexOf(p)
            list.inUse!!.remove(list!!.inUse!![product])
            list.inUse!!.add(p);

        }
        for( p in list!!.returned!!){
            var product=list.inUse!!.indexOf(p)
            list.inUse!!.remove(list!!.inUse!![product])
            list.inUse!!.add(p);
        }
        rv_returned_products.adapter=ReturnProductsAdapter(currentContext,list,this@EditOrderFragment,rv_excess_products)
        rv_returned_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)

        rv_products.adapter=ProductInUseAdapter(currentContext,list,this@EditOrderFragment)
        rv_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)

        rv_excess_products.adapter=ExcessProductsAdapter(currentContext,list,this@EditOrderFragment,rv_excess_products)
        rv_excess_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)


    }

    fun observeViewModel(){
        with(editOrderViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                showMessage(it)
            })
            products.observe(viewLifecycleOwner, Observer {
                this@EditOrderFragment.products=it
                for(p in it){
                    if(p.excessQuantity!=null){
                        list.excess!!.add(p)
                    }
                    if(p.excessQuantity!=0){
                        list.inUse!!.add(p)
                    }

                }
                rv_products.adapter=ProductInUseAdapter(currentContext,list,this@EditOrderFragment)
                rv_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)

                rv_excess_products.adapter=ExcessProductsAdapter(currentContext,list,this@EditOrderFragment,rv_excess_products)
                rv_excess_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)



            })
        }
    }
}