package com.example.sk_android.utils

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.example.sk_android.R
import kotlinx.android.synthetic.main.person_state.*

class MyDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.person_state)

        state_image.setOnClickListener {
            this.dismiss()
        }

        state_cancel.setOnClickListener {
            this.dismiss()
        }
    }

    fun startPage():TextView{
        var testText= state_required
        return testText
    }
}