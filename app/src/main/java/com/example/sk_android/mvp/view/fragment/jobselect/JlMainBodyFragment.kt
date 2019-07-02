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
import android.preference.PreferenceManager
import android.text.InputFilter
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.register.*
import com.example.sk_android.mvp.view.adapter.jobselect.JobWantAdapter
import com.example.sk_android.mvp.view.adapter.resume.ResumeAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.FileUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException
import java.io.Serializable
import java.util.*


class JlMainBodyFragment : Fragment() {
    private lateinit var myDialog: MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var myList: ListView
    var mId = 2
    lateinit var jobWantAdapter: JobWantAdapter
    var emptyArray = arrayListOf<String>()
    var emptyMutableList = mutableListOf<String>()
    var myAttributes = mapOf<String, Serializable>()

    var userJobIntention = UserJobIntention(emptyArray,emptyMutableList,myAttributes,"","","","","",emptyArray,emptyMutableList,"","",0,0,0,0,
        0,0,0,0,"",0,0,"","",0,emptyArray)


    companion object {
        fun newInstance(): JlMainBodyFragment {
            val fragment = JlMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = MyDialog.Builder(activity!!)
            .setMessage(this.getString(R.string.loadingHint))
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        super.onActivityCreated(savedInstanceState)
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


                linearLayout {

                    setOnClickListener(object :View.OnClickListener{

                        override fun onClick(v: View?) {
                            var intent = Intent(mContext, JobWantedEditActivity::class.java)
                            var bundle = Bundle()
                            bundle.putParcelable("userJobIntention", userJobIntention)
                            bundle.putInt("condition",2)
                            intent.putExtra("bundle", bundle)
                            startActivity(intent)
                        }

                    })



                    gravity = Gravity.CENTER
                    backgroundColorResource = R.color.yellowFFB706
                    imageView {
                        imageResource = R.mipmap.add
                    }.lparams(width = dip(20), height = dip(20))
                    textView {
                        text = "希望職種"
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
    private fun initView() {
        mContext = activity
        myList = this.find(mId)
        myDialog.show()

        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.userUrl))
        // 获取用户的求职列表
        retrofitUils.create(RegisterApi::class.java)
            .jobIntentIons
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var myResult: ArrayList<UserJobIntention> = arrayListOf()
                println("获取用户求职意向列表")
                if (it.size() == 0) {
                    myDialog.dismiss()
                    println("数据为空")
                } else {
                    println("--------------------")
                    println(it.size())
                    var gson = Gson()
                    var newCondition: MutableList<Boolean> = mutableListOf()

                    var newSize = it.size()

                    for (i in 0 until newSize) {
                        newCondition.add(false)
                    }

                    for (i in 0 until it.size()) {
                        var j = i
                        var result = it[i].asJsonObject
                        var uji: UserJobIntention = gson.fromJson(result, UserJobIntention::class.java)
                        uji.areaName = mutableListOf()
                        uji.industryName = mutableListOf()
                        var condition: MutableList<Boolean> = mutableListOf()
                        var areaIds = uji.areaIds
                        var industryIds = uji.industryIds
                        var conditionSize = areaIds.size + industryIds.size


                        for (i in 0 until conditionSize) {
                            condition.add(false)
                        }


                        for (i in 0 until areaIds.size) {
                            var baseRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.baseUrl))
                            baseRetrofitUils.create(RegisterApi::class.java)
                                .getAreaById(areaIds[i])
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    var areaName = it.get("name").toString().replace("\"", "")
                                    println(areaName)
                                    condition.set(i, true)
                                    uji.areaName.add(areaName)
                                    for (i in 0 until conditionSize) {
                                        if (condition.get(i) != true) {
                                            break
                                        }
                                        if (i == conditionSize - 1) {
                                            println(uji)
                                            myResult.add(uji)
                                            newCondition.set(j, true)
                                            for (i in 0 until newSize) {
                                                if (newCondition.get(i) != true) {
                                                    break
                                                }
                                                if (i == newSize - 1) {
                                                    jobWantAdapter = JobWantAdapter(myResult, mContext)
                                                    myList.setAdapter(jobWantAdapter)
                                                    myDialog.dismiss()
                                                }
                                            }
                                        }
                                    }

                                }, {
                                    myDialog.dismiss()
                                    println("地址错误")
                                    println(it)
                                })
                        }


                        for (i in 0 until industryIds.size) {
                            var industryRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.industryUrl))
                            industryRetrofitUils.create(RegisterApi::class.java)
                                .getIndusTryById(industryIds[i])
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    var industryName = it.get("name").toString().replace("\"", "")
                                    println(industryName)
                                    uji.industryName.add(industryName)
                                    condition.set(i + areaIds.size, true)
                                    for (i in 0 until conditionSize) {
                                        if (condition.get(i) != true) {
                                            break
                                        }
                                        if (i == conditionSize - 1) {
                                            myResult.add(uji)
                                            newCondition.set(j, true)
                                            for (i in 0 until newSize) {
                                                println(uji)
                                                if (newCondition.get(i) != true) {
                                                    break
                                                }
                                                if (i == newSize - 1) {
                                                    jobWantAdapter = JobWantAdapter(myResult, mContext)
                                                    myList.setAdapter(jobWantAdapter)
                                                    myDialog.dismiss()
                                                }
                                            }
                                        }
                                    }

                                }, {
                                    myDialog.dismiss()
                                    println("行业错误")
                                    println(it)
                                })
                        }

                    }

                }

            }, {
                myDialog.dismiss()
                println("意向失败")
                println(it)
            })

    }



}
