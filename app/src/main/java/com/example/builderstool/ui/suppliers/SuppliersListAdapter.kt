package com.example.builderstool.ui.suppliers




import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.Supplier
import kotlinx.android.synthetic.main.card_list_sites.view.*


class SuppliersListAdapter(var context: Context,var suppliers:ArrayList<Supplier>):RecyclerView.Adapter<SuppliersListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_suppliers,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var supplier=suppliers[position]
        holder.itemView.tv_name.text=supplier.name
        holder.itemView.tv_address.text=supplier.address
        holder.itemView.tv_mobile.text=supplier.mobile


    }

    override fun getItemCount(): Int {
        return suppliers.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
}