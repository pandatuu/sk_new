package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobState
import com.example.sk_android.mvp.store.FetchEditOnlineAsyncAction
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UpLoadApi
import com.example.sk_android.utils.UploadPic
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.lcw.library.imagepicker.ImagePicker
import com.umeng.message.PushAgent
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
import org.jetbrains.anko.support.v4.nestedScrollView
import retrofit2.HttpException
import zendesk.suas.AsyncMiddleware
import java.io.*

class ResumeEdit : AppCompatActivity(), ResumeEditBackground.BackgroundBtn,
    ResumeEditBasic.UserResume, ResumeEditEdu.EduFrag,
    ResumeEditProject.ProjectFrag, ResumeEditJob.JobFrag,
    ResumeEditWanted.WantedFrag, ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect, ResumeEditWantedState.WantedFrag,
    ResumeEditBarFrag.EditBar {

    var actionBarNormalFragment: ResumeEditBarFrag? = null
    private var mImagePaths: ArrayList<String>? = null
    private var resumeback: ResumeEditBackground? = null
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
    private var isChecked = false
    private lateinit var resumeBasic: ResumeEditBasic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val bottomBeha = BottomSheetBehavior<View>(this@ResumeEdit, null)
        bottomBeha.peekHeight = dip(370)
        frameLayout {
            id = mainId
            coordinatorLayout {
                appBarLayout {
                    val actionBarId = 10
                    frameLayout {
                        id = actionBarId
                        actionBarNormalFragment = ResumeEditBarFrag.newInstance("")
                        supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                val back = 8
                frameLayout {
                    id = back
                    resumeback = ResumeEditBackground.newInstance()
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
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        frameLayout {
                            id = basic
                            resumeBasic = ResumeEditBasic.newInstance(actionBarNormalFragment)
                            supportFragmentManager.beginTransaction().add(basic, resumeBasic).commit()
                        }
                        frameLayout {
                            id = state
                            resumeWantedstate = ResumeEditWantedState.newInstance()
                            supportFragmentManager.beginTransaction().add(state, resumeWantedstate).commit()
                        }
                        frameLayout {
                            id = want
                            resumeWanted = ResumeEditWanted.newInstance()
                            supportFragmentManager.beginTransaction().add(want, resumeWanted).commit()
                        }
                        frameLayout {
                            id = job
                            resumeJob = ResumeEditJob.newInstance()
                            supportFragmentManager.beginTransaction().add(job, resumeJob).commit()
                        }
                        frameLayout {
                            id = project
                            resumeProject = ResumeEditProject.newInstance()
                            supportFragmentManager.beginTransaction().add(project, resumeProject).commit()
                        }
                        frameLayout {
                            id = edu
                            resumeEdu = ResumeEditEdu.newInstance(resumeBasic)
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

        println("$requestCode-----------$resultCode-----------$data")
        //图片视频选择器点击选择
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mImagePaths = data!!.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES) as ArrayList<String>
            modifyPictrue()
        }
        //求职意向
        if (requestCode == 1 && resultCode == 1001) {
            val want = 4
            resumeWanted = ResumeEditWanted.newInstance()
            supportFragmentManager.beginTransaction().replace(want, resumeWanted).commit()
        }
        //工作经历
        if (requestCode == 1 && resultCode == 101) {
            val job = 5
            resumeJob = ResumeEditJob.newInstance()
            supportFragmentManager.beginTransaction().replace(job, resumeJob).commit()
        }
        //项目经历
        if (requestCode == 1 && resultCode == 102) {
            val project = 6
            resumeProject = ResumeEditProject.newInstance()
            supportFragmentManager.beginTransaction().replace(project, resumeProject).commit()
        }
        //教育经历
        if (requestCode == 1 && resultCode == 103) {
            val edu = 7
            resumeEdu = ResumeEditEdu.newInstance(resumeBasic)
            supportFragmentManager.beginTransaction().replace(edu, resumeEdu).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@ResumeEdit, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@ResumeEdit, PersonSetActivity::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getResumeId()
            getUserJobState()
        }
    }

    //跳转基本信息编辑页面
    override fun jumpToBasic() {
        val intent = Intent(this@ResumeEdit, EditBasicInformation::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //跳转查看示范页面
    override fun jumpExample() {
        val intent = Intent(this@ResumeEdit, ShowExample::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //点击选择视频按钮
    override fun clickButton() {
        if (!isChecked) {
            chooseVideo()
        } else {
            val toast = Toast.makeText(applicationContext, "映像を審査中です。結果の確認後、再提出してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    //每次修改图片list,重新刷新fragment
    private fun modifyPictrue() {
        if (mImagePaths != null && mImagePaths!!.size > 0) {
            vedioUrl = mImagePaths?.get(0)!!
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                upLoadVideo(vedioUrl)
            }
        }
    }

    //跳转预览页面
    override fun jumpNextPage() {
        // 给bnt1添加点击响应事件
        val intent = Intent(this@ResumeEdit, ResumePreview::class.java)
        if (mImagePaths != null) {
            intent.putExtra("imageUrl", mImagePaths!![0])
            mImagePaths = null
        }
        //启动
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
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
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //点击某一行项目经历,跳转
    override fun projectClick(projectId: String) {
        val intent = Intent(this@ResumeEdit, EditProjectExperience::class.java)
        intent.putExtra("projectId", projectId)
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //点击某一行教育经历,跳转
    override fun eduClick(eduId: String) {
        val intent = Intent(this@ResumeEdit, EditEduExperience::class.java)
        intent.putExtra("eduId", eduId)
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //添加工作经历
    override fun addJobClick() {
        val intent = Intent(this@ResumeEdit, AddJobExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //添加项目经历
    override fun addProjectClick() {
        val intent = Intent(this@ResumeEdit, AddProjectExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //添加教育经历
    override fun addEduClick() {
        val intent = Intent(this@ResumeEdit, AddEduExperience::class.java)
        intent.putExtra("resumeId", resumeId)
        startActivityForResult(intent,1)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    //选择某一行求职意向
    override fun wantedClick(id: UserJobIntention) {
        println(id)
        val intent = Intent(this@ResumeEdit, JobWantedEditActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("userJobIntention", id)
        bundle.putInt("condition", 1)
        intent.putExtra("bundle", bundle)
        startActivityForResult(intent,1)
    }

    //选择添加求职意向
    override fun addWanted() {
        val emptyArray = arrayListOf<String>()
        val emptyMutableList = mutableListOf<String>()
        val myAttributes = mapOf<String, Serializable>()

        val userJobIntention = UserJobIntention(
            emptyArray,
            emptyMutableList,
            myAttributes,
            "",
            "",
            "",
            "",
            "",
            emptyArray,
            emptyMutableList,
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "",
            0,
            0,
            "",
            "",
            0,
            emptyArray
        )
        val intent = Intent(this@ResumeEdit, JobWantedEditActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("userJobIntention", userJobIntention)
        bundle.putInt("condition", 2)
        intent.putExtra("bundle", bundle)
        startActivityForResult(intent,1)
    }

    // 获取用户求职状态
    private suspend fun getUserJobState() {
        try {
            val retrofitUils = RetrofitUtils(this@ResumeEdit, this.getString(R.string.userUrl))
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

            val retrofitUils = RetrofitUtils(this@ResumeEdit, this.getString(R.string.userUrl))
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
            val retrofitUils = RetrofitUtils(this@ResumeEdit, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserResume("ONLINE")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                resumeId = page.data[0].get("id").asString
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //更新用户在线简历信息
    private suspend fun updateUserResume(id: String, vedioUrl: String, imageUrl: String) {
        try {
            val params = mapOf(
                "attributes" to mapOf<String, Any>(),
                "type" to "ONLINE",
                "videoThumbnailUrl" to imageUrl,
                "videoURL" to vedioUrl
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@ResumeEdit, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateUserResume(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                frush()

                val toast = Toast.makeText(
                    applicationContext,
                    "PR映像のアップロードに成功しました！審査合格後に反映されます。",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            if (it.code() == 403) {
                val toast = Toast.makeText(applicationContext, "映像を審査中です。結果の確認後、再提出してください。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
    //上传视频
    private suspend fun upLoadVideo(url: String) {
        val videoFile = File(url)
        val byteArray: ByteArray?
        val size = videoFile.length()
        if (size > 50 * 1024 * 1024 || size == 0L) {
            val toast = Toast.makeText(applicationContext, "映像サイズは50Mまで", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
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
            try {
                val multipart = MultipartBody.Builder()
                    .setType(MimeType.MULTIPART_FORM_DATA)
                    .addFormDataPart("bucket", "user-resume-video")
                    .addFormDataPart("type", "VIDEO")
                    .addFormDataPart("file", videoFile.name, videoBody)
                    .build()
                println("---------------------" + videoFile.name + ":" + byteArray.size)

                val retrofitUils = RetrofitUtils(this@ResumeEdit, this.getString(R.string.storageUrl))
                val it = retrofitUils.create(UpLoadApi::class.java)
                    .upLoadVideo(multipart)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .awaitSingle()
                if (it.code() in 200..299) {
                    val videoUrl = it.body()!!.get("url").asString
                    // 获取视频的第一帧作为缩略图
                    val media = MediaMetadataRetriever()
                    media.setDataSource(url)
                    val bitmap = media.frameAtTime
                    //上传缩略图
                    val imageUrl = upLoadThumb(bitmap).split(";")[0]
                    //更新用户在线简历信息
                    updateUserResume(resumeId, videoUrl, imageUrl)
                    val scroll = 8
                    resumeback = ResumeEditBackground.newInstance()
                    supportFragmentManager.beginTransaction().replace(scroll, resumeback!!).commit()
                }
            } catch (e: Throwable) {
                if (e is HttpException) {
                    println(e)
                }
            }
        }
    }

    //上传缩略图
    private suspend fun upLoadThumb(bit: Bitmap): String {
        try {
            val obj = UploadPic().upLoadVedioThumb(bit, this@ResumeEdit, "thumbnail-video")
            println(obj)
            if (obj != null) {
                return obj.get("url")!!.asString.split(";")[0]
            }
            return ""
        } catch (throwable: Throwable) {
            println(throwable)
            return ""
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

        editAlertDialog = BottomSelectDialogFragment.newInstance("求職ステータス", list)
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (editAlertDialog == null && shadowFragment == null) {
                val intent = Intent(this@ResumeEdit, PersonSetActivity::class.java)
                startActivity(intent)
                finish()//返回
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
                true
            } else {
                closeDialog()
                false
            }
        } else {
            false
        }
    }
    private fun frush(){
        val fetchEditOnlineAsyncAction = AsyncMiddleware.create(FetchEditOnlineAsyncAction(this))
        val application: App? = App.getInstance()
        application?.store?.dispatch(fetchEditOnlineAsyncAction)
    }
}