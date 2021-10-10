package com.example.builderstool.ui.balance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.network.request.UpdateBalanceRequest
import com.example.builderstool.network.response.PurchaseBalance
import kotlinx.android.synthetic.main.card_order_balances.view.*
import kotlinx.android.synthetic.main.card_purchase_balances.view.*
import kotlinx.android.synthetic.main.card_purchase_balances.view.btn_complete
import kotlinx.android.synthetic.main.card_purchase_balances.view.tv_balance
import kotlinx.android.synthetic.main.card_purchase_balances.view.tv_id
import kotlinx.android.synthetic.main.card_purchase_balances.view.tv_supplier
import kotlinx.android.synthetic.main.card_purchase_balances.view.tv_total

class PurchaseBalanceAdapter(var context:Context,var balances:ArrayList<PurchaseBalance>,var viewModel: PurchaseBalancesViewModel) :RecyclerView.Adapter<PurchaseBalanceAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_purchase_balances,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var balance=balances[position]
        holder.itemView.tv_id.text=balance.purchaseId.toString()
        holder.itemView.tv_supplier.text=balance.supplierName.toString()
        holder.itemView.tv_total.text=balance.total.toString()
        holder.itemView.tv_balance.text=balance.balance.toString()
        holder.itemView.btn_complete.setOnClickListener {
            var request= UpdateBalanceRequest()
            request.id=balance.purchaseId
            request.balance=balance.balance
            if(balance.balance!!<0){
                request.type="debit"
            }else{
                request.type="credit";
            }
            viewModel.updateBalance(request)
        }
    }

    override fun getItemCount(): Int {
        return balances.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}