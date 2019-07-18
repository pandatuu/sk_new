package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.View
import android.widget.*
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.JlMainBodyFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*

class JobWantedManageActivity : AppCompatActivity(), BottomSelectDialogFragment.BottomSelectDialogSelect,
    ShadowFragment.ShadowClick {
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun shadowClicked() {
        closeBottomSelector()
    }


    override fun getBottomSelectDialogSelect() {
        closeBottomSelector()

    }

    @SuppressLint("CheckResult")
    override fun getback(index: Int, list: MutableList<String>) {

        var str=list.get(index)

        var workStatus = ""
        when(str){
            this.getString(R.string.IiStatusOne) -> workStatus = "OTHER"
            this.getString(R.string.IiStatusTwo) -> workStatus = "ON_NEXT_MONTH"
            this.getString(R.string.IiStatusThree) -> workStatus = "ON_CONSIDERING"
            this.getString(R.string.IiStatusFour) -> workStatus = "OFF"
        }

        val statuParams = mapOf(
            "attributes" to {},
            "state" to workStatus
        )

        val statuJson = JSON.toJSONString(statuParams)
        val statusBody = RequestBody.create(json, statuJson)

        var userretrofitUils = RetrofitUtils(this, this.getString(R.string.userUrl))
        //更改工作状态
        userretrofitUils.create(RegisterApi::class.java)
            .UpdateWorkStatu(statusBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if(it.code() in 200..299){
                    println("更改工作状态成功")
                    nowState.text=str
                }
            },{})


        closeBottomSelector()
    }

    private lateinit var toolbar1: Toolbar
    lateinit var imageView: ImageView
    lateinit var nowState: TextView
    lateinit var outerContainer: FrameLayout


    var shadowFragment: ShadowFragment? = null
    var bottomSelectDialogFragment: BottomSelectDialogFragment? = null
    var jlMainBodyFragment: JlMainBodyFragment? = null


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var outerContainerId = 11
        outerContainer = frameLayout() {
            id = outerContainerId
            verticalLayout {
                relativeLayout() {
                    backgroundColor = Color.BLUE
                    imageView = imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.pic_top)
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                    relativeLayout() {
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back_white
                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }
                        textView {
                            textResource = R.string.jmTitle
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height = dip(65 - getStatusBarHeight(this@JobWantedManageActivity))
                            alignParentBottom()
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }

                relativeLayout {
                    backgroundResource = R.color.grayF6
                    textView {
                        textResource = R.string.jmStateTitle
                        textSize = 13f
                        gravity = Gravity.CENTER
                        textColorResource = R.color.underToolBarTextColorLeft
                    }.lparams() {
                        alignParentLeft()
                        leftMargin = 50
                        width = wrapContent
                        height = matchParent

                    }

                    var verticalCornerId = 1
                    linearLayout() {
                        id = verticalCornerId
                        gravity = Gravity.CENTER_VERTICAL


                        setOnClickListener(object : View.OnClickListener {

                            override fun onClick(v: View?) {
                                showBottomSelector()
                            }

                        })

                        nowState = textView {
                            text = "しばらくは考えない"
                            textSize = 13f
                            gravity = Gravity.CENTER
                            textColorResource = R.color.underToolBarTextColorRight
                            backgroundColor = Color.TRANSPARENT
                        }.lparams() {
                            width = wrapContent
                            height = matchParent
                            rightMargin = dip(5)


                        }

                        imageView() {
                            setImageResource(R.mipmap.icon_go_position)


                        }.lparams() {
                            width = wrapContent
                            height = wrapContent
                            rightMargin = dip(15)
                        }
                    }.lparams() {
                        width = wrapContent
                        height = matchParent
                        alignParentRight()

                    }


                }.lparams() {
                    width = matchParent
                    height = dip(53)

                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    jlMainBodyFragment = JlMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, jlMainBodyFragment!!).commit()
                }.lparams(width = matchParent, height = matchParent){
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }
        }
        setActionBar(toolbar1)
        actionBar!!.setDisplayHomeAsUpEnabled(true);
        StatusBarUtil.setTranslucentForImageView(this@JobWantedManageActivity, 0, toolbar1)


        toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

    fun showBottomSelector() {
        var mTransaction = supportFragmentManager.beginTransaction()

        if (shadowFragment != null) {
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }

        if (bottomSelectDialogFragment != null) {
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
        }


        shadowFragment = ShadowFragment.newInstance()
        mTransaction.add(outerContainer.id, shadowFragment!!)


        var title = this.getString(R.string.jmStateTitle)
        var strArray: MutableList<String> = mutableListOf(this.getString(R.string.IiStatusOne), this.getString(R.string.IiStatusTwo), this.getString(R.string.IiStatusThree), this.getString(R.string.IiStatusFour))

        bottomSelectDialogFragment = BottomSelectDialogFragment.newInstance(title, strArray)

        mTransaction.setCustomAnimations(R.anim.bottom_in, R.anim.bottom_in)
        mTransaction.add(outerContainer.id, bottomSelectDialogFragment!!)


        mTransaction.commit()
    }


    fun closeBottomSelector() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(R.anim.bottom_out, R.anim.bottom_out)
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(R.anim.fade_in_out, R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }


        mTransaction.commit()
    }

    // 获取页面数据
    @SuppressLint("CheckResult")
    private fun init(){
        var retrofitUils = RetrofitUtils(this, this.getString(R.string.userUrl))
        // 获取用户工作状态
        retrofitUils.create(PersonApi::class.java)
            .jobStatu
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var statu = this.getString(R.string.IiStatusOne)
                var workStatu = it.get("state").toString().replace("\"","")
                when(workStatu){
                    "OTHER" -> statu = this.getString(R.string.IiStatusOne)
                    "ON_NEXT_MONTH"-> statu = this.getString(R.string.IiStatusTwo)
                    "ON_CONSIDERING" -> statu = this.getString(R.string.IiStatusThree)
                    "OFF"-> statu = this.getString(R.string.IiStatusFour)
                }
                nowState.text = statu
            }, {

            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 1001){
            jlMainBodyFragment!!.initView()
            init()
        }
    }


}

