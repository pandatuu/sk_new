package com.example.sk_android.mvp.view.activity.onlineresume

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobState
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobWantedModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UpLoadApi
import com.google.gson.Gson
import com.lcw.library.imagepicker.ImagePicker
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import retrofit2.HttpException
import java.io.*
import java.util.*

class ResumeEdit : AppCompatActivity(), ResumePreviewBackground.BackgroundBtn,
    ResumeEditBasic.UserResume, ResumeEditEdu.EduFrag,
    ResumeEditProject.ProjectFrag, ResumeEditJob.JobFrag,
    ResumeEditWanted.WantedFrag, ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect, ResumeEditWantedState.WantedFrag {

    private lateinit var myDialog: MyDialog
    private var basic: UserBasicInformation? = null
    private var mImagePaths: ArrayList<String>? = null
    private var resumeback: ResumePreviewBackground? = null
    private lateinit var resumeBasic: ResumeEditBasic
    private var shadowFragment: ShadowFragment? = null
    private var editAlertDialog: BottomSelectDialogFragment? = null
    private lateinit var resumeWantedstate: ResumeEditWantedState
    private lateinit var resumeWanted: ResumeEditWanted
    private lateinit var resumeJob: ResumeEditJob
    private lateinit var resumeProject: ResumeEditProject
    private lateinit var resumeEdu: ResumeEditEdu
    private var resumeId: String = ""
    private var vedioUrl: String = ""
    private val mainId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomBeha = BottomSheetBehavior<View>(this@ResumeEdit, null)
        bottomBeha.peekHeight = dip(370)

        frameLayout {
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
                                val intent = Intent(this@ResumeEdit, PersonSetActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "視覚デザイン履歴1"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                            textSize = 16f
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerInParent()
                        }
                        textView {
                            text = "プレビュー"
                            textColor = Color.parseColor("#FFFFB706")
                            textSize = 14f
                            onClick {
                                jumpNextPage()
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentRight()
                            centerInParent()
                            rightMargin = dip(15)
                        }
                    }.lparams(matchParent, matchParent) {
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                val back = 8
                frameLayout {
                    id = back
                    resumeback = if (vedioUrl != "") {
                        ResumePreviewBackground.newInstance(vedioUrl, true)
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
                            resumeBasic = ResumeEditBasic.newInstance()
                            supportFragmentManager.beginTransaction().add(basic, resumeBasic!!).commit()
                        }
                        frameLayout {
                            id = state
                            resumeWantedstate = ResumeEditWantedState.newInstance()
                            supportFragmentManager.beginTransaction().add(state, resumeWantedstate).commit()
                        }
                        frameLayout {
                            id = want
                            resumeWanted = ResumeEditWanted.newInstance(null, null, null)
                            supportFragmentManager.beginTransaction().add(want, resumeWanted).commit()
                        }
                        frameLayout {
                            id = job
                            resumeJob = ResumeEditJob.newInstance(null)
                            supportFragmentManager.beginTransaction().add(job, resumeJob).commit()
                        }
                        frameLayout {
                            id = project
                            resumeProject = ResumeEditProject.newInstance(null)
                            supportFragmentManager.beginTransaction().add(project, resumeProject).commit()
                        }
                        frameLayout {
                            id = edu
                            resumeEdu = ResumeEditEdu.newInstance(null)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mImagePaths = data!!.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES) as ArrayList<String>
            val stringBuffer = StringBuffer()
            stringBuffer.append("当前选中图片路径：\n\n")
            for (i in mImagePaths!!) {
                stringBuffer.append(i + "\n\n")
            }
            modifyPictrue()
        }
    }

    override fun onResume() {
        super.onResume()

        showLoading()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getResumeId()
            getUser()
            getUserJobState()
//            getUserWanted()
            getJobByResumeId(resumeId)
            getProjectByResumeId(resumeId)
            getEduByResumeId(resumeId)

            hideLoading()
        }
    }

    //跳转基本信息编辑页面
    override fun jumpToBasic() {
        val intent = Intent(this@ResumeEdit, EditBasicInformation::class.java)
        intent.putExtra("resumeId", "3bff6ea9-08a6-4947-bc4a-c85312957885")
        startActivity(intent)
    }

    //点击选择视频按钮
    override fun clickButton() {
        chooseVideo()
    }

    //每次修改图片list,重新刷新fragment
    private fun modifyPictrue() {
        if (mImagePaths != null && mImagePaths!!.size > 0) {
            vedioUrl = mImagePaths?.get(0)!!
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                upLoadVideo(vedioUrl)
            }
        }
        val scroll = 8
        if (resumeback != null) {
            resumeback = ResumePreviewBackground.newInstance(vedioUrl, true)
            supportFragmentManager.beginTransaction().replace(scroll, resumeback!!).commit()
        } else {
            resumeback = ResumePreviewBackground.newInstance(vedioUrl, true)
            supportFragmentManager.beginTransaction().replace(scroll, resumeback!!).commit()
        }
    }

    //跳转预览页面
    private fun jumpNextPage() {
        // 给bnt1添加点击响应事件
        val intent = Intent(this@ResumeEdit, ResumePreview::class.java)
        if(mImagePaths!=null){
            intent.putExtra("imageUrl", mImagePaths!![0])
            mImagePaths = null
        }
        //启动
        startActivity(intent)
    }

    //选择视频
    private fun chooseVideo() {
        ImagePicker.getInstance()
            .setTitle("写真/ビデオを選択する")
            .showCamera(false)
            .showImage(false)
            .setMaxCount(1)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@ResumeEdit, 1)
    }

    //选择当前工作状态,打开弹窗
    override fun jobState() {
        toast("1111111111")
        openDialog()
    }

    //点击弹窗关闭按钮
    override fun getBottomSelectDialogSelect() {
        closeDialog()
    }

    //选择当前工作状态,改变字段
    override fun getback(index: Int, list: MutableList<String>) {
        resumeWantedstate.setJobState(list[index])

        when (list[index]) {
            "離職中" -> {
                GlobalScope.launch(
                    Dispatchers.Main,
                    CoroutineStart.DEFAULT
                ) { updateUserJobState(JobState.OFF) }
            }
            "１か月以内には退職予定" -> {
                GlobalScope.launch(
                    Dispatchers.Main,
                    CoroutineStart.DEFAULT
                ) { updateUserJobState(JobState.ON_NEXT_MONTH) }
            }
            "良い条件が見つかり次第" -> {
                GlobalScope.launch(
                    Dispatchers.Main,
                    CoroutineStart.DEFAULT
                ) { updateUserJobState(JobState.ON_CONSIDERING) }
            }
            "その他" -> {
                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) { updateUserJobState(JobState.OTHER) }
            }
        }
        closeDialog()
    }

    //点击黑色透明背景
    override fun shadowClicked() {
        closeDialog()
    }

    //点击某一行工作经历,跳转
    override fun JobClick(jobId: String) {
        val intent = Intent(this@ResumeEdit, EditJobExperience::class.java)
        intent.putExtra("jobId", jobId)
        startActivity(intent)
    }

    //点击某一行项目经历,跳转
    override fun projectClick(projectId: String) {
        val intent = Intent(this@ResumeEdit, EditProjectExperience::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }

    //点击某一行教育经历,跳转
    override fun eduClick(eduId: String) {
        val intent = Intent(this@ResumeEdit, EditEduExperience::class.java)
        intent.putExtra("eduId", eduId)
        startActivity(intent)
    }

    //添加工作经历
    override fun addJobClick() {
        val intent = Intent(this@ResumeEdit, AddJobExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivity(intent)
    }

    //添加项目经历
    override fun addProjectClick() {
        val intent = Intent(this@ResumeEdit, AddProjectExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivity(intent)
    }

    //添加教育经历
    override fun addEduClick() {
        val intent = Intent(this@ResumeEdit, AddEduExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivity(intent)
    }

    //选择某一行求职意向
    override fun wantedClick() {

    }

    //选择添加求职意向
    override fun addWanted() {

    }

    // 获取用户基本信息
    private suspend fun getUser() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserSelf()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                if (basic == null) {
                    val json = it.body()?.asJsonObject
                    basic = Gson().fromJson<UserBasicInformation>(json, UserBasicInformation::class.java)
                    resumeBasic.setUserBasicInfo(basic!!)
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://user.sk.cgland.top/")
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
                resumeWanted = ResumeEditWanted.newInstance(list, jobName, areaName)
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserWantedState()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                val model = it.body()!!.asJsonObject
                resumeWantedstate.setJobState(model.get("state").asString)
                return
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 更改用户求职状态
    private suspend fun updateUserJobState(state: JobState) {
        try {
            val params = mapOf<String, Any?>(
                "attributes" to mapOf<String, Any>(),
                "state" to "$state"
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateUserWantedState(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                resumeWantedstate.setJobState(state.toString())
                return
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
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
                    resumeback = ResumePreviewBackground.newInstance(url, true)
                    supportFragmentManager.beginTransaction().replace(id, resumeback!!).commit()
                }
                return
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //更新用户在线简历信息
    private suspend fun updateUserResume(id: String, url: String) {
        try {
            val params = mapOf(
                "attributes" to mapOf<String, Any>(),
                "type" to "ONLINE",
                "videoURL" to url
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateUserResume(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                println("22222222222")
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://industry.sk.cgland.top/")
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://basic-info.sk.cgland.top/")
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
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
                resumeJob = ResumeEditJob.newInstance(list)
                supportFragmentManager.beginTransaction().replace(job, resumeJob).commit()
                return
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
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
                resumeProject = ResumeEditProject.newInstance(list)
                supportFragmentManager.beginTransaction().replace(edu, resumeProject).commit()
                return
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
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
                resumeEdu = ResumeEditEdu.newInstance(list)
                supportFragmentManager.beginTransaction().replace(edu, resumeEdu).commit()
                return
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //上传视频
    private suspend fun upLoadVideo(url: String) {
        try {
            val videoFile = File(url)
            val byteArray: ByteArray?
            val size = videoFile.length()
            if (size > 50 * 1024 * 1024 || size == 0L) {
                toast("视频超过上限50M")
            } else {
                val videoBody = when (videoFile.extension.toLowerCase()) {
                    "mp4" -> {
                        byteArray = getByteByVideo(url)
                        FormBody.create(MimeType.VIDEO_MP4, byteArray!!)
                    }
                    "avi" -> {
                        byteArray = getByteByVideo(url)
                        FormBody.create(MimeType.VIDEO_AVI, byteArray!!)
                    }
                    "wmv" -> {
                        byteArray = getByteByVideo(url)
                        FormBody.create(MimeType.VIDEO_WMV, byteArray!!)
                    }
                    else -> {
                        byteArray = getByteByVideo(url)
                        FormBody.create(MimeType.VIDEO_FLV, byteArray!!)
                    }
                }
                val multipart = MultipartBody.Builder()
                    .setType(MimeType.MULTIPART_FORM_DATA)
                    .addFormDataPart("bucket", "user-resume-video")
                    .addFormDataPart("type", "VIDEO")
                    .addFormDataPart("file", videoFile.name, videoBody)
                    .build()
                println("---------------------" + videoFile.name + ":" + byteArray.size)

                val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://storage.sk.cgland.top/")
                val it = retrofitUils.create(UpLoadApi::class.java)
                    .upLoadVideo(multipart)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .awaitSingle()
                if (it.code() == 200) {
                    toast("上传视频完毕")
                    updateUserResume(resumeId, it.body()!!.get("url").asString)
                }
            }
        } catch (e: Throwable) {
            if (e is HttpException) {
                println(e)
            }
        }
    }

    //打开求职状态弹窗
    private fun openDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(mainId, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        val list = mutableListOf("離職中", "１か月以内には退職予定", "良い条件が見つかり次第", "その他")

        editAlertDialog = BottomSelectDialogFragment.newInstance("求職状態", list)
        mTransaction.add(mainId, editAlertDialog!!)
        mTransaction.commit()
    }

    //关闭求职状态弹窗
    private fun closeDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }

    //　将视频转换成二进制流
    private fun getByteByVideo(url: String): ByteArray? {
        val file = File(url)
        var out: ByteArrayOutputStream? = null
        try {
            val inn = FileInputStream(file)
            out = ByteArrayOutputStream()
            val b = ByteArray(1024)
            while (inn.read(b) != -1) {
                out.write(b, 0, b.size)
            }
            out.close()
            inn.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out!!.toByteArray()
    }

    //弹出等待转圈窗口
    private fun showLoading() {
        if (isInit()) {
            myDialog.dismiss()
            val builder = MyDialog.Builder(this@ResumeEdit)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(this@ResumeEdit)
                .setMessage("新しいバージョンを チェックしている")
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()
        }
        myDialog.show()
    }

    //关闭等待转圈窗口
    private fun hideLoading() {
        if (isInit() && myDialog.isShowing()) {
            myDialog.dismiss()
        }
    }

    //判断mmloading是否初始化,因为lainit修饰的变量,不能直接判断为null,要先判断初始化
    private fun isInit(): Boolean {
        return ::myDialog.isInitialized
    }
}