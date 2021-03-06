package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.store.FetchInformationAsyncAction
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RollThreeChooseFrag
import com.example.sk_android.mvp.view.fragment.jobselect.RollTwoChooseFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.person.PiActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PiMainBodyFragment
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import retrofit2.adapter.rxjava2.HttpException
import java.util.*
import kotlin.collections.HashMap

class PersonInformation : AppCompatActivity(),
    PiMainBodyFragment.Middleware,
    ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect,
    RollChooseFrag.RollToolClick,
    RollTwoChooseFrag.DemoClick{

    var rolltwo: RollTwoChooseFrag? = null

    override fun getback(index: Int, list: MutableList<String>) {

    }

    lateinit var piActionBarFragment: PiActionBarFragment
    lateinit var piMainBodyFragment: PiMainBodyFragment
    lateinit var baseFragment: FrameLayout
    var rollChoose: RollChooseFrag? = null
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    var ImagePaths = HashMap<String, Uri>()
    var mlist: MutableList<String> = mutableListOf()
    var condition: Int = 0

    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(this, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        mlist.add(this.getString(R.string.IiStatusOne))
        mlist.add(this.getString(R.string.IiStatusTwo))
        mlist.add(this.getString(R.string.IiStatusThree))
        mlist.add(this.getString(R.string.IiStatusFour))

        condition = intent.getIntExtra("condition", 0)

        super.onCreate(savedInstanceState)
        var mainScreenId = 1
        PushAgent.getInstance(this).onAppStart()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        baseFragment = frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                id = 30
                //ActionBar
                val actionBarId = 2
                frameLayout {

                    id = actionBarId
                    piActionBarFragment = PiActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id, piActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                frameLayout {
                    id = 3
                    piMainBodyFragment = PiMainBodyFragment.newInstance(ImagePaths, condition)
                    supportFragmentManager.beginTransaction().add(id, piMainBodyFragment).commit()
                }.lparams(width = matchParent, height = wrapContent)

            }.lparams {
                width = matchParent
                height = matchParent
            }


        }

        init()
    }


    override fun onStart() {
        super.onStart()
        setActionBar(piActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonInformation, 0, piActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        piActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }


    override fun birthdate() {
        openDate("生年月日")
    }

    private fun openDate(s: String) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(piMainBodyFragment.view!!.windowToken, 0)

        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )
        if (rollChoose == null) {
            rollChoose = RollChooseFrag.newInstance(s)
            mTransaction.add(baseFragment.id, rollChoose!!)
        }

        mTransaction.commit()
    }

    override fun jobdate() {
        openDate("初就職年月")
    }

    //打开弹窗
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

        editAlertDialog = BottomSelectDialogFragment.newInstance(this.getString(R.string.jobSearchStatus), mlist)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
    }

    //　日期滚动选择器关闭按钮
    override fun cancelClick() {
        closeAlertDialog()
    }

    //　日期滚动选择器确定按钮
    override fun confirmClick(methodName: String, text: String) {
        if (methodName == "生年月日") {
            piMainBodyFragment.setbirthDate(text)
        } else {
            piMainBodyFragment.setjobDate(text)
        }
        closeAlertDialog()
    }

    //关闭弹窗
    fun closeAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }
        if (rollChoose != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(rollChoose!!)
            rollChoose = null
        }

        if(rolltwo != null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,R.anim.bottom_out
            )
            mTransaction.remove(rolltwo!!)
            rolltwo = null
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

    override fun addImage() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("カスタマ")
            .setCropMenuCropButtonTitle("確認")
            .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                ImagePaths.put("uri", result.uri)
                println(ImagePaths)
                piMainBodyFragment.setImage(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
            }
        }
    }


    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }


    override fun shadowClicked() {
        closeAlertDialog()
    }

    @SuppressLint("CheckResult")
    fun init() {
        // 1:创建  0:更新
        if (condition == 0) {
            thisDialog= DialogUtils.showLoading(this)
            mHandler.postDelayed(r, 12000)

            var retrofitUils = RetrofitUtils(this, this.getString(R.string.userUrl))
            retrofitUils.create(PersonApi::class.java)
                .information
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("++++++++++++++++++**************")
                    println(it)
                    DialogUtils.hideLoading(thisDialog)
                    piMainBodyFragment.ininData(it)
                }, {
                    if (it is HttpException) {
                        if (it.code() == 401) {

                        }
                    }
                    toast(R.string.piGetPerson)
                })
        }else{

        }
    }

    override fun twoOnClick() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(piMainBodyFragment.view!!.windowToken, 0)

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
        piMainBodyFragment.setPositionDate(result)
        closeAlertDialog()
    }
}