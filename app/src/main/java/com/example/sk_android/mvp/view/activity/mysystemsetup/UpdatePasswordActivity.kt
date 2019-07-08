package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import java.util.regex.Pattern

class UpdatePasswordActivity : AppCompatActivity() {

    var actionBarNormalFragment:ActionBarNormalFragment?=null
    lateinit var oldpwd : EditText
    lateinit var nowpwd : EditText
    lateinit var secondpwd : EditText
    private val REGEX_PASSWORD = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,20}\$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("パスワードを設置する");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "現在のパスワード    "
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams{
                                alignParentLeft()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                oldpwd = editText {
                                    hint = "現在のパスワードを入力してください"
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    textSize = 15f
                                    background = null
                                    padding = dip(10)
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード"
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams{
                                alignParentLeft()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                nowpwd = editText {
                                    hint = "6-20桁の数字/アルバイト"
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    textSize = 15f
                                    background = null
                                    padding = dip(10)
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード（確認用）"
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams{
                                alignParentLeft()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                secondpwd = editText {
                                    hint = "もう一度入力してください"
                                    textSize = 15f
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    background = null
                                    padding = dip(10)
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }

                    relativeLayout {
                        backgroundResource = R.drawable.button_shape_orange
                        textView {
                            text = "パスワードを設置する"
                            textSize = 16f
                            textColor = Color.WHITE
                        }.lparams{
                            width = dip(148)
                            height = dip(23)
                            centerInParent()
                        }
                        onClick{
                            //清除光标
                            oldpwd.clearFocus()
                            nowpwd.clearFocus()
                            secondpwd.clearFocus()
                            val old = oldpwd.text.toString().trim()
                            val now = nowpwd.text.toString().trim()
                            val second = secondpwd.text.toString().trim()
                            //判断新密码格式
                            if(isTrue(now)){
                                if(now.equals(second)){
                                    updatePassword(old,now,second)
                                }else{
                                    toast("二次输入密码不一致")
                                }
                            }else{
                                toast("密码格式错误，请重新输入密码")
                            }
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(47)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        alignParentBottom()
                        bottomMargin = dip(20)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    topMargin = dip(34)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@UpdatePasswordActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }
    }
    //　更新密码
    private suspend fun updatePassword(old: String, now: String, second: String) {
        try {
            val params = mapOf(
                "oldPwd" to old,
                "newPwd" to now,
                "confirmPwd" to second
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@UpdatePasswordActivity, "https://auth.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updatePassword(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 204) {
                toast("修改密码成功")
                oldpwd.text.clear()
                nowpwd.text.clear()
                secondpwd.text.clear()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    //　验证密码是否符合规定
    private fun isTrue(pwd : String) : Boolean{
        return Pattern.matches(REGEX_PASSWORD, pwd)
    }
}