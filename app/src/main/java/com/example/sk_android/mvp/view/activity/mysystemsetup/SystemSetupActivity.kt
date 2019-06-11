package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.mysystemsetup.LoginOutFrag
import com.example.sk_android.mvp.view.fragment.mysystemsetup.UpdateTipsFrag
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException


class SystemSetupActivity : AppCompatActivity(), ShadowFragment.ShadowClick, UpdateTipsFrag.ButtomCLick,
    LoginOutFrag.TextViewCLick {
    //登出取消按钮
    override fun cancelClick() {
        closeAlertDialog()
    }
    //登出确定按钮
    override suspend fun chooseClick() {
        closeAlertDialog()
        //dengchu
        try{
            val retrofitUils = RetrofitUtils(this@SystemSetupActivity, "https://auth.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .logout()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 204) {
                toast("登出成功")
            }
        } catch (throwable:Throwable){
            println("登出失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    private lateinit var myDialog: MyDialog
    var mainId = 1
    var shadowFragment: ShadowFragment? = null
    var logoutFragment: LoginOutFrag? = null
    var updateTips: UpdateTipsFrag? = null
    var userInformation: UserSystemSetup? = null

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            getUserInformation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            id = mainId
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "設定"
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
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position

                                onClick {
                                    // 给bnt1添加点击响应事件
                                    val intent =
                                        Intent(this@SystemSetupActivity, NotificationSettingsActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
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
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                onClick {
                                    // 给bnt1添加点击响应事件
                                    val intent = Intent(this@SystemSetupActivity, GreetingsActivity::class.java)
                                    intent.putExtra("greeting",userInformation!!.greeting)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
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
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                isEnabled = true
                                onClick {
                                    // 给bnt1添加点击响应事件
                                    val intent = Intent(this@SystemSetupActivity, BindPhoneNumberActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
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
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                isEnabled = true
                                onClick {
                                    // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                    val intent = Intent(this@SystemSetupActivity, UpdatePasswordActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                            }
                        }.lparams {
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
                            }.lparams {
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.new_icon
                                textView {
                                    text = "New"
                                    textSize = 10f
                                    textColor = Color.parseColor("#FFFFFF")
                                }.lparams {
                                    setMargins(dip(4), dip(1), dip(4), dip(1))
                                }
                            }.lparams {
                                width = dip(29)
                                height = dip(16)
                                leftMargin = dip(64)
                                centerVertically()
                            }
                            textView {
                                text = "バージョン1.0.1"
                                textColor = Color.parseColor("#B3B3B3")
                                textSize = 12f
                            }.lparams {
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(36)
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                isEnabled = true
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
                            }
                            onClick {
                                showNormalDialog()
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
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                                isEnabled = true
                                onClick {
                                    // 这里要判断有无密码，有就进入修改密码页面，无则进入设置密码页面
                                    val intent = Intent(this@SystemSetupActivity, AboutUsActivity::class.java)
                                    //启动
                                    startActivity(intent)
                                }
                            }.lparams {
                                alignParentRight()
                                width = dip(30)
                                height = wrapContent
                                centerVertically()
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
                            text = "登録をログアウトする"
                            textSize = 16f
                            textColor = Color.parseColor("#FFFFFF")
                            gravity = Gravity.CENTER
                            onClick {
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
        }
    }

    private suspend fun getUserInformation() {
        try {
            val retrofitUils = RetrofitUtils(this@SystemSetupActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getUserInformation()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                val json = it.body()!!.asJsonObject
                userInformation = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
            }

//            userInformation = Gson().fromJson<UserSystemSetup>(it!!, UserSystemSetup::class.java)
//            println(userInformation.toString())
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }


    private fun showNormalDialog() {
        showLoading()
        //延迟3秒关闭
        Handler().postDelayed({ hideLoading(); afterShowLoading() }, 3000)
    }

    //弹出等待转圈窗口
    private fun showLoading() {
        if (isInit()) {
            myDialog.dismiss()
            val builder = MyDialog.Builder(this@SystemSetupActivity)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(this@SystemSetupActivity)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()
        }
        myDialog.show()
    }

    //关闭等待转圈窗口
    private fun hideLoading() {
        if (isInit() && myDialog.isShowing()) {
            myDialog.dismiss()
        }
    }

    //判断mmloading是否初始化,因为lainit修饰的变量,不能直接判断为null,要先判断初始化
    private fun isInit(): Boolean {
        return ::myDialog.isInitialized
    }

    private fun showLogoutDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(mainId, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        logoutFragment = LoginOutFrag.newInstance(this@SystemSetupActivity)
        mTransaction.add(mainId, logoutFragment!!)
        mTransaction.commit()
    }

    //弹出更新窗口
    private fun afterShowLoading() {

        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(mainId, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        updateTips = UpdateTipsFrag.newInstance(this@SystemSetupActivity)
        mTransaction.add(mainId, updateTips!!)
        mTransaction.commit()
    }

    //关闭更新提示弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (updateTips != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(updateTips!!)
            updateTips = null
        }

        if (logoutFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
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
        closeAlertDialog()
    }

    override fun onDialogClick() {
        closeAlertDialog()
    }

}