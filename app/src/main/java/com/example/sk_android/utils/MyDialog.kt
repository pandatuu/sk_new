package com.example.sk_android.utils

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import click
import com.example.sk_android.R
import kotlinx.android.synthetic.main.person_state.*
import withTrigger

class MyDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.person_state)

        state_image.withTrigger().click {
            this.dismiss()
        }

        state_cancel.withTrigger().click {
            this.dismiss()
        }
    }

    fun startPage():TextView{
        var testText= state_required
        return testText
    }
}