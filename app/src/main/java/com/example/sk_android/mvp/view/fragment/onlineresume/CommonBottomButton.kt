package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class CommonBottomButton : Fragment() {

    lateinit var title: String
    var imageUrl: Int = 0
    var color : Int = 0

    companion object {
        fun newInstance(text: String, url: Int, color: Int): CommonBottomButton {
            val fragment = CommonBottomButton()
            fragment.title = text
            fragment.imageUrl = url
            fragment.color = color
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
                            backgroundResource = color
                            gravity = Gravity.CENTER
                            if (imageUrl != 0) {
                                imageView {
                                    imageResource = imageUrl
                                }.lparams {
                                    width = dip(15)
                                    height = dip(15)
                                    centerVertically()
                                }
                            }
                            textView {
                                text = title
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
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
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
}