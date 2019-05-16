package com.example.sk_android.mvp.view.activity.company

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoListFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import android.text.method.Touch.onTouchEvent
import android.view.ViewGroup
import kotlinx.android.synthetic.main.radion.view.*
import android.R.attr.endY
import android.animation.PropertyValuesHolder
import android.R.attr.endY
import android.R.attr.endX
import android.content.Context
import android.support.v4.content.ContextCompat.startActivity






class CompanyInfoDetailActivity : AppCompatActivity() {

    private var mgListener: MyGestureListener? = null
    private var mDetector: GestureDetector? = null

    lateinit var mainBody:FrameLayout
    lateinit var selectBar:FrameLayout

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


        frameLayout {
            backgroundColor=Color.WHITE

                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                   companyDetailActionBarFragment= CompanyDetailActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id,companyDetailActionBarFragment).commit()

                   companyDetailInfoFragment=CompanyDetailInfoFragment.newInstance("アニメ谷はデジタル映像制作に携わっており、CG技 术作品で世界を繋ぐことに力を注いでいる。！私たち は、世界市场に向けてより広范なグローバル市场に进 むことができるように、制作の実力の向上とチーー…")
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
            return false
        }

        override fun onShowPress(motionEvent: MotionEvent) {
            toast("onShowPress:手指按下一段时间,不过还没到长按")
        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            toast("onSingleTapUp:手指离开屏幕的一瞬间")


            return false
        }

        override  fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
//
//
//            toast("滑动")
//            toast(motionEvent1.getY().toString())
//
//            if(objectAnimator!=null){
//               // toast(motionEvent.getY().toString()+"---"+motionEvent1.getY().toString())
//
//            }
//            val endY =  companyDetailActionBarFragment.mainLayout.getMeasuredHeight()
//
//            if(objectAnimator!==null)
//                toast((motionEvent1.getY()-motionEvent.getY()).toString())
//
//            val transXHolder = PropertyValuesHolder.ofFloat("translationX", 0f, 0f)
//            val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1f, 1f)
//            val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1f)
//
//
//            //dp转像素
//            var px=dip2px(ctx,343f)
//
//            //上滑动  且  触发的位置是指定位置时
//            if ( (motionEvent.getY() - motionEvent1.getY() > 0   &&  px<motionEvent.getY())) {
//                //未上滑时 可上滑  否则不可
//                    if(objectAnimator==null ||(  objectAnimator!!.getAnimatedValue("translationY").toString().toFloat() > 0-endY * 0.7258f)){
//
//                        var end=motionEvent1.getY()-motionEvent.getY()
//                        if(end<0-endY * 0.7258f){
//                            end=0-endY * 0.7258f
//                        }
//                        //(383-105)/383 上滑动的距离 比例
//                        val transYHolder = PropertyValuesHolder.ofFloat("translationY", start, end)
//                        start=end
//                        objectAnimator =
//                            ObjectAnimator.ofPropertyValuesHolder(
//                                companyDetailInfoFragment.swipeLayout,
//                                transXHolder,
//                                transYHolder,
//                                scaleXHolder,
//                                scaleYHolder
//                            )
//
//                        objectAnimator!!.start()//播放完后，图片会回到原来的位置
//                    }
//            }
////            else if(motionEvent.getY() - motionEvent1.getY() <0){//下滑时
////                //已经被滑动上去后 才能下滑
////                if(objectAnimator!=null ) {
////
////
////
////
////
////                    //(383-105)/383 下滑动的距离 比例
////                    val transYHolder = PropertyValuesHolder.ofFloat("translationY", -endY * 0.7258f, v1-endY * 0.7258f)
////
////                    objectAnimator =
////                        ObjectAnimator.ofPropertyValuesHolder(
////                            companyDetailInfoFragment.swipeLayout,
////                            transXHolder,
////                            transYHolder,
////                            scaleXHolder,
////                            scaleYHolder
////                        )
////                    objectAnimator!!.setDuration(200)
////                    objectAnimator!!.start()//播放完后，图片会回到原来的位置
////
////                }
////            }


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




}
