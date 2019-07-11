package com.example.sk_android.mvp.view.adapter.resume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.activity.resume.ResumeWebSiteActivity
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import withTrigger
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
        var state = mData.get(position).status
        operateImg.setImageResource(R.mipmap.behavior)

        operateImg.withTrigger().click  {
            if(state == 0){
                tool.addList(mData[position])
            }else{
                tool.dResume(id)
            }
        }


        textName.withTrigger().click {
            if(state == 0){
                val intent = Intent(mContext, ResumeWebSiteActivity::class.java)
                var myUrl = mData[position].downloadURL.replace("\"","")
                var webUrl ="https://docs.google.com/viewer?url=$myUrl"
//                val webUrl = "https://view.officeapps.live.com/op/view.aspx?src=$myUrl"
                intent.putExtra("webUrl",webUrl)
                intent.putExtra("resumeName",mData[position].name)
                mContext.startActivity(intent)
            }else{
                tool.dResume(id)
            }
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


