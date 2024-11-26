package com.garlicbread.includify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.R
import com.garlicbread.includify.ResourceDetailsActivity
import com.garlicbread.includify.models.response.Organisation
import com.garlicbread.includify.models.response.Resource
import com.garlicbread.includify.util.Constants

class ResourceAdapter(private val itemList: List<Resource>, private val context: Context, private val organisation: Organisation) :
    RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.description)
        val usageDesc: TextView = itemView.findViewById(R.id.usage_desc)
        val resTypeDesc: TextView = itemView.findViewById(R.id.res_type_desc)
        val userCatDesc: TextView = itemView.findViewById(R.id.user_cat_desc)
        val card: CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.resource_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.usageDesc.text = item.usageInstructions ?: context.resources.getText(R.string.no_usage_instr)
        holder.resTypeDesc.text = item.resourceType.joinToString(", ") { it.title }.ifBlank { context.resources.getText(R.string.no_res_type) }
        holder.userCatDesc.text = item.targetUserCategory.joinToString(", ") { it.title }.ifBlank { context.resources.getText(R.string.no_target_user_cat) }
        holder.desc.text = item.description ?: context.resources.getText(R.string.includify)

        holder.card.setOnClickListener {
            val newIntent = Intent(context, ResourceDetailsActivity::class.java)
            newIntent.putExtra(Constants.INTENT_EXTRAS_ORGANISATION_NAME, organisation.name)
            newIntent.putExtra(Constants.INTENT_EXTRAS_ORGANISATION_EMAIL, organisation.email)
            newIntent.putExtra(Constants.INTENT_EXTRAS_ORGANISATION_ADDRESS, organisation.address)
            newIntent.putExtra(Constants.INTENT_EXTRAS_ORGANISATION_DESC, organisation.description)
            newIntent.putExtra(Constants.INTENT_EXTRAS_RESOURCE_ID, item.id)
            context.startActivity(newIntent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
