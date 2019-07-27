package com.example.sk_android.mvp.view.activity.register

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RollTwoChooseFrag
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
import java.util.*

class PersonInformationTwoActivity:AppCompatActivity(),PtwoMainBodyFragment.Intermediary, ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect,RollTwoChooseFrag.DemoClick {

    lateinit var ptwoActionBarFragment:PtwoActionBarFragment
    lateinit var baseFragment: FrameLayout
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    lateinit var ptwoMainBodyFragment:PtwoMainBodyFragment
    var mlist: MutableList<String> = mutableListOf()
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var timeCondition = "start"
    var rolltwo: RollTwoChooseFrag? = null

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
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(ptwoMainBodyFragment.view!!.windowToken, 0)

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

        if(rolltwo != null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,R.anim.bottom_out
            )
            mTransaction.remove(rolltwo!!)
            rolltwo = null
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

    override fun twoOnClick(condition: String) {

        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(ptwoMainBodyFragment.view!!.windowToken, 0)
        timeCondition = condition
        // 弹出年月选择器
        var cd = Calendar.getInstance()
        var year = cd.get(Calendar.YEAR)
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment != null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        val list1:MutableList<String> = mutableListOf()

        var number = 0
        for(index in 1970..year){
            list1.add(number,index.toString()+"年")
            number += 1
        }
        val list2 = mutableListOf("01月","02月","03月","04月","05月","06月","07月","08月","09月",
            "10月","11月","12月")

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        if (rolltwo == null) {
            rolltwo = RollTwoChooseFrag.newInstance("", list1, list2)
            mTransaction.add(baseFragment.id, rolltwo!!).commit()
        }


    }

    override fun rollTwoCancel() {
        closeAlertDialog()
    }

    override fun rollTwoConfirm(text1: String, text2: String) {
        var year = text1.trim().substring(0,text1.trim().length-1)
        var month = text2.trim().substring(0,text2.trim().length-1)
        var result = "$year-$month"
        when(timeCondition){
            "start" -> ptwoMainBodyFragment.showStartPicker(result)
            "end" -> ptwoMainBodyFragment.showEndPicker(result)
        }
        closeAlertDialog()
    }

}