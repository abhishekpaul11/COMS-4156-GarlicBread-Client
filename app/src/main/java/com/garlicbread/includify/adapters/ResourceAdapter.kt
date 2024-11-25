package com.garlicbread.includify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.R
import com.garlicbread.includify.models.response.Resource

class ResourceAdapter(private val itemList: List<Resource>) :
    RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.description)
        val usageDesc: TextView = itemView.findViewById(R.id.usage_desc)
        val resTypeDesc: TextView = itemView.findViewById(R.id.res_type_desc)
        val userCatDesc: TextView = itemView.findViewById(R.id.user_cat_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.resource_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.usageDesc.text = item.usageInstructions ?: "No usage instructions provided"
        holder.resTypeDesc.text = item.resourceType.joinToString(", ") { it.title }.ifBlank { "No resource types mentioned" }
        holder.userCatDesc.text = item.targetUserCategory.joinToString(", ") { it.title }.ifBlank { "No target user category mentioned" }
        holder.desc.text = item.description ?: "No description provided"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
