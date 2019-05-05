package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class FeedbackSuggestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    toolbar {
                        backgroundResource = Color.TRANSPARENT
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams() {
                        width = dip(9)
                        height = dip(15)
                        alignParentLeft()
                        leftMargin = dip(15)
                        topMargin = dip(35)
                    }


                    textView {
                        text = "フィードバックとアドバイス"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams {
                        width = dip(213)
                        height = dip(23)
                        centerHorizontally()
                        bottomMargin = dip(11)
                        topMargin = dip(31)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(64)
                }
                relativeLayout {
                    editText {
                        backgroundResource = R.drawable.area_text
                        backgroundColor = Color.parseColor("#F6F6F6")
                        hint = "内容をここで書いてください"
                    }.lparams{
                        width = dip(345)
                        height = dip(300)
                        centerHorizontally()
                        topMargin = dip(20)
                    }
                }
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }
}