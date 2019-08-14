package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.model.mysystemsetup.Version
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.mysystemsetup.LoginOutFrag
import com.example.sk_android.mvp.view.fragment.mysystemsetup.UpdateTipsFrag
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import retrofit2.HttpException
import withTrigger


class SystemSetupActivity : AppCompatActivity(), ShadowFragment.ShadowClick, UpdateTipsFrag.ButtomCLick,
    LoginOutFrag.TextViewCLick {

    //登出取消按钮
    override fun cancelLogClick() {
        closeAlertDialog()
    }

    //登出确定按钮
    override suspend fun chooseClick() {
        closeAlertDialog()
        DialogUtils.showLoading(this@SystemSetupActivity)
        //dengchu
        try {
            val retrofitUils = RetrofitUtils(this@SystemSetupActivity, "https://auth.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .logout("MOBILE")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val mEditor: SharedPreferences.Editor =
                    PreferenceManager.getDefaultSharedPreferences(this@SystemSetupActivity).edit()

                var result = PreferenceManager.getDefaultSharedPreferences(this@SystemSetupActivity)
                val phone = result.getString("phone","")
                val password = result.getString("password","")
                val country = result.getString("country","")
                var newEditor = result.edit()
//                mEditor.putString("token", "")
//                mEditor.putString("id", "")
//                mEditor.putString("avatarURL", "")
//                mEditor.putString("name","")
//                mEditor.putString("phone","")
//                mEditor.putString("country","")
                mEditor.clear()
                mEditor.commit()

                newEditor.putString("phone",phone)
                newEditor.putString("password",password)
                newEditor.putString("country",country)
                newEditor.commit()


                val intent = Intent(this@SystemSetupActivity, LoginActivity::class.java)
                intent.putExtra("condition", 1)
                startActivity(intent)
                if(fatherActivity!=null)
                    fatherActivity!!.finish()
                finish()
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    var mainId = 1
    var shadowFragment: ShadowFragment? = null
    var logoutFragment: LoginOutFrag? = null
    var updateTips: UpdateTipsFrag? = null
    var userInformation: UserSystemSetup? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    lateinit var newVersion: RelativeLayout
    private lateinit var dialogLoading: FrameLayout
    lateinit var versionModel: Version
    var versionBool = false

    companion object {
        var fatherActivity: Activity?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();


        frameLayout {
            id = mainId
            verticalLayout {
                verticalLayout {
                    val actionBarId = 3
                    frameLayout {
                        id = actionBarId
                        actionBarNormalFragment = ActionBarNormalFragment.newInstance("設定")
                        supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                    }.lparams {
                        height = wrapContent
                        width = matchParent
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
                                }.lparams {
                                    alignParentLeft()
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position

                                    this.withTrigger().click {
                                        // 给bnt1添加点击响应事件
                                        val intent =
                                            Intent(this@SystemSetupActivity, NotificationSettingsActivity::class.java)
                                        //启动
                                        startActivity(intent)
                                    }
                                }.lparams {
                                    alignParentRight()
                                    width = dip(6)
                                    height = dip(11)
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    // 给bnt1添加点击响应事件
                                    val intent =
                                        Intent(this@SystemSetupActivity, NotificationSettingsActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(55)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }
                            //ご挨拶を編集
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                isEnabled = true
                                textView {
                                    text = "ご挨拶を編集"
                                    textSize = 13f
                                    textColor = Color.parseColor("#5C5C5C")
                                }.lparams {
                                    alignParentLeft()
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    this.withTrigger().click {
                                        // 给bnt1添加点击响应事件
                                        val intent = Intent(this@SystemSetupActivity, GreetingsActivity::class.java)
                                        //启动
                                        startActivity(intent)
                                    }
                                }.lparams {
                                    alignParentRight()
                                    width = dip(6)
                                    height = dip(11)
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    // 给bnt1添加点击响应事件
                                    val intent = Intent(this@SystemSetupActivity, GreetingsActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
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
                                }.lparams {
                                    alignParentLeft()
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    isEnabled = true
                                    this.withTrigger().click {
                                        // 给bnt1添加点击响应事件
                                        val intent =
                                            Intent(this@SystemSetupActivity, BindPhoneNumberActivity::class.java)
                                        //启动
                                        startActivity(intent)
                                    }
                                }.lparams {
                                    alignParentRight()
                                    width = dip(6)
                                    height = dip(11)
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    // 给bnt1添加点击响应事件
                                    val intent = Intent(this@SystemSetupActivity, BindPhoneNumberActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
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
                                }.lparams {
                                    alignParentLeft()
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    isEnabled = true
                                    this.withTrigger().click {
                                        // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                        val intent =
                                            Intent(this@SystemSetupActivity, UpdatePasswordActivity::class.java)
                                        //启动
                                        startActivity(intent)
                                    }
                                }.lparams {
                                    alignParentRight()
                                    width = dip(6)
                                    height = dip(11)
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                    val intent = Intent(this@SystemSetupActivity, UpdatePasswordActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(55)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }
                            //版本更新
                            linearLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                textView {
                                    text = "アップデート"
                                    textSize = 13f
                                    textColor = Color.parseColor("#5C5C5C")
                                }
                                relativeLayout {
                                    gravity = Gravity.CENTER
                                    dialogLoading = frameLayout {
                                        val image = imageView {}.lparams(dip(30), dip(40))
                                        Glide.with(this@relativeLayout)
                                            .load(R.mipmap.turn_around)
                                            .into(image)
                                    }
                                    newVersion = relativeLayout {
                                        backgroundResource = R.drawable.new_icon
                                        visibility = LinearLayout.GONE
                                        textView {
                                            backgroundResource = R.drawable.new_icon
                                            text = "New"
                                            textSize = 10f
                                            textColor = Color.parseColor("#FFFFFF")
                                        }.lparams(wrapContent, wrapContent){
                                            setMargins(dip(4), dip(1), dip(4), dip(1))
                                        }
                                    }.lparams(wrapContent, wrapContent)
                                }.lparams {
                                    width = wrapContent
                                    height = matchParent
                                    leftMargin = dip(5)
                                }
                                val version = getLocalVersionName(this@SystemSetupActivity)
                                relativeLayout {
                                    textView {
                                        text = "v$version"
                                        textColor = Color.parseColor("#B3B3B3")
                                        textSize = 12f
                                    }.lparams(wrapContent, wrapContent) {
                                        alignParentRight()
                                        centerVertically()
                                        rightMargin = dip(36)
                                    }
                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        isEnabled = true
                                        this.withTrigger().click {
                                            opendialog()
                                        }
                                    }.lparams {
                                        alignParentRight()
                                        centerVertically()
                                        width = dip(6)
                                        height = dip(11)
                                    }
                                }.lparams(matchParent, matchParent)
                                this.withTrigger().click {
                                    opendialog()
                                }
                            }.lparams {
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
                                }.lparams {
                                    alignParentLeft()
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    isEnabled = true
                                    this.withTrigger().click {
                                        // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                        val intent = Intent(this@SystemSetupActivity, AboutUsActivity::class.java)
                                        //启动
                                        startActivity(intent)
                                    }
                                }.lparams {
                                    alignParentRight()
                                    width = dip(6)
                                    height = dip(11)
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                    val intent = Intent(this@SystemSetupActivity, AboutUsActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(55)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(332)
                    }

                    relativeLayout {
                        relativeLayout {
                            textView {
                                backgroundResource = R.drawable.button_shape_orange
                                text = "ログアウト"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFF")
                                gravity = Gravity.CENTER
                                this.withTrigger().click {
                                    showLogoutDialog()
                                }
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(50)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            bottomMargin = dip(10)
                            alignParentBottom()
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    backgroundColor = Color.WHITE
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }


    }

    override fun onStart() {
        super.onStart()

        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SystemSetupActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@SystemSetupActivity, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUserInformation()
            showNormalDialog()
        }
    }

    private suspend fun getUserInformation() {
        try {
            val retrofitUils = RetrofitUtils(this@SystemSetupActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getUserInformation()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val json = it.body()!!.asJsonObject
                userInformation = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
            }

        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    //一开始判断是否要更新,那个new
    private suspend fun showNormalDialog() {
        try {
            val retrofitUils = RetrofitUtils(this@SystemSetupActivity, "https://app-version.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .checkUpdate("ANDROID")
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            // 如果获取成功,则弹出下一个弹窗
            if (it.code() in 200..299) {
                println(it)
                val json = it.body()!!.asJsonObject
                versionModel = Gson().fromJson<Version>(json, Version::class.java)
                afterShowLoading(versionModel)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //弹出登出窗口
    private fun showLogoutDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(mainId, shadowFragment!!)
        }

        logoutFragment = LoginOutFrag.newInstance(this@SystemSetupActivity)
        mTransaction.add(mainId, logoutFragment!!)
        mTransaction.commit()
    }

    //弹出更新窗口
    private fun afterShowLoading(model: Version) {
        //先获取本地版本信息
        val version = getLocalVersion(this@SystemSetupActivity)
        if (version < model.number) {
            versionBool = true
            dialogLoading.visibility = LinearLayout.GONE
            newVersion.visibility = LinearLayout.VISIBLE
        } else {
            versionBool = false
        }
    }

    //打开弹窗
    private fun opendialog() {
        if (versionBool) {
            println("要更新")
            //如果版本低,弹出更新弹窗
            val mTransaction = supportFragmentManager.beginTransaction()
            mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (shadowFragment == null) {
                shadowFragment = ShadowFragment.newInstance()
                mTransaction.add(mainId, shadowFragment!!)
            }

            updateTips = UpdateTipsFrag.newInstance(this@SystemSetupActivity,versionModel)
            mTransaction.add(mainId, updateTips!!)
            mTransaction.commit()
        } else {
            val toast = Toast.makeText(applicationContext, "これは最新バージョンです", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
    }

    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (updateTips != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(updateTips!!)
            updateTips = null
        }

        if (logoutFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(logoutFragment!!)
            logoutFragment = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }

    override fun shadowClicked() {
//        closeAlertDialog()
    }

    //停止更新
    override fun cancelUpdateClick() {
        closeAlertDialog()
    }

    //开始更新
    override fun defineClick(downloadUrl: String) {
        val uri = Uri.parse(downloadUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        closeAlertDialog()
    }

    // 获取软件的版本号 versionCode,用于比较版本
    private fun getLocalVersion(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                .packageManager
                .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionCode
            Log.d("TAG", "本软件的版本号。。$localVersion")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }

    // 获取软件的版本号名称 versionName,用于显示版本
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (updateTips != null && logoutFragment != null && shadowFragment != null) {
                closeAlertDialog()
                false
            } else {
                val intent = Intent(this@SystemSetupActivity, PersonSetActivity::class.java)
                startActivity(intent)
                finish()//返回
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
                true
            }
        } else {
            false
        }
    }

}