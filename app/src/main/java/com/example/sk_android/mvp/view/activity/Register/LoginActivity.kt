package com.example.sk_android.mvp.view.activity.Register

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.Register.LoginMainBodyFragment
import com.example.sk_android.mvp.view.fragment.Register.LoginThemeActionBarFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity(){

    lateinit var themeActionBarFragment:LoginThemeActionBarFragment

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
                    themeActionBarFragment= LoginThemeActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,themeActionBarFragment).commit()

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
                    var loginMainBodyFragment= LoginMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,loginMainBodyFragment!!).commit()
                }.lparams() {
                    height= wrapContent
                    width= matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }



            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }


    }

    override fun onStart() {
        super.onStart()
        setActionBar(themeActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@LoginActivity, 0, themeActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}