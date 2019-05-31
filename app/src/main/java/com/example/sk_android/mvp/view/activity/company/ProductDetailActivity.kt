package com.example.sk_android.mvp.view.activity.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.jobselect.ProductBriefInfoFragment
import com.example.sk_android.mvp.view.fragment.jobselect.ProductDetailInfoFragment
import com.example.sk_android.mvp.view.fragment.jobselect.ProductPicShowFragment
import com.umeng.message.PushAgent


class ProductDetailActivity : AppCompatActivity() {





    lateinit var mainBody:LinearLayout

    lateinit var actionBarNormalFragment:ActionBarNormalFragment


    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@ProductDetailActivity, 0, actionBarNormalFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();
        var mainBodyId=1
        mainBody=verticalLayout {
            id=mainBodyId
            backgroundColor=Color.WHITE

                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                   actionBarNormalFragment= ActionBarNormalFragment.newInstance("製品詳細")
                    supportFragmentManager.beginTransaction().add(id,actionBarNormalFragment).commit()

               }.lparams {
                    height= wrapContent
                    width= matchParent
               }

                var briefInfoId=3
                frameLayout{
                    id=briefInfoId
                    var productBriefInfoFragment= ProductBriefInfoFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id,productBriefInfoFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }


                view {
                    backgroundColorResource= R.color.grayF6
                }.lparams {
                    height=dip(8)
                    width= matchParent
                }


            var picShowId=4
            frameLayout{
                id=picShowId
                var productPicShowFragment= ProductPicShowFragment.newInstance()
                supportFragmentManager.beginTransaction().add(id,productPicShowFragment).commit()

            }.lparams {
                height= wrapContent
                width= matchParent
                leftMargin=dip(15)
                rightMargin=dip(15)
                topMargin=dip(20)
            }


            var detailInfoId=5
            frameLayout{
                id=detailInfoId
                var productDetailInfoFragment= ProductDetailInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().add(id,productDetailInfoFragment).commit()

            }.lparams {
                height= wrapContent
                width= matchParent
                leftMargin=dip(15)
                rightMargin=dip(15)
            }








        }



    }




}
