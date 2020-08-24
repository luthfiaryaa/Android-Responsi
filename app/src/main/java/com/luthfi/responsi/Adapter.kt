package com.luthfi.responsi

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(private val context: Activity, private val users: ArrayList<User>)
    : ArrayAdapter<User>(context,
    R.layout.user_layout, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.user_layout, null, true)

        val name = rowView.findViewById<TextView>(R.id.userName)
        name.text = users[position].name

        return rowView
    }
}