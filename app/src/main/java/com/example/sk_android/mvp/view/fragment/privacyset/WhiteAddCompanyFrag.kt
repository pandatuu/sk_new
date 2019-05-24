package com.example.sk_android.mvp.view.fragment.privacyset

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class WhiteAddCompanyFrag() : Fragment() {

    lateinit var buttonClickListener : WhiteButtonClickListener
    var isTrueNumber = 0

    companion object {
        fun newInstance(index : Int): WhiteAddCompanyFrag {
            val fragment = WhiteAddCompanyFrag()
            fragment.isTrueNumber = index
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttonClickListener = activity as WhiteButtonClickListener
        var fragmentView = createView()

        return fragmentView
    }

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
                            onClick {
                                buttonClickListener.whitecancelClick(false)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            alignParentTop()
                            setMargins(dip(15),0,dip(15),0)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            textView {
                                text = "選択した会社に注目する(${isTrueNumber})"
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
                                buttonClickListener.whiteOkClick()
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
    interface WhiteButtonClickListener {

        fun whitecancelClick(bool : Boolean)

        fun whiteOkClick()
    }

}