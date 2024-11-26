package com.garlicbread.includify.components

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat.getColor
import com.garlicbread.includify.R

class DisplayAttribute @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.display_attribute, this, true)
    }

    fun setKey(key: String) {
        findViewById<TextView>(R.id.key).text = key
    }

    fun setValue(
        value: String,
        italics: Boolean = false,
        location: Boolean = false,
        latitude: String = "",
        longitude: String = "",
        markerName: String = "",
        context: Context? = null
    ) {
        val textView = findViewById<TextView>(R.id.`val`)
        textView.text = value

        if (italics) {
            findViewById<TextView>(R.id.`val`).setTypeface(
                findViewById<TextView>(R.id.`val`).typeface, Typeface.ITALIC);
        }

        if (location) {
            findViewById<TextView>(R.id.`val`).setOnClickListener {
                val uri = "http://maps.google.com/maps?z=17&q=$latitude,$longitude(${markerName})"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context!!.startActivity(intent)
            }
            textView.setTextColor(getColor(resources, android.R.color.holo_blue_dark, null))
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }
}
