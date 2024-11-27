package com.garlicbread.includify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.garlicbread.includify.AppointmentDetailsActivity
import com.garlicbread.includify.R
import com.garlicbread.includify.models.response.Appointment
import com.garlicbread.includify.util.Constants
import com.garlicbread.includify.util.HelperMethods
import com.garlicbread.includify.util.HelperMethods.Companion.formatTime

class AppointmentAdapter(private val itemList: List<Appointment>, private val context: Context) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.card)
        val organisation: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.organisation.text = item.organisation.name
        holder.date.text = HelperMethods.formatDateString(item.date)
        holder.time.text = "${formatTime(item.timeStart)} to ${formatTime(item.timeEnd)}"

        holder.card.setOnClickListener {
            val newIntent = Intent(context, AppointmentDetailsActivity::class.java)
            newIntent.putExtra(Constants.INTENT_EXTRAS_APPOINTMENT_ID, item.id)
            context.startActivity(newIntent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
