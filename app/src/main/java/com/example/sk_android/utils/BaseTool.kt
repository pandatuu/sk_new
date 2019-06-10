package com.example.sk_android.utils

import android.text.TextUtils
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
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

    // 统一对EditText未赋值进行处理
//    fun treatEditText(v:EditText){
//        val result = v.text.toString().trim()
//        if(result == ""){
//            (v.parent as LinearLayout).setBackgroundResource(R.drawable.edit_text_empty)
//        }
//    }

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


}