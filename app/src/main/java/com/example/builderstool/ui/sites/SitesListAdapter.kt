package com.example.builderstool.ui.sites



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.builderstool.R
import com.example.builderstool.model.Product
import com.example.builderstool.model.Site
import kotlinx.android.synthetic.main.card_list_sites.view.*


class SitesListAdapter(var context: Context,var sites:ArrayList<Site>):RecyclerView.Adapter<SitesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_sites,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var site=sites[position]
        holder.itemView.tv_name.text=site.name
        holder.itemView.tv_address.text=site.address
        holder.itemView.tv_mobile.text=site.mobile

        if (site.status!="in_progresss"){
            holder.itemView.iv_status.setImageResource(R.drawable.ic_approve)
        }else{
            holder.itemView.iv_status.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
        }

    }

    override fun getItemCount(): Int {
        return sites.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
}