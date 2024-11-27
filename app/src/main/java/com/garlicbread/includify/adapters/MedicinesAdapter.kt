package com.garlicbread.includify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.R
import com.garlicbread.includify.models.Medicine


class MedicinesAdapter(private val itemList: List<Medicine>, private val context: Context) :
    RecyclerView.Adapter<MedicinesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val timing: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.medicine_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.name.text = item.name
        holder.desc.text = item.description
        holder.timing.text = item.time
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
