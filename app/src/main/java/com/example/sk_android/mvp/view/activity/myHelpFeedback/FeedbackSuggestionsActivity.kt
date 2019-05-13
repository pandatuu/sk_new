package com.example.sk_android.mvp.view.activity.myHelpFeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FeedbackSuggestionsActivity : AppCompatActivity() {
    lateinit var textv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "フィードバックとアドバイス"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }
                relativeLayout {
                    var edit=editText {
                        backgroundResource = R.drawable.area_text
                        backgroundColor = Color.parseColor("#F6F6F6")
                        hint = "内容をここで書いてください"
                        gravity= top
                    }.lparams{
                        width = matchParent
                        height = dip(300)
                        topMargin = dip(20)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    relativeLayout{

                        relativeLayout {
                            textv=textView {
                                text = "0/100"
                                textSize = 12f
                                textColor = Color.parseColor("#FF898989")
                            }
                        }.lparams{
                            width = dip(62)
                            height = wrapContent
                            alignParentRight()
                            rightMargin = dip(15)
                            topMargin = dip(10)
                        }
                        edit.addTextChangedListener(object : TextWatcher{
                            override fun afterTextChanged(s: Editable?) {}
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                println("onText---"+edit.text)
                                textv.text=edit.text.length.toString()+"/100"
                            }
                        })
                    }.lparams{
                        width = matchParent
                        height = dip(27)
                        topMargin = dip(320)
                        alignParentRight()
                    }
                    textView {
                        text = "提出"
                        textSize = 16f
                        gravity = Gravity.CENTER
                        backgroundResource = R.drawable.button_shape_orange
                        onClick {
                            if(edit.text.length<101){
                                toast(edit.text)
                            }else{
                                toast("字数超出上限")
                            }
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(45)
                        setMargins(dip(15),0,dip(15),30)
                        alignParentBottom()
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }
}