package com.example.sk_android.mvp.view.activity.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.common.BaseActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import withTrigger

class CompanyWebSiteActivity : BaseActivity() {

    private lateinit var web: WebView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        var url = ""
        var name = ""
        if (intent.getStringExtra("webUrl") != null) {
            var web = intent.getStringExtra("webUrl")
            if (URLUtil.isValidUrl(web)) {
                if ("http" == web.substring(0, 4)) {
                    web = "${web.substring(0, 4)}s${web.substring(5, web.length)}"
                }
                url = web
            }else{
                url = "https://$web"
            }
        }
        if(intent.getStringExtra("companyName") != null){
            name = intent.getStringExtra("companyName")
        }

        frameLayout {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                linearLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    orientation = LinearLayout.HORIZONTAL
                    toolbar {
                        navigationIconResource = R.mipmap.icon_back
                        this.withTrigger().click {
                            web.clearCache(true)
                            finish()
                            overridePendingTransition(R.anim.left_in,R.anim.right_out)
                        }
                    }.lparams(dip(20), dip(20)) {
                        leftMargin = dip(15)
                        gravity = Gravity.BOTTOM
                        bottomMargin = dip(15)
                    }
                    relativeLayout {
                        textView {
                            if(name.length>14){
                                text = "${name.substring(0,14)}...-採用情報"
                            }else{
                                text = "$name-採用情報"
                            }
                            textSize = 16f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams(wrapContent, wrapContent) {
                            centerHorizontally()
                            alignParentBottom()
                            bottomMargin = dip(20)
                        }
                        textView {
                            if(url.length>40){
                                text = "(${url.substring(0,40)}...)"
                            }else{
                                text = "($url)"
                            }
                            textSize = 13f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams(wrapContent, wrapContent) {
                            centerHorizontally()
                            alignParentBottom()
                            bottomMargin = dip(5)
                        }
                    }.lparams(matchParent, matchParent){
                        leftMargin = dip(5)
                        rightMargin = dip(5)
                    }
                }.lparams(matchParent, dip(70))

                relativeLayout {
                    if(url!=""){
                        web = webView {
                            //打开不为空白 && 不弹出浏览器打开
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.blockNetworkImage = false
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.setSupportMultipleWindows(true)
                            webViewClient = WebViewClient()
                            webChromeClient = WebChromeClient()
                            loadUrl(url)
                        }.lparams(matchParent, matchParent)
                    }
                }.lparams(matchParent, matchParent)
            }
        }
    }

    //打开网页,回退时不会退出activity,而是退回上个网页
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (web.canGoBack()) {
                web.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}