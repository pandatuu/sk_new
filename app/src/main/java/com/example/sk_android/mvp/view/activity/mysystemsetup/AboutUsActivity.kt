package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import com.example.sk_android.R
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class AboutUsActivity : AppCompatActivity() {


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
                        text = "私たちについて"
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

                verticalLayout {
                    relativeLayout {
                        imageView {
                            backgroundResource = R.mipmap.sk
                        }.lparams {
                            width = wrapContent
                            height = matchParent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(110)
                        topMargin = dip(90)
                    }
                    relativeLayout {
                        textView {
                            text = "SK logo"
                            textSize = 24f
                            textColor = Color.parseColor("#333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(9)
                    }
                    relativeLayout{
                        val version = getLocalVersionName(this@AboutUsActivity)
                        textView {
                            text = "版本：v${version}"
                            textSize = 14f
                            textColor = Color.parseColor("#333333")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(14)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    backgroundColor = Color.TRANSPARENT
                }

            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.TRANSPARENT
            }
        }
    }
    private fun getLocalVersionName(ctx: Context): String {
        var localVersion = ""
        try {
            val packageInfo = ctx.getApplicationContext()
                .getPackageManager()
                .getPackageInfo(ctx.getPackageName(), 0)
            localVersion = packageInfo.versionName
            Log.d("TAG", "本软件的版本号。。$localVersion")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }
}