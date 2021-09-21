package com.example.builderstool.ui.purchase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.Purchase
import kotlinx.android.synthetic.main.card_added_suppliers.view.*

class SelectedSuppliersAdapter(var context: Context,var purchaseDetails:ArrayList<Purchase>,var listener:PurchaseFragment) :RecyclerView.Adapter<SelectedSuppliersAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_added_suppliers,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {


            var purchase = purchaseDetails.get(position)
            holder.itemView.tv_supplier_name.text = purchase.supplier.name
            holder.itemView.rv_products.adapter =
                ProductsAdapter(context, purchaseDetails[position].products,listener)
            holder.itemView.rv_products.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return purchaseDetails.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){}
}