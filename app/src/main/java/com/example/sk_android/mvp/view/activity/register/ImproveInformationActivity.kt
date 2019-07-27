package com.example.sk_android.mvp.view.activity.register

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.sk_android.R
import com.jaeger.library.StatusBarUtil
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import android.R.attr.fragment
import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.view.inputmethod.InputMethodManager
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RollTwoChooseFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.mvp.view.fragment.register.*
import com.example.sk_android.utils.PermissionHelper
import com.example.sk_android.utils.PermissionInterface
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap


class ImproveInformationActivity : AppCompatActivity(),
    IiMainBodyFragment.Middleware,
    ShadowFragment.ShadowClick,
    RollChooseFrag.RollToolClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect,
    RollTwoChooseFrag.DemoClick{

    lateinit var iiActionBarFragment: IiActionBarFragment
    lateinit var iiMainBodyFragment: IiMainBodyFragment
    lateinit var baseFragment: FrameLayout
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    var ImagePaths = HashMap<String, Uri>()
    var mlist: MutableList<String> = mutableListOf()
    var rollChoose: RollChooseFrag? = null
    var rolltwo: RollTwoChooseFrag? = null



    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        mlist.add(this.getString(R.string.IiStatusOne))
        mlist.add(this.getString(R.string.IiStatusTwo))
        mlist.add(this.getString(R.string.IiStatusThree))
        mlist.add(this.getString(R.string.IiStatusFour))

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
                    iiActionBarFragment = IiActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id, iiActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }
                scrollView {

                    frameLayout {
                        id = 3
                        iiMainBodyFragment = IiMainBodyFragment.newInstance(ImagePaths)
                        supportFragmentManager.beginTransaction().add(id, iiMainBodyFragment).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams(width = matchParent, height = wrapContent)

            }.lparams {
                width = matchParent
                height = matchParent
            }


        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(iiActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@ImproveInformationActivity, 0, iiActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        iiActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
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

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
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

        mTransaction.commit()
    }

    override fun addImage() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("标题")
            .setCropMenuCropButtonTitle("裁剪")
            .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                ImagePaths.put("uri", result.uri)
                println(ImagePaths)
                iiMainBodyFragment.setImage(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
            }
        }
    }


    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    override fun getback(index: Int, list: MutableList<String>) {
        println(list)
        println(list[index])

        iiMainBodyFragment.setData(list[index])
        closeAlertDialog()
    }

    override fun shadowClicked() {
        closeAlertDialog()
    }

    override fun birthdate() {
        openDate("生年月日")
    }

    private fun openDate(s: String) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(iiMainBodyFragment.view!!.windowToken, 0)

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

    override fun twoOnClick() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(iiMainBodyFragment.view!!.windowToken, 0)

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

    //取消按钮
    override fun cancelClick() {
        closeAlertDialog()
    }

    //确认按钮
    override fun confirmClick(methodName: String, text: String) {
        if (methodName == "生年月日") {
            iiMainBodyFragment.setBirthday(text)
        }
        closeAlertDialog()
    }

    override fun rollTwoCancel() {
        closeAlertDialog()
    }

    override fun rollTwoConfirm(text1: String, text2: String) {
        var year = text1.trim().substring(0,text1.trim().length-1)
        var month = text2.trim().substring(0,text2.trim().length-1)
        var result = "$year-$month"
        iiMainBodyFragment.setPositionDate(result)
        closeAlertDialog()
    }

}