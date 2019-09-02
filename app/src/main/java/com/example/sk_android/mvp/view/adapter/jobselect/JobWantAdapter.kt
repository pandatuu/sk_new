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
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint.Join
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.widget.LinearLayout
import click
import com.dropbox.core.util.StringUtil
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedManageActivity
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.mvp.view.fragment.jobselect.JlMainBodyFragment
import org.apache.commons.lang.StringUtils
import org.jetbrains.anko.support.v4.startActivityForResult
import withTrigger
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class JobWantAdapter(
    mData: ArrayList<UserJobIntention>,
    val mFragment: Fragment
):BaseAdapter() {
    var mData: ArrayList<UserJobIntention> = arrayListOf()

    init{
        this.mData = mData
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val wantPosition: TextView
        val wantAddress: TextView
        val wantMoney: TextView
        val wantType: TextView
        var myWant:LinearLayout

        var convertView = LayoutInflater.from(mFragment.context).inflate(R.layout.jobwant_list, parent, false)
        wantPosition = convertView.findViewById(R.id.wantposition)
        wantAddress  = convertView.findViewById(R.id.wantaddress)
        wantMoney  = convertView.findViewById(R.id.wantmoney)
        wantType  = convertView.findViewById(R.id.wanttype)
        myWant = convertView.findViewById(R.id.mywant)


        var address = mData.get(position).areaName
        var myAddress = StringUtils.join(address,"●")
        wantAddress.text = "| $myAddress"

        wantPosition.text =if(mData[position].industryName.size>0){mData[position].industryName[0]}else{""}


        var min = mData[position].salaryMin
        var max = mData[position].salaryMax
        var myMin = min.toString()+'円'

        if(min >= 100000000){
            var result = division(min,100000000)
            myMin = result + "亿円"
        }

        if(min >= 1000000){
            var result = division(min,1000000)
            myMin = result + "台円"
        }

        if(min in 10000..999999){
            var result = division(min,10000)
            myMin = result + "万円"
        }

        var myMax = max.toString()+'円'
        if(max >= 100000000){
            var result = division(max,100000000)
            myMax = result + "亿円"
        }

        if(max >= 1000000){
            var result = division(max,1000000)
            myMax = result + "台円"
        }

        if(max in 10000..999999){
            var result = division(max,10000)
            myMax = result + "万円"
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
            var intent = Intent(mFragment.context, JobWantedEditActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelable("userJobIntention", result)
            bundle.putInt("condition",1)
            intent.putExtra("bundle", bundle)
            mFragment.startActivityForResult(intent,1)
            mFragment.activity!!.overridePendingTransition(R.anim.right_in,R.anim.left_out)
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
        return mData.size
    }


    fun division(a: Int, b: Int): String {
        var result = ""
        val num = a.toFloat() / b

        val df = DecimalFormat("0.0")

        result = df.format(num)

        return result

    }


}


