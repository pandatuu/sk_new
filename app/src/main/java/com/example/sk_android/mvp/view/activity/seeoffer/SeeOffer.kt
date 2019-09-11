package com.example.sk_android.mvp.view.activity.seeoffer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.seeoffer.OfferApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.example.sk_android.mvp.view.fragment.videointerview.SeeOfferAccept
import com.example.sk_android.mvp.view.fragment.videointerview.SeeOfferFrag
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException

class SeeOffer : AppCompatActivity(), ShadowFragment.ShadowClick, TipDialogFragment.TipDialogSelect
    , SeeOfferFrag.SeeOfferButton, SeeOfferAccept.OfferAccept {

    var shadowFragment: ShadowFragment? = null
    private var tipDialogFragment: TipDialogFragment? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null

    private var relative: LinearLayout? = null

    private lateinit var mainBody: FrameLayout
    private lateinit var offimage: ImageView
    private lateinit var titleView: TextView
    private lateinit var frame: FrameLayout
    private lateinit var verla: LinearLayout
    private lateinit var webVi: WebView
    private var offerId = ""
    private var channelMsgId = ""
    private var webHtml = ""
    private var userMail = ""
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

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        val id1 = 1
        mainBody = frameLayout {
            id = id1
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    toolbar {
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            finish()//返回
                            overridePendingTransition(R.anim.left_in, R.anim.right_out)
                        }
                    }.lparams(dip(25), dip(25)) {
                        leftMargin = dip(15)
                        alignParentBottom()
                        bottomMargin = dip(10)
                    }
                    titleView = textView {
                        text = "offer詳細を見る"
                        textColorResource = R.color.toolBarTextColor
                        textSize = 16f
                    }.lparams(wrapContent, wrapContent) {
                        centerHorizontally()
                        alignParentBottom()
                        bottomMargin = dip(10)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(65)
                }
                frame = frameLayout {
                    verla = verticalLayout {
                        backgroundResource = R.mipmap.ico_offer_background
                        verticalLayout {
                            backgroundColor = Color.TRANSPARENT
                            webVi = webView {
                                isVerticalScrollBarEnabled = false
                                backgroundColor = Color.TRANSPARENT
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.blockNetworkImage = false
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true
                                settings.javaScriptCanOpenWindowsAutomatically = true
                                settings.setSupportMultipleWindows(true)
                                settings.textZoom = 300
                                webViewClient = WebViewClient()
                                webChromeClient = WebChromeClient()
                            }.lparams(matchParent, matchParent)
                        }.lparams {
                            width = matchParent
                            height = 0
                            weight = 1f
                            setMargins(dip(32), dip(37), dip(32), 0)
                        }

                        val button = 2
                        relative = linearLayout {
                            id = button
                            gravity = Gravity.CENTER_VERTICAL

                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        setMargins(dip(15), dip(50), dip(15), 0)
                    }
                    offimage = imageView {
                        imageResource = R.mipmap.ico_offer
                    }.lparams(dip(110), dip(80)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams {
                    width = matchParent
                    height = 0
                    weight = 1f
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
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("offerId") != null) {
            offerId = intent.getStringExtra("offerId")
            channelMsgId = intent.getStringExtra("channelMsgId")
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getOfferInfo(offerId)
            }
        }
    }

    //选择拒绝后,弹窗再次确认
    override fun cancel() {
        showAlertDialog()
    }

    //选择确认offer
    override suspend fun demire() {
        updateOfferState(offerId, true)

        val mIntent = Intent()
        mIntent.putExtra("offerState", "OK")
        mIntent.putExtra("offerId", offerId)
        mIntent.putExtra("channelMsgId", channelMsgId)

        setResult(RESULT_OK, mIntent)
        finish()//返回
        overridePendingTransition(R.anim.left_in, R.anim.right_out)


    }

    //选择弹窗的按钮
    override suspend fun getTipDialogSelect(b: Boolean) {
        if (b) {
            thisDialog=DialogUtils.showLoading(this@SeeOffer)
            mHandler.postDelayed(r, 12000)
            updateOfferState(offerId, false)

            val mIntent = Intent()
            mIntent.putExtra("offerState", "REFUSE")
            mIntent.putExtra("offerId", offerId)
            mIntent.putExtra("channelMsgId", channelMsgId)

            setResult(RESULT_OK, mIntent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)

        }
        closeAlertDialog()
    }

    //选择转发到邮箱
    override suspend fun email() {
        thisDialog=DialogUtils.showLoading(this@SeeOffer)
        mHandler.postDelayed(r, 12000)
        if (webHtml != "") {
            try {
                val param = mapOf(
                    "type" to "SELF_OFFER",
                    "to" to userMail,
                    "subject" to "sk offer",
                    "html" to webHtml
                )
                val userJson = JSON.toJSONString(param)
                val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

                val retrofitUils = RetrofitUtils(this@SeeOffer, this.getString(R.string.mailUrl))
                val it = retrofitUils.create(OfferApi::class.java)
                    .createToMyMail(body)
                    .subscribeOn(Schedulers.io())
                    .awaitSingle()

                if (it.code() in 200..299) {
                    DialogUtils.hideLoading(thisDialog)
                    val toast = Toast.makeText(applicationContext, "転送は成功しました", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    finish()//返回
                    overridePendingTransition(R.anim.left_in, R.anim.right_out)
                }else{
                    DialogUtils.hideLoading(thisDialog)
                    val toast = Toast.makeText(applicationContext, "転送は失敗しました", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            } catch (throwable: Throwable) {
                if (throwable is HttpException) {
                    println(throwable.code())
                }
                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "転送は失敗しました", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        } else {
            DialogUtils.hideLoading(thisDialog)
            val toast = Toast.makeText(applicationContext, "転送は失敗しました", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    override fun shadowClicked() {

    }

    // 获取offer记录
    private suspend fun getOfferInfo(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@SeeOffer, this.getString(R.string.organizationUrl))
            val it = retrofitUils.create(OfferApi::class.java)
                .getUserPrivacy(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                if (it.body()!!.get("state").asString == "PENDING") {
                    val seebutton = SeeOfferFrag.newInstance()
                    supportFragmentManager.beginTransaction().add(relative!!.id, seebutton).commit()
                }
                if (it.body()!!.get("state").asString == "ACCEPTED") {
                    val seebutton = SeeOfferAccept.newInstance()
                    supportFragmentManager.beginTransaction().add(relative!!.id, seebutton).commit()
                }
                webHtml = it.body()!!["attributes"].asJsonObject.get("html").asString

                //拼接并转化HTML代码格式
                val stringBuffer = StringBuilder()
                stringBuffer.append("<html>")
                stringBuffer.append("<body style='margin-top:35px'>")
                stringBuffer.append(webHtml)
                stringBuffer.append("</body>")
                stringBuffer.append("</html>")
                //显示HTML代码
                webVi.loadDataWithBaseURL(null, stringBuffer.toString(), "text/html", "UTF-8", null)

                val userId = it.body()!!.get("targetUserId").asString
                getUserInfoById(userId)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 修改offer状态并返回
    private suspend fun updateOfferState(id: String, bool: Boolean) {
        val state = if (bool) "ACCEPTED" else "REJECTED"
        try {
            val param = mapOf(
                "state" to state
            )
            val userJson = JSON.toJSONString(param)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@SeeOffer, this.getString(R.string.organizationUrl))
            val it = retrofitUils.create(OfferApi::class.java)
                .updateOfferState(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //根据用户Id获取用户email
    private suspend fun getUserInfoById(id: String) {
        try {

            val retrofitUils = RetrofitUtils(this@SeeOffer, this.getString(R.string.userUrl))
            val it = retrofitUils.create(OfferApi::class.java)
                .getmail(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                userMail = it.body()!!.get("email").asString
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //打开弹窗
    private fun showAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(mainBody.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )


        tipDialogFragment = TipDialogFragment.newInstance(1, "拒否すると企業にお知らせし ますが、確かに拒否しますか？")
        mTransaction.add(mainBody.id, tipDialogFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (tipDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(tipDialogFragment!!)
            tipDialogFragment = null
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
}