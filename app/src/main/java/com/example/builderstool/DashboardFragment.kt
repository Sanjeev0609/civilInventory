package com.example.builderstool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.builderstool.ui.BottomPopupDialog
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            var bottomPopup=BottomPopupDialog()
            bottomPopup.show(requireActivity().supportFragmentManager,"ModalBottomSheet")
        }

        cv_products.setOnClickListener {
            findNavController().navigate(R.id.productsListFragment)
        }

        cv_sites.setOnClickListener {
            findNavController().navigate(R.id.sitesListFragment)
        }

        cv_suppliers.setOnClickListener {
            findNavController().navigate(R.id.suppliersListFragment)
        }
        cv_purchases.setOnClickListener {
            findNavController().navigate(R.id.listPurchaseFragment)
        }


    }
}