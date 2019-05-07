package com.example.sk_android.mvp.view.activity.Register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.Register.LoginMainBodyFragment
import com.example.sk_android.mvp.view.fragment.Register.MrActionBarFragment
import com.example.sk_android.mvp.view.fragment.Register.MrMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class MemberRegistActivity: AppCompatActivity() {
    lateinit var mrActionBarFragment:MrActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainScreenId=1
        frameLayout {
            backgroundResource= R.mipmap.login_background
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    mrActionBarFragment= MrActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,mrActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        imageResource = R.mipmap.sk
                    }.lparams(width = dip(126), height = dip(63))
                }.lparams(width = matchParent, height = dip(167))


                var recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    var mrMainBodyFragment= MrMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,mrMainBodyFragment!!).commit()
                }.lparams() {
                    height= wrapContent
                    width= matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    imageView {
                        imageResource = R.mipmap.checkbox_nor
                    }.lparams(width = dip(12), height = dip(12)) {
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    textView {
                        textResource = R.string.loginRequired
                        textSize = 10f //sp
                        textColorResource = R.color.companyNameGray
                    }.lparams(height = matchParent)
                }.lparams(width = matchParent, height = dip(14)) {
                    topMargin = dip(35)
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }

    override fun onStart() {
        super.onStart()
        setActionBar(mrActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MemberRegistActivity, 0, mrActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}