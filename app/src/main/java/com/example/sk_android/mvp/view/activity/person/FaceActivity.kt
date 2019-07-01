package com.example.sk_android.mvp.view.activity.person

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.person.FaActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.FaMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class FaceActivity:AppCompatActivity() {
    lateinit var faActionBarFragment: FaActionBarFragment
    override fun onCreate(savedInstanceState: Bundle?) {

        PushAgent.getInstance(this).onAppStart();


        super.onCreate(savedInstanceState)
        var mainScreenId=1
        frameLayout {
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    faActionBarFragment= FaActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,faActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val faMainBodyFragment = FaMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, faMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent){}

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }



    override fun onStart() {
        super.onStart()
        setActionBar(faActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@FaceActivity, 0, faActionBarFragment.TrpToolbar)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)



        faActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }

    }

}