package com.example.builderstool.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.builderstool.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomPopupDialog:BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_popup,container,false)
    }
}