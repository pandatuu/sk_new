package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.umeng.message.PushAgent
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.HelpFeedbackAdapter
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import org.jetbrains.anko.sdk25.coroutines.onClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.adapter.rxjava2.HttpException


class HelpFeedbackActivity : AppCompatActivity() {

    private lateinit var recycle: RecyclerView
    var retrofitUils = RetrofitUtils(this,"https://help.sk.cgland.top/")
    var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NDZkNjgxMS1lYWRlLTQ4N2QtODhjYi1hNTZjMTIwMzMxY2EiLCJ1c2VybmFtZSI6ImxpemhlbmNodWFuIiwidGltZXN0YW1wIjoxNTU5NTQxNzg5OTc1LCJpYXQiOjE1NTk1NDE3ODl9.veMqePNpWbTpQPyWMqTU-8Kb-FjCD_uvIdPJNTSqeMD4PcykTdAJYQIJfkYeqv1eP64WfFltgm0OXdtSpppG3JWfyrK0VHt7R_UdU4yV97rK5CLKp8Ax4-cB_EUZx8Hm63mviJ_BsToV7n1rcc1SI_-CUdMJTIobUlcBPc_J0UuRVhFhkD2bLN1bw1LCDbAj25Qm17EUpot0Tre4OZGeqi3ugbkOscY_08f-_gp-EOuhhiEGfi8M64u1Azslcw41VdHkmeEWPqJMh0fNqC4ttNej3Dzg5bzqdn67pawD2qG8qqw0upIcn4ZOQRCxUuRV6hPG-vhxA02AOMJjKepebQ"

    override fun onStart() {
        super.onStart()
//        getToken()
        getInformation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            relativeLayout {
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
                        text = "よくある質問"
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
                    scrollView {
                        relativeLayout {
                            recycle = recyclerView {
                                layoutManager =
                                    LinearLayoutManager(this@HelpFeedbackActivity) as RecyclerView.LayoutManager?
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(120)
                    topMargin = dip(54)
                }
                relativeLayout {
                    verticalLayout {
                        textView {
                            text = "私のフィードバック"
                            backgroundResource = R.drawable.button_shape
                            textColor = Color.parseColor("#FF202020")
                            gravity = Gravity.CENTER
                            onClick {
                                toast("私のフィードバック")
                                val intent = Intent(this@HelpFeedbackActivity, MyFeedbackActivity::class.java)
                                intent.putExtra("tokenId",token)
                                startActivity(intent)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            bottomMargin = dip(10)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        textView {
                            backgroundResource = R.drawable.button_shape_orange
                            text = "フィードバックとアドバイス"
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER
                            onClick {
                                toast("フィードバックとアドバイス")
                                val intent = Intent(this@HelpFeedbackActivity, FeedbackSuggestionsActivity::class.java)
                                startActivity(intent)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = dip(114)
                    alignParentBottom()
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.parseColor("#FFFFFF")
            }
        }

    }

    //获取全部帮助信息
    private fun getInformation() {
        val list = mutableListOf<HelpModel>()
        retrofitUils.create(HelpFeedbackApi::class.java)
            .getHelpInformation()
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // Json转对象
                val page = Gson().fromJson(it,PagedList::class.java)
                val obj = page.data.toMutableList()
                for (item in obj) {
                    println(item)
                    val model =  Gson().fromJson(item,HelpModel::class.java)
                        list.add(model)
                }
                recycle.adapter = HelpFeedbackAdapter(list, this@HelpFeedbackActivity)
            }, {
                println("失败！！！！！！！！！")
            })
    }

    //　用户登录，获取token
    private fun getToken(){
        //构造HashMap
        val params = HashMap<String, String>()
        params["username"]= "lizhenchuan"
        params["password"] = "lizhenchuan"
        params["deviceType"] = "ANDROID"
        params["system"] = "WEB"
        params["scope"] = "offline_access"
        val userJson = JSON.toJSONString(params)
        var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(json,userJson)
        println("body------"+userJson)
        retrofitUils.create(HelpFeedbackApi::class.java)
            .loginUser(body)
            .map { it ?: "" }
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("111111111111111111111111111")

            },{
                println("token--失败！！！！！！！！！")
                println(it.toString())
            })
    }

}