package com.example.sk_android.mvp.view.activity.myHelpFeedback

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.HelpDeedbackAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class HelpFeedbackActivity : AppCompatActivity() {

    private lateinit var recycle : RecyclerView

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var list = LinkedList<Array<String>>()
        list.add(arrayOf("求職攻略","チュートリアル","攻略","アクティビティ"))
        list.add(arrayOf("認証フロー","チュートリアル1","攻略1","アクティビティ1"))
        list.add(arrayOf("規則違反","チュートリアル2","攻略2","アクティビティ2"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))

        relativeLayout {
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "よくある質問"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }
                relativeLayout {
                    scrollView {
                        relativeLayout {
                            recycle = recyclerView {
                                layoutManager = LinearLayoutManager(this@HelpFeedbackActivity) as RecyclerView.LayoutManager?
                            }
                            recycle.adapter = HelpDeedbackAdapter(list)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(120)
                    topMargin = dip(54)
                }
                relativeLayout {
                    verticalLayout {
                    textView {
                        text = "私のフィードバック"
                        backgroundResource = R.drawable.button_shape
                        textColor = Color.parseColor("#FF202020")
                        gravity = Gravity.CENTER
                        onClick {
                            toast("私のフィードバック")
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(47)
                        bottomMargin = dip(10)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    textView {
                        backgroundResource = R.drawable.button_shape_orange
                        text = "フィードバックとアドバイス"
                        textColor = Color.WHITE
                        gravity = Gravity.CENTER
                        onClick {
                            toast("フィードバックとアドバイス")
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(47)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                    }
                }.lparams{
                    width = matchParent
                    height = dip(114)
                    alignParentBottom()
                }
            }.lparams{
                width = matchParent
                height = matchParent
                backgroundColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}