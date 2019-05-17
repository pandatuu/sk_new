package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditBackground
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditItem
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeManagementItem
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ResumeEdit : AppCompatActivity() {

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
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "視覚デザイン履歴1"
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
                    textView {
                        text = "プレビュー"
                        textColor = Color.parseColor("#FFFFB706")
                        textSize = 14f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentRight()
                        centerInParent()
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                val resumeListid = 1
                val buttonFrag = 2
                frameLayout {
                    frameLayout {
                        id = buttonFrag
                        var resumebutton = ResumeEditBackground.newInstance()
                        supportFragmentManager.beginTransaction().add(buttonFrag, resumebutton).commit()
                    }
                    frameLayout {
                        id = resumeListid
                        var resumeItem = ResumeEditItem.newInstance()
                        supportFragmentManager.beginTransaction().add(resumeListid, resumeItem).commit()
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}