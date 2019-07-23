package com.example.sk_android.mvp.view.activity.resume

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.resume.SrActionBarFragment
import com.example.sk_android.mvp.view.fragment.resume.SrMainBodyFragment
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*

class SendResumeActivity :AppCompatActivity(),SrActionBarFragment.newTool{
    lateinit var srActionBarFragment:SrActionBarFragment
    lateinit var condition:String
    lateinit var srMainBodyFragment:SrMainBodyFragment
    var resumeUrl = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle = intent.extras!!.get("bundle") as Bundle
        val resume = bundle.getParcelable<Parcelable>("resume") as Resume
        resumeUrl = resume.downloadURL

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        var mainScreenId=1

        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    srActionBarFragment = SrActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, srActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    srMainBodyFragment = SrMainBodyFragment.newInstance(resume)
                    supportFragmentManager.beginTransaction().replace(id, srMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }

        init()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(srActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SendResumeActivity, 0, srActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        srActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    private fun init(){

    }

    @SuppressLint("CheckResult")
    override fun sendEmail() {
        var result = srMainBodyFragment.getEmail()
        if(result["email"]!!.isNotEmpty()){

            var htmlResult = "这是我的简历,请下载并查看<br/>" + "<a href = ${result["download"]}>${result["name"]}</a>"
            val sendParam = mapOf(
               "type" to "ATTACHMENTS_RESUME",
                "to" to result["email"],
                "subject" to "【SK】${result["name"]}",
                "html" to htmlResult
            )

            val sendJson = JSON.toJSONString(sendParam)

            val body = RequestBody.create(json, sendJson)

            var mailRetrofitUils = RetrofitUtils(this, this.getString(R.string.mailUrl))

            mailRetrofitUils.create(PersonApi::class.java)
                .sendResume(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if(it.code() in 200..299){
                        toast(this.getString(R.string.sendResumeResult))
                        finish()
                        overridePendingTransition(R.anim.left_in, R.anim.right_out)
                    }else{
                        toast(this.getString(R.string.sendResumeError))
                    }
                },{})

        }
    }

}