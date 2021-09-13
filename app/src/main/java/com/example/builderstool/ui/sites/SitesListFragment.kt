package com.example.builderstool.ui.sites

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
import com.example.builderstool.ui.products.ProductsListAdapter
import com.example.builderstool.ui.products.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_list_products.*
import kotlinx.android.synthetic.main.fragment_list_sites.*

class SitesListFragment :BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var products = ArrayList<Product>()
        products.add(Product())
        products.add(Product())
        products.add(Product())


        init()
        observeViewModel()
    }

    val listSitesViewModel by lazy {
        ViewModelProvider(this).get(ListSitesViewModel::class.java)
    }

    fun init() {
        listSitesViewModel.listSites()
    }

    fun observeViewModel() {
        with(listSitesViewModel) {
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            sites.observe(viewLifecycleOwner, Observer {
                rv_sites.adapter = SitesListAdapter(currentContext, it)
                rv_sites.layoutManager =
                    LinearLayoutManager(currentContext, LinearLayoutManager.VERTICAL, false)
            })
        }
    }
}
