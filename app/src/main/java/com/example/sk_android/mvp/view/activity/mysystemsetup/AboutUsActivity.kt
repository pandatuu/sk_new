package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import withTrigger


class AboutUsActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("私たちについて");
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                verticalLayout {
                    imageView {
                        backgroundResource = R.mipmap.icon_launcher
                    }.lparams {
                        width = dip(110)
                        height = dip(110)
                        topMargin = dip(90)
                        gravity = Gravity.CENTER_HORIZONTAL
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
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(9)                    }
                    relativeLayout {
                        val version = getLocalVersionName(this@AboutUsActivity)
                        textView {
                            text = "バージョン：v${version}"
                            textSize = 14f
                            textColor = Color.parseColor("#333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(14)
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            text = "電話番号 :"
                            textSize = 16f
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            textColor = Color.parseColor("#333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                        }
                        textView {
                            text = "181-1234-5678"
                            textSize = 14f
                            textColor = Color.BLUE
//                            autoLinkMask =
//                            linksClickable = true
                            this.withTrigger().click {
                                val intent = Intent(Intent.ACTION_DIAL)
                                val data = Uri.parse("tel:$text")
                                intent.data = data
                                startActivity(intent)
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(10)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(20)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams {
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

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@AboutUsActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
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