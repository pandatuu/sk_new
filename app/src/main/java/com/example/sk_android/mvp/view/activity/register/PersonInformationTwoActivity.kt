package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.register.PtwoActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PtwoMainBodyFragment
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent
import retrofit2.adapter.rxjava2.HttpException

class PersonInformationTwoActivity:AppCompatActivity(),PtwoMainBodyFragment.Intermediary, ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect {
    lateinit var ptwoActionBarFragment:PtwoActionBarFragment
    lateinit var baseFragment: FrameLayout
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    lateinit var ptwoMainBodyFragment:PtwoMainBodyFragment
    var mlist: MutableList<String> = mutableListOf()
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        mlist.add(this.getString(R.string.educationOne))
        mlist.add(this.getString(R.string.educationTwo))
        mlist.add(this.getString(R.string.educationThree))
        mlist.add(this.getString(R.string.educationFour))
        mlist.add(this.getString(R.string.educationFive))
        mlist.add(this.getString(R.string.educationSix))

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var resumeId = intent.getStringExtra("resumeId")
        var mainScreenId=1
        baseFragment = frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    ptwoActionBarFragment = PtwoActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, ptwoActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    ptwoMainBodyFragment = PtwoMainBodyFragment.newInstance(resumeId)
                    supportFragmentManager.beginTransaction().replace(id, ptwoMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(ptwoActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonInformationTwoActivity, 0, ptwoActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    // 展开弹窗
    override fun addListFragment() {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        editAlertDialog = BottomSelectDialogFragment.newInstance(this.getString(R.string.education), mlist)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
    }

    // 关闭弹窗
    fun closeAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }

    override fun shadowClicked() {
        closeAlertDialog()
    }

    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    override fun getback(index: Int, list: MutableList<String>) {
        println(list)
        println(list[index])

        ptwoMainBodyFragment.setEducation(list[index])
        closeAlertDialog()

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event!!.keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }else {
            return super.dispatchKeyEvent(event)
        }
    }

}