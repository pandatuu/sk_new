package com.example.sk_android.utils

import android.text.TextUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import anet.channel.util.Utils.context
import com.example.sk_android.R
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.jetbrains.anko.support.v4.toast
import java.text.DecimalFormat
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

    fun moneyToString(money:String):MutableMap<String,String>{
        var moneyMap = mutableMapOf(
            "money" to money
        )
        var myMin = "0"
        var min = money.toInt()
        if(min >= 100000000){
            var result = division(min,100000000)
            myMin = result + "亿"
        }

        if(min >= 1000000){
            var result = division(min,1000000)
            myMin = result + "台"
        }

        if(min in 10000..999999){
            var result = division(min,10000)
            myMin = result + "万"
        }

        if (min < 10000){
            myMin = money
        }

        moneyMap.set("result",myMin)

        return moneyMap
    }

    fun division(a: Int, b: Int): String {
        var result = ""
        val num = a.toFloat() / b

        val df = DecimalFormat("0.0")

        result = df.format(num)

        return result

    }

    /**
     * 根据区号判断是否是正确的电话号码
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :默认国家码
     * return ：true 合法  false：不合法
     */
    fun isPhoneNumberValid(phoneNumber: String, countryCode: String): Boolean {

        println("isPhoneNumberValid: $phoneNumber/$countryCode")
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            return phoneUtil.isValidNumber(numberProto)
        } catch (e: NumberParseException) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: " + e.toString())
        }

        return false
    }

}