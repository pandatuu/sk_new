package com.example.sk_android.mvp.view.activity.videointerview

import android.graphics.Color
import android.graphics.Typeface
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
    lateinit var webVi: WebView
    var offerId = "88229df3-c3d7-4b78-820c-fa7fa55646b0"




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
                    verticalLayout {
                        backgroundResource = R.mipmap.shading
                        scrollView {
                            overScrollMode=OVER_SCROLL_NEVER
                            isVerticalScrollBarEnabled = false
                            backgroundColor=Color.TRANSPARENT
                            verticalLayout {
                                backgroundColor=Color.TRANSPARENT
                                webVi = webView {
                                    backgroundColor=Color.TRANSPARENT

                                }.lparams(matchParent, wrapContent){
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
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

//                    verticalLayout {
//
//                    }.lparams {
//                        width = matchParent
//                        height = matchParent
//                    }
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

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("offerId")!=null){
            offerId = intent.getStringExtra("offerId")
        }
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {

            getOfferInfo(offerId)
        }

    }


    override fun cancel() {
        showAlertDialog()
    }

    override suspend fun demire() {
        updateOfferState(offerId,true)
    }

    override suspend fun getTipDialogSelect(b: Boolean) {
        if(b){
            toast("确认拒绝")
            updateOfferState(offerId,false)
        }else{
            toast("取消拒绝")
        }
        closeAlertDialog()
    }

    override suspend fun email() {

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

            if (it.code() == 200) {
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

            if (it.code() == 200) {
//                finish()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //打开弹窗
    fun showAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
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
    fun closeAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
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