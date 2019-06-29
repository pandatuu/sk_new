package com.example.sk_android.mvp.view.activity.company

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.KeyEvent
import android.webkit.URLUtil
import android.webkit.WebView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CompanyWebSiteActivity : AppCompatActivity() {

    private lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            web.clearCache(true)
                            finish()
                        }
                    }.lparams(dip(20), dip(20)) {
                        leftMargin = dip(15)
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "$name-採用情報"
                        textSize = 16f
                        textColor = Color.parseColor("#FF333333")
                    }.lparams(wrapContent, wrapContent) {
                        centerInParent()
                    }
                }.lparams(matchParent, dip(50))

                relativeLayout {
                    if(url!=""){
                        web = webView {
                            loadUrl(url)
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
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