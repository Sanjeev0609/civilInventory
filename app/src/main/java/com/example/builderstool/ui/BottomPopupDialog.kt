package com.example.builderstool.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.builderstool.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_popup.*

class BottomPopupDialog:BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_popup,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cv_products.setOnClickListener {
            findNavController().navigate(R.id.addProductFragment)
            dismiss()
        }

        cv_sites.setOnClickListener {
            findNavController().navigate(R.id.addSiteFragment)
            dismiss()
        }

        cv_suppliers.setOnClickListener {
            findNavController().navigate(R.id.addSupplierFragment)
            dismiss()
        }

        cv_purchases.setOnClickListener {
            findNavController().navigate(R.id.purchaseFragment)
            dismiss()
        }
    }
}