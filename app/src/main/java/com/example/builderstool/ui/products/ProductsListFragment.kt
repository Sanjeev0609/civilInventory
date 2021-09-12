package com.example.builderstool.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import com.example.builderstool.model.Product
import kotlinx.android.synthetic.main.fragment_list_products.*

class ProductsListFragment :BaseFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_products,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var products=ArrayList<Product>()
        products.add(Product())
        products.add(Product())
        products.add(Product())


        init()
        observeViewModel()
    }
    val productsViewModel by lazy {
        ViewModelProvider(this).get(ProductsViewModel::class.java)
    }

    fun init(){
        productsViewModel.listProducts()
    }
    fun observeViewModel(){
        with(productsViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            products.observe(viewLifecycleOwner, Observer {
                rv_products.adapter=ProductsListAdapter(currentContext,it)
                rv_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
            })
        }
    }
}