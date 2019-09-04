package com.example.sk_android.mvp.view.fragment.privacyset

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class BlackAddCompanyFrag : Fragment() {

    private lateinit var buttonClickListener : BlackButtonClickListener
    var isTrueNumber = 0

    companion object {
        fun newInstance(index : Int): BlackAddCompanyFrag {
            val fragment = BlackAddCompanyFrag()
            fragment.isTrueNumber = index
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttonClickListener = activity as BlackButtonClickListener

        return createView()
    }

    @SuppressLint("SetTextI18n")
    private fun createView(): View? {
        return UI {
            relativeLayout{
                relativeLayout {
                    relativeLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_grey
                            textView {
                                text = "全てを非選択"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams{
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                                centerInParent()
                            }
                            this.withTrigger().click {
                                buttonClickListener.blackcancelClick(false)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            alignParentTop()
                            setMargins(dip(15),dip(10),dip(15),0)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            textView {
                                text = "選択された会社をブラックリストに追加(${isTrueNumber})"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams{
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                                centerInParent()
                            }
                            onClick {
                                buttonClickListener.blackOkClick()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            alignParentBottom()
                            setMargins(dip(15),0,dip(15),dip(10))
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(125)
                        alignParentBottom()
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }

    /**
     * 设置item的监听事件的接口
     */
    interface BlackButtonClickListener {

        fun blackcancelClick(bool : Boolean)

        suspend fun blackOkClick()
    }
}