package com.example.sk_android.mvp.view.fragment.privacyset

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.privacyset.BlackAddCompanyActivity
import com.example.sk_android.mvp.view.activity.privacyset.WhiteAddCompanyActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class WhiteListBottomButton : Fragment() {

    lateinit var mContext : Context
    lateinit var whiteListJump : WhiteListJump

    companion object {
        fun newInstance(context : Context): WhiteListBottomButton {
            val fragment = WhiteListBottomButton()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        whiteListJump = activity as WhiteListJump
        var fragmentView = createView()
        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            linearLayout{
                relativeLayout {
                    relativeLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.icon_add_position
                            }.lparams{
                                width = dip(15)
                                height = dip(15)
                                centerVertically()
                            }
                            textView {
                                text = "フォローアップ会社"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams{
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                            }
                            onClick {
                                whiteListJump.whiteButtonClick()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            setMargins(dip(15),0,dip(15),dip(10))
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(60)
                        alignParentBottom()
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }

    interface WhiteListJump{
        fun whiteButtonClick()
    }
}