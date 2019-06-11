package com.example.sk_android.mvp.view.activity.privacyset

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.EditAlertDialog
import com.umeng.message.PushAgent
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.privacyset.CauseChooseDialog
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick

class PrivacySetActivity : AppCompatActivity(),ShadowFragment.ShadowClick,
    CauseChooseDialog.CauseChoose, EditAlertDialog.EditDialogSelect{
    override fun EditCancelSelect() {
        dele()
    }

    override fun EditDefineSelect(trim: String) {
        //其他理由
        toast(trim)
        dele()
    }

    override fun cancleClick() {
        dele()
    }

    override fun chooseClick(name: String) {
        dele()
        //选择关闭原因
        if(name!=null){
            toast(name)
            if(name.equals("その他")){
                reasonDialog()
            }else{

            }
        }
    }
    //关闭dialog
    private fun dele(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(chooseDialog!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(chooseDialog!!)
            chooseDialog=null
        }

        if(editAlertDialog!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog=null
        }

        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null
        }
        mTransaction.commit()
    }

    override fun shadowClicked() {

    }
    var shadowFragment: ShadowFragment?=null
    var chooseDialog: CauseChooseDialog?=null
    var editAlertDialog : EditAlertDialog? = null
    lateinit var texView : TextView
    val outside = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            id = outside
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "プライバシー設定"
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
                    //履歴書の表示
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.vc
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "公开简历"
                            textSize = 13f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                            isChecked = true
                            onCheckedChange { buttonView, isChecked ->
                                if (!isChecked) {
                                    // 透明黑色背景
                                    shadowFragment = ShadowFragment.newInstance()
                                    supportFragmentManager.beginTransaction().add(outside,shadowFragment!!).commit()
                                    // 原因选择弹窗
                                    chooseDialog = CauseChooseDialog.newInstance()
                                    supportFragmentManager.beginTransaction().add(outside,chooseDialog!!).commit()
                                }
                            }
                        }.lparams(wrapContent, wrapContent){
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                    //ブラックリスト
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.black_list
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ブラックリスト"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_go_position
                            onClick {
                                val intent = Intent(this@PrivacySetActivity, BlackListActivity::class.java)
                                startActivity(intent)
                            }
                        }.lparams{
                            width = dip(20)
                            height = dip(20)
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                    //ビデオ履歴書有効
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.open_video_cv
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ビデオ履歴書有効"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                            isChecked = true
                        }.lparams{
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                    //就職経験に会社フルネームが表示される
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.show_work_experience
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "就職経験に会社フルネームが表示される"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                            isChecked = true
                        }.lparams{
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                    //猟師は私に連絡する
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.allow_liaison
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "猟師は私に連絡する"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }

            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    // 选择其他后,弹出理由弹窗
    private fun reasonDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance()
            mTransaction.add(outside,shadowFragment!!)
        }
        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)

        if(editAlertDialog==null){
            editAlertDialog= EditAlertDialog.newInstance("理由を入力してください",null,"キャンセル","確定", 14f)
            mTransaction.add(outside, editAlertDialog!!)
        }
        mTransaction.commit()
    }

}