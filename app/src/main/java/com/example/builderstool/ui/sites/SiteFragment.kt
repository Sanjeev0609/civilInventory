package com.example.builderstool.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_site.*

class SiteFragment:BaseFragment(){
    var siteId:Int?=null
    var siteName:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_site,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            var action=SiteFragmentDirections.actionSiteFragmentToOrderFragment(siteId!!,siteName!!)
            findNavController().navigate(action)
        }
        arguments?.let { 
            var args=SiteFragmentArgs.fromBundle(it)
            siteId=args.id
            siteName=args.name
        }
        requireActivity().toolbar.title=siteName
        init()
        observeViewModel()
    }
    val siteViewModel by lazy {
        ViewModelProvider(this).get(SiteViewModel::class.java)
    }
    fun init(){
        siteViewModel.listOrders(siteId!!)
    }
    fun observeViewModel(){
        with(siteViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                showMessage(it)
            })
            orders.observe(viewLifecycleOwner, Observer {
                rv_products.adapter=ListOrdersAdapter(currentContext,it)
                rv_products.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
            })
        }
    }
}