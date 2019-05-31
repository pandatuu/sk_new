package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class JobSearchStrategyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            finish()
                        }
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "求職攻略"
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
                    verticalLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "チュートリアル"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                onClick {
                                    val intent = Intent(this@JobSearchStrategyActivity, HowModifyPasswordActivity::class.java)
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = dip(20)
                                height = dip(20)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "攻略"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                onClick {
                                    val intent = Intent(this@JobSearchStrategyActivity, HowModifyPasswordActivity::class.java)
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = dip(20)
                                height = dip(20)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "アクティビティ"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                onClick {
                                    val intent = Intent(this@JobSearchStrategyActivity, HowModifyPasswordActivity::class.java)
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = dip(20)
                                height = dip(20)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams{
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}