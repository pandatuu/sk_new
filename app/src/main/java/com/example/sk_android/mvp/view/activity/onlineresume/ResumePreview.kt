package com.example.sk_android.mvp.view.activity.onlineresume

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.view.activity.company.VideoShowActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectApi
import com.example.sk_android.mvp.view.fragment.common.ShareFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.PushAgent
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMWeb
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView
import retrofit2.HttpException
import java.net.URL


class ResumePreview : AppCompatActivity(), ShareFragment.SharetDialogSelect, ResumePerviewBackground.BackgroundBtn,
    ResumePerviewBarFrag.PerviewBar{

    private var basic: UserBasicInformation? = null
    private lateinit var baseFragment: FrameLayout
    private var wsBackgroundFragment: ResumeBackgroundFragment? = null
    private var resumeBasic: ResumePerviewBasic? = null
    private var wsListFragment: ShareFragment? = null
    private var resumeback: ResumePerviewBackground? = null
    private lateinit var resumeWantedstate: ResumePerviewWantedState
    private lateinit var resumeWanted: ResumePerviewWanted
    private lateinit var resumeJob: ResumePerviewJob
    private lateinit var resumeProject: ResumePerviewProject
    var actionBarNormalFragment: ResumePerviewBarFrag?=null
    private lateinit var resumeEdu: ResumePerviewEdu
    private val mainId = 1
    private var resumeId: String = ""
    private var name: String = ""
    private var videoUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val bottomBeha = BottomSheetBehavior<View>(this@ResumePreview, null)
        bottomBeha.peekHeight = dip(370)

        baseFragment = frameLayout {
            id = mainId
            coordinatorLayout {
                appBarLayout {
                    val actionBarId=10
                    frameLayout{
                        id=actionBarId
                        actionBarNormalFragment= ResumePerviewBarFrag.newInstance()
                        supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()
                    }.lparams {
                        width= matchParent
                        height= wrapContent
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                val back = 8
                frameLayout {
                    id = back
                    resumeback = ResumePerviewBackground.newInstance()
                    supportFragmentManager.beginTransaction().add(back, resumeback!!).commit()
                }.lparams(matchParent, dip(370)) {
                    topMargin = dip(54)
                }
                val resumeListid = 2
                nestedScrollView {
                    id = resumeListid
                    backgroundResource = R.drawable.twenty_three_radius_top
                    val basic = 3
                    val state = 9
                    val want = 4
                    val job = 5
                    val project = 6
                    val edu = 7
                    topPadding = dip(5)
                    verticalLayout {
                        frameLayout {
                            id = basic
                            resumeBasic = ResumePerviewBasic.newInstance()
                            supportFragmentManager.beginTransaction().add(basic, resumeBasic!!).commit()
                        }
                        frameLayout {
                            id = state
                            resumeWantedstate = ResumePerviewWantedState.newInstance()
                            supportFragmentManager.beginTransaction().add(state, resumeWantedstate).commit()
                        }
                        frameLayout {
                            id = want
                            resumeWanted = ResumePerviewWanted.newInstance()
                            supportFragmentManager.beginTransaction().add(want, resumeWanted).commit()
                        }
                        frameLayout {
                            id = job
                            resumeJob = ResumePerviewJob.newInstance()
                            supportFragmentManager.beginTransaction().add(job, resumeJob).commit()
                        }
                        frameLayout {
                            id = project
                            resumeProject = ResumePerviewProject.newInstance()
                            supportFragmentManager.beginTransaction().add(project, resumeProject).commit()
                        }
                        frameLayout {
                            id = edu
                            resumeEdu = ResumePerviewEdu.newInstance(resumeBasic!!)
                            supportFragmentManager.beginTransaction().add(edu, resumeEdu).commit()
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    behavior = bottomBeha
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
        StatusBarUtil.setTranslucentForImageView(this@ResumePreview, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@ResumePreview,ResumeEdit::class.java)//返回
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getResumeId()
            getUserJobState()
        }
    }

    //点击分享按钮
    override fun openShare() {
        addListFragment()
    }
    //点击分享的选项
    override suspend fun getSelectedItem(index: Int) {

        UMConfigure.init(this,"5cdcc324570df3ffc60009c3"
            ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"")
        when (index) {
            0 -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    val mPermissionList = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS
                    )
                    ActivityCompat.requestPermissions(this, mPermissionList, 123)
                }
                val content = "${name}の履歴書---"
                //https://sk.cgland.top/appuri.html?type=resume&resume_id=
                val web = UMWeb("$videoUrl\n https://sk.cgland.top/appuri.html?type=resume&resume_id=$resumeId")
//                web.title = content//标题
//                web.description = "欢迎打开skAPP"//描述

                ShareAction(this@ResumePreview)
                    .setPlatform(SHARE_MEDIA.LINE)//传入平台
                    .withMedia(web)//分享内容
                    .withText(content)
                    .setShareboardclickCallback { _, _ -> println("11111111111111111111111111111111111111111 ") }
                    .share()

                //调用创建分享信息接口
                createShareMessage("LINE", "user-online-resume", content)
            }
            1 -> {
                val content = "${name}の履歴書---$videoUrl\n"

                val builder = TweetComposer.Builder(this@ResumePreview)
                builder.text(content)
                builder.url(URL("https://sk.cgland.top/appuri.html?type=resume&resume_id=$resumeId"))
                    .show()

                //调用创建分享信息接口
                createShareMessage("TWITTER","title",content)
            }
            else -> {
                closeAlertDialog()
            }
        }
    }

    override fun clickButton() {
        if(videoUrl!=""){
            val intent = Intent(this@ResumePreview, VideoShowActivity::class.java)
            intent.putExtra("url", videoUrl)
            startActivity(intent)
            this@ResumePreview.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    // 获取用户求职状态
    private suspend fun getUserJobState() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, this.getString(R.string.userUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserWantedState()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val model = it.body()!!.asJsonObject
                resumeWantedstate.setJobState(model.get("state").asString)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
    //获取用户在线简历
    private suspend fun getResumeId() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserResume("ONLINE")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                if (page.data.size > 0) {
                    resumeId = page.data[0].get("id").asString
                    name = page.data[0].get("name").asString
                    val id = 8
                    val changedContent = page.data[0].get("changedContent").asJsonObject
                    videoUrl = if (changedContent != null && changedContent.size() > 0) {
                        page.data[0].get("changedContent")!!.asJsonObject.get("videoURL").asString
                    } else {
                        page.data[0].get("videoURL").asString
                    }
                    supportFragmentManager.beginTransaction().replace(id, resumeback!!).commit()
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
    //打开弹窗
    private fun addListFragment() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (wsBackgroundFragment == null) {
            wsBackgroundFragment = ResumeBackgroundFragment.newInstance()
            mTransaction.add(baseFragment.id, wsBackgroundFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        wsListFragment = ShareFragment.newInstance()
        mTransaction.add(baseFragment.id, wsListFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (wsListFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(wsListFragment!!)
            wsListFragment = null
        }

        if (wsBackgroundFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null
        }
        mTransaction.commit()
    }

    //创建分享的信息
    private suspend fun createShareMessage(platform: String, title: String, content: String) {
        try {
            val params = mapOf(
                "deviceType" to "ANDROID",
                "platform" to platform,
                "title" to title,
                "content" to content,
                "targetEntityId" to resumeId,
                "targetEntityType" to "USER"
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@ResumePreview, this.getString(R.string.pushUrl))
            val it = retrofitUils.create(JobSelectApi::class.java)
                .createShare(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
            }
        } catch (throwable: Throwable) {
            println(throwable)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK) {
            val intent = Intent(this@ResumePreview,ResumeEdit::class.java)//返回
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
            return true
        }
        return false
    }

}