package com.example.builderstool.ui.sites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.common.PdfUtils
import com.example.builderstool.model.Order
import com.example.builderstool.model.Purchase
import com.example.builderstool.ui.purchase.BillItemAdapter
import kotlinx.android.synthetic.main.card_orders.view.*


class ListOrdersAdapter(var context: Context, var purchases:ArrayList<Order>): RecyclerView.Adapter<ListOrdersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_orders,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var purchase=purchases[position]
        holder.itemView.tv_id.text=purchase.id.toString()
//        holder.itemView.t.text=purchase.customerName.toString()
        holder.itemView.tv_balance.text=purchase.balance.toString()
        holder.itemView.tv_paid.text=purchase.paid.toString()
        holder.itemView.tv_total.text=purchase.total.toString()
        holder.itemView.rv_bill.adapter= BillItemAdapter(context,purchase.products)
        holder.itemView.rv_bill.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        holder.itemView.cl_1.setOnClickListener {
            if(holder.itemView.cl_2.visibility== View.VISIBLE){
                holder.itemView.cl_2.visibility= View.GONE
            }else{
                holder.itemView.cl_2.visibility= View.VISIBLE
            }
        }
        holder.itemView.btn_bill.setOnClickListener {
            purchase.billType="purchase_bill"
            PdfUtils().createOrderBill(context,purchase)
        }
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}