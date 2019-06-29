package com.example.sk_android.mvp.view.activity.videointerview

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
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

class SeeOffer : AppCompatActivity(),ShadowFragment.ShadowClick , TipDialogFragment.TipDialogSelect {

    var shadowFragment: ShadowFragment?=null
    var tipDialogFragment:TipDialogFragment?=null
    lateinit var mainBody: FrameLayout
    lateinit var webVi: WebView
    var offerId = "88229df3-c3d7-4b78-820c-fa7fa55646b0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id1 = 1
        mainBody = frameLayout {
            id = id1
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "offer詳細を見る"
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

                verticalLayout {
                    backgroundResource = R.mipmap.shading
                    scrollView {
                        isVerticalScrollBarEnabled = false
                        verticalLayout {
                            webVi = webView {
//                                loadData(stringBuffer.toString(), "text/html", "UTF-8")
                            }.lparams(matchParent, wrapContent)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams{
                        setMargins(dip(32), dip(110), dip(32), 0)
                        width = matchParent
                        height = dip(410)
                    }

                    relativeLayout {
                        button {
                            backgroundResource = R.drawable.button_shape_grey
                            text = "このofferを拒否する"
                            textSize = 13f
                            textColor = Color.WHITE
                            onClick {
                                showAlertDialog()
                            }
                        }.lparams{
                            width = dip(150)
                            height = dip(50)
                            alignParentLeft()
                        }
                        button {
                            backgroundResource = R.drawable.button_shape_orange
                            text = "このofferを承認する"
                            textSize = 13f
                            textColor = Color.WHITE
                            onClick {
                                toast("このofferを承認する")
                                updateOfferState(offerId,true)
                            }
                        }.lparams{
                            width = dip(150)
                            height = dip(50)
                            alignParentRight()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        setMargins(dip(25),dip(40),dip(25),0)
                    }
                }.lparams{
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

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("offerId")!=null){
            offerId = intent.getStringExtra("offerId")
        }
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {

            getOfferInfo(offerId)
        }

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
                val webHtml = it.body()!!["attributes"].asJsonObject.get("html").asString

                //拼接并转化HTML代码格式
                val stringBuffer = StringBuilder()
                stringBuffer.append("<html>")
                stringBuffer.append("<body>")
                stringBuffer.append(webHtml)
//                val array = webHtml.split("\n")
//                for (str in array){
//                    stringBuffer.append(str)
//                }
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