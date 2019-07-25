package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.view.activity.mysystemsetup.SystemSetupActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.EditBasicInformation
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.UploadPic
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException
import java.io.File


class EditBasicInformation : AppCompatActivity(), ShadowFragment.ShadowClick,
    EditBasicInformation.Middleware, BottomSelectDialogFragment.BottomSelectDialogSelect,
    RollChooseFrag.RollToolClick, CommonBottomButton.CommonButton {

    private var resumeId: String? = null
    private var basic: UserBasicInformation? = null
    private lateinit var resumebutton: CommonBottomButton
    private lateinit var editList: EditBasicInformation
    private lateinit var baseFragment: FrameLayout
    private var shadowFragment: ShadowFragment? = null
    private var editAlertDialog: BottomSelectDialogFragment? = null
    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private var rollChoose: RollChooseFrag? = null
    private lateinit var imagePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val base = 1
        baseFragment = frameLayout {
            id = base
            verticalLayout {
                val actionBarId=4
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("基本情報を編集");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                val itemList = 2
                val button = 3
                frameLayout {
                    frameLayout {
                        id = itemList
                        editList = EditBasicInformation.newInstance()
                        supportFragmentManager.beginTransaction().add(itemList, editList).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(70)
                    }
                    frameLayout {
                        id = button
                        resumebutton = CommonBottomButton.newInstance("セーブ", 0, R.drawable.button_shape_orange)
                        supportFragmentManager.beginTransaction().add(button, resumebutton).commit()
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@EditBasicInformation, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        if(intent.getStringExtra("resumeId")!=null){
            resumeId = intent.getStringExtra("resumeId")
        }

        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUser()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        basic = null
    }



    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    // 选择弹窗的选中的item
    override fun getback(index: Int, list: MutableList<String>) {
        if (index != -1) {
            //如果是选择的头像按钮
            when (list[index]) {
                "自定する" -> {
                    camera()
                }
                "黙認" -> editList.setDefaultImg()
                "男" -> editList.setSex(list[index])
                "女" -> editList.setSex(list[index])
            }
        }

        closeAlertDialog()
    }

    //　黑色背景弹窗
    override fun shadowClicked() {
        closeAlertDialog()
    }

    //打开弹窗
    override fun addListFragment(title: String, list: MutableList<String>) {
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

        editAlertDialog = BottomSelectDialogFragment.newInstance(title, list)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
    }

    //　点击出生日期
    override fun birthdateclick(text: String) {
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
            rollChoose = RollChooseFrag.newInstance(text)
        }
        mTransaction.add(baseFragment.id, rollChoose!!)
        mTransaction.commit()
    }

    //　点击就职日期
    override fun jobdateClick(text: String) {
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
            rollChoose = RollChooseFrag.newInstance(text)
        }
        mTransaction.add(baseFragment.id, rollChoose!!)
        mTransaction.commit()
    }

    //　日期滚动选择器关闭按钮
    override fun cancelClick() {
        closeAlertDialog()
    }

    //　日期滚动选择器确定按钮
    override fun confirmClick(methodName: String, text: String) {
        if (methodName == "jobDate") {
            editList.setJobDate(text)
        } else {
            editList.setBirthday(text)
        }
        closeAlertDialog()
    }

    // 点击底部橘黄按钮
    override suspend fun btnClick(text: String) {
        val userBasic = editList.getBasic()
        if (userBasic != null) {
            changeUserPhoneNum(userBasic.phone)
            updateUser(userBasic)
        }
    }

    //摄像,选择图片
    private fun camera() {
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
                imagePath = result.uri
                println("imagePath-----------------------------" + imagePath.path)
                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                    upLoadPic(imagePath)
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                println(result.error)
            }
        }
    }

    // 上传图片
    private suspend fun upLoadPic(avatarURL: Uri) {
        var file = File(avatarURL.path)
        if(file.length() <= 1024*1024){
            val obj = UploadPic().upLoadPic(avatarURL.path!!, this@EditBasicInformation, "user-head")
            val sub = obj?.get("url")!!.asString.split(";")[0]
            println("sub-----------------$sub")
            editList.setImage(sub)
        }else{
            val toast = Toast.makeText(applicationContext, "头像超过1M", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
    }

    // 更新用户基本信息
    private suspend fun updateUser(userOnlioneResume: UserBasicInformation) {
        try {
            // 再更新用户信息
            val params = mapOf(
                "attributes" to userOnlioneResume.attributes,
                "avatarUrl" to userOnlioneResume.avatarURL,
                "birthday" to userOnlioneResume.birthday.toString(),
                "displayName" to userOnlioneResume.displayName,
                "educationalBackground" to userOnlioneResume.educationalBackground,
                "email" to userOnlioneResume.email,
                "firstName" to userOnlioneResume.firstName,
                "gender" to userOnlioneResume.gender,
                "lastName" to userOnlioneResume.lastName,
                "line" to userOnlioneResume.line,
                "phone" to userOnlioneResume.phone,
                "videoThumbnailUrl" to userOnlioneResume.videoThumbnailURL,
                "videoUrl" to userOnlioneResume.videoURL,
                "workingStartDate" to userOnlioneResume.workingStartDate.toString()
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@EditBasicInformation, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateUserSelf(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "更新成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                val intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
                overridePendingTransition(R.anim.left_in,R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 获取用户基本信息
    private suspend fun getUser() {
        try {
            val retrofitUils = RetrofitUtils(this@EditBasicInformation, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getUserSelf()
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                if (!it.body()?.get("changedContent")!!.isJsonNull) {
                    var json = it.body()?.get("changedContent")!!.asJsonObject
                    basic = Gson().fromJson<UserBasicInformation>(json, UserBasicInformation::class.java)
                    basic?.id= it.body()?.get("id")!!.asString
                    basic?.phone = it.body()?.get("phone")!!.asString
                    editList.setUserBasicInfo(basic!!)
                }else{
                    val json = it.body()?.asJsonObject
                    basic = Gson().fromJson<UserBasicInformation>(json, UserBasicInformation::class.java)
                    editList.setUserBasicInfo(basic!!)
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 修改个人信息的手机号 user接口
    private suspend fun changeUserPhoneNum(phoneNum: String) {
        try {
            val params = mapOf(
                "code" to "86",
                "phone" to phoneNum
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@EditBasicInformation, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .changeUserPhoneNum(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {

            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
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
        mTransaction.commit()
    }
}