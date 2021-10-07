package com.example.builderstool

import com.example.builderstool.network.response.ProductsAvailableInSites


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R.layout.card_products_in_inventory
import com.example.builderstool.R.layout.card_products_in_nearby_sites
import com.example.builderstool.network.response.ProductsAvalailableInInventory
import kotlinx.android.synthetic.main.card_products_in_nearby_sites.view.*

class ProductsInNearbySitesAdapter(var context:Context,var products:ArrayList<ProductsAvailableInSites>):RecyclerView.Adapter<ProductsInNearbySitesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(card_products_in_nearby_sites,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = products[position]
        holder.itemView.tv_name.text=product.productName
        holder.itemView.tv_price.text="₹ "+product.price
        holder.itemView.tv_quantity.text=product.quantity.toString()+" available"
        holder.itemView.tv_location.text=product.street
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}