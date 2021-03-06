package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.DialogUtils
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
import withTrigger
import java.util.regex.Pattern

class UpdatePasswordActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment? = null
    private lateinit var oldpwd: EditText
    private lateinit var nowpwd: EditText
    private lateinit var secondpwd: EditText
    private val regexPasswrod = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,20}\$"
    private var flagOne: Boolean = false
    private var flagTwo: Boolean = false
    private var flagThree: Boolean = false
    lateinit var ms: SharedPreferences
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        relativeLayout {
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("パスワード変更")
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "現在のパスワード    "
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams {
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            linearLayout {
                                oldpwd = editText {
                                    hint = "現在のパスワードを入力してください"
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    textSize = 15f
                                    background = null
                                    padding = dip(10)
                                    singleLine = true
                                    transformationMethod = PasswordTransformationMethod.getInstance()
                                }.lparams {
                                    weight = 1f
                                    height = wrapContent
                                }
                                relativeLayout {
                                    val image=imageView {
                                        imageResource = R.mipmap.ico_eyes_no
                                    }.lparams(dip(20), dip(20)) {
                                        alignParentRight()
                                        centerVertically()
                                        rightMargin = dip(15)
                                        leftMargin = dip(5)
                                    }
                                    onClick {
                                        changeImage(oldpwd, image, flagOne)
                                        flagOne = !flagOne
                                    }
                                }.lparams(dip(40), matchParent)
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード"
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams {
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            linearLayout {
                                nowpwd = editText {
                                    hint = "6-20桁で数字とアルファベットの組合"
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    textSize = 15f
                                    background = null
                                    padding = dip(10)
                                    singleLine = true
                                    transformationMethod = PasswordTransformationMethod.getInstance()
                                }.lparams {
                                    weight = 1f
                                    height = wrapContent
                                }
                                relativeLayout {
                                    val image=imageView {
                                        imageResource = R.mipmap.ico_eyes_no
                                    }.lparams(dip(20), dip(20)) {
                                        alignParentRight()
                                        centerVertically()
                                        rightMargin = dip(15)
                                        leftMargin = dip(5)
                                    }
                                    onClick {
                                        changeImage(nowpwd, image, flagTwo)
                                        flagTwo = !flagTwo
                                    }
                                }.lparams(dip(40), matchParent)
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード（確認用）"
                                textColor = Color.parseColor("#FF202020")
                                textSize = 15f
                            }.lparams {
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            linearLayout {
                                secondpwd = editText {
                                    hint = "もう一度入力してください"
                                    textSize = 15f
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    background = null
                                    padding = dip(10)
                                    singleLine = true
                                    transformationMethod = PasswordTransformationMethod.getInstance()
                                }.lparams {
                                    weight = 1f
                                    height = wrapContent
                                }
                                relativeLayout {
                                    val image=imageView {
                                        imageResource = R.mipmap.ico_eyes_no
                                    }.lparams(dip(20), dip(20)) {
                                        alignParentRight()
                                        centerVertically()
                                        rightMargin = dip(15)
                                        leftMargin = dip(5)
                                    }
                                    onClick {
                                        changeImage(secondpwd, image, flagThree)
                                        flagThree = !flagThree
                                    }
                                }.lparams(dip(40), matchParent)
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        this.withTrigger().click {
                            closeKeyfocus()
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }

                    relativeLayout {
                        backgroundResource = R.drawable.button_shape_orange
                        textView {
                            text = "パスワードをリセット"
                            textSize = 16f
                            textColor = Color.WHITE
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerInParent()
                        }
                        onClick {
                            //清除光标
                            closeKeyfocus()

                            val old = oldpwd.text.toString().trim()
                            val now = nowpwd.text.toString().trim()
                            val second = secondpwd.text.toString().trim()
                            if(old.isEmpty()){
                                val toast = Toast.makeText(this@UpdatePasswordActivity, "現在のパスワードを入力してください。", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                return@onClick
                            }
                            if(now.isEmpty()){
                                val toast = Toast.makeText(this@UpdatePasswordActivity, "新しいパスワードを入力してください。", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                return@onClick
                            }
                            if(second.isEmpty()){
                                val toast = Toast.makeText(this@UpdatePasswordActivity, "新しいパスワードを入力してください。（確認用）", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                return@onClick
                            }
                            //判断新密码格式
                            if (isTrue(now)) {
                                if (now == second) {
                                    thisDialog=DialogUtils.showLoading(this@UpdatePasswordActivity)
                                    mHandler.postDelayed(r, 12000)
                                    updatePassword(old, now, second)
                                } else {
                                    val toast = Toast.makeText(applicationContext, "パスワードが一致しません。", Toast.LENGTH_SHORT)
                                    toast.setGravity(Gravity.CENTER,0,0)
                                    toast.show()
                                }
                            } else {
                                val toast = Toast.makeText(applicationContext, "パスワードが正しくありません。", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER,0,0)
                                toast.show()
                            }
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(47)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        alignParentBottom()
                        bottomMargin = dip(20)
                    }
                }.lparams {
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
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
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

            val retrofitUils = RetrofitUtils(this@UpdatePasswordActivity, this.getString(R.string.authUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updatePassword(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "パスワードを変更しました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                ms = PreferenceManager.getDefaultSharedPreferences(this@UpdatePasswordActivity)
                val mEditor: SharedPreferences.Editor = ms.edit()
                mEditor.putString("password",now)
                mEditor.commit()

                finish()
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    //　验证密码是否符合规定
    private fun isTrue(pwd: String): Boolean {
        return Pattern.matches(regexPasswrod, pwd)
    }

    //隐藏密码
    private fun changeImage(edit: EditText, image: ImageView,flag: Boolean) {
        if (flag) {
            edit.transformationMethod = HideReturnsTransformationMethod.getInstance()
            image.setImageResource(R.mipmap.ico_eyes)
        } else {
            edit.transformationMethod = PasswordTransformationMethod.getInstance()
            image.setImageResource(R.mipmap.ico_eyes_no)
        }

    }

    private fun closeKeyfocus() {
        val imm = this@UpdatePasswordActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.peekDecorView().windowToken, 0)

        oldpwd.clearFocus()
        nowpwd.clearFocus()
        secondpwd.clearFocus()
    }
}