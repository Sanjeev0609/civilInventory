package com.example.builderstool.ui.balance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.network.request.UpdateBalanceRequest
import com.example.builderstool.network.response.OrderBalance
import com.example.builderstool.network.response.PurchaseBalance
import kotlinx.android.synthetic.main.card_order_balances.view.*


class OrderBalanceAdapter(var context: Context, var balances:ArrayList<OrderBalance>,var viewModel: OrderBalanceViewModel) :
        RecyclerView.Adapter<OrderBalanceAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_order_balances,parent,false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var balance=balances[position]
            holder.itemView.tv_id.text=balance.orderId.toString()
            holder.itemView.tv_supplier.text=balance.supplierName.toString()
            holder.itemView.tv_total.text=balance.total.toString()
            holder.itemView.tv_balance.text=Math.abs(balance.balance!!).toString()
            if(balance.balance!! < 0){
                holder.itemView.iv_arrow.setImageResource(R.drawable.ic_up_arrow)
            }else{
                holder.itemView.iv_arrow.setImageResource(R.drawable.ic_arrrow_down)
            }
            holder.itemView.btn_complete.setOnClickListener {
                var request=UpdateBalanceRequest()
                request.id=balance.orderId
                request.balance=balance.balance
                if(balance.balance!!<0){
                    request.type="debit"
                }else{
                    request.type="credit"
                }
                viewModel.updateBalance(request)
            }

        }

        override fun getItemCount(): Int {
            return balances.size
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    }
