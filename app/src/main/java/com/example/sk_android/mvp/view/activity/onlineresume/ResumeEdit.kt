package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
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
import com.example.sk_android.mvp.view.activity.mysystemsetup.NotificationSettingsActivity
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.PictrueScroll
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditItem
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumePreviewBackground
import com.lcw.library.imagepicker.ImagePicker
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import java.io.Serializable
import java.util.ArrayList

class ResumeEdit : AppCompatActivity(), ResumePreviewBackground.BackgroundBtn {

    override fun clickButton() {
        choosePicture()
    }


    val REQUEST_SELECT_IMAGES_CODE = 0x01
    var mImagePaths : ArrayList<String>? = null

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
                    var resumeItem = ResumePreviewBackground.newInstance(null,true)
                    supportFragmentManager.beginTransaction().add(back, resumeItem).commit()
                }.lparams(matchParent, dip(370)) {
                    topMargin = dip(54)
                }
                val resumeListid = 2
                nestedScrollView {
                    id = resumeListid
                    backgroundResource = R.drawable.twenty_three_radius_top
                    var resumeItem = ResumeEditItem.newInstance()
                    supportFragmentManager.beginTransaction().add(resumeListid, resumeItem).commit()
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

    fun choosePicture() {
        ImagePicker.getInstance()
            .setTitle("写真/ビデオを選択する")
            .showCamera(false)
            .showImage(false)
            .setMaxCount(1)
            .setImagePaths(mImagePaths)
            .setImageLoader(PictruePicker())
            .start(this@ResumeEdit, REQUEST_SELECT_IMAGES_CODE)
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
        var url :String? = null
        if(mImagePaths!!.size >0)
            url = mImagePaths?.get(0)
        val scroll = 1
        var urlPictrue = ResumePreviewBackground.newInstance(url,true)
        supportFragmentManager.beginTransaction().replace(scroll,urlPictrue).commit()
    }

    //跳转
    fun jumpNextPage(){
        // 给bnt1添加点击响应事件
        val intent = Intent(this@ResumeEdit, ResumePreview::class.java)
        intent.putExtra("imageUrl", mImagePaths as Serializable)
        //启动
        startActivity(intent)
    }
}