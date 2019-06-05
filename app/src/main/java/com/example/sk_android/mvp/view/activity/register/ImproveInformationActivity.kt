package com.example.sk_android.mvp.view.activity.register

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
import com.example.sk_android.mvp.view.fragment.register.IiActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.IiMainBodyFragment
import com.example.sk_android.mvp.view.fragment.register.WsBackgroundFragment
import com.example.sk_android.mvp.view.fragment.register.WsListFragment
import com.jaeger.library.StatusBarUtil
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import java.util.ArrayList

class ImproveInformationActivity : AppCompatActivity() ,IiMainBodyFragment.Middleware,WsListFragment.CancelTool{

    lateinit var iiActionBarFragment: IiActionBarFragment
    lateinit var baseFragment: FrameLayout

    var wsBackgroundFragment:WsBackgroundFragment?=null
    var wsListFragment:WsListFragment?=null

    var ImagePaths = HashMap<String,Uri>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId = 1
        PushAgent.getInstance(this).onAppStart()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        baseFragment = frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            scrollView {
                relativeLayout {
                    id = 30
                    //ActionBar
                    val actionBarId = 2
                    frameLayout {

                        id = actionBarId
                        iiActionBarFragment = IiActionBarFragment.newInstance();
                        supportFragmentManager.beginTransaction().add(id, iiActionBarFragment).commit()

                    }.lparams {
                        height = wrapContent
                        width = matchParent
                    }

                    frameLayout {
                        id = 3
                        val iiMainBodyFragment = IiMainBodyFragment.newInstance(ImagePaths)
                        supportFragmentManager.beginTransaction().add(id, iiMainBodyFragment).commit()
                    }.lparams(width = matchParent, height = matchParent)

                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(iiActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@ImproveInformationActivity, 0, iiActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @SuppressLint("ResourceType")
    override fun addListFragment() {

        var mTransaction=supportFragmentManager.beginTransaction()

        wsBackgroundFragment = WsBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, wsBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)


        wsListFragment = WsListFragment.newInstance()
        mTransaction.add(baseFragment.id, wsListFragment!!)

        mTransaction.commit()
    }

    override fun cancelList() {
        var mTransaction=supportFragmentManager.beginTransaction()

        if (wsListFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)

            mTransaction.remove(wsListFragment!!)
            wsListFragment = null
        }

        if (wsBackgroundFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)

            mTransaction.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null

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
                ImagePaths.put("uri",result.uri)
                println(ImagePaths)
                modifyPictrue()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    //每次修改图片list,重新刷新fragment
    private fun modifyPictrue(){
        val id = 3
        val iiMainBodyFragment = IiMainBodyFragment.newInstance(ImagePaths)
        supportFragmentManager.beginTransaction().replace(id, iiMainBodyFragment).commitAllowingStateLoss()
    }



}