package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackSuggestionXiaLa
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackWhiteBackground
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.PictrueScroll
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.SuggestionFrag
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UploadPic
import com.jaeger.library.StatusBarUtil
import com.lcw.library.imagepicker.ImagePicker
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import withTrigger
import java.util.*


class FeedbackSuggestionsActivity : AppCompatActivity(), SuggestionFrag.TextClick, PictrueScroll.PictureItem
    , FeedbackSuggestionXiaLa.XiaLaKuang, FeedbackWhiteBackground.WhitebBack {
    override fun clickwhite() {
        closeXiala()
    }

    override fun onClickXiala(text1: String) {
        xialatext.text = text1
        closeXiala()
    }

    override fun clickItem(url: String) {
        mImagePaths.remove(url)
        modifyPictrue()
    }

    override suspend fun clicktichu() {
        thisDialog= DialogUtils.showLoading(this@FeedbackSuggestionsActivity)
        mHandler.postDelayed(r, 12000)
        if (edit.text.length in 1..1000) {
            createFeed(edit.text, xialatext.text.toString(), mImagePaths)
        } else {
            DialogUtils.hideLoading(thisDialog)
            val toast = Toast.makeText(applicationContext, "文字制限を超えました。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
    }


    lateinit var textv: TextView
    private val code = 0x01
    private var mImagePaths = ArrayList<String>()
    lateinit var edit: EditText
    private lateinit var xialatext: TextView
    var mm: FeedbackSuggestionXiaLa? = null
    private var backgroundwhite: FeedbackWhiteBackground? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val mainId = 1
        frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("フィードバックとアドバイス")
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                verticalLayout {
                    frameLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            xialatext = textView {
                                text = "アドバイス"
                                textSize = 13f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(wrapContent, wrapContent) {
                                gravity = Gravity.LEFT
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_down
                                this.withTrigger().click {
                                    addDialog()
                                }
                            }.lparams(dip(20), dip(20)) {
                                leftMargin = dip(15)
                            }
                        }.lparams(wrapContent, wrapContent)
                        this.withTrigger().click {
                            addDialog()
                        }
                    }.lparams(wrapContent, wrapContent) {
                        setMargins(dip(15), dip(20), 0, 0)
                    }

                    edit = editText {
                        backgroundResource = R.drawable.area_text
                        backgroundColor = Color.parseColor("#F6F6F6")
                        hint = "内容をここで書いてください"
                        textSize = 13f
                        gravity = top
                        padding = dip(10)
                    }.lparams {
                        width = matchParent
                        height = dip(250)
                        topMargin = dip(10)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    relativeLayout {
                        relativeLayout {
                            textv = textView {
                                text = "0/1000"
                                textSize = 12f
                                textColor = Color.parseColor("#FF898989")
                            }
                        }.lparams {
                            width = dip(62)
                            height = wrapContent
                            alignParentRight()
                            rightMargin = dip(15)
                            topMargin = dip(5)
                        }
                        edit.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {}
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            @SuppressLint("SetTextI18n")
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                println("onText---" + edit.text)
                                textv.text = edit.text.length.toString() + "/1000"
                            }
                        })
                    }.lparams {
                        width = matchParent
                        height = dip(27)
                    }
                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        backgroundColor = Color.parseColor("#FFF6F6F6")
                        imageView {
                            imageResource = R.mipmap.icon_add
                        }.lparams(wrapContent, wrapContent) {
                            alignParentLeft()
                            leftMargin = dip(10)
                        }
                        textView {
                            text = "画像を追加"
                            textSize = 12f
                            textColor = Color.parseColor("#FF636363")
                        }.lparams(wrapContent, wrapContent) {
                            alignParentRight()
                            rightMargin = dip(10)
                        }
                        this.withTrigger().click {
                            choosePicture()
                        }
                    }.lparams(dip(100), dip(35)) {
                        leftMargin = dip(15)
                        bottomMargin = dip(10)
                    }
                    val scroll = 2
                    scrollView {
                        id = scroll
                        val urlPictrue = PictrueScroll.newInstance(mImagePaths)
                        supportFragmentManager.beginTransaction().add(scroll, urlPictrue).commit()

                    }.lparams(matchParent, matchParent)
                }.lparams {
                    width = matchParent
                    height = matchParent
                    bottomMargin = dip(70)
                }
                frameLayout {
                    val suggestion = SuggestionFrag.newInstance()
                    supportFragmentManager.beginTransaction().add(mainId, suggestion).commit()
                }.lparams(matchParent, matchParent)
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(
            this@FeedbackSuggestionsActivity,
            0,
            actionBarNormalFragment!!.toolbar1
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@FeedbackSuggestionsActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    //调用图片选择器
    private fun choosePicture() {
        ImagePicker.getInstance()
            .setTitle("ビデオを選択する")
            .showCamera(true)
            .showImage(true)
            .showVideo(false)
            .setMaxCount(9)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@FeedbackSuggestionsActivity, code)
    }

    //调用图片选择器的必备方法
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == code && resultCode == Activity.RESULT_OK) {
            mImagePaths = data!!.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES) as ArrayList<String>
            val stringBuffer = StringBuffer()
            stringBuffer.append("当前选中图片路径：\n\n")
            for (i in mImagePaths) {
                stringBuffer.append(i + "\n\n")
            }
            println(stringBuffer.toString())
            modifyPictrue()
        }
    }

    //每次修改图片list,重新刷新fragment
    private fun modifyPictrue() {
        val scroll = 2
        val urlPictrue = PictrueScroll.newInstance(mImagePaths)
        supportFragmentManager.beginTransaction().replace(scroll, urlPictrue).commit()
    }

    //先调用上传接口，成功后，调用创建反馈借口
    private suspend fun createFeed(content: CharSequence, type: String, imagePaths: List<String>) {
        try {
            val medias = mutableListOf<String>()
            for (imagePath in imagePaths) {
                medias.add(
                    UploadPic().upLoadPic(
                        imagePath,
                        this@FeedbackSuggestionsActivity,
                        "user-feedback"
                    )!!.get("url").asString ?: continue
                )
            }
            for (item in medias) {
                println("上传返回值－－－－－－$item")
            }
            val params = mapOf(
                "content" to content,
                "type" to if("アドバイス" == type) "ADVICE" else "INTERFACE",
                "attachments" to medias,
                "attributes" to mapOf<String, Any>()
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@FeedbackSuggestionsActivity, this.getString(R.string.helpUrl))
            val rebody = retrofitUils.create(HelpFeedbackApi::class.java)
                .createFeedback(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .awaitSingle()
            if (rebody.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
                val intent = Intent(this@FeedbackSuggestionsActivity, HelpFeedbackActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.right_in, R.anim.left_out)

                finish()
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
            }

        } catch (throwable: Throwable) {
            println("token--失败！！！！！！！！！")
            println(throwable)
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
            println("token--失败！！！！！！！！！")
        }
    }

    //　下拉框
    private fun addDialog() {
        val typeList = mutableListOf<String>()
        typeList.add("アドバイス")
        typeList.add("アプリの問題")
        val mainId = 1
        if (mm != null) {
            closeXiala()
        } else {
            backgroundwhite = FeedbackWhiteBackground.newInstance()
            supportFragmentManager.beginTransaction().add(mainId, backgroundwhite!!).commit()
            mm = FeedbackSuggestionXiaLa.newInstance(typeList, this@FeedbackSuggestionsActivity)
            supportFragmentManager.beginTransaction().add(mainId, mm!!).commit()
        }
    }

    private fun closeXiala() {
        if (mm != null) {
            supportFragmentManager.beginTransaction().remove(mm!!).commit()
            mm = null
        }

        supportFragmentManager.beginTransaction().remove(backgroundwhite!!).commit()
        backgroundwhite = null

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (mm == null) {
                val intent = Intent(this@FeedbackSuggestionsActivity, HelpFeedbackActivity::class.java)
                startActivity(intent)
                finish()//返回
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
                true
            }else{
                closeXiala()
                false
            }
        } else {
            false
        }
    }
}
