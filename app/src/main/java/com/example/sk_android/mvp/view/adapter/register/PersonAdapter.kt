package com.example.sk_android.mvp.view.adapter.register

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sk_android.R



class PersonAdapter(mData: ArrayList<String>, mContext: Context?):BaseAdapter() {
    var mDatas: ArrayList<String> = ArrayList()
    var mContext:Context

    init{
        this.mDatas = mData
        this.mContext = mContext!!
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView:TextView
        var convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        textView = convertView.findViewById(R.id.test)
        textView.text = mDatas.get(position)
        return convertView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mDatas.size
    }
}