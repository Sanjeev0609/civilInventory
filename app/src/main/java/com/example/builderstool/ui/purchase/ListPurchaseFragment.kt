package com.example.builderstool.ui.purchase

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
import com.example.builderstool.model.Supplier
import kotlinx.android.synthetic.main.fragment_list_purchases.*
import kotlinx.android.synthetic.main.fragment_purchase.*


class ListPurchaseFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_purchases,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

init()
        observeViewModel()
    }
    val listPurchaseViewModel by lazy {
        ViewModelProvider(this).get(ListPurchaseViewModel::class.java)
    }
    fun init(){
        listPurchaseViewModel.listPurchases()
    }
    fun observeViewModel(){
        with(listPurchaseViewModel){

            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                showMessage(it)
            })
            purchases.observe(viewLifecycleOwner, Observer {
                rv_purchases.adapter=ListPurchaseAdapter(currentContext,it)
                rv_purchases.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
            })

        }
    }

}