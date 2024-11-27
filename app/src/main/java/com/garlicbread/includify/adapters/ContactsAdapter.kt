package com.garlicbread.includify.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.R
import com.garlicbread.includify.models.Contact


class ContactsAdapter(private val itemList: List<Contact>, private val context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val relation: TextView = itemView.findViewById(R.id.relation)
        val number: TextView = itemView.findViewById(R.id.number)
        val phoneButton: ImageView = itemView.findViewById(R.id.phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.name.text = item.name
        holder.relation.text = item.relation
        holder.number.text = item.number

        holder.phoneButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:${formatNumber(item.number)}"))
            context.startActivity(intent)
        }
    }

    private fun formatNumber(number: String): String {
        return number.toCharArray().filter { it.isDigit() }.joinToString("")
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
