package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeManagementItem
import org.jetbrains.anko.*
import java.util.*

class ResumeManagement : AppCompatActivity() {

    lateinit var resumebutton : CommonBottomButton
    lateinit var resumeItem : ResumeManagementItem
    var resumeList = LinkedList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resumeList.add("視覚デザイン履歴書1")
        resumeList.add("視覚デザイン履歴書2")
        resumeList.add("視覚デザイン履歴書3")

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
                        text = "電子履歴書を編集"
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
                        text = "編集"
                        textColor = Color.parseColor("#FFFFB706")
                        textSize = 14f
                    }.lparams{
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
                        id = resumeListid
                        resumeItem = ResumeManagementItem.newInstance(resumeList)
                        supportFragmentManager.beginTransaction().add(resumeListid,resumeItem).commit()
                    }.lparams{
                        bottomMargin = dip(90)
                    }
                    frameLayout {
                        id = buttonFrag
                        resumebutton = CommonBottomButton.newInstance("履歴書を新規する",R.mipmap.icon_add_position)
                        supportFragmentManager.beginTransaction().add(buttonFrag,resumebutton).commit()
                    }
                }.lparams{
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