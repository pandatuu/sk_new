package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.CompoundButton
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class GreetingsActivity : AppCompatActivity() {

    private lateinit var myDialog : MyDialog

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
                        text = "ご挨拶"
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
                    textView{
                        text = "「ご挨拶を」"
                        textSize = 13f
                        textColor = Color.parseColor("#5C5C5C")
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams{
                        alignParentLeft()
                        centerVertically()
                    }
                    switch {
                        setThumbResource(R.drawable.thumb)
                        setTrackResource(R.drawable.track)
                        isChecked = true
                    }.lparams{
                        alignParentRight()
                        centerVertically()
                    }
                }.lparams{
                    width = matchParent
                    height = dip(55)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
                relativeLayout {
                    backgroundColor = Color.parseColor("#F6F6F6")
                }.lparams{
                    width = matchParent
                    height = dip(8)
                }
                verticalLayout {
                    relativeLayout {
                        radioGroup {
                            radioButton {
                                backgroundResource = R.drawable.text_view_bottom_border
                                buttonDrawableResource = R.mipmap.oval

                                setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                        if(isChecked){
                                            buttonDrawableResource = R.mipmap.hook
                                            showNormalDialog()
                                        }else{
                                            buttonDrawableResource = R.mipmap.oval
                                        }
                                    }
                                })
                                leftPadding = dip(10)
                                text = "こんにちは、贵社に兴味を持っています"
                                textSize = 16f
                                textColor = Color.parseColor("#202020")

                            }.lparams{
                                width = matchParent
                                height = dip(62)
                            }
                            radioButton {
                                backgroundResource = R.drawable.text_view_bottom_border
                                buttonDrawableResource = R.mipmap.oval
                                setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                        if(isChecked){
                                            buttonDrawableResource = R.mipmap.hook
                                            showNormalDialog()
                                        }else{
                                            buttonDrawableResource = R.mipmap.oval
                                        }
                                    }
                                })
                                leftPadding = dip(10)
                                text = "こんにちは"
                                textSize = 16f
                                textColor = Color.parseColor("#202020")
                            }.lparams{
                                width = matchParent
                                height = dip(62)
                            }
                            radioButton {
                                backgroundResource = R.drawable.text_view_bottom_border
                                buttonDrawableResource = R.mipmap.oval
                                setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                        if(isChecked){
                                            buttonDrawableResource = R.mipmap.hook
                                            showNormalDialog()
                                        }else{
                                            buttonDrawableResource = R.mipmap.oval
                                        }
                                    }
                                })
                                leftPadding = dip(10)
                                text = "御社の制品マネージャーの职はまだ空い ていますか。？個人的には興味がありま す"
                                textSize = 16f
                                textColor = Color.parseColor("#202020")
                            }.lparams{
                                width = matchParent
                                height = dip(62)
                            }
                        }
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }.lparams{
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    fun showNormalDialog(){
        showLoading()
        //延迟3秒关闭
        Handler().postDelayed({ hideLoading() }, 3000)
    }

    protected fun showLoading() {
        val builder = MyDialog.Builder(this@GreetingsActivity)
            .setMessage("提出中")
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        myDialog.show()
    }
    protected fun hideLoading() {
        if (isInit() && myDialog.isShowing()) {
            myDialog.dismiss()
        }
    }
    fun isInit() : Boolean{

        return ::myDialog.isInitialized
    }
}