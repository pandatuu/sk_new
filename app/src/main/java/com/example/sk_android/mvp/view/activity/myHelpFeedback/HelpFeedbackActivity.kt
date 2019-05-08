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
import android.view.View
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.HelpDeedbackAdapter
import com.example.sk_android.mvp.view.fragment.HelpDeedbackFragment
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class HelpFeedbackActivity : AppCompatActivity() {

    private lateinit var recycle : RecyclerView
    lateinit var fragment1 : HelpDeedbackFragment

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var list = LinkedList<Array<String>>()
        list.add(arrayOf("求職攻略","チュートリアル","攻略","アクティビティ"))
        list.add(arrayOf("認証フロー","チュートリアル1","攻略1","アクティビティ1"))
        list.add(arrayOf("規則違反","チュートリアル2","攻略2","アクティビティ2"))
        list.add(arrayOf("規則違反","チュートリアル3","攻略3","アクティビティ3"))


        relativeLayout {
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    toolbar {
                        backgroundResource=Color.TRANSPARENT
                        isEnabled = true
                        title = "111"
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams() {
                        width = dip(9)
                        height = dip(15)
                        alignParentLeft()
                        leftMargin = dip(15)
                        topMargin = dip(35)
                    }


                    textView {
                        text = "よくある質問"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams {
                        width = dip(98)
                        height = dip(23)
                        centerHorizontally()
                        bottomMargin = dip(11)
                        topMargin = dip(31)
                    }

                }.lparams {
                    width = matchParent
                    height = dip(64)
                }

                scrollView {
                    relativeLayout {
                        recycle = recyclerView {
                            backgroundColor = Color.WHITE
                            layoutManager = LinearLayoutManager(this@HelpFeedbackActivity)
                        }
                        recycle.adapter = HelpDeedbackAdapter(list)
                    }

                }.lparams{
                    topMargin = dip(64)
                }

                relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "私のフィードバック"
                                backgroundResource = R.drawable.button_shape
                                textColor = Color.parseColor("#02B8F7")
                                gravity = Gravity.CENTER
                                onClick {
                                    toast("私のフィードバック")
                                }
                            }.lparams {
                                width = dip(345)
                                height = dip(47)
                                bottomMargin = dip(10)
                            }
                            textView {
                                backgroundResource = R.drawable.button_shape_blue
                                text = "フィードバックとアドバイス"
                                backgroundColor = Color.parseColor("#02B8F7")
                                textColor = Color.WHITE
                                gravity = Gravity.CENTER
                                onClick {
                                    toast("フィードバックとアドバイス")
                                }
                            }.lparams {
                                width = dip(345)
                                height = dip(47)
                                alignParentBottom()
                            }
                        }
                    }.lparams{
                        centerHorizontally()
                        bottomMargin = dip(10)
                    }
                }.lparams{
                    width = matchParent
                    height = dip(114)
                    alignParentBottom()
                }
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }
}