package com.example.sk_android.mvp.view.adapter.resume

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import java.util.*

class ResumeAdapter(mData: LinkedList<Resume>, mContext: Context?,tool:RlMainBodyFragment.Tool):BaseAdapter() {
    var mData: LinkedList<Resume> = LinkedList()
    var mContext:Context
    var tool: RlMainBodyFragment.Tool

    init{
        this.mData = mData
        this.mContext = mContext!!
        this.tool = tool
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textName: TextView
        val fileImg: ImageView
        val textSize: TextView
        val textDate: TextView
        val operateImg: ImageView
        var convertView = LayoutInflater.from(mContext).inflate(R.layout.resume_list, parent, false)
        textName = convertView.findViewById(R.id.text_Name)
        fileImg  = convertView.findViewById(R.id.file_img)
        textSize  = convertView.findViewById(R.id.text_size)
        textDate  = convertView.findViewById(R.id.text_date)
        operateImg  = convertView.findViewById(R.id.operate_img)
        var format = mData.get(position).url
        var imageUrl = R.mipmap.word
        if (format == "word"){
            imageUrl = R.mipmap.word
        }
        if (format == "pdf"){
            imageUrl = R.mipmap.pdf
        }
        if (format == "jpg"){
            imageUrl = R.mipmap.jpg
        }
        fileImg.setImageResource(imageUrl)
        mData[position].imageUrl = imageUrl

        textName.text = mData.get(position).name
        textSize.text = mData.get(position).size
        textDate.text= mData.get(position).updateData
        var id= mData.get(position).id
        operateImg.setImageResource(R.mipmap.behavior)

        operateImg.setOnClickListener {
            tool.addList(mData[position])
        }



        return convertView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData!!.size
    }
}


