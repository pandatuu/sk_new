package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.company.CompanyWebSiteActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import withTrigger


class AboutUsActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        relativeLayout {
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("私たちについて")
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
                        val version = getLocalVersionName(this@AboutUsActivity)
                        textView {
                            text = "バージョン：v$version"
                            textSize = 14f
                            textColor = Color.parseColor("#FF333333")
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
                        orientation = LinearLayout.VERTICAL
                        linearLayout {
                            backgroundResource = R.drawable.aboutas_bottom_border
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                text = "会社名：株式会社アジアスター "
                                textSize = 13f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(matchParent, wrapContent)
                        }.lparams(matchParent, dip(55)) {
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.aboutas_bottom_border
                            textView {
                                text = "ホームページ：https://astar2020.jp/ "
                                textSize = 13f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(wrapContent, wrapContent) {
                                alignParentLeft()
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.icon_go_position
                            }.lparams(dip(6), dip(11)) {
                                alignParentRight()
                                centerVertically()
                            }
                            this.withTrigger().click {
                                val intent = Intent(context!!, CompanyWebSiteActivity::class.java)
                                intent.putExtra("webUrl", "https://astar2020.jp/")
                                intent.putExtra("companyName", "株式会社アジアスター")
                                startActivity(intent)
                                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(matchParent, dip(55)) {
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.aboutas_bottom_border
                            val phoneNum = "03-6806-0908"
                            textView {
                                text = "電話番号： $phoneNum（対応時間：9:00~18:00）"
                                textSize = 13f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentLeft()
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.icon_go_position
                            }.lparams(dip(6), dip(11)) {
                                alignParentRight()
                                centerVertically()
                            }
                            this.withTrigger().click {
                                val intent = Intent(Intent.ACTION_DIAL)
                                val data = Uri.parse("tel:$phoneNum")
                                intent.data = data
                                startActivity(intent)
                            }
                        }.lparams(matchParent, dip(55)) {
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dip(40)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    backgroundColor = Color.parseColor("#FFFFFFFF")
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.parseColor("#FFFFFFFF")
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
            val packageInfo = ctx.applicationContext
                .packageManager
                .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionName
            Log.d("TAG", "本软件的版本号。。$localVersion")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }
}