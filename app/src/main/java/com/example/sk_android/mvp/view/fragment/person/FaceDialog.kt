package com.example.sk_android.mvp.view.fragment.person

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.sk_android.R
import kotlinx.android.synthetic.main.cancel_interview.*
import kotlinx.android.synthetic.main.person_state.*

class FaceDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.cancel_interview)

        cancel_button.setOnClickListener {
            this.dismiss()
        }

    }

    fun getEdit():EditText{
        var myEdit = interview_edit
        return myEdit
    }

    fun getButton(): Button {
        var myButton = request_button
        return myButton
    }
}