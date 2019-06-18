package com.example.sk_android.mvp.view.activity.onlineresume

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.lcw.library.imagepicker.ImagePicker
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
import java.util.ArrayList

class ResumeEdit : AppCompatActivity(), ResumePreviewBackground.BackgroundBtn,
    ResumeEditBasic.UserResume, ResumeEditEdu.EduFrag{

    private var basic: UserBasicInformation? = null
    var mImagePaths : ArrayList<String>? = null
    var resumeback: ResumePreviewBackground? = null
    var resumeBasic: ResumeEditBasic? = null
    lateinit var resumeWanted: ResumeEditWanted
    lateinit var resumeJob: ResumeEditJob
    lateinit var resumeProject: ResumeEditProject
    lateinit var resumeEdu: ResumeEditEdu
    var resumeId: String = "f637d036-f978-4f39-aa3b-4eaa5b815107"
    var vedioUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bottomBeha = BottomSheetBehavior<View>(this@ResumeEdit, null)
        bottomBeha.setPeekHeight(dip(370))

        linearLayout {
            coordinatorLayout {
                appBarLayout {
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
                            text = "視覚デザイン履歴1"
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

                val back = 1
                frameLayout {
                    id = back
                    if(vedioUrl!="") {
                        resumeback = ResumePreviewBackground.newInstance(vedioUrl, true)
                    }else {
                        resumeback = ResumePreviewBackground.newInstance(null, true)
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
        if (requestCode == 2) {
                modifyPictrue()
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUser()
            getEduByResumeId()
        }
    }

    //跳转基本信息编辑页面
    override fun jumpToBasic() {
        val intent = Intent(this@ResumeEdit, EditBasicInformation::class.java)
        intent.putExtra("resumeId","3bff6ea9-08a6-4947-bc4a-c85312957885")
        startActivity(intent)
    }

    override fun clickButton() {
        choosePicture()
    }
    //每次修改图片list,重新刷新fragment
    private fun modifyPictrue(){
        if(mImagePaths!=null && mImagePaths!!.size >0)
            vedioUrl = mImagePaths?.get(0)!!
        val scroll = 1
        if(resumeback!=null){
            resumeback = ResumePreviewBackground.newInstance(vedioUrl,true)
            supportFragmentManager.beginTransaction().add(scroll,resumeback!!).commit()
        }else{
            resumeback = ResumePreviewBackground.newInstance(vedioUrl,true)
            supportFragmentManager.beginTransaction().replace(scroll,resumeback!!).commit()
        }
    }

    //跳转
    private fun jumpNextPage(){
        // 给bnt1添加点击响应事件
        val intent = Intent(this@ResumeEdit, ResumePreview::class.java)
        intent.putExtra("imageUrl", mImagePaths!!.get(0))
        mImagePaths = null
        //启动
        startActivityForResult(intent,2)
    }

    //选择视频
    private fun choosePicture() {
        ImagePicker.getInstance()
            .setTitle("写真/ビデオを選択する")
            .showCamera(false)
            .showImage(false)
            .setMaxCount(1)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@ResumeEdit, 1)
    }

    //点击某一行教育经历,跳转
    override fun eduCLick(eduId: String) {
        val intent = Intent(this@ResumeEdit,EditEduExperience::class.java)
        intent.putExtra("eduId",eduId)
        startActivity(intent)
    }
    
    private fun getResumeId(){

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
                toast("获取成功")
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

//    //根据简历ID获取教育经历
    private suspend fun getEduByResumeId(){
        try {
            val retrofitUils = RetrofitUtils(this@ResumeEdit, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getEduById(resumeId)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                toast("获取成功")
                val list = mutableListOf<EduExperienceModel>()
                for (item in it.body()!!.asJsonArray){
                    list.add(Gson().fromJson(item,EduExperienceModel::class.java))
                }
                val edu = 7
                resumeEdu = ResumeEditEdu.newInstance(list)
                supportFragmentManager.beginTransaction().replace(edu,resumeEdu).commit()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
}