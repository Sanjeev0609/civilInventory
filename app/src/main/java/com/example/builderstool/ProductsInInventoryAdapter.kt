package com.example.builderstool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.builderstool.R.layout.card_products_in_inventory
import com.example.builderstool.network.response.ProductsAvalailableInInventory
import kotlinx.android.synthetic.main.card_products_in_inventory.view.*
import java.io.File

class ProductsInInventoryAdapter(var context:Context,var products:ArrayList<ProductsAvalailableInInventory>):RecyclerView.Adapter<ProductsInInventoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(card_products_in_inventory,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = products[position]
        holder.itemView.tv_name.text=product.name
        holder.itemView.tv_price.text="₹ "+product.price
        holder.itemView.tv_quantity.text=product.stock.toString()+" available"
        Glide.with(context).load(File(product.image!!)).error(R.drawable.ic_building_construction).into(holder.itemView.iv_product)
    }

    override fun getItemCount(): Int {
       return products.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}