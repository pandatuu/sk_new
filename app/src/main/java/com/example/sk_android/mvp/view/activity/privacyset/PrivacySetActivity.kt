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
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.privacyset.RollChooseFrag
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PrivacySetActivity : AppCompatActivity(),ShadowFragment.ShadowClick, RollChooseFrag.RollToolClick {
    override fun cancelClick() {
        dele()
    }

    override fun confirmClick(rooltext : String) {
        if(rooltext!=""){
            texView.text = rooltext
        }
        if(texView.text.equals("完全に非公開")){
            val intent = Intent(this@PrivacySetActivity, CauseChooseActivity::class.java)
            startActivity(intent)
        }
        toast("${texView.text}")
        dele()
    }
    fun dele(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(rollChoose!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(rollChoose!!)
            rollChoose=null
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
    var rollChoose: RollChooseFrag?=null
    lateinit var texView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val outside = 1
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
                            text = "履歴書の表示"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        texView = textView {
                            text = "ブラックリストは有効"
                            textSize = 12f
                            textColor = Color.parseColor("#FFB3B3B3")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            rightMargin = dip(20)
                            alignParentRight()
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_go_position
                        }.lparams{
                            width = dip(20)
                            height = dip(20)
                            alignParentRight()
                            centerVertically()
                        }
                        onClick {
                            var mTransaction=supportFragmentManager.beginTransaction()
                            mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            if(shadowFragment==null){
                                shadowFragment= ShadowFragment.newInstance()
                                mTransaction.add(outside,shadowFragment!!)
                            }

                            mTransaction.setCustomAnimations(
                                R.anim.bottom_in,
                                R.anim.bottom_in)

                            rollChoose= RollChooseFrag.newInstance(texView.getText().toString())
                            mTransaction.add(outside, rollChoose!!)
                            mTransaction.commit()
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
}