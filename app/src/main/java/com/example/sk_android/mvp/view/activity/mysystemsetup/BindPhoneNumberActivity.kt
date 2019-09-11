package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
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
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import withTrigger

class BindPhoneNumberActivity : AppCompatActivity() {

    var phonetext: EditText? = null
    private var vCodetext: EditText? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    private var runningDownTimer: Boolean = false
    private lateinit var pourtime: TextView
    private lateinit var areaNum: TextView
    var bool = false
    lateinit var ms: SharedPreferences
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        relativeLayout {
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("電話番号変更")
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                relativeLayout {
                    verticalLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource = R.drawable.input_box
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                areaNum = textView {
                                    text = "+86"
                                    textSize = 15f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = dip(21)
                                    leftMargin = dip(10)
                                    gravity = Gravity.CENTER_VERTICAL
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    leftMargin = dip(10)
                                    gravity = Gravity.CENTER_VERTICAL
                                }

                                this.withTrigger().click {
                                    startActivityForResult(Intent(applicationContext, PickActivity::class.java), 111)
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                            }
                            phonetext = editText {
                                hint = "電話番号を入力してください"
                                textSize = 15f
                                hintTextColor = Color.parseColor("#FFB3B3B3")
                                background = null
                            }.lparams {
                                weight = 1f
                                height = matchParent
                                leftMargin = dip(10)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        linearLayout {
                            backgroundResource = R.drawable.input_box
                            orientation = LinearLayout.HORIZONTAL
                            relativeLayout {
                                vCodetext = editText {
                                    hint = "検証コードを入力してください"
                                    textSize = 14f
                                    hintTextColor = Color.parseColor("#FFB3B3B3")
                                    background = null
                                    padding = dip(5)
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.lparams {
                                weight = 1f
                                height = matchParent
                                gravity = Gravity.LEFT
                                leftMargin = dip(5)
                            }
                            relativeLayout {
                                pourtime = textView {
                                    text = "検証コードを取得"
                                    textColor = Color.parseColor("#FFFFB706")
                                    textSize = 12f
                                    onClick {
                                        closeFocusjianpan()
                                        if (phonetext?.text!!.isEmpty()) {
                                            val toast = Toast.makeText(
                                                this@BindPhoneNumberActivity,
                                                "携帯番号を入力してください。",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.setGravity(Gravity.CENTER, 0, 0)
                                            toast.show()
                                            return@onClick
                                        } else {
                                            val countryText = areaNum.text.toString().trim()
                                            val country = countryText.substring(1, 3)
                                            val myPhone = countryText + phonetext?.text.toString().trim()
                                            val result = isPhoneNumberValid(myPhone, country)
                                            //不同国家手机测试
                                            if (!result) {
                                                val toast = Toast.makeText(
                                                    this@BindPhoneNumberActivity,
                                                    "正しい携帯番号を入力してください。",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast.setGravity(Gravity.CENTER, 0, 0)
                                                toast.show()
                                                return@onClick
                                            }
                                            bool = sendVerificationCode(phonetext?.text.toString().trim())
                                            if (bool)
                                                onPcode()
                                        }
                                    }
                                }.lparams(wrapContent, wrapContent) {
                                    centerInParent()
                                }
                            }.lparams {
                                width = dip(105)
                                height = matchParent
                                gravity = Gravity.RIGHT
                                leftMargin = dip(5)
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
                            onClick {
                                closeFocusjianpan()
                                if (bool) {
                                    val phoneNum = phonetext!!.text.toString().trim()
                                    val verifyCode = vCodetext!!.text.toString().trim()
                                    if (phoneNum.isEmpty()) {
                                        val toast = Toast.makeText(
                                            this@BindPhoneNumberActivity,
                                            "携帯番号を入力してください。",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setGravity(Gravity.CENTER, 0, 0)
                                        toast.show()
                                    }
                                    if (verifyCode.isEmpty()) {
                                        val toast = Toast.makeText(
                                            this@BindPhoneNumberActivity,
                                            "認証コードを入力してください。",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setGravity(Gravity.CENTER, 0, 0)
                                        toast.show()
                                    }

                                    thisDialog = DialogUtils.showLoading(this@BindPhoneNumberActivity)
                                    mHandler.postDelayed(r, 12000)
                                    val or = validateVerificationCode(phoneNum, verifyCode)
                                    if (or) {
                                        changePhoneNum(phoneNum, verifyCode)
                                    }
                                }
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
                                this.withTrigger().click {
                                    val intent = Intent(this@BindPhoneNumberActivity, AboutUsActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                }
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

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@BindPhoneNumberActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    // 发送校验码
    private suspend fun sendVerificationCode(phoneNum: String): Boolean {
        val deviceModel: String = Build.MODEL
        val manufacturer: String = Build.BRAND
        val countryText = areaNum.text.toString().trim()
        val country = countryText.substring(1, 3)
        val result = isPhoneNumberValid(phoneNum, country)
        //不同国家手机测试
//        if (!result) {
//            //手机格式不正确
//            val toast = Toast.makeText(applicationContext, "正しい携帯番号を入力してください。。", Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.CENTER, 0, 0)
//            toast.show()
//            return false
//        }
        try {  //15208340775
            val params = mapOf(
                "phone" to phoneNum,
                "country" to country,
                "deviceType" to "ANDROID",
                "codeType" to "USERNAME",
                "manufacturer" to manufacturer,
                "deviceModel" to deviceModel
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@BindPhoneNumberActivity, this.getString(R.string.authUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .sendvCode(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "認証コードは既に送信されました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return true
            }
            if (it.code() == 409) {
                val toast = Toast.makeText(applicationContext, "この携帯番号は既に登録されました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return false
            }
            return false
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
            return false
        }
    }

    //　校验验证码
    private suspend fun validateVerificationCode(phoneNum: String, verifyCode: String): Boolean {
        val countryText = areaNum.text.toString().trim()
        val country = countryText.substring(1, 3)
        try {
            val params = mapOf(
                "phone" to phoneNum,
                "country" to country,
                "code" to verifyCode
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@BindPhoneNumberActivity, this.getString(R.string.authUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .validateCode(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "認証コード確認成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return true
            }
            if (it.code() == 406) {
                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "認証コード取得失敗", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return false
            }
            return false
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
            return false
        }
    }

    //　修改手机号 auth接口
    private suspend fun changePhoneNum(phoneNum: String, verifyCode: String) {
        val countryText = areaNum.text.toString().trim()
        val country = countryText.substring(1, 3)
        try {
            val params = mapOf(
                "phone" to phoneNum,
                "country" to country,
                "code" to verifyCode
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@BindPhoneNumberActivity, this.getString(R.string.authUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .changePhoneNum(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                changeUserPhoneNum(phoneNum)
            }
            if (it.code() == 409) {
                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "この携帯番号は既に登録されました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 修改个人信息的手机号 user接口
    private suspend fun changeUserPhoneNum(phoneNum: String) {
        val countryText = areaNum.text.toString().trim()
        val country = countryText.substring(1, 3)
        try {
            val params = mapOf(
                "code" to country,
                "phone" to phoneNum
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@BindPhoneNumberActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .changeUserPhoneNum(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "携帯番号を変更しました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

                ms = PreferenceManager.getDefaultSharedPreferences(this@BindPhoneNumberActivity)
                val mEditor: SharedPreferences.Editor = ms.edit()
                mEditor.putString("phone", phoneNum)
                mEditor.commit()

                // 给bnt1添加点击响应事件
                val intent = Intent(this@BindPhoneNumberActivity, SystemSetupActivity::class.java)
                //启动
                startActivity(intent)
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        phonetext!!.clearFocus()
        vCodetext!!.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(phonetext!!.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(vCodetext!!.windowToken, 0)
    }

    //发送验证码按钮
    fun onPcode() {

        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return
        }

        downTimer.start()  // 倒计时开始

    }

    /**
     * 倒计时
     */
    private val downTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(l: Long) {
            runningDownTimer = true
            pourtime.text = (l / 1000).toString() + "s"
            pourtime.setOnClickListener { null }
        }

        override fun onFinish() {
            runningDownTimer = false
            pourtime.text = "検証コードを取得"
            pourtime.onClick {
                onPcode()
                bool = sendVerificationCode(phonetext!!.text.toString().trim())
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            val countryCode = "+" + country.code
            areaNum.text = countryCode
        }
    }


    /**
     * 根据区号判断是否是正确的电话号码
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :默认国家码
     * return ：true 合法  false：不合法
     */
    private fun isPhoneNumberValid(phoneNumber: String, countryCode: String): Boolean {

        println("isPhoneNumberValid: $phoneNumber/$countryCode")
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            return phoneUtil.isValidNumber(numberProto)
        } catch (e: NumberParseException) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: $e")
        }

        return false
    }
}