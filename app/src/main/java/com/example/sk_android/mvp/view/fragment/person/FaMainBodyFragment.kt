package com.example.sk_android.mvp.view.fragment.person

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*

class FaMainBodyFragment : Fragment() {
    private lateinit var myDialog: MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var companyText: TextView
    lateinit var timeText: TextView
    lateinit var positionText: TextView
    lateinit var addressText: TextView
    lateinit var doText:TextView
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    var id = ""
    var type = "APPOINTING"

    companion object {
        fun newInstance(myId:String,myType:String): FaMainBodyFragment {
            val fragment = FaMainBodyFragment()
            fragment.id = myId
            fragment.type = myType
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = MyDialog.Builder(activity!!)
            .setMessage(this.getString(R.string.loadingHint))
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            verticalLayout {
                leftPadding = dip(15)
                rightPadding = dip(15)
                linearLayout {
                    linearLayout {
                        gravity = Gravity.CENTER
                        imageView {
                            imageResource = R.mipmap.company_logo
                        }.lparams(width = dip(60), height = dip(60))
                    }.lparams(width = dip(60), height = matchParent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        orientation = LinearLayout.VERTICAL
                        companyText = textView {
//                            textResource = R.string.faceCompany
                            textSize = 17f
                            textColorResource = R.color.black20
                        }.lparams(height = wrapContent)

                        textView {
                            textResource = R.string.reserved
                            textSize = 13f
                            textColorResource = R.color.gray99
                        }.lparams(height = wrapContent) {

                        }
                    }.lparams(height = matchParent) {
                        weight = 1f
                        leftMargin = dip(10)
                    }
                }.lparams(width = matchParent, height = dip(90))

                linearLayout {
                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_work
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.facePosition
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_navigation
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.faceNavi
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_chat
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.faceChat
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = dip(105))

                view {
                    backgroundColorResource = R.color.grayEBEAEB
                }.lparams(width = matchParent, height = dip(1)) {}

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_time
                    }.lparams(width = dip(16), height = dip(16))
                    timeText = textView {
//                        textResource = R.string.faceTime
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(23)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_job
                    }.lparams(width = dip(16), height = dip(16))
                    positionText = textView {
//                        textResource = R.string.faceTask
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(15)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_address
                    }.lparams(width = dip(16), height = dip(16))
                    addressText = textView {
//                        textResource = R.string.faceAddress
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(15)
                }

                linearLayout {
                }.lparams(width = matchParent, height = wrapContent) {
                    weight = 1f
                }

                doText = textView {
                    gravity = Gravity.CENTER_HORIZONTAL
                    textResource = R.string.cancelInterView
                    textSize = 12f
                    textColorResource = R.color.gray89
                    visibility = View.GONE
                    setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            afterShowLoading()
                        }

                    })

                }.lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(52)
                }
            }
        }.view
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val faceDialog = FaceDialog(activity!!)
        faceDialog.show()
        var confirmButton = faceDialog.getButton()
        var reasonEdit = faceDialog.getEdit()
        var reason = reasonEdit.text.trim()
        confirmButton.setOnClickListener {
            val resonParams = mapOf(
                "state" to "REJECTED",
                "cancelReason" to reason
            )
            val reasonJson = JSON.toJSONString(resonParams)
            val reasonBody = RequestBody.create(json, reasonJson)

            var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.interUrl))


            retrofitUils.create(PersonApi::class.java)
                .changeInterViewSchedule(id,reasonBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.code() == 200){
                        toast("状态变更成功")
                    }else{
                        toast("状态变更失败，请重试")
                    }
                }, {

                })
        }
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        myDialog.show()
        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.interUrl))
        // 获取用户工作状态
        retrofitUils.create(PersonApi::class.java)
            .getInterViewById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("查找面试信息")
                var gson = Gson()
                var res = it
                var recruitOrganizationName = ""
                var recruitPositionName = ""
                var addressName = ""
                var recruitOrganizationId = it.get("recruitOrganizationId").toString().replace("\"", "")
                var recruitPositionId = it.get("recruitPositionId").toString().replace("\"", "")
                var type = it.get("type").toString().replace("\"", "")

                var newCondition: MutableList<Boolean> = mutableListOf()

                for (i in 0 until 3) {
                    newCondition.add(false)
                }

                var companyRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.orgUrl))
                // 获取公司name
                companyRetrofitUils.create(PersonApi::class.java)
                    .getCompanyName(recruitOrganizationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var companyName = it.get("name").toString().replace("\"", "")
                        newCondition.set(0, true)
                        recruitOrganizationName = companyName
                        for (i in 0 until 3) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 2) {
                                myDialog.dismiss()
                                initPage(recruitOrganizationName, recruitPositionName, addressName, res)
                            }
                        }
                    }, {
                        myDialog.dismiss()
                        println("查询公司出错")
                        println(it)
                        toast("查询公司信息出错！")
                    })

                var positionRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.organizationUrl))
                // 获取用户工作状态
                positionRetrofitUils.create(PersonApi::class.java)
                    .getPositionName(recruitPositionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var positionName = it.get("organization").asJsonObject.get("name").toString().replace("\"", "")
                        newCondition.set(1, true)
                        recruitPositionName = positionName
                        for (i in 0 until 3) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 2) {
                                myDialog.dismiss()
                                initPage(recruitOrganizationName, recruitPositionName, addressName, res)
                            }
                        }
                    }, {
                        myDialog.dismiss()
                        println("下旬职业出粗")
                        println(it)
                        toast("查询职位信息出错！！")
                    })


                if (type.equals("OFFLINE")) {
                    var addressId = res.get("attributes").asJsonObject.get("addressId").toString().replace("\"", "")
                    // 查询公司地址
                    var baseRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.baseUrl))
                    baseRetrofitUils.create(RegisterApi::class.java)
                        .getAreaById(addressId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            addressName = it.get("name").toString().replace("\"", "")
                            newCondition.set(2, true)
                            for (i in 0 until 3) {
                                if (!newCondition[i]) {
                                    break
                                }
                                if (i == 2) {
                                    myDialog.dismiss()
                                    initPage(recruitOrganizationName, recruitPositionName, addressName, res)
                                }
                            }
                        }, {
                            myDialog.dismiss()
                            println("查询地址信息出错！！")
                            println(it)
                            toast("查询地址信息出错！！")
                        })
                } else {
                    newCondition.set(2, true)
                    for (i in 0 until 3) {
                        if (!newCondition[i]) {
                            break
                        }
                        if (i == 2) {
                            myDialog.dismiss()
                            initPage(recruitOrganizationName, recruitPositionName, addressName, res)
                        }
                    }
                }


            }, {
                myDialog.dismiss()
                toast("查询面试失败！")
            })
    }

    private fun initPage(companyName: String, positionName: String, addressName: String, result: JsonObject) {
        println("*-*-*-*-*-*-*-*-")
        companyText.text = companyName
        positionText.text = positionName
        addressText.text = addressName
        var start = result.get("appointedStartTime").toString().replace("\"", "").toLong()
        timeText.text = longToString(start)

        if(type == "APPOINTING"){
            doText.visibility = View.VISIBLE
        }
        println(companyName)
        println(positionName)
        println(addressName)
        println(result)
        println("++++++++++++++++++++++++")
    }


    // 类型转换
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(long))
    }
}

