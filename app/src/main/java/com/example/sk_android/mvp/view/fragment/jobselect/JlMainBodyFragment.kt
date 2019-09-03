package com.example.sk_android.mvp.view.fragment.jobselect

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.R.drawable.shape_corner
import com.yatoooon.screenadaptation.ScreenAdapterTools
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.os.Build
import android.os.Handler
import android.preference.PreferenceManager
import android.text.InputFilter
import android.util.Log
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.register.*
import com.example.sk_android.mvp.view.adapter.jobselect.JobWantAdapter
import com.example.sk_android.mvp.view.adapter.resume.ResumeAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.FileUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
import withTrigger
import java.io.Serializable
import java.util.*


class JlMainBodyFragment : Fragment() {
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var myList: ListView
    var mId = 2
    lateinit var jobWantAdapter: JobWantAdapter
    lateinit var totalText: TextView
    var emptyArray = arrayListOf<String>()
    var emptyMutableList = mutableListOf<String>()
    var myAttributes = mapOf<String, Serializable>()

    var userJobIntention = UserJobIntention(
        emptyArray,
        emptyMutableList,
        myAttributes,
        "",
        "",
        "",
        "",
        "",
        emptyArray,
        emptyMutableList,
        "",
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        "",
        0,
        0,
        "",
        "",
        0,
        emptyArray
    )


    companion object {

        var myResult: ArrayList<UserJobIntention> = arrayListOf()
        var totalNum = 0
        fun newInstance(): JlMainBodyFragment {
            val fragment = JlMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView(1)
        super.onActivityCreated(savedInstanceState)
    }

    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(activity, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }
    fun addIndex(){
        thisDialog=DialogUtils.showLoading(activity!!)
        mHandler.postDelayed(r, 12000)
    }

    override fun onResume() {
        super.onResume()

    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            verticalLayout {
                myList = listView {
                    id = mId
                }.lparams(width = matchParent, height = wrapContent) {
                    weight = 1f
                }

                totalText = textView {
                    visibility = View.GONE
                }


                linearLayout {

                    this.withTrigger().click {
                        Log.i("JlMainBodyFragment", totalText.text.toString())

                        //java.lang.NumberFormatException: Invalid int: ""
                        if (totalNum < 3) {
                            var intent = Intent(mContext, JobWantedEditActivity::class.java)
                            var bundle = Bundle()
                            bundle.putParcelable("userJobIntention", userJobIntention)
                            bundle.putInt("condition", 2)
                            intent.putExtra("bundle", bundle)
                            startActivityForResult(intent, 1)
                            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        } else {
                            toast(activity!!.getString(R.string.stateNumberError))
                        }


                    }



                    gravity = Gravity.CENTER
                    backgroundColorResource = R.color.yellowFFB706
                    imageView {
                        imageResource = R.mipmap.add
                    }.lparams(width = dip(20), height = dip(20))

                    textView {
                        text = "就職希望追加"
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                    }

                }.lparams(width = matchParent, height = dip(47)) {
                    topMargin = dip(10)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(30)
                }
            }
        }.view
    }


    @SuppressLint("CheckResult")
    fun initView(from: Int) {
        mContext = activity
        myList = this.find(mId)


        if(  from == 1){
            var application: App? = null
            application = App.getInstance()
            application!!.setJlMainBodyFragment(this)
        }

        if ((myResult == null || myResult.size == 0)) {
            //第一次进入



        } else {
            jobWantAdapter = JobWantAdapter(myResult, this)
            myList.adapter = jobWantAdapter
            DialogUtils.hideLoading(thisDialog)
        }


//        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.userUrl))
//        // 获取用户的求职列表
//        retrofitUils.create(RegisterApi::class.java)
//            .jobIntentIons
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                var myResult: ArrayList<UserJobIntention> = arrayListOf()
//                println("获取用户求职意向列表")
//                totalText.text = it.size().toString()
//                if (it.size() == 0) {
//                    DialogUtils.hideLoading(myDialog)
//                    println("数据为空")
//                } else {
//                    println("--------------------")
//                    println(it.size())
//                    println(it)
//                    var gson = Gson()
//                    var newCondition: MutableList<Boolean> = mutableListOf()
//
//                    var newSize = it.size()
//
//                    for (i in 0 until newSize) {
//                        newCondition.add(false)
//                    }
//
//                    for (i in 0 until it.size()) {
//                        var j = i
//                        var result = it[i].asJsonObject
//                        var uji: UserJobIntention = gson.fromJson(result, UserJobIntention::class.java)
//                        uji.areaName = mutableListOf()
//                        uji.industryName = mutableListOf()
//                        var condition: MutableList<Boolean> = mutableListOf()
//                        var areaIds = uji.areaIds
//                        var industryIds = uji.industryIds
//                        var conditionSize = areaIds.size + industryIds.size
//
//
//                        for (i in 0 until conditionSize) {
//                            condition.add(false)
//                        }
//
//
//                        for (i in 0 until areaIds.size) {
//                            var baseRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.baseUrl))
//                            baseRetrofitUils.create(RegisterApi::class.java)
//                                .getAreaById(areaIds[i])
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe({
//                                    var areaName = it.get("name").toString().replace("\"", "")
//                                    println(areaName)
//                                    condition.set(i, true)
//                                    uji.areaName.add(areaName)
//                                    for (i in 0 until conditionSize) {
//                                        if (condition.get(i) != true) {
//                                            break
//                                        }
//                                        if (i == conditionSize - 1) {
//                                            println(uji)
//                                            myResult.add(uji)
//                                            newCondition.set(j, true)
//                                            for (i in 0 until newSize) {
//                                                if (newCondition.get(i) != true) {
//                                                    break
//                                                }
//                                                if (i == newSize - 1) {
//                                                    jobWantAdapter = JobWantAdapter(myResult, this)
//                                                    myList.adapter = jobWantAdapter
//                                                    DialogUtils.hideLoading(myDialog)
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                }, {
//                                    DialogUtils.hideLoading(myDialog)
//                                    println("地址错误")
//                                    println(it)
//                                })
//                        }
//
//
//                        for (i in 0 until industryIds.size) {
//                            var industryRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.industryUrl))
//                            industryRetrofitUils.create(RegisterApi::class.java)
//                                .getIndusTryById(industryIds[i])
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe({
//                                    var industryName = it.get("name").toString().replace("\"", "")
//                                    println(industryName)
//                                    uji.industryName.add(industryName)
//                                    condition.set(i + areaIds.size, true)
//                                    for (i in 0 until conditionSize) {
//                                        if (condition.get(i) != true) {
//                                            break
//                                        }
//                                        if (i == conditionSize - 1) {
//                                            myResult.add(uji)
//                                            newCondition.set(j, true)
//                                            for (i in 0 until newSize) {
//                                                println(uji)
//                                                if (newCondition.get(i) != true) {
//                                                    break
//                                                }
//                                                if (i == newSize - 1) {
//                                                    jobWantAdapter = JobWantAdapter(myResult, this)
//                                                    myList.adapter = jobWantAdapter
//                                                    DialogUtils.hideLoading(myDialog)
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                }, {
//                                    DialogUtils.hideLoading(myDialog)
//                                    println("行业错误")
//                                    println(it)
//                                })
//                        }
//
//                    }
//
//                }
//
//            }, {
//                DialogUtils.hideLoading(myDialog)
////                toast(this.getString(R.string.getInitFail))
//                println(it)
//            })

    }


    override fun onDestroy() {
        super.onDestroy()
        var application: App? = null
        application = App.getInstance()
        application!!.setJlMainBodyFragment(null)
    }

}
