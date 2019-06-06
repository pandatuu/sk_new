package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class BindPhoneNumberActivity : AppCompatActivity() {

    var phonetext: EditText? = null
    var vCodetext: EditText? = null

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
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "電話番号変更"
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
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                textView {
                                    text = "+86"
                                    textSize = 15f
                                    textColor = Color.parseColor("#202020")
                                }.lparams {
                                    width = dip(28)
                                    height = dip(21)
                                    leftMargin = dip(10)
                                    centerVertically()
                                }
                                toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                width = dip(51)
                                height = matchParent
                                alignParentLeft()
                            }
                            relativeLayout {
                                phonetext = editText {
                                    hint = "電話番号を入力してください"
                                    textSize = 15f
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    background = null
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams {
                                width = dip(212)
                                height = matchParent
                                leftMargin = dip(64)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                vCodetext = editText {
                                    hint = "検証コードを入力してください"
                                    textSize = 14f
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    background = null
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }
                            }.lparams {
                                width = dip(215)
                                height = wrapContent
                                leftMargin = dip(5)
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                textView {
                                    text = "検証コードを取得"
                                    textColor = Color.parseColor("#FFFFB706")
                                    textSize = 12f
                                    onClick {
                                        //关闭ｅｄｉｔ光标
                                        phonetext!!.clearFocus()
                                        //关闭键盘事件
                                        val mm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                        mm.hideSoftInputFromWindow(phonetext!!.windowToken, 0)
                                        clickSendButton(phonetext!!.text.toString().trim())
                                    }
                                }.lparams {
                                    centerInParent()
                                }
                            }.lparams {
                                centerVertically()
                                width = dip(103)
                                height = dip(27)
                                alignParentRight()
                                rightMargin = dip(5)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(55)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            textView {
                                text = "電話番号を設置する"
                                textSize = 16f
                                textColor = Color.WHITE
                            }.lparams {
                                width = dip(148)
                                height = dip(23)
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            textView {
                                text = "検証コードの受信が出来なかった？"
                                textSize = 12f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = dip(198)
                                height = dip(17)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(17)
                        }

                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(269)
                    topMargin = dip(45)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    private suspend fun clickSendButton(phoneNum: String) {
            sendVerificationCode(phoneNum)
    }

    private suspend fun sendVerificationCode(phoneNum: String) {
        val deviceModel: String = Build.MODEL
        val manufacturer: String = Build.BRAND
        //日本手机号
//        var pattern: Pattern = Pattern.compile("/^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}\$/")
//        var matcher: Matcher = pattern.matcher(phoneNum)
//        测试阶段先暂时屏蔽
//            if(!matcher.matches()){
//                accountErrorMessage.textResource = R.string.mrTelephoneFormat
//                accountErrorMessage.visibility = View.VISIBLE
//                return
//            }
        try {
            val params = mapOf(
                "phone" to phoneNum,
                "country" to "86",
                "deviceType" to "ANDROID",
                "codeType" to "USERNAME",
                "manufacturer" to manufacturer,
                "deviceModel" to deviceModel
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)
            println("body-------------------${body}")

            val retrofitUils = RetrofitUtils(this@BindPhoneNumberActivity, "https://auth.sk.cgland.top/")

            val response = retrofitUils.create(SystemSetupApi::class.java)
                .sendvCode(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

//            if(response is HttpException){
//                println("成功－－－－－－－－－－－－－－"+response.code())
                toast("验证码已发送")
//            }

        } catch (throwable: Throwable) {
            println("手机验证码发送失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }
}