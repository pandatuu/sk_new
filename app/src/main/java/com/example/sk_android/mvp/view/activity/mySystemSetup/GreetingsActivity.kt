package com.example.sk_android.mvp.view.activity.mySystemSetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.CompoundButton
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MMLoading
import org.jetbrains.anko.*

class GreetingsActivity : AppCompatActivity() {

    private lateinit var mmLoading : MMLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    toolbar {
                        backgroundResource = Color.TRANSPARENT
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams() {
                        width = dip(9)
                        height = dip(15)
                        alignParentLeft()
                        leftMargin = dip(15)
                        topMargin = dip(35)
                    }

                    textView {
                        text = "ご挨拶"
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
        if (isInit()) {
            mmLoading.dismiss()
            val builder = MMLoading.Builder(this@GreetingsActivity)
                .setMessage("提出中")
                .setCancelable(false)
                .setCancelOutside(false)
            mmLoading = builder.create()

        }else{
            val builder = MMLoading.Builder(this@GreetingsActivity)
                .setMessage("提出中")
                .setCancelable(false)
                .setCancelOutside(false)
            mmLoading = builder.create()
        }
        mmLoading.show()
    }
    protected fun hideLoading() {
        if (isInit() && mmLoading.isShowing()) {
            mmLoading.dismiss()
        }
    }
    fun isInit() : Boolean{

        return ::mmLoading.isInitialized
    }
}