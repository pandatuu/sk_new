package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.View
import com.example.sk_android.mvp.view.fragment.common.ActionBarThemeFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent

class RecruitListActivity : AppCompatActivity() {

    val CONTACT=1
    val COLLECT=2
    val HANDIN=3


    lateinit var  actionBar:ActionBarThemeFragment
    lateinit var  listShow:RecruitInfoListFragment

    var type=2

    override fun onStart() {
        super.onStart()
        setActionBar(actionBar.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RecruitListActivity, 0, actionBar.toolbar1)

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            backgroundColor=Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId

                    var titleShow=""
                    if(type==CONTACT){
                        titleShow= "疎通した職"
                    }else if(type==COLLECT){
                        titleShow= "私のお気に入りのポジション"
                    }else if(type==HANDIN){
                        titleShow= "履歴書を郵送した役職です"
                    }

                    actionBar= ActionBarThemeFragment.newInstance(titleShow);
                    supportFragmentManager.beginTransaction().replace(id,actionBar).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }



                var mainBodyId=6
                frameLayout{
                    id=mainBodyId


                    listShow= RecruitInfoListFragment.newInstance(null,null,null)
                    supportFragmentManager.beginTransaction().replace(id,listShow!!).commit()



                }.lparams {
                    height=0
                    weight=1f
                    width= matchParent
                }




            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }
}
