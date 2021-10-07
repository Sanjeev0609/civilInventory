package com.example.builderstool.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.PurchaseProducts
import com.example.builderstool.network.response.ListOrderProductsResponse
import kotlinx.android.synthetic.main.card_products_in_order.view.*

class ProductInUseAdapter(var context:Context,var products:ListOrderProductsResponse,var listener:EditOrderFragment):RecyclerView.Adapter<ProductInUseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_products_in_order,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product=products!!.inUse!![position];
        if(product.inUseQuantity==0){
            holder.itemView.visibility=View.GONE
        }
        holder.itemView.tv_name.text=product.name
        holder.itemView.tv_price.text=product.price
        holder.itemView.tv_stock.text=product.inUseQuantity.toString()
        var statuses= arrayListOf<String>("in_use","excess","returned")

        holder.itemView.sp_status.adapter=ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,statuses)
        holder.itemView.sp_status.setSelection(statuses.indexOf(product.status))

        holder.itemView.sp_status.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var status=statuses.get(p2)

//                    product.status = status
                if(status!="in_use") {
                    if (status == "excess") {
                        product.excessQuantity = 1
                        product.inUseQuantity= product.inUseQuantity!!-1
                        products.excess!!.add(product)
                    }else if(status=="returned"){
                        Toast.makeText(context,"returnef",Toast.LENGTH_SHORT).show()
                        product.returnQuantity=1
                        product.inUseQuantity=product.inUseQuantity!!-1
                        products.returned!!.add(product)
                    }
                    listener.dataChanged()
                }

//                notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun getItemCount(): Int {
        return  products.inUse!!.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}