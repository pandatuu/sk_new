package com.example.sk_android.mvp.view.activity.company

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import android.animation.PropertyValuesHolder
import android.content.Context
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.umeng.message.PushAgent


class CompanyInfoDetailActivity : AppCompatActivity(), CompanyDetailActionBarFragment.CompanyDetailActionBarSelect,ShadowFragment.ShadowClick,
    BottomSelectDialogFragment.BottomSelectDialogSelect , TipDialogFragment.TipDialogSelect, CompanyInfoSelectbarFragment.SelectBar {


    override fun getSelectBarItem(index: Int) {

    }


    override fun getTipDialogSelect(b: Boolean) {
        closeAlertDialog()
    }

    //底部弹框
    override fun getBottomSelectDialogSelect(index: Int) {
        toast(index.toString())
        closeBottomDialog()
        showAlertDialog()
    }
    //阴影
    override fun shadowClicked() {
        closeBottomDialog()
    }

    //举报
    override fun jubaoSelect() {

        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance();
            mTransaction.add(mainBody.id,shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)


        bottomSelectDialogFragment= BottomSelectDialogFragment.newInstance(mutableListOf("広告","内容が偽りである","他の"));
        mTransaction.add(mainBody.id, bottomSelectDialogFragment!!)

        mTransaction.commit()
    }




    var shadowFragment: ShadowFragment?=null
    var bottomSelectDialogFragment: BottomSelectDialogFragment?=null
    var tipDialogFragment:TipDialogFragment?=null


    private var mgListener: MyGestureListener? = null
    private var mDetector: GestureDetector? = null

    lateinit var mainBody:FrameLayout

    lateinit var companyDetailActionBarFragment:CompanyDetailActionBarFragment
    lateinit var companyDetailInfoFragment:CompanyDetailInfoFragment

    var objectAnimator: ObjectAnimator?=null

    override fun onStart() {
        super.onStart()
        setActionBar(companyDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@CompanyInfoDetailActivity, 0, companyDetailActionBarFragment.toolbar1)

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


        var start=0f


        override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {


            if(objectAnimator!==null)
                toast(objectAnimator!!.getAnimatedValue("translationY").toString())

            toast("onFling:迅速滑动，并松开")
            var  dm =getResources().getDisplayMetrics();
            var h_screen = dm.heightPixels;

            val endY =  companyDetailActionBarFragment.mainLayout.getMeasuredHeight()
            val transXHolder = PropertyValuesHolder.ofFloat("translationX", 0f, 0f)
            val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1f, 1f)
            val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1f)

            //dp转像素
            var px=dip2px(ctx,343f)

            //上滑动  且  触发的位置是指定位置时
            if ( (motionEvent.getY() - motionEvent1.getY() > 0   &&  px<motionEvent.getY())) {
                //未上滑时 可上滑  否则不可
                if(objectAnimator==null || objectAnimator!!.getAnimatedValue("translationY").toString().equals("0.0")){

                    //(383-105)/383 上滑动的距离 比例
                    val transYHolder = PropertyValuesHolder.ofFloat("translationY", 0f, 0-endY*0.7258f)

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
            }else if(motionEvent.getY() - motionEvent1.getY() <0){//下滑时
                //已经被滑动上去后 才能下滑
                if(objectAnimator!=null && objectAnimator!!.getAnimatedValue("translationY").toString().equals((0-endY * 0.7258f).toString())) {





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

        override  fun onDown(motionEvent: MotionEvent): Boolean {
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

        override  fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {

            return false
        }

        override fun onLongPress(motionEvent: MotionEvent) {
            //toast("onLongPress:长按并且没有松开")
        }


    }


    fun dip2px(context: Context, dipValue:Float):Int{
        var scale = context.getResources().getDisplayMetrics().density;
        return ((dipValue * scale + 0.5f).toInt())
    }


    fun closeBottomDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(bottomSelectDialogFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment=null
        }


        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()
    }



    fun showAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance();
            mTransaction.add(mainBody.id,shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)


        tipDialogFragment= TipDialogFragment.newInstance(1,"通報成功")
        mTransaction.add(mainBody.id, tipDialogFragment!!)

        mTransaction.commit()
    }


    fun closeAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(tipDialogFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(tipDialogFragment!!)
            tipDialogFragment=null
        }


        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()
    }

}
