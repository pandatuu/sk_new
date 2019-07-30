package com.example.sk_android.mvp.view.activity.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.jobselect.JobInfoDetailAccuseDialogFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.PictrueScroll
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UploadPic
import com.google.gson.JsonObject
import com.jaeger.library.StatusBarUtil
import com.lcw.library.imagepicker.ImagePicker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class AccusationActivity : BaseActivity(), JobInfoDetailAccuseDialogFragment.AddPictrue,
    PictrueScroll.PictureItem {


    lateinit var desInfo: FrameLayout
    lateinit var mainContainer: FrameLayout
    var mImagePaths = ArrayList<String>()
    val REQUEST_SELECT_IMAGES_CODE = 0x01
    //目标ID
    private var targetEntityId = ""
    //目标类型 公司还是职位
    private var targetEntityType = ""
    lateinit var actionBarNormalFragment: ActionBarNormalFragment
    var jobInfoDetailAccuseDialogFragment: JobInfoDetailAccuseDialogFragment? = null

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@AccusationActivity, 0, actionBarNormalFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        actionBarNormalFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)

        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getStringExtra("targetEntityId") !== null) {
            targetEntityId = intent.getStringExtra("targetEntityId")
        }
        if (intent.getStringExtra("targetEntityType") !== null) {
            targetEntityType = intent.getStringExtra("targetEntityType")
        }

        var mainContainerId = 1
        mainContainer = frameLayout {
            id = mainContainerId
            backgroundColorResource = R.color.white
            linearLayout {
                orientation = LinearLayout.VERTICAL
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    backgroundColor = Color.YELLOW
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("通報");
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment).commit()
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                var centerBodyId = 3
                frameLayout {
                    id = centerBodyId
                    jobInfoDetailAccuseDialogFragment = JobInfoDetailAccuseDialogFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, jobInfoDetailAccuseDialogFragment!!).commit()


                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    val scroll = 4
                    scrollView {
                        id = scroll
                        var urlPictrue = PictrueScroll.newInstance(mImagePaths)
                        supportFragmentManager.beginTransaction().add(scroll, urlPictrue).commit()
                    }.lparams {
                        width = matchParent
                        weight = 1f
                        topMargin = dip(10)
                        gravity = Gravity.TOP
                    }

                    textView {
                        text = "送信"
                        backgroundResource = R.drawable.radius_button_theme
                        gravity = Gravity.CENTER
                        textSize = 15f
                        textColor = Color.WHITE
                        onClick {
                            val reportType = jobInfoDetailAccuseDialogFragment!!.getReportType()
                            val content = jobInfoDetailAccuseDialogFragment!!.getContent()
                            creatReport(mImagePaths, reportType, content)
                        }
                    }.lparams(matchParent, dip(47)) {
                        leftMargin = dip(23)
                        rightMargin = dip(23)
                        bottomMargin = dip(13)
                        gravity = Gravity.BOTTOM
                    }
                }.lparams(matchParent, matchParent) {
                    topMargin = dip(14)
                }
            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }

    //调用图片选择器
    override fun addPic() {
        ImagePicker.getInstance()
            .setTitle("ビデオを選択する")
            .showCamera(true)
            .showImage(true)
            .showVideo(false)
            .setMaxCount(9)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@AccusationActivity, REQUEST_SELECT_IMAGES_CODE)
    }

    //调用图片选择器的必备方法
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == Activity.RESULT_OK) {
            mImagePaths = data!!.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES) as ArrayList<String>
            val stringBuffer = StringBuffer()
            stringBuffer.append("当前选中图片路径：\n\n")
            for (i in mImagePaths!!) {
                stringBuffer.append(i + "\n\n")
            }
            println(stringBuffer.toString())
            modifyPictrue()
        }
    }

    //每次修改图片list,重新刷新fragment
    fun modifyPictrue() {
        val scroll = 4
        var urlPictrue = PictrueScroll.newInstance(mImagePaths)
        supportFragmentManager.beginTransaction().replace(scroll, urlPictrue).commit()
    }

    // 点击图片上的×,删除图片
    override fun clickItem(urlItem: String) {

        mImagePaths.remove(urlItem)

        modifyPictrue()
    }


    // 创建举报记录
    private suspend fun creatReport(
        mImagePaths: ArrayList<String>,
        report: String,
        content: String
    ) {
        try {
            val medias = mutableListOf<JsonObject>()
            val urls = mutableListOf<String>()
            for (imagePath in mImagePaths) {
                medias.add(UploadPic().upLoadPic(imagePath, this@AccusationActivity, "organization-report") ?: continue)
            }
            for (item in medias) {
                println("上传返回值－－－－－－" + item)
                urls.add(item.get("url").asString)
            }
            val params = mapOf(
                "type" to
                        when (report) {
                            "広告" -> ReportType.ADVERTISEMENT.toString()
                            "嫌がらせ" -> ReportType.MOLESTING.toString()
                            "詐欺情報" -> ReportType.FRAUD.toString()
                            else -> ReportType.OTHER.toString()
                        },
                "content" to content,
                "targetEntityType" to targetEntityType,
                "targetEntityId" to targetEntityId,
                "attachments" to urls,
                "attributes" to mapOf<String, Any>()
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@AccusationActivity, "https://report.sk.cgland.top/")
            val it = retrofitUils.create(JobSelectApi::class.java)
                .creatReport(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
                val toast = Toast.makeText(applicationContext, "通報提出成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                val mIntent = Intent()
                mIntent.putExtra("isReport", true)
                setResult(RESULT_OK, mIntent)
                finish()
                overridePendingTransition(R.anim.left_in,R.anim.right_out)
            } else {
                val toast = Toast.makeText(applicationContext, "通報提出失敗", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        } catch (throwable: Throwable) {
            println(throwable)
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
        }
    }

    enum class ReportType {
        MOLESTING, //骚扰
        ADVERTISEMENT, //广告
        FRAUD, //诈骗信息
        OTHER //其他
    }
}
