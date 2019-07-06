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
import java.util.ArrayList
import android.R.attr.fragment
import android.support.v4.app.FragmentTransaction
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.register.*
import com.example.sk_android.utils.PermissionHelper
import com.example.sk_android.utils.PermissionInterface
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.Serializable


class ImproveInformationActivity : AppCompatActivity(),
    IiMainBodyFragment.Middleware,
    ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect{



    lateinit var iiActionBarFragment: IiActionBarFragment
    lateinit var iiMainBodyFragment: IiMainBodyFragment
    lateinit var baseFragment: FrameLayout
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    var ImagePaths = HashMap<String, Uri>()
    var mlist: MutableList<String> = mutableListOf()



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
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
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
    
}