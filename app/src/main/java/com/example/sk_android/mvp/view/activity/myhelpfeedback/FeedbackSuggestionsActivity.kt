package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.PictrueScroll
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.SuggestionFrag
import com.lcw.library.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.roll_choose.view.*
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.ArrayList

class FeedbackSuggestionsActivity : AppCompatActivity(),SuggestionFrag.TextClick,PictrueScroll.PictureItem {
    override fun clickItem(url: String) {
        mImagePaths.remove(url)
        toast("删除成功")
        modifyPictrue()
    }

    override fun clicktichu() {
        if(edit.text.length<=1000){
            toast(edit.text)
        }else{
            toast("字数超出上限")
        }
    }

    lateinit var textv : TextView
    val REQUEST_SELECT_IMAGES_CODE = 0x01
    var mImagePaths = ArrayList<String>()
    lateinit var edit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        val mainId= 1
        frameLayout {
            id = mainId
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            finish()
                        }
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "フィードバックとアドバイス"
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
                verticalLayout {
                    edit=editText {
                        backgroundResource = R.drawable.area_text
                        backgroundColor = Color.parseColor("#F6F6F6")
                        hint = "内容をここで書いてください"
                        gravity= top
                    }.lparams{
                        width = matchParent
                        height = dip(250)
                        topMargin = dip(10)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    relativeLayout{
                        relativeLayout {
                            textv=textView {
                                text = "0/1000"
                                textSize = 12f
                                textColor = Color.parseColor("#FF898989")
                            }
                        }.lparams{
                            width = dip(62)
                            height = wrapContent
                            alignParentRight()
                            rightMargin = dip(15)
                            topMargin = dip(5)
                        }
                        edit.addTextChangedListener(object : TextWatcher{
                            override fun afterTextChanged(s: Editable?) {}
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                println("onText---"+edit.text)
                                textv.text = edit.text.length.toString()+"/1000"
                            }
                        })
                    }.lparams{
                        width = matchParent
                        height = dip(27)
                    }
                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        backgroundColor = Color.parseColor("#FFF6F6F6")
                        imageView{
                            imageResource = R.mipmap.icon_add
                        }.lparams(wrapContent, wrapContent){
                            alignParentLeft()
                            leftMargin = dip(10)
                        }
                        textView {
                            text = "画像を追加"
                            textSize =12f
                            textColor = Color.parseColor("#FF636363")
                        }.lparams(wrapContent, wrapContent){
                            alignParentRight()
                            rightMargin = dip(10)
                        }
                        onClick {
                            choosePicture()
                        }
                    }.lparams(dip(100), dip(35)){
                        leftMargin = dip(15)
                        bottomMargin = dip(10)
                    }
                    val scroll = 2
                    scrollView{
                        id = scroll
                            var urlPictrue = PictrueScroll.newInstance(mImagePaths)
                            supportFragmentManager.beginTransaction().add(scroll,urlPictrue).commit()

                    }.lparams(matchParent, matchParent)
                }.lparams{
                    width = matchParent
                    height = matchParent
                    bottomMargin = dip(70)
                }
                frameLayout {
                    var suggestion = SuggestionFrag.newInstance()
                    supportFragmentManager.beginTransaction().add(mainId,suggestion).commit()
                }.lparams(matchParent, matchParent)
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }

    fun choosePicture(){
        ImagePicker.getInstance()
            .setTitle("ビデオを選択する")
            .showCamera(true)
            .showImage(true)
            .setMaxCount(9)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@FeedbackSuggestionsActivity,REQUEST_SELECT_IMAGES_CODE)
    }

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
    fun modifyPictrue(){
        val scroll = 2
        var urlPictrue = PictrueScroll.newInstance(mImagePaths)
        supportFragmentManager.beginTransaction().replace(scroll,urlPictrue).commit()
    }
}