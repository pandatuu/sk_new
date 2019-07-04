package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class AboutUsActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("私たちについて");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
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
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@AboutUsActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
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