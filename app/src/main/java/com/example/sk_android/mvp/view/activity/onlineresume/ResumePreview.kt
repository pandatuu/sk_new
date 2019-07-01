package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobWantedModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import retrofit2.HttpException

class ResumePreview : AppCompatActivity(),ResumeShareFragment.CancelTool, ResumePreviewBackground.BackgroundBtn{
    override fun clickButton() {
        toast("1111111")
    }

    override fun cancelList() {
        closeAlertDialog()
    }


    private var basic: UserBasicInformation? = null
    private lateinit var baseFragment: FrameLayout
    private var wsBackgroundFragment: ResumeBackgroundFragment? = null
    private var resumeBasic: ResumePerviewBasic? = null
    private var wsListFragment: ResumeShareFragment? = null
    private var resumeback: ResumePreviewBackground? = null
    private lateinit var resumeWantedstate: ResumePerviewWantedState
    private lateinit var resumeWanted: ResumePerviewWanted
    private lateinit var resumeJob: ResumePerviewJob
    private lateinit var resumeProject: ResumePerviewProject
    private lateinit var resumeEdu: ResumePerviewEdu
    private val mainId = 1
    private var resumeId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomBeha =  BottomSheetBehavior<View>(this@ResumePreview,null)
        bottomBeha.peekHeight = dip(370)

        var imageurl = ""
        if(intent.getStringExtra("imageUrl")!=null) {
           imageurl = intent.getStringExtra("imageUrl")
        }
        baseFragment = frameLayout {
            id = mainId
            coordinatorLayout {
                appBarLayout {
                    relativeLayout {
                        backgroundResource = R.drawable.title_bottom_border
                        toolbar {
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back
                            onClick {
                                finish()
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_share_zwxq
                            onClick {
                                addListFragment()
                            }
                        }.lparams {
                            width = dip(20)
                            height = dip(20)
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams(matchParent, matchParent){
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                val back = 8
                frameLayout {
                    id = back
                    resumeback = if (imageurl != "") {
                        ResumePreviewBackground.newInstance(imageurl, true)
                    } else {
                        ResumePreviewBackground.newInstance(null, true)
                    }
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

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getResumeId()
            getUser()
            getUserJobState()
//            getUserWanted()
            getJobByResumeId(resumeId)
            getProjectByResumeId(resumeId)
            getEduByResumeId(resumeId)
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

            if (it.code() == 200) {
                if (basic == null) {
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

            if (it.code() == 200) {
                val list = mutableListOf<JobWantedModel>()
                val jobName = mutableListOf<List<String>>()
                val areaName = mutableListOf<List<String>>()
                for (item in it.body()!!.asJsonArray) {
                    val model = Gson().fromJson(item, JobWantedModel::class.java)
                    val jobList = mutableListOf<String>()
                    val areaList = mutableListOf<String>()
                    for (index in model.industryIds.indices) {
                        if (index == 0) {
                            jobList.add(getUserJobName(model.industryIds[index]))
                        }
                    }
                    jobName.add(jobList)
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

            if (it.code() == 200) {
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

            if (it.code() == 200) {
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                resumeId = page.data[0].get("id").asString
                val url = page.data[0].get("videoURL").asString
                if (url != null) {
                    val id = 8
                    resumeback = ResumePreviewBackground.newInstance(url, false)
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
    private suspend fun getUserJobName(id: String): String {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://industry.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserJobName(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
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

    // 获取用户求职期望的地区名字
    private suspend fun getUserAddress(id: String): String {
        try {
            val retrofitUils = RetrofitUtils(this@ResumePreview, "https://basic-info.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserAddress(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
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

            if (it.code() == 200) {
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

            if (it.code() == 200) {
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

            if (it.code() == 200) {
                val list = mutableListOf<EduExperienceModel>()
                for (item in it.body()!!.asJsonArray) {
                    list.add(Gson().fromJson(item, EduExperienceModel::class.java))
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
    private fun addListFragment(){
        val mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(wsBackgroundFragment==null){
            wsBackgroundFragment= ResumeBackgroundFragment.newInstance()
            mTransaction.add(baseFragment.id, wsBackgroundFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)

        wsListFragment= ResumeShareFragment.newInstance()
        mTransaction.add(baseFragment.id,wsListFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    private fun closeAlertDialog(){
        val mTransaction=supportFragmentManager.beginTransaction()
        if(wsListFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(wsListFragment!!)
            wsListFragment=null
        }

        if(wsBackgroundFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(wsBackgroundFragment!!)
            wsBackgroundFragment=null
        }
        mTransaction.commit()
    }
}