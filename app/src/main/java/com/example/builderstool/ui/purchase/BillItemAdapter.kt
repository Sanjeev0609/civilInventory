package com.example.builderstool.ui.purchase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.PurchaseProducts
import kotlinx.android.synthetic.main.layout_bill_item.view.*

class BillItemAdapter(var context:Context,var products:ArrayList<PurchaseProducts>):RecyclerView.Adapter<BillItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_bill_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product=products[position]
        holder.itemView.tv_product_amount.text=product.price!!.toInt().times(product.quantity!!).toString()
        holder.itemView.tv_product_name.text=product.name
        holder.itemView.tv_product_price.text=product.price.toString()
        holder.itemView.tv_product_quantity.text=product.quantity.toString()
    }

    override fun getItemCount(): Int {
       return products.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}