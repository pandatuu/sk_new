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
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.activity.myhelpfeedback.MyFeedbackContentActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

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

        var view = createV()
        return view
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
                                if (title.length > 10) {
                                    val newtitle = title.substring(0, 10) + "..."
                                    text = newtitle
                                } else {
                                    text = title
                                }
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            textView {
                                text = state
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF999999")
                                textSize = 12f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentRight()
                                centerInParent()
                                rightMargin = dip(30)
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                onClick {
                                    val intent = Intent(mContext, MyFeedbackContentActivity::class.java)
                                    intent.putExtra("id", item.id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }
                            }.lparams {
                                width = dip(30)
                                height = wrapContent
                                alignParentRight()
                                centerVertically()
                            }
                            onClick {
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