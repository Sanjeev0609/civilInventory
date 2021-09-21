package com.example.builderstool.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.Product
import kotlinx.android.synthetic.main.card_list_products.view.*

class ProductsListAdapter(var context: Context,var products:ArrayList<Product>):RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_products,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product=products[position]
        holder.itemView.tv_name.text=product.name
        holder.itemView.tv_stock.text=product.stock.toString()
        holder.itemView.tv_price.text="₹ "+product.price

        if(product.image!=null){
            holder.itemView.iv_product.setImageURI(product.image!!.toUri())
        }

        if (10 > product.stock!!){
            holder.itemView.iv_status.setImageResource(R.drawable.ic_warning)
        }else{
            holder.itemView.iv_status.setImageResource(R.drawable.ic_approve)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
}