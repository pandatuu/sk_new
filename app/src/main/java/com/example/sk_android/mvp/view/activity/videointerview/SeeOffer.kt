package com.example.sk_android.mvp.view.activity.videointerview

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.example.sk_android.mvp.view.fragment.videointerview.SeeOfferAccept
import com.example.sk_android.mvp.view.fragment.videointerview.SeeOfferFrag
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
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

class SeeOffer : AppCompatActivity(),ShadowFragment.ShadowClick , TipDialogFragment.TipDialogSelect
,SeeOfferFrag.SeeOfferButton, SeeOfferAccept.OfferAccept{

    var shadowFragment: ShadowFragment?=null
    var tipDialogFragment:TipDialogFragment?=null
    var actionBarNormalFragment:ActionBarNormalFragment?=null

    var relative:LinearLayout?=null

    lateinit var mainBody: LinearLayout
    lateinit var verla: LinearLayout
    lateinit var webVi: WebView
    var offerId = ""
    var resumeId = ""
    var channelMsgId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id1 = 1
        mainBody = verticalLayout {
            id = id1
            verticalLayout {
                val actionBarId=3
                frameLayout{

                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("offer詳細を見る");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                frameLayout {
                    verla = verticalLayout {
                        backgroundResource = R.mipmap.shading
                        scrollView {
                            overScrollMode=OVER_SCROLL_NEVER
                            isVerticalScrollBarEnabled = false
                            backgroundColor=Color.TRANSPARENT
                            verticalLayout {
                                backgroundColor=Color.TRANSPARENT
                                webVi = webView {

                                }.lparams(matchParent,dip(500))
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            setMargins(dip(32), dip(37), dip(32), 0)
                            width = matchParent
                            height = 0
                            weight=1f
                        }

                        val button = 2
                        relative=linearLayout{
                            id = button
                            gravity=Gravity.CENTER_VERTICAL

                        }.lparams(){
                            width = matchParent
                            height= wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }

                }.lparams{
                    width = matchParent
                    height = 0
                    weight=1f
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
        StatusBarUtil.setTranslucentForImageView(this@SeeOffer, 0, actionBarNormalFragment!!.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)



        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {

            finish()//返回
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }

    }
    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("offerId")!=null){
            offerId = intent.getStringExtra("offerId")
            channelMsgId=intent.getStringExtra("channelMsgId")
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getOfferInfo(offerId)
            }
        }
        if(intent.getStringExtra("id")!=null){
            resumeId = intent.getStringExtra("id")
        }
        var url: String
        if(intent.getStringExtra("url")!=null){
            url = intent.getStringExtra("url")
//            val weburl = "https://view.officeapps.live.com/op/view.aspx?src=$url"
            val weburl = "https://docs.google.com/viewer?url=$url"
            webVi.loadUrl(weburl)
            webVi.settings.javaScriptEnabled = true
            webVi.settings.domStorageEnabled = true
            verla.background = null
//            val linearParams = LinearLayout.LayoutParams(this@SeeOffer,null)
//            linearParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//            linearParams.height = dip(500)
//            webVi.layoutParams = linearParams
        }

    }

    //选择拒绝后,弹窗再次确认
    override fun cancel() {
        showAlertDialog()
    }
    //选择确认offer
    override suspend fun demire() {
        updateOfferState(offerId,true)

        var mIntent = Intent()
        mIntent.putExtra("offerState","OK")
        mIntent.putExtra("offerId",offerId)
        mIntent.putExtra("channelMsgId",channelMsgId)

        setResult(RESULT_OK, mIntent)
        finish()//返回
        overridePendingTransition(R.anim.right_out, R.anim.right_out)


    }
    //选择弹窗的按钮
    override suspend fun getTipDialogSelect(b: Boolean) {
        if(b){
            toast("确认拒绝")
            updateOfferState(offerId,false)

            var mIntent = Intent()
            mIntent.putExtra("offerState","REFUSE")
            mIntent.putExtra("offerId",offerId)
            mIntent.putExtra("channelMsgId",channelMsgId)

            setResult(RESULT_OK, mIntent)
            finish()//返回
            overridePendingTransition(R.anim.right_out, R.anim.right_out)


        }else{
            toast("取消拒绝")
        }
        closeAlertDialog()
    }
    //选择转发到邮箱
    override suspend fun email() {
        val i = Intent(Intent.ACTION_SEND)
        // i.setType("text/plain"); //模拟器请使用这行
        i.type = "message/rfc822" // 真机上使用这行
        i.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf("395387944@qq.com")
        )
        i.putExtra(Intent.EXTRA_SUBJECT, "您的建议")
        i.putExtra(Intent.EXTRA_TEXT, "我们很希望能得到您的建议！！！")
        startActivity(
            Intent.createChooser(
                i,
                "Select email application."
            )
        )
    }

    override fun shadowClicked() {

    }

    // 获取offer记录
    private suspend fun getOfferInfo(id: String){
        try {
            val retrofitUils = RetrofitUtils(this@SeeOffer, "https://organization-position.sk.cgland.top/")
            val it = retrofitUils.create(OfferApi::class.java)
                .getUserPrivacy(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                if(it.body()!!.get("state").asString == "PENDING" ){
                    val seebutton = SeeOfferFrag.newInstance()
                    supportFragmentManager.beginTransaction().add(relative!!.id,seebutton).commit()
                }
                if(it.body()!!.get("state").asString == "ACCEPTED"){
                    val seebutton = SeeOfferAccept.newInstance()
                    supportFragmentManager.beginTransaction().add(relative!!.id,seebutton).commit()
                }
                val webHtml = it.body()!!["attributes"].asJsonObject.get("html").asString

                //拼接并转化HTML代码格式
                val stringBuffer = StringBuilder()
                stringBuffer.append("<html>")
                stringBuffer.append("<body style='margin-top:35px'>")
                stringBuffer.append(webHtml)
                stringBuffer.append("</body>")
                stringBuffer.append("</html>")
                //显示HTML代码
                webVi.loadDataWithBaseURL(null,stringBuffer.toString(), "text/html", "UTF-8",null)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
    // 修改offer状态并返回
    private suspend fun updateOfferState(id: String, bool: Boolean){
        val state = if(bool)"ACCEPTED" else "REJECTED"
        try {
            val param = mapOf(
                "state" to state
            )
            val userJson = JSON.toJSONString(param)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@SeeOffer, "https://organization-position.sk.cgland.top/")
            val it = retrofitUils.create(OfferApi::class.java)
                .updateOfferState(id,body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
//                finish()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //打开弹窗
    private fun showAlertDialog(){
        val mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance()
            mTransaction.add(mainBody.id,shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)


        tipDialogFragment= TipDialogFragment.newInstance(1,"拒否すると企業にお知らせし ますが、確かに拒否しますか？")
        mTransaction.add(mainBody.id, tipDialogFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    private fun closeAlertDialog(){
        val mTransaction=supportFragmentManager.beginTransaction()
        if(tipDialogFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(tipDialogFragment!!)
            tipDialogFragment=null
        }


        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()
    }
}