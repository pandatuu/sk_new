package com.example.sk_android.mvp.view.activity.privacySet

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.CompoundButton
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CauseChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "原因の選択"
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
                    var group = radioGroup {
                        //仕事が見つかりました
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "仕事が見つかりました"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //しばらくは仕事を探したくないです。
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "しばらくは仕事を探したくないです。"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //資料を暴露するのは嫌いです。
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "資料を暴露するのは嫌いです。"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //いつも挨拶をする人がいます。面倒くさいです。
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "いつも挨拶をする人がいます。面倒くさいです。"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //その他
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "その他"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    textView {
                        backgroundResource = R.drawable.button_shape_orange
                        text = "完了"
                        textSize = 16f
                        textColor = Color.parseColor("#FFFFFFFF")
                        gravity = Gravity.CENTER
                        onClick {
                            group.clearCheck()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        alignParentBottom()
                        setMargins(dip(15),0,dip(15),0)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomPadding = dip(20)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}