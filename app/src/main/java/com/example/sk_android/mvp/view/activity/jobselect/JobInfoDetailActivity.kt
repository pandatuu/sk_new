package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.View
import android.widget.*
import anet.channel.util.Utils.context
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.common.AccusationActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.ShareFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import android.content.pm.ActivityInfo
import android.widget.Toast
import android.os.Parcelable
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat.startActivity
import android.content.ComponentName
import android.os.Handler
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.shareboard.SnsPlatform
import com.umeng.socialize.utils.ShareBoardlistener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.json.JSONObject
import withTrigger
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class JobInfoDetailActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    JobInfoDetailSkillLabelFragment.JobInfoDetailSkillLabelSelect,
    JobInfoDetailActionBarFragment.ActionBarSelecter,
    BottomSelectDialogFragment.BottomSelectDialogSelect,
    ShareFragment.SharetDialogSelect, OnMapReadyCallback {


    var dataFromType = ""

    var userId = ""

    var companyName = ""

    var organizationId = ""

    var userName = ""

    var recruitMessageId = ""

    var avatarURL = ""

    var mContext = this

    private var dialogLoading: DialogLoading? = null

    private var mMap: GoogleMap? = null

    private var mMapView: MapView? = null
    lateinit var testFrame: FrameLayout

    var MAPVIEW_BUNDLE_KEY: String = "MapViewBundleKey"
    //分享的选项
    override fun getSelectedItem(index: Int) {
        hideDialog()

        when (index) {
            0 -> {
                toast("line")

                val content = "hello world"
                ShareAction(this@JobInfoDetailActivity)
                    .setPlatform(SHARE_MEDIA.LINE)//传入平台
                    .withText(content)//分享内容
                    .share()

                GlobalScope.launch() {
                    createShareMessage("LINE", "title", content)
                }
            }
            1 -> {
                toast("twitter")

                val content = "hello world"

                val builder = TweetComposer.Builder(this@JobInfoDetailActivity)
                builder.text(content)
                    .show()

                GlobalScope.launch() {
                    createShareMessage("TWITTER", "title", content)
                }
            }
            else -> {
                toast("暂未开放")
            }
        }

    }

    //点击取消按钮
    override fun getBottomSelectDialogSelect() {
        hideDialog()

    }

    override fun getback(index: Int, list: MutableList<String>) {
        hideDialog()

        var intent = Intent(this, AccusationActivity::class.java)
        intent.putExtra("type", list.get(index))
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }


    lateinit var desInfo: FrameLayout
    lateinit var mainContainer: FrameLayout


    lateinit var jobInfoDetailActionBarFragment: JobInfoDetailActionBarFragment


    var shareFragment: ShareFragment? = null

    var jobInfoDetailDescribeInfoFragment: JobInfoDetailDescribeInfoFragment? = null
    var bottomSelectDialogFragment: BottomSelectDialogFragment? = null

    var shadowFragment: ShadowFragment? = null

    var jobInfoDetailCompanyInfoFragment: JobInfoDetailCompanyInfoFragment? = null
    var jobInfoDetailBossInfoFragment: JobInfoDetailBossInfoFragment? = null

    //action bar 上的图标 被选择
    override fun gerActionBarSelectedItem(index: Int) {

        hideDialog()

        var mTransaction = supportFragmentManager.beginTransaction()
        showShadow(mTransaction)


        //举报
        if (index == 1) {

            var strArray: MutableList<String> = mutableListOf("広告", "嫌がらせ", "詐欺情報", "その他")

            bottomSelectDialogFragment = BottomSelectDialogFragment.newInstance("告発", strArray)
            mTransaction.setCustomAnimations(
                R.anim.bottom_in, R.anim.bottom_in
            )
            mTransaction.add(mainContainer.id, bottomSelectDialogFragment!!)

        } else if (index == 2) {
            //分享

            shareFragment = ShareFragment.newInstance()
            mTransaction.setCustomAnimations(
                R.anim.bottom_in, R.anim.bottom_in
            )
            mTransaction.add(mainContainer.id, shareFragment!!)
        }



        mTransaction.commit()

    }

    //技能标签 被选择
    override fun getSelectedLabel(str: String) {
        toast(str)
    }


    //收回下拉框
    override fun shadowClicked() {
        hideDialog()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(jobInfoDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobInfoDetailActivity, 0, jobInfoDetailActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        jobInfoDetailActionBarFragment.toolbar1!!.setNavigationOnClickListener {

            var mIntent = Intent()
            mIntent.putExtra("position", jobInfoDetailActionBarFragment!!.getPosition())
            mIntent.putExtra("isCollection", jobInfoDetailActionBarFragment!!.getIsCollection())
            mIntent.putExtra("collectionId", jobInfoDetailActionBarFragment!!.getCollectionId())

            setResult(RESULT_OK, mIntent);
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            mMapView!!.onStart()

        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        UMConfigure.init(
            this, "5cdcc324570df3ffc60009c3"
            , "umeng", UMConfigure.DEVICE_TYPE_PHONE, ""
        )

        dataFromType = intent.getStringExtra("fromType")

        userId = (intent.getStringExtra("userId"))

        companyName = (intent.getStringExtra("companyName"))

        organizationId = (intent.getStringExtra("organizationId"))

        userName = (intent.getStringExtra("userName"))

        recruitMessageId = (intent.getStringExtra("recruitMessageId"))

        avatarURL = (intent.getStringExtra("avatarURL"))

        var view = View.inflate(this, R.layout.map_view, null)
        var mapViewBundle: Bundle? = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = view.findViewById(R.id.mMapView)
        mMapView!!.onCreate(mapViewBundle);

        mMapView!!.getMapAsync(this);

        var mainContainerId = 1
        mainContainer = frameLayout {
            id = mainContainerId
            backgroundColorResource = R.color.white
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    jobInfoDetailActionBarFragment = JobInfoDetailActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, jobInfoDetailActionBarFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                scrollView {
                    overScrollMode = View.OVER_SCROLL_NEVER
                    verticalLayout {
                        var topInfoId = 10
                        frameLayout {
                            id = topInfoId
                            var jobInfoDetailTopInfoFragment = JobInfoDetailTopInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailTopInfoFragment).commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var bossInfoId = 11
                        frameLayout {
                            id = bossInfoId
                            jobInfoDetailBossInfoFragment = JobInfoDetailBossInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailBossInfoFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var desInfoId = 12
                        desInfo = frameLayout {
                            id = desInfoId
                            jobInfoDetailDescribeInfoFragment =
                                JobInfoDetailDescribeInfoFragment.newInstance("1、バックグランドシステムの设计、开発作业を担当している;…");
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailDescribeInfoFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var skillInfoId = 13
                        frameLayout {
                            id = skillInfoId
                            var jobInfoDetailSkillLabelFragment =
                                JobInfoDetailSkillLabelFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailSkillLabelFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }


                        var companyInfoId = 14
                        frameLayout {
                            id = companyInfoId
                            jobInfoDetailCompanyInfoFragment = JobInfoDetailCompanyInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailCompanyInfoFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var MapInfoId = 20
                        testFrame = frameLayout {
                            addView(view)
                        }.lparams {
                            height = dip(230)
                            width = matchParent
                        }


                    }.lparams(matchParent)
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }

                textView {
                    text = "すぐに連絡"
                    backgroundResource = R.drawable.radius_button_theme
                    gravity = Gravity.CENTER
                    textSize = 15f
                    textColor = Color.WHITE





                    this.withTrigger().click {


                        if (dataFromType.equals("CHAT")) {
                            //从聊天界面转过来的
                            finish()//返回
                            overridePendingTransition(R.anim.left_in, R.anim.right_out)
                        } else {
                            println("跳转到聊天！！！！！！！")

                            //跳转到聊天界面
                            intent = Intent(mContext, MessageListActivity::class.java)
                            intent.putExtra("hisId", userId)
                            intent.putExtra("companyName", companyName)
                            intent.putExtra("company_id", organizationId)
                            intent.putExtra("hisName", userName)
                            intent.putExtra("position_id", recruitMessageId)
                            intent.putExtra("hislogo", avatarURL)

                            startActivity(intent)
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)

                        }

                    }


                }.lparams {
                    height = dip(47)
                    width = matchParent
                    leftMargin = dip(23)
                    rightMargin = dip(23)
                    bottomMargin = dip(13)
                    topMargin = dip(14)
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

        getPositionNum()


    }


    fun getPositionNum() {
        DialogUtils.showLoading(context!!)
        var positionNameRequest =
            RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
        positionNameRequest.create(CompanyInfoApi::class.java)
            .getPositionNumberOfCompany(
                organizationId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("公司的职位个数请求成功!!!")
                println(it)


                var json = JSONObject(it.toString())
                var positionNum = json.getInt("positionCount")
                jobInfoDetailCompanyInfoFragment!!.setPositionNum(positionNum)
                jobInfoDetailBossInfoFragment!!.setPositionNum(positionNum)
                 DialogUtils.hideLoading()
            }, {
                println("公司的职位个数请求失败!!!")
                println(it)
                 DialogUtils.hideLoading()
            })
    }


    fun hideDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null

        }

        if (shareFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(shareFragment!!)
            shareFragment = null

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

    fun showShadow(mTransaction: FragmentTransaction) {

        shadowFragment = ShadowFragment.newInstance()
        mTransaction.setCustomAnimations(
            R.anim.fade_in_out, R.anim.fade_in_out
        )
        mTransaction.add(mainContainer.id, shadowFragment!!)

    }

    private val shareListener = object : UMShareListener {
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@JobInfoDetailActivity, "成功了", Toast.LENGTH_LONG).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {

        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {

        }

        override fun onStart(p0: SHARE_MEDIA?) {

        }
    }
//
//    @SuppressLint("CheckResult")
//    fun getData(): Any {
//        var list = Any()
//        var baseRetrofitUils = RetrofitUtils(this, this.getString(R.string.orgUrl))
//        baseRetrofitUils.create(PersonApi::class.java)
//            .getAddressByCompanyId(organizationId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                var result = it.get("data").asJsonArray[0].asJsonObject.get("coordinate").asJsonObject.get("coordinates").asJsonArray
//                list = result
//                return list
//            },{
//                return null
//            })
//        return null
//    }

    @SuppressLint("CheckResult")
    override fun onMapReady(map: GoogleMap) {
//
        var baseRetrofitUils = RetrofitUtils(this, this.getString(R.string.orgUrl))
        baseRetrofitUils.create(PersonApi::class.java)
            .getAddressByCompanyId(organizationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println(it)
                var result =
                    it.get("data").asJsonArray[0].asJsonObject.get("coordinate").asJsonObject.get("coordinates")
                        .asJsonArray
                var addressName = it.get("data").asJsonArray[0].asJsonObject.get("address").toString().replace("\"", "")
                val appointLoc = LatLng(result[1].asDouble, result[0].asDouble)
//                var lat = 39.937795;
//                var lng = 116.387224;
//                var appointLoc = LatLng(lat, lng);

                map!!.addMarker(MarkerOptions().position(appointLoc).title(addressName));
                map!!.moveCamera(CameraUpdateFactory.newLatLng(appointLoc))

                // 不允许手势缩放
                map.uiSettings.isZoomGesturesEnabled = false
                // 不允许拖动地图
                map.uiSettings.isScrollGesturesEnabled = false

                // 设置缩放级别
                val zoom = CameraUpdateFactory.zoomTo(15f)
                map.animateCamera(zoom)
            }, {

            })
//        // Add a marker in Sydney, Australia, and move the camera.
//        val sydney = LatLng(-34.0, 151.0)
//        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))

//        var lat = 39.937795;
//        var lng = 116.387224;
//        var appointLoc = LatLng(lat, lng);
//
//        map!!.addMarker(MarkerOptions().position(appointLoc).title("Marker"));
//        map!!.moveCamera(CameraUpdateFactory.newLatLng(appointLoc))
//
//        // 不允许手势缩放
//        map.uiSettings.isZoomGesturesEnabled = false
//        // 不允许拖动地图
//        map.uiSettings.isScrollGesturesEnabled = false
//
//        // 设置缩放级别
//        val zoom = CameraUpdateFactory.zoomTo(13f)
//        map.animateCamera(zoom)

    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        mMapView!!.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume();
    }

    override fun onStop() {
        super.onStop()
        mMapView!!.onStop()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }


    //创建分享的信息
    private suspend fun createShareMessage(platform: String, title: String, content: String) {
        try {
            val params = mapOf(
                "deviceType" to "ANDROID",
                "platform" to platform,
                "title" to title,
                "content" to content,
                "targetEntityType" to "ORGANIZATION_POSITION"
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@JobInfoDetailActivity, "https://push.sk.cgland.top/")
            val it = retrofitUils.create(JobSelectApi::class.java)
                .createShare(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                toast("更换成功")
            }
        } catch (throwable: Throwable) {
            println(throwable)
        }
    }

}
