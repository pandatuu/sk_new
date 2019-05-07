package com.example.sk_android.mvp.tool

import android.text.TextUtils
import android.widget.EditText

class BaseTool {

    fun getEditText(v: EditText) : String{
        val username = v.text.toString().trim()
        return if (TextUtils.isEmpty(username))
            ""
        else
            username
    }

}