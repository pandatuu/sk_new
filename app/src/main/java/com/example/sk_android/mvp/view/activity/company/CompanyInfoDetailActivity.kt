package com.example.sk_android.mvp.view.activity.company

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.mvp.view.activity.common.AccusationActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.CompanyDetailActionBarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.CompanyDetailInfoFragment
import com.example.sk_android.mvp.view.fragment.mysystemsetup.LoginOutFrag
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*


class CompanyInfoDetailActivity : AppCompatActivity(), CompanyDetailActionBarFragment.CompanyDetailActionBarSelect,
    ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect, TipDialogFragment.TipDialogSelect,
    CompanyInfoSelectbarFragment.SelectBar,
    LoginOutFrag.TextViewCLick {
    override fun cancelLogClick() {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null

        }
        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
        }

        mTransaction.commit()
    }

    override suspend fun chooseClick() {

    }

    //得到选项
    override fun getback(index: Int, list: MutableList<String>) {

        closeBottomDialog()


        var intent = Intent(this, AccusationActivity::class.java)
        intent.putExtra("type", list.get(index))
        intent.putExtra("organizationId", "de92bc91-4280-4f8b-ba1b-9b32409db109")
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }


    override fun getSelectBarItem(index: Int) {

    }


    override suspend fun getTipDialogSelect(b: Boolean) {
        closeAlertDialog()
    }

    //底部弹框
    override fun getBottomSelectDialogSelect() {
    }

    //阴影
    override fun shadowClicked() {
        closeBottomDialog()
    }

    //举报
    override fun jubaoSelect() {

        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance();
            mTransaction.add(mainBody.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )


        bottomSelectDialogFragment =
            BottomSelectDialogFragment.newInstance("告発", mutableListOf("嫌がらせ", "広告", "詐欺情報", "その他"));
        mTransaction.add(mainBody.id, bottomSelectDialogFragment!!)

        mTransaction.commit()
    }


    var shadowFragment: ShadowFragment? = null
    var bottomSelectDialogFragment: BottomSelectDialogFragment? = null
    var tipDialogFragment: TipDialogFragment? = null


    private var mgListener: MyGestureListener? = null
    private var mDetector: GestureDetector? = null
    var logoutFragment: LoginOutFrag? = null

    lateinit var mainBody: FrameLayout

    lateinit var companyDetailActionBarFragment: CompanyDetailActionBarFragment
    lateinit var companyDetailInfoFragment: CompanyDetailInfoFragment

    var objectAnimator: ObjectAnimator? = null

    override fun onStart() {
        super.onStart()
        setActionBar(companyDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(
            this@CompanyInfoDetailActivity,
            0,
            companyDetailActionBarFragment.toolbar1
        )
        companyDetailActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }

        if (intent.getStringExtra("companyId") != null) {
            val id = intent.getStringExtra("companyId")
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getCompany(id)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            val mTransaction = supportFragmentManager.beginTransaction()
            mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (shadowFragment == null) {
                shadowFragment = ShadowFragment.newInstance()
                mTransaction.add(mainBody.id, shadowFragment!!)
            }
            logoutFragment = LoginOutFrag.newInstance(this@CompanyInfoDetailActivity)
            mTransaction.add(mainBody.id, logoutFragment!!)
            mTransaction.commit()
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();
        var mainBodyId=1
        mainBody=frameLayout {
            id=mainBodyId
            backgroundColor=Color.WHITE

            //ActionBar
            var actionBarId=2
            frameLayout{
                id=actionBarId
                companyDetailActionBarFragment= CompanyDetailActionBarFragment.newInstance()
                supportFragmentManager.beginTransaction().add(id,companyDetailActionBarFragment).commit()

//                   companyDetailInfoFragment=CompanyDetailInfoFragment_old.newInstance("アニメ谷はデジタル映像制作に携わっており、CG技 术作品で世界を繋ぐことに力を注いでいる。！私たち は、世界市场に向けてより広范なグローバル市场に进 むことができるように、制作の実力の向上とチーー…")
//                   supportFragmentManager.beginTransaction().add(id,companyDetailInfoFragment).commit()



                companyDetailInfoFragment= CompanyDetailInfoFragment.newInstance("")
                supportFragmentManager.beginTransaction().add(id,companyDetailInfoFragment).commit()

            }.lparams {
                height= matchParent
                width= matchParent
            }
        }


        mgListener = MyGestureListener()
        mDetector = GestureDetector(this, mgListener)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mDetector!!.onTouchEvent(event)
        // return false
    }

    private inner class MyGestureListener : GestureDetector.OnGestureListener {


        var start = 0f


        override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {


            if (objectAnimator !== null)
                toast(objectAnimator!!.getAnimatedValue("translationY").toString())

            toast("onFling:迅速滑动，并松开")
            var dm = getResources().getDisplayMetrics();
            var h_screen = dm.heightPixels;

            val endY = companyDetailActionBarFragment.mainLayout.getMeasuredHeight()
            val transXHolder = PropertyValuesHolder.ofFloat("translationX", 0f, 0f)
            val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1f, 1f)
            val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1f)

            //dp转像素
            var px = dip2px(ctx, 343f)

            //上滑动  且  触发的位置是指定位置时
            if ((motionEvent.getY() - motionEvent1.getY() > 0 && px < motionEvent.getY())) {
                //未上滑时 可上滑  否则不可
                if (objectAnimator == null || objectAnimator!!.getAnimatedValue("translationY").toString().equals("0.0")) {

                    //(383-105)/383 上滑动的距离 比例
                    val transYHolder = PropertyValuesHolder.ofFloat("translationY", 0f, 0 - endY * 0.7258f)

                    objectAnimator =
                        ObjectAnimator.ofPropertyValuesHolder(
                            companyDetailInfoFragment.swipeLayout,
                            transXHolder,
                            transYHolder,
                            scaleXHolder,
                            scaleYHolder
                        )

                    objectAnimator!!.setDuration(200)
                    objectAnimator!!.start()//播放完后，图片会回到原来的位置

                }
            } else if (motionEvent.getY() - motionEvent1.getY() < 0) {//下滑时
                //已经被滑动上去后 才能下滑
                if (objectAnimator != null && objectAnimator!!.getAnimatedValue("translationY").toString().equals((0 - endY * 0.7258f).toString())) {


                    //(383-105)/383 下滑动的距离 比例
                    val transYHolder = PropertyValuesHolder.ofFloat("translationY", -endY * 0.7258f, 0f)

                    objectAnimator =
                        ObjectAnimator.ofPropertyValuesHolder(
                            companyDetailInfoFragment.swipeLayout,
                            transXHolder,
                            transYHolder,
                            scaleXHolder,
                            scaleYHolder
                        )
                    objectAnimator!!.setDuration(200)
                    objectAnimator!!.start()//播放完后，图片会回到原来的位置

                }
            }
            return false
        }

        override fun onDown(motionEvent: MotionEvent): Boolean {
            toast("onDown:按下")
            return true
        }

        override fun onShowPress(motionEvent: MotionEvent) {
            toast("onShowPress:手指按下一段时间,不过还没到长按")
        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            toast("onSingleTapUp:手指离开屏幕的一瞬间")


            return false
        }

        override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {

            return false
        }

        override fun onLongPress(motionEvent: MotionEvent) {
            //toast("onLongPress:长按并且没有松开")
        }


    }


    fun dip2px(context: Context, dipValue: Float): Int {
        var scale = context.getResources().getDisplayMetrics().density;
        return ((dipValue * scale + 0.5f).toInt())
    }


    fun closeBottomDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
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


    fun showAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance();
            mTransaction.add(mainBody.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )


        tipDialogFragment = TipDialogFragment.newInstance(1, "通報成功")
        mTransaction.add(mainBody.id, tipDialogFragment!!)

        mTransaction.commit()
    }


    fun closeAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (tipDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(tipDialogFragment!!)
            tipDialogFragment = null
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

    private suspend fun getCompany(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@CompanyInfoDetailActivity, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .getCompanyById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                val model = it.body()!!.asJsonObject
                getCompanyAddress(id, model)
//                CompanyBriefInfo
            }
        } catch (e: Throwable) {
            println(e)
        }
    }

    private suspend fun getCompanyAddress(id: String, body: JsonObject) {
//        val sizes = mapOf(
//            "TINY" to R.string.IiBorn,//"0-22",
//            "SMALL" to R.string.IiBorn,//20-99",
//            "MEDIUM" to R.string.IiBorn,//"100-499",
//            "BIG" to R.string.IiBorn,//"500-999",
//            "HUGE" to R.string.IiBorn,//"1000-9999",
//            "SUPER" to R.string.IiBorn//"10000以上"
//        )
//
        try {
            val retrofitUils = RetrofitUtils(this@CompanyInfoDetailActivity, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .getCompanyAddressById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                val model = it.body()!!
                val companyIntroduce= if(body.get("attributes").asJsonObject.get("companyIntroduce")!=null)body.get("attributes").asJsonObject.get("companyIntroduce").asString else ""
                val imageUrls = if(body.get("attributes").asJsonObject.get("image_urls")!=null)body.get("image_urls").asJsonArray.map { it.asString } as MutableList<String> else mutableListOf<String>()
                val startTime = if(body.get("attributes").asJsonObject.get("startTime")!=null)body.get("attributes").asJsonObject.get("startTime").asString else ""
                val overtime = if(body.get("attributes").asJsonObject.get("endtime")!=null)body.get("attributes").asJsonObject.get("endtime").asString else ""
                val company = CompanyInfo(
                    body.get("id").asString,
                    body.get("videoUrl").asString,
                    body.get("logo").asString,
                    body.get("name").asString,
                    body.get("size").asString,
                    body.get("financingStage").asString,
                    body.get("type").asString,
                    body.get("website").asString,
                    body.get("benifits").asJsonArray.map { it.asString } as MutableList<String>,
                    companyIntroduce,
                    model.get("data").asJsonArray.map { arrayListOf(it.asJsonObject.get("areaId").asString,it.asJsonObject.get("address").asString) } as MutableList<ArrayList<String>>,
                    imageUrls,
                    startTime,
                    overtime
                )
                companyDetailActionBarFragment.setUrl(company.videoUrl)
                companyDetailInfoFragment.setDetailInfo(company)
            }
        } catch (e: Throwable) {
            println(e)
        }
    }

}
