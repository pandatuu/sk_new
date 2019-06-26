package com.example.sk_android.mvp.view.activity.register

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.model.register.Work
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.fragment.register.PfourActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PfourMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import java.io.Serializable


class PersonInformationFourActivity:AppCompatActivity(),PfourActionBarFragment.mm,PfourMainBodyFragment.Mid{
    lateinit var pfourActionBarFragment:PfourActionBarFragment
    lateinit var pfourMainBodyFragment:PfourMainBodyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras!!.get("bundle") as Bundle
        val person = bundle.getParcelable<Parcelable>("person") as Person
        val education =bundle.getParcelable<Parcelable>("education") as Education
        val work =bundle.getParcelable<Parcelable>("work") as Work
        val condition = bundle.getInt("condition")

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainScreenId=1
        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    pfourActionBarFragment = PfourActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, pfourActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    pfourMainBodyFragment = PfourMainBodyFragment.newInstance(person,education,work,condition)
                    supportFragmentManager.beginTransaction().replace(id, pfourMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)


            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(pfourActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonInformationFourActivity, 0, pfourActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        pfourActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }
    }

    override fun goback() {
        alert ("密码不可为空"){
            yesButton { toast("Yes!!!") }
            noButton { }
        }.show()
    }

    override fun confirmJob() {
        var mIntent = Intent(this, JobSelectActivity::class.java)
        startActivityForResult(mIntent,1)
    }

    override fun confirmAddress() {
        var mIntent = Intent(this, CitySelectActivity::class.java)
        startActivityForResult(mIntent,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            val dataStringExtra2 = data!!.getStringExtra("job")
            pfourMainBodyFragment.setJob(dataStringExtra2)
        }

        if(requestCode == 2 && resultCode == RESULT_OK){
            val dataStringExtra2 = data!!.getStringExtra("address")
            pfourMainBodyFragment.setAddress(dataStringExtra2)
        }
    }

}