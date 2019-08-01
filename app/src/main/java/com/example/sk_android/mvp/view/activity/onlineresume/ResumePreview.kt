package com.example.sk_android.mvp.view.activity.onlineresume

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobWantedModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
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


class ResumePreview : AppCompatActivity(), ShareFragment.SharetDialogSelect, ResumePreviewBackground.BackgroundBtn,
    ResumePerviewBarFrag.PerviewBar{

    private var basic: UserBasicInformation? = null
    private lateinit var baseFragment: FrameLayout
    private var wsBackgroundFragment: ResumeBackgroundFragment? = null
    private var resumeBasic: ResumePerviewBasic? = null
    private var wsListFragment: ShareFragment? = null
    private var resumeback: ResumePreviewBackground? = null
    private lateinit var resumeWantedstate: ResumePerviewWantedState
    private lateinit var resumeWanted: ResumePerviewWanted
    private lateinit var resumeJob: ResumePerviewJob
    private lateinit var resumeProject: ResumePerviewProject
    var actionBarNormalFragment: ResumePerviewBarFrag?=null
    private lateinit var resumeEdu: ResumePerviewEdu
    private val mainId = 1
    private var resumeId: String = ""
    private var videoUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val bottomBeha = BottomSheetBehavior<View>(this@ResumePreview, null)
        bottomBeha.peekHeight = dip(370)

        var imageurl = ""
        if (intent.getStringExtra("imageUrl") != null) {
            imageurl = intent.getStringExtra("imageUrl")
        }
        baseFragment = frameLayout {
            id = mainId
            coordinatorLayout {
                appBarLayout {
                    val actionBarId=10
                    frameLayout{
                        id=actionBarId
                        actionBarNormalFragment= ResumePerviewBarFrag.newInstance("視覚デザイン履歴1");
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
                    resumeback = ResumePreviewBackground.newInstance("", "IMAGE")
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
                            resumeWanted = ResumePerviewWanted.newInstance(null, null, null)
                            supportFragmentManager.beginTransaction().add(want, resumeWanted).commit()
                        }
                        frameLayout {
                            id = job
                            resumeJob = ResumePerviewJob.newInstance(null)
                            supportFragmentManager.beginTransaction().add(job, resumeJob).commit()
                        }
                        frameLayout {
                            id = project
                            resumeProject = ResumePerviewProject.newInstance(null)
                            supportFragmentManager.beginTransaction().add(project, resumeProject).commit()
                        }
                        frameLayout {
                            id = edu
                            resumeEdu = ResumePerviewEdu.newInstance(null)
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
            getUser()
            getUserJobState()
            getUserWanted()
            getJobByResumeId(resumeId)
            getProjectByResumeId(resumeId)
            getEduByResumeId(resumeId)
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
        val content = "${basic!!.displayName}的简历"
        when (index) {
            0 -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    val mPermissionList = arrayOf<String>(
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

                val web = UMWeb("http://192.168.3.78?type=resumeId&position_id=$resumeId");
                web.setTitle(content);//标题
                web.setDescription("欢迎打开skAPP");//描述

                ShareAction(this@ResumePreview)
                    .setPlatform(SHARE_MEDIA.LINE)//传入平台
                    .withMedia(web)//分享内容
                    .setShareboardclickCallback { _, _ -> println("11111111111111111111111111111111111111111 ") }
                    .share()

                //调用创建分享信息接口
                createShareMessage("LINE", "user-online-resume", content)
            }
            1 -> {
                val content = "${basic!!.displayName}的简历"

                val builder = TweetComposer.Builder(this@ResumePreview)
                builder.text(content)
                builder.url(URL("http://192.168.3.78?type=resumeId&position_id=$resumeId"))
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

    // 获取用户基本信息
    private suspend fun getUser() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserSelf()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                if (it.body()?.get("changedContent") != null) {
                    var json = it.body()?.get("changedContent")!!.asJsonObject
                    basic = Gson().fromJson<UserBasicInformation>(json, UserBasicInformation::class.java)
                    basic?.id= it.body()?.get("id")!!.asString
                    resumeBasic?.setUserBasicInfo(basic!!)
                }else{
                    val json = it.body()?.asJsonObject
                    basic = Gson().fromJson<UserBasicInformation>(json, UserBasicInformation::class.java)
                    resumeBasic?.setUserBasicInfo(basic!!)
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 获取用户求职期望
    private suspend fun getUserWanted() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserWanted()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val list = mutableListOf<JobWantedModel>()
                val jobName = mutableListOf<List<String>>()
                val areaName = mutableListOf<List<String>>()
                for (item in it.body()!!.asJsonArray) {
                    val model = Gson().fromJson(item, JobWantedModel::class.java)
                    val jobList = mutableListOf<String>()
                    val areaList = mutableListOf<String>()
                    //获取行业信息
                    for (index in model.industryIds.indices) {
                        if (index == 0) {
                            val jobArray = getUserJobName(model.industryIds[index])
                            val jobname = if(jobArray.size>0) jobArray[0] else ""
                            jobList.add(jobname)
                            if(jobArray.size>1){
                                jobList.add(jobArray[1])
                            }
                        }
                    }
                    jobName.add(jobList)
                    //获取地区信息
                    for (area in model.areaIds) {
                        areaList.add(getUserAddress(area))
                    }
                    areaName.add(areaList)
                    list.add(model)
                }
                val want = 4
                resumeWanted = ResumePerviewWanted.newInstance(list, jobName, areaName)
                supportFragmentManager.beginTransaction().replace(want, resumeWanted).commit()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 获取用户求职状态
    private suspend fun getUserJobState() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://user.sk.cgland.top/")
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
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserResume("ONLINE")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                if (page.data != null && page.data.size > 0) {
                    resumeId = page.data[0].get("id").asString
                    val id = 8
                    val changedContent = page.data[0].get("changedContent").asJsonObject
                    if (changedContent != null && changedContent.size() > 0) {
                        val imageUrl =
                            page.data[0].get("changedContent")!!.asJsonObject.get("videoThumbnailURL").asString
                        videoUrl = page.data[0].get("changedContent")!!.asJsonObject.get("videoURL").asString
                        if (imageUrl != "") {
                            resumeback = ResumePreviewBackground.newInstance(imageUrl, "IMAGE")
                        } else {
                            resumeback = ResumePreviewBackground.newInstance(videoUrl, "VIDEO")
                        }
                    } else {
                        val imageUrl = page.data[0].get("videoThumbnailURL").asString
                        videoUrl = page.data[0].get("videoURL").asString
                        if (imageUrl != "") {
                            resumeback = ResumePreviewBackground.newInstance(imageUrl, "IMAGE")
                        } else {
                            resumeback = ResumePreviewBackground.newInstance(videoUrl, "VIDEO")
                        }
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

    // 获取用户求职期望的行业名字
    private suspend fun getUserJobName(id: String): ArrayList<String> {
        try {
            val array = arrayListOf<String>()
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://industry.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserJobName(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val model = it.body()!!.asJsonObject
                array.add(model.get("name").asString)
                if(model.get("parentId")!=null){
                    val retrofitUils = RetrofitUtils(this@ResumePreview, "https://industry.sk.cgland.top/")
                    val it = retrofitUils.create(OnlineResumeApi::class.java)
                        .getUserJobName(model.get("parentId").asString)
                        .subscribeOn(Schedulers.io())
                        .awaitSingle()
                    if(it.code() in 200..299){
                        if(it.body()!=null)
                            array.add(it.body()!!.get("name").asString)
                    }
                }
                return array
            }
            return arrayListOf()
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
            return arrayListOf()
        }
    }

    // 获取用户求职期望的地区名字
    private suspend fun getUserAddress(id: String): String {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://basic-info.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserAddress(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val model = it.body()!!.asJsonObject
                return model.get("name").asString
            }
            return ""
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
            return ""
        }
    }

    //根据简历ID获取工作经历
    private suspend fun getJobByResumeId(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getJobById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val list = mutableListOf<JobExperienceModel>()
                for (item in it.body()!!.asJsonArray) {
                    list.add(Gson().fromJson(item, JobExperienceModel::class.java))
                }
                val job = 5
                resumeJob = ResumePerviewJob.newInstance(list)
                supportFragmentManager.beginTransaction().replace(job, resumeJob).commit()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //根据简历ID获取项目经历
    private suspend fun getProjectByResumeId(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getProjectById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val list = mutableListOf<ProjectExperienceModel>()
                for (item in it.body()!!.asJsonArray) {
                    list.add(Gson().fromJson(item, ProjectExperienceModel::class.java))
                }
                val edu = 6
                resumeProject = ResumePerviewProject.newInstance(list)
                supportFragmentManager.beginTransaction().replace(edu, resumeProject).commit()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //根据简历ID获取教育经历
    private suspend fun getEduByResumeId(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getEduById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val list = mutableListOf<EduExperienceModel>()
                for (item in it.body()!!.asJsonArray) {
                    list.add(Gson().fromJson(item, EduExperienceModel::class.java))
                }
                if (list.size > 0) {
                    val edubackground = list[0].educationalBackground
                    resumeBasic?.setBackground(edubackground)
                }
                val edu = 7
                resumeEdu = ResumePerviewEdu.newInstance(list)
                supportFragmentManager.beginTransaction().replace(edu, resumeEdu).commit()
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

            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://push.sk.cgland.top/")
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
}