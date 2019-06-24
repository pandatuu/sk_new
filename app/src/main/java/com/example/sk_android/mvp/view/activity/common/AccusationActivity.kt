package com.example.sk_android.mvp.view.activity.common

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent

class AccusationActivity : AppCompatActivity()
{


    lateinit var desInfo: FrameLayout
    lateinit var mainContainer: FrameLayout

    lateinit var actionBarNormalFragment: ActionBarNormalFragment

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@AccusationActivity, 0, actionBarNormalFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        actionBarNormalFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)

        }




    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainContainerId=1
        mainContainer=frameLayout {
            id=mainContainerId
            backgroundColorResource=R.color.white
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("告発");
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var centerBodyId = 3
                frameLayout {
                    id = centerBodyId

                    var jobInfoDetailAccuseDialogFragment = JobInfoDetailAccuseDialogFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, jobInfoDetailAccuseDialogFragment).commit()


                }.lparams {
                    height = dip(0)
                    weight=1f
                    width = matchParent
                }

                textView {
                    text = "送信"
                    backgroundResource = R.drawable.radius_button_theme
                    gravity = Gravity.CENTER
                    textSize = 15f
                    textColor = Color.WHITE
                }.lparams {
                    height = dip(47)
                    width = matchParent
                    leftMargin = dip(23)
                    rightMargin = dip(23)
                    bottomMargin = dip(13)
                    topMargin = dip(14)
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }






}
