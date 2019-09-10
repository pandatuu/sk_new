package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.activity.myhelpfeedback.MyFeedbackContentActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger

class FeedbackInformationList : Fragment() {

    var mList = mutableListOf<FeedbackModel>()
    lateinit var mContext: Context

    companion object {
        fun newInstance(list: MutableList<FeedbackModel>, context: Context): FeedbackInformationList {
            val fragment = FeedbackInformationList()
            fragment.mContext = context
            fragment.mList = list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return createV()
    }

    private fun createV(): View? {
        println(mList.size)

        return UI {
            scrollView {
                verticalLayout {
                    for (item in mList) {
                        val title = item.content
                        val state = item.processState
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = if (title.length > 10) {
                                    val newtitle = title.substring(0, 10) + "..."
                                    newtitle
                                } else {
                                    title
                                }
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            textView {
                                text = if("PENDING" == state) "未返信" else "返信済み"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF999999")
                                textSize = 12f
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }.lparams {
                                alignParentRight()
                                centerInParent()
                                rightMargin = dip(30)
                            }
                            imageView {
                                imageResource = R.mipmap.icon_go_position
                               this.withTrigger().click {
                                    val intent = Intent(mContext, MyFeedbackContentActivity::class.java)
                                    intent.putExtra("id", item.id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }
                            }.lparams {
                                width = dip(6)
                                height = dip(11)
                                alignParentRight()
                                centerVertically()
                            }
                           this.withTrigger().click {
                                val intent = Intent(mContext, MyFeedbackContentActivity::class.java)
                                intent.putExtra("id", item.id)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                    }
                }
            }
        }.view
    }


}