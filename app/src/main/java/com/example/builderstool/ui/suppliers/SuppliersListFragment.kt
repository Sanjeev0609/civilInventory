package com.example.builderstool.ui.suppliers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import com.example.builderstool.ui.sites.ListSitesViewModel
import com.example.builderstool.ui.sites.SitesListAdapter
import kotlinx.android.synthetic.main.fragment_list_sites.*
import kotlinx.android.synthetic.main.fragment_list_supplier.*

class SuppliersListFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_supplier,container,false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeViewModel()
    }

    val listSuppliersViewModel by lazy {
        ViewModelProvider(this).get(ListSuppliersViewModel::class.java)
    }

    fun init() {
        listSuppliersViewModel.listSuppliers()
    }

    fun observeViewModel() {
        with(listSuppliersViewModel) {
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            suppliers.observe(viewLifecycleOwner, Observer {
                rv_suppliers.adapter = SuppliersListAdapter(currentContext, it)
                rv_suppliers.layoutManager =
                    LinearLayoutManager(currentContext, LinearLayoutManager.VERTICAL, false)
            })
        }
    }
}