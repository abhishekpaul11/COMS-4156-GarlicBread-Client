package com.garlicbread.includify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.OrganisationDetailsActivity
import com.garlicbread.includify.R
import com.garlicbread.includify.models.response.Organisation
import com.garlicbread.includify.util.Constants

class OrganisationAdapter(private val itemList: List<Organisation>, private val context: Context) :
    RecyclerView.Adapter<OrganisationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.card)
        val title: TextView = itemView.findViewById(R.id.title)
        val address: TextView = itemView.findViewById(R.id.address)
        val email: TextView = itemView.findViewById(R.id.email)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.organisation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.name
        holder.email.text = item.email
        holder.address.text = item.address
        holder.desc.text = item.description ?: "No description provided"

        holder.card.setOnClickListener {
            val newIntent = Intent(context, OrganisationDetailsActivity::class.java)
            newIntent.putExtra(Constants.INTENT_EXTRAS_ORGANISATION_ID, item.id)
            context.startActivity(newIntent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
