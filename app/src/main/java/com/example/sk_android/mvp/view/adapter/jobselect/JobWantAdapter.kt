package com.example.sk_android.mvp.view.adapter.jobselect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import java.util.*
import android.R.string
import android.content.Intent
import android.graphics.Paint.Join
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.widget.LinearLayout
import click
import com.dropbox.core.util.StringUtil
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.mvp.view.fragment.jobselect.JlMainBodyFragment
import org.apache.commons.lang.StringUtils
import withTrigger
import kotlin.collections.ArrayList


class JobWantAdapter(mData: ArrayList<UserJobIntention>, mContext: Context?):BaseAdapter() {
    var mData: ArrayList<UserJobIntention> = arrayListOf()
    var mContext:Context
    var test = JlMainBodyFragment.newInstance()

    init{
        this.mData = mData
        this.mContext = mContext!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val wantPosition: TextView
        val wantAddress: TextView
        val wantMoney: TextView
        val wantType: TextView
        var myWant:LinearLayout

        var convertView = LayoutInflater.from(mContext).inflate(R.layout.jobwant_list, parent, false)
        wantPosition = convertView.findViewById(R.id.wantposition)
        wantAddress  = convertView.findViewById(R.id.wantaddress)
        wantMoney  = convertView.findViewById(R.id.wantmoney)
        wantType  = convertView.findViewById(R.id.wanttype)
        myWant = convertView.findViewById(R.id.mywant)


        var address = mData.get(position).areaName
        var myAddress = StringUtils.join(address,"●")
        wantAddress.text = "| "+myAddress

        wantPosition.text = mData.get(position).industryName[0]


        var min = mData.get(position).salaryMin
        var max = mData.get(position).salaryMax
        var myMin = min.toString()
        if(min >= 10000000){
            myMin = (min/10000000).toString()+"台"
        }

        if(min in 1000000..9999999){
            myMin = (min/1000000).toString()+"百万"
        }

        if(min in 100000..999999){
            myMin = (min/10000).toString()+"万"
        }

        if(min in 10000..99999){
            myMin = (min/1000).toString() + "千"
        }

        var myMax = max.toString()
        if(max >= 10000000){
            myMax = (max/10000000).toString()+"台"
        }

        if(max in 1000000..9999999){
            myMax = (max/1000000).toString()+"百万"
        }

        if(max in 100000..999999){
            myMax = (max/10000).toString()+"万"
        }

        if(max in 10000..99999){
            myMax = (max/1000).toString() + "千"
        }
        wantMoney.text = "| "+myMin+"~"+myMax
        var recruitMethod = mData.get(position).recruitMethod
        var recrText = "フルタイム"
        when(recruitMethod){
            "FULL_TIME" -> recrText = "フルタイム"
            "PART_TIME" -> recrText = "パートタイム"
        }
        wantType.text = "| "+recrText

        var result = mData.get(position)

        myWant.withTrigger().click {
            var intent = Intent(mContext, JobWantedEditActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelable("userJobIntention", result)
            bundle.putInt("condition",1)
            intent.putExtra("bundle", bundle)
            mContext.startActivity(intent)

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


