package com.example.builderstool.ui.orders


import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.PurchaseProducts
import com.example.builderstool.network.response.ListOrderProductsResponse

import kotlinx.android.synthetic.main.card_products_in_excess.view.*
import kotlinx.android.synthetic.main.card_products_in_order.view.*
import kotlinx.android.synthetic.main.card_products_in_order.view.tv_name
import kotlinx.android.synthetic.main.card_products_in_order.view.tv_price

class ExcessProductsAdapter(var context:Context,var products:ListOrderProductsResponse,var listener:EditOrderFragment,var recyclerView: RecyclerView):RecyclerView.Adapter<ExcessProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_products_in_excess,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product=products!!.excess!![position];
        if(product.excessQuantity!=null ) {
            if (product.excessQuantity != 0) {
                holder.itemView.tv_name.text = product.name
                holder.itemView.tv_price.text = product.price
                holder.itemView.et_quantity.setText(product.excessQuantity.toString())
                if (product.inUseQuantity == 0) {
                    holder.itemView.iv_plus.visibility = View.GONE
                }
                holder.itemView.iv_plus.setOnClickListener {
                    product.excessQuantity=  product.excessQuantity!!+1
                    product.inUseQuantity= product.inUseQuantity!!-1
                    holder.itemView.et_quantity.setText(product.excessQuantity.toString())
                    listener.dataChanged()
                }
                holder.itemView.iv_minus.setOnClickListener {
                    product.excessQuantity=  product.excessQuantity!!-1
                    product.inUseQuantity= product.inUseQuantity!!+1
                    holder.itemView.et_quantity.setText(product.excessQuantity.toString())

                    listener.dataChanged()

                }
            }else{
                holder.itemView.visibility=View.GONE
//                recyclerView.layoutParams= ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT
//                )
//                notifyDataSetChanged()
            }
        }else{
            holder.itemView.visibility=View.GONE
//            recyclerView.layoutParams=ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)

        }
//        recyclerView.layoutParams= ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.WRAP_CONTENT,
//            ConstraintLayout.LayoutParams.WRAP_CONTENT
//        )
//        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return  products.excess!!.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}