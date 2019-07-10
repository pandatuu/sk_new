package com.example.sk_android.mvp.view.fragment.myhelpfeedback

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
import com.example.sk_android.mvp.view.activity.myhelpfeedback.FeedbackSuggestionsActivity
import com.example.sk_android.mvp.view.activity.myhelpfeedback.HelpFeedbackActivity
import com.google.gson.JsonObject
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class HelpAnswerButton : Fragment() {

    lateinit var mContext: Context

    companion object {
        fun newInstance(context: Context): HelpAnswerButton {
            val fragment = HelpAnswerButton()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = createV()
        return view
    }

    private fun createV(): View? {
        return UI {
            linearLayout {
                relativeLayout {
                    verticalLayout {
                        textView {
                            text = "未解决"
                            backgroundResource = R.drawable.button_shape_grey
                            textColor = Color.parseColor("#FFFFFFFF")
                            gravity = Gravity.CENTER
                           this.withTrigger().click {
                                val intent = Intent(mContext, FeedbackSuggestionsActivity::class.java)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            bottomMargin = dip(16)
                        }
                        textView {
                            text = "解決済み"
                            backgroundResource = R.drawable.button_shape_orange
                            textColor = Color.parseColor("#FFFFFFFF")
                            gravity = Gravity.CENTER
                            this.withTrigger().click {
                                val intent = Intent(mContext, HelpFeedbackActivity::class.java)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        centerHorizontally()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(110)
                    gravity = Gravity.BOTTOM
                    bottomPadding = dip(20)
                }
            }
        }.view
    }
}