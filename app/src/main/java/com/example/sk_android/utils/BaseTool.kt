package com.example.sk_android.utils

import android.text.TextUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import anet.channel.util.Utils.context
import com.example.sk_android.R
import org.jetbrains.anko.support.v4.toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseTool {

    // 获取EditText的值
    fun getEditText(v: EditText) : String{
        val username = v.text.toString().trim()
        return if (TextUtils.isEmpty(username))
            ""
        else
            username
    }

    // 获取TextView的值
    fun getText(v: TextView) : String{
        val result = v.text.toString().trim()
        return if (TextUtils.isEmpty(result))
            ""
        else
            result
    }


    // 字符串转换为date
    fun StrToDateOne(str: String): Date? {

        val format = SimpleDateFormat("yyyy-MM")
        var date: Date? = null
        try {
            date = format.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }


    // 字符串转换为date
    fun StrToDateTwo(str: String): Date? {

        val format = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = format.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    // 日期字符串转换为毫秒时间戳
    fun date2TimeStamp(date: String, format: String): String {
        try {
            val sdf = SimpleDateFormat(format)
            val myDate = StrToDateTwo(date)
            return myDate!!.getTime().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    // 将毫秒时间戳装换为日期字符串
    fun timeStamp2Date(seconds: String?, format: String?): String {
        var format = format
        if (seconds == null || seconds.isEmpty() || seconds == "null") {
            return ""
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        return sdf.format(Date(java.lang.Long.valueOf(seconds + "000")))
    }

    // date转换为字符串
    fun dateToStrLong(timeMillies:Long,format:String):String
    {
        var formatter = SimpleDateFormat(format)
        var date = Date()
        date.time = timeMillies
        var dateString = formatter.format (date)
        return dateString
    }

}