package com.example.builderstool.ui.purchase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.builderstool.R
import com.example.builderstool.model.Supplier

class CustomSupplierAdapter(context: Context, var suppliers:ArrayList<Supplier>):
    ArrayAdapter<Supplier>(context, R.layout.support_simple_spinner_dropdown_item,suppliers) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null)
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.support_simple_spinner_dropdown_item, parent, false)
        var slotName = view!!.findViewById<TextView>(android.R.id.text1)
        slotName.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START

        var supplier = suppliers[position]
        slotName!!.setText(supplier!!.name)


        return view
    }
}
