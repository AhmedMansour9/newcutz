package com.cairocart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cairocart.Model.Cities_Response
import com.cairocart.R

class StatesAdapter (
    val applicationContext: Context,
    val data: List<Cities_Response.Data>
) : BaseAdapter() {
    lateinit var list: List<Cities_Response.Data>
    lateinit var inflter: LayoutInflater
    init {
        this.inflter = LayoutInflater.from(applicationContext)
    }

    override fun getCount(): Int {

        return  data.size

    }

    override fun getItem(position: Int): String? {
        return data.get(position).city
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.inflter.inflate(R.layout.custom_spinner_items, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = data.get(position).city
        return view
    }

    private class ListRowHolder(row: View?) {
        val label: TextView
        init {
            this.label = row?.findViewById(R.id.T_City) as TextView
        }
    }
}