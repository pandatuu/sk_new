package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MMLoading
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick



class SystemSetupActivity : AppCompatActivity() {

    private lateinit var mmLoading : MMLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = dip(9)
                        height = dip(15)
                        alignParentBottom()
                        leftMargin = dip(15)
                        bottomMargin =  dip(11)
                    }

                    textView {
                        text = "設定"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = dip(147)
                        height = dip(23)
                        alignParentBottom()
                        centerHorizontally()
                        bottomMargin =  dip(11)
                    }
                }.lparams{
                    width = matchParent
                    height = dip(64)
                }

                relativeLayout {
                    verticalLayout {
                        //通知設定
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "通知設定"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //ご挨拶を編集
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "ご挨拶を編集"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //電話番号変更
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "電話番号変更"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //パスワード変更
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "パスワード変更"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //版本更新
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "版本更新"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.new_icon
                                textView {
                                    text = "New"
                                    textSize =  10f
                                    textColor = Color.parseColor("#FFFFFF")
                                }.lparams{
                                    setMargins(dip(4),dip(1),dip(4),dip(1))
                                }
                            }.lparams{
                                width = dip(29)
                                height = dip(16)
                                leftMargin = dip(64)
                                centerVertically()
                            }
                            textView{
                                text = "バージョン1.0.1"
                                textColor = Color.parseColor("#B3B3B3")
                                textSize = 12f
                            }.lparams{
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(16)
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                            onClick {
                                showNormalDialog()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //私たちについて
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "私たちについて"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }
                }.lparams{
                    width = matchParent
                    height = dip(332)
                }

                relativeLayout{
                    relativeLayout{
                        button{
                            backgroundResource = R.drawable.button_shape_blue
                            text = "登録をログアウトする"
                            textSize = 16f
                            textColor = Color.parseColor("#FFFFFF")
                            onClick {
                                showLogoutDialog()
                            }
                        }.lparams{
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(57)
                        leftPadding = dip(15)
                        rightPadding = dip(15)
                        bottomMargin = dip(10)
                        alignParentBottom()
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(this@SystemSetupActivity)
        val view = inflater.inflate(R.layout.update_tips, null)
        val mmLoading2 = MMLoading(this@SystemSetupActivity, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        mmLoading.setCancelable(false)
        mmLoading = mmLoading2
        mmLoading.show()
        var cancelBtn = view.findViewById<Button>(R.id.update_cancel)
        var determineBtn = view.findViewById<Button>(R.id.update_determine)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mmLoading.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mmLoading.dismiss()
            }
        })
    }

    fun showNormalDialog(){
        showLoading()
        //延迟3秒关闭
        Handler().postDelayed({ hideLoading(); afterShowLoading() }, 3000)
    }

    //弹出等待转圈窗口
    protected fun showLoading() {
        if (isInit()) {
            mmLoading.dismiss()
            val builder = MMLoading.Builder(this@SystemSetupActivity)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            mmLoading = builder.create()

        }else{
            val builder = MMLoading.Builder(this@SystemSetupActivity)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            mmLoading = builder.create()
        }
        mmLoading.show()
    }
    //关闭等待转圈窗口
    protected fun hideLoading() {
        if (isInit() && mmLoading.isShowing()) {
            mmLoading.dismiss()
        }
    }
    //判断mmloading是否初始化,因为lainit修饰的变量,不能直接判断为null,要先判断初始化
    fun isInit() : Boolean{
        return ::mmLoading.isInitialized
    }

    fun showLogoutDialog(){
        val inflater = LayoutInflater.from(this@SystemSetupActivity)
        val view = inflater.inflate(R.layout.logout, null)
        val mmLoading2 = MMLoading(this@SystemSetupActivity, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        mmLoading2.setCancelable(false)
        mmLoading = mmLoading2
        mmLoading.show()
        var cancelBtn = view.findViewById<TextView>(R.id.logout_cancel)
        var determineBtn = view.findViewById<TextView>(R.id.logout_determine)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mmLoading.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mmLoading.dismiss()
            }
        })
    }

}