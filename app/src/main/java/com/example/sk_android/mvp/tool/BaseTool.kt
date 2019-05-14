package com.example.sk_android.mvp.tool

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.EditText

open class BaseTool {

    fun getEditText(v: EditText) : String{
        val username = v.text.toString().trim()
        return if (TextUtils.isEmpty(username))
            ""
        else
            username
    }


}