package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
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
            textBack = feedback!!.processReply
        }
        return UI {
            relativeLayout {
                verticalLayout {
                    relativeLayout {
                        textView {
                            text="内容"
                            textSize = 18f
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
                                    width = matchParent
                                    height = matchParent
                                    leftMargin = dip(10)
                                    rightMargin = dip(8)
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(61)
                    }
                    relativeLayout {
                        textView {
                            text="返事"
                            textSize = 18f

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
                                imageView {
                                    setImageResource(R.drawable.qipao_border)
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                                textView {
                                    text = textBack
                                    textSize = 14f
                                    gravity = Gravity.CENTER_VERTICAL
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                    leftMargin = dip(10)
                                    rightMargin = dip(8)
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(146)
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