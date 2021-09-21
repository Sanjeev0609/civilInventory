package com.example.builderstool.ui.purchase

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.Purchase
import com.example.builderstool.model.PurchaseProducts
import kotlinx.android.synthetic.main.card_added_products.view.*
import kotlinx.android.synthetic.main.card_added_suppliers.view.*
import java.lang.Exception


class ProductsAdapter(var context: Context, var products:ArrayList<PurchaseProducts>,var listener:PurchaseFragment) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_added_products,parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {


            var product = products.get(position)
            holder.itemView.tv_product_name.text = product.name
            holder.itemView.et_quantity.setText(product.quantity.toString())

            holder.itemView.iv_plus.setOnClickListener {
                product.quantity=product.quantity!!.plus(1)
                listener.dataChanged()
                notifyDataSetChanged()
            }
            holder.itemView.iv_minus.setOnClickListener {
                product.quantity=product.quantity!!.minus(1)
                listener.dataChanged()
                notifyDataSetChanged()
            }
            holder.itemView.iv_delete.setOnClickListener {
                listener.removeProduct(product.id!!)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}
}