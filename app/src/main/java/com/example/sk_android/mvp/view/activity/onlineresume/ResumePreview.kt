package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ResumePreview : AppCompatActivity() {

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
                    imageView {
                        imageResource = R.mipmap.icon_share_zwxq
                    }.lparams {
                        width = dip(20)
                        height = dip(20)
                        alignParentRight()
                        centerVertically()
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
                        var resumebutton = ResumePreviewBackground.newInstance()
                        supportFragmentManager.beginTransaction().add(buttonFrag, resumebutton).commit()
                    }
                    frameLayout {
                        id = resumeListid
                        var resumeItem = ResumePreviewItem.newInstance()
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