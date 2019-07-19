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
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.privacyset.BlackAddCompanyActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger

class BlackListBottomButton : Fragment() {

    lateinit var mContext: Context

    companion object {
        fun newInstance(context: Context): BlackListBottomButton {
            val fragment = BlackListBottomButton()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                relativeLayout {
                    relativeLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.icon_add_position
                            }.lparams {
                                width = dip(15)
                                height = dip(15)
                                centerVertically()
                            }
                            textView {
                                text = "会社を追加する"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                            }
                            this.withTrigger().click {
                                val intent = Intent(activity, BlackAddCompanyActivity::class.java)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(50)
                            setMargins(dip(15), 0, dip(15), dip(10))
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(60)
                        alignParentBottom()
                        topMargin = dip(5)
                        bottomMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
}