package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditItem
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumePreviewBackground
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class ResumeEdit : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bottomBeha =  BottomSheetBehavior<View>(this@ResumeEdit,null)
        bottomBeha.setPeekHeight(dip(370))

        linearLayout {
            coordinatorLayout {
                appBarLayout {
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
                    }.lparams(matchParent, matchParent){
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }


                val back = 1
                frameLayout {
                    id = back
                    var resumeItem = ResumePreviewBackground.newInstance()
                    supportFragmentManager.beginTransaction().add(back, resumeItem).commit()
                }.lparams(matchParent, dip(370)){
                    topMargin = dip(54)
                }
                val resumeListid = 2
                nestedScrollView {
                    id = resumeListid
                    backgroundResource = R.drawable.twenty_three_radius_top
                    var resumeItem = ResumeEditItem.newInstance()
                    supportFragmentManager.beginTransaction().add(resumeListid, resumeItem).commit()
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    behavior = bottomBeha
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}