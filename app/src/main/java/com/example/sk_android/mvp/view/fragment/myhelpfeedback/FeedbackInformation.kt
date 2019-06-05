package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class FeedbackInformation :Fragment() {

    var feedback : FeedbackModel? = null
    lateinit var mContext: Context
    var content = ""
    var textBack = ""

    companion object {
        fun newInstance(model: FeedbackModel?, context: Context): FeedbackInformation {
            val fragment = FeedbackInformation()
            fragment.mContext = context
            fragment.feedback = model
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = createV()
        return view
    }

    private fun createV(): View? {
        if(feedback!=null){
            content = feedback!!.content
            textBack = if (feedback!!.processReply==null) "未返事" else feedback!!.processReply
            println(textBack)
        }
        return UI {
            relativeLayout {
                verticalLayout {
                    relativeLayout {
                        textView {
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            text="内容"
                            textSize = 18f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams{
                            alignParentLeft()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(25)
                        topMargin = dip(20)
                    }
                    relativeLayout {
                        verticalLayout {
                            imageView {
                                setImageResource(R.drawable.qipao)
                            }.lparams {
                                width = wrapContent
                                height = dip(16)
                                leftMargin = dip(5)
                                topPadding = dip(-5)
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.qipao_border
                                textView {
                                    text = content
                                    textSize = 14f
                                    gravity = Gravity.CENTER_VERTICAL
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    setMargins(dip(10),dip(10),dip(8),dip(10))
                                }
                            }.lparams{
                                width = matchParent
                                height = wrapContent
                            }
                        }.lparams{
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        minimumHeight = dip(61)
                    }
                    relativeLayout {
                        textView {
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            text="返事"
                            textSize = 18f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams{
                            alignParentLeft()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(25)
                        topMargin = dip(20)
                    }
                    relativeLayout {
                        verticalLayout {
                            imageView {
                                setImageResource(R.drawable.qipao)
                            }.lparams {
                                width = wrapContent
                                height = dip(16)
                                leftMargin = dip(5)
                                topPadding = dip(-5)
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.qipao_border
                                textView {
                                    text = textBack
                                    textSize = 14f
                                    gravity = Gravity.CENTER_VERTICAL
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    setMargins(dip(10),dip(10),dip(8),dip(10))
                                }
                            }.lparams{
                                width = matchParent
                                height = wrapContent
                            }
                        }.lparams{
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }
}