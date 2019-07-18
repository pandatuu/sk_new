package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ProductDetailInfoTopPartFragment : Fragment() {


    private var mContext: Context? = null
    private var company: CompanyInfo? = null
    private lateinit var dianzanText: TextView
    private lateinit var recyView: RecyclerView
    private var dianzanNum = 0
    private var isDianzan: Boolean = false
    private lateinit var dianzanImage: ImageView


    private var actionMove: ActionMove? = null

    private val sizes = mapOf(
        "TINY" to "0-22人",//"0-22",
        "SMALL" to "20-99人",//20-99",
        "MEDIUM" to "100-499人",//"100-499",
        "BIG" to "500-999人",//"500-999",
        "HUGE" to "1000-9999人",//"1000-9999",
        "SUPER" to "10000人以上"//"10000以上"
    )

    private val stage = mapOf(
        "TSE_1" to "上市",//上市
        "TSE_2" to "上市",//上市
        "TSE_MOTHERS" to "上市",//上市
        "OTHER" to "上市",//上市
        "NONE" to "未上市"//未上市
    )

    private val companyType = mapOf(
        "NON_PROFIT" to "非盈利",//非盈利
        "STATE_OWNED" to "国企",//国企
        "SOLE" to "独资",//独资
        "JOINT" to "合资",//合资
        "FOREIGN" to "外资"//外资
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        actionMove = activity as ActionMove
    }

    companion object {
        fun newInstance(com: CompanyInfo?): ProductDetailInfoTopPartFragment {
            var f = ProductDetailInfoTopPartFragment()
            f.company = com
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }


    private fun createView(): View {
        if (activity!!.intent.getStringExtra("companyId") != null) {
            val id = activity!!.intent.getStringExtra("companyId")
            if (company != null) {
                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                    isDianZan(id)
                    getCompanyDianZan(id)
                }
                return UI {
                    linearLayout {
                        verticalLayout {
                            relativeLayout {
                                val image = imageView {
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                }.lparams() {
                                    width = dip(70)
                                    height = dip(70)
                                    alignParentLeft()
                                }
                                if (company != null) {
                                    Glide.with(activity!!)
                                        .asBitmap()
                                        .load(company?.logo)
                                        .placeholder(R.mipmap.ico_company_default_logo)
                                        .into(image)
                                }

                                verticalLayout {
                                    gravity = Gravity.RIGHT
                                    dianzanImage = imageView {
                                        backgroundColor = Color.TRANSPARENT
                                        setImageResource(R.mipmap.notdianzan)
                                        onClick {
                                            if (!isDianzan)
                                                dianZanCompany(company!!.id)
                                            else
                                                toast("已经点赞了")
                                        }

                                        setOnTouchListener(object : View.OnTouchListener {
                                            var startx = 0f
                                            var endx = 0f
                                            var starty = 0f
                                            var endy = 0f

                                            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                                                if (event != null) {
                                                    if (event!!.action == MotionEvent.ACTION_DOWN) {
                                                        //开始
                                                        startx = event.x
                                                        starty = event.y

                                                    }
                                                    if (event!!.action == MotionEvent.ACTION_UP) {
                                                        //结束
                                                        endx = event.x
                                                        endy = event.y

                                                        //差值
                                                        var xdiff = endx - startx
                                                        var ydiff = endy - starty
                                                        //绝对值
                                                        var xvalue = xdiff
                                                        var yvalue = ydiff

                                                        if (xvalue < 0) {
                                                            xvalue = 0 - xvalue
                                                        }

                                                        if (yvalue < 0) {
                                                            yvalue = 0 - yvalue
                                                        }


                                                        if (xvalue > yvalue) {
                                                            //横向移动占据主导
                                                        } else {
                                                            //纵向移动占据主导
                                                            if (ydiff > 0) {
                                                                //向下滑动
                                                                if (actionMove != null)
                                                                    actionMove!!.isMoveDown(true)
                                                            } else {
                                                                //向上滑动
                                                                if (actionMove != null)
                                                                    actionMove!!.isMoveDown(false)
                                                            }

                                                        }

                                                    }
                                                }
                                                return false
                                            }

                                        })

                                    }.lparams(dip(30), dip(30)) {
                                        topMargin = dip(10)
                                        rightMargin = dip(10)
                                        bottomMargin = dip(10)
                                    }

                                    dianzanText = textView {
                                        gravity = Gravity.RIGHT
                                        textSize = 13f
                                        textColorResource = R.color.themeColor
                                    }.lparams {
                                        width = wrapContent
                                        gravity = Gravity.CENTER_HORIZONTAL
                                        rightMargin = dip(5)
                                    }

                                }.lparams() {
                                    alignParentRight()
                                }


                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                                topMargin = dip(25)
                            }


                            textView {
                                text = company?.name ?: ""
                                textSize = 24f
                                textColorResource = R.color.black33
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))



                            }.lparams {
                                topMargin = dip(15)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }


                            linearLayout {


                                gravity = Gravity.CENTER_VERTICAL

                                textView {
                                    textSize = 13f
                                    textColorResource = R.color.gray5c
                                    if (company?.financingStage != null && company?.financingStage != "") {
                                        text = stage[company?.financingStage!!]
                                    } else {
                                        text = "未知"
                                    }
                                    gravity = Gravity.CENTER
                                }

                                textView {
                                    backgroundResource = R.color.grayb8
                                }.lparams {
                                    height = matchParent
                                    width = dip(1)
                                    leftMargin = dip(10)
                                }

                                textView {
                                    textSize = 13f
                                    textColorResource = R.color.gray5c
                                    if (company != null && company?.size != "") {
                                        text = sizes[company?.size]
                                    }
                                }.lparams {
                                    leftMargin = dip(10)
                                }

                                textView {
                                    backgroundResource = R.color.grayb8
                                }.lparams {
                                    height = matchParent
                                    width = dip(1)
                                    leftMargin = dip(10)
                                }

                                textView {
                                    textSize = 13f
                                    textColorResource = R.color.gray5c
                                    if (company?.type != null && company?.type != "") {
                                        text = companyType[company?.type!!]
                                    } else {
                                        text = "未知"
                                    }
                                }.lparams {
                                    leftMargin = dip(10)
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(10)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                                bottomMargin = dip(10)
                            }
                            horizontalScrollView {
                                isHorizontalScrollBarEnabled = false
                                if (company?.imageUrls != null && company?.imageUrls!!.size > 0) {
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        for (url in company?.imageUrls!!) {
                                            val image = imageView {
                                                padding = dip(5)
                                                scaleType = ImageView.ScaleType.CENTER_CROP
                                                adjustViewBounds = true
                                                maxHeight = dip(110)
                                            }.lparams {
                                                height = matchParent
                                                width = wrapContent
                                            }
                                            Glide.with(context)
                                                .load(url)
                                                .placeholder(R.mipmap.no_pic_show)
                                                .into(image)

                                        }
                                    }.lparams(wrapContent, matchParent)
                                } else {
                                    textView {
                                        text = "暂无公司图片"
                                        textSize = 16f
                                    }.lparams(wrapContent, wrapContent) {
                                        gravity = Gravity.CENTER_VERTICAL
                                    }
                                }



                                setOnTouchListener(object : View.OnTouchListener {
                                    var startx = 0f
                                    var endx = 0f
                                    var starty = 0f
                                    var endy = 0f

                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                                        if (event != null) {
                                            if (event!!.action == MotionEvent.ACTION_DOWN) {
                                                //开始
                                                startx = event.x
                                                starty = event.y

                                            }
                                            if (event!!.action == MotionEvent.ACTION_UP) {
                                                //结束
                                                endx = event.x
                                                endy = event.y

                                                //差值
                                                var xdiff = endx - startx
                                                var ydiff = endy - starty
                                                //绝对值
                                                var xvalue = xdiff
                                                var yvalue = ydiff

                                                if (xvalue < 0) {
                                                    xvalue = 0 - xvalue
                                                }

                                                if (yvalue < 0) {
                                                    yvalue = 0 - yvalue
                                                }


                                                if (xvalue > yvalue) {
                                                    //横向移动占据主导
                                                } else {
                                                    //纵向移动占据主导
                                                    if (ydiff > 0) {
                                                        //向下滑动
                                                        if (actionMove != null)
                                                            actionMove!!.isMoveDown(true)
                                                    } else {
                                                        //向上滑动
                                                        if (actionMove != null)
                                                            actionMove!!.isMoveDown(false)
                                                    }

                                                }

                                            }
                                        }
                                        return false
                                    }

                                })


                            }.lparams {
                                width = matchParent
                                height = dip(120)
                                leftMargin = dip(10)
                                rightMargin = dip(10)
                            }

                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(8)
                            }

                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }
                }.view
            }
        }
        return UI {
            linearLayout {
                gravity = Gravity.CENTER
                frameLayout {
                    val image = imageView {}.lparams(dip(70), dip(80))
                    Glide.with(this@linearLayout)
                        .load(R.mipmap.turn_around)
                        .into(image)
                }
            }
        }.view
    }

    private fun danzanshu(number: Int): String {
        if (number > 1000) {
            return "${number / 1000}K"
        } else {
            return "$number"
        }
    }

    //判断自己是否点赞(因为只能点赞一次)
    private suspend fun isDianZan(id: String) {
        try {
            val retrofitUils = RetrofitUtils(context!!, "https://praise.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .isDianZan(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                isDianzan = it.body()!!
                if (isDianzan)
                    dianzanImage.setImageResource(R.mipmap.dianzan)
            }
        } catch (e: Throwable) {
            println(e)
        }
    }

    //点赞该公司
    private suspend fun dianZanCompany(id: String) {
        try {
            val map = mapOf(
                "praisedOrganizationId" to id
            )
            val userJson = JSON.toJSONString(map)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(context!!, "https://praise.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .createCompanyDianZan(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                dianzanImage.setImageResource(R.mipmap.dianzan)
                val number = danzanshu(dianzanNum + 1)
                dianzanText.text = "$number"
                isDianzan = true
            }
        } catch (e: Throwable) {
            println(e)
        }
    }

    //获取该公司的点数赞
    private suspend fun getCompanyDianZan(id: String) {
        try {
            val retrofitUils = RetrofitUtils(context!!, "https://praise.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .getCompanyDianZan(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                dianzanNum = it.body()!!
                val number = danzanshu(dianzanNum)
                dianzanText.text = "$number"
            }
        } catch (e: Throwable) {
            println(e)
        }
    }


    interface ActionMove {
        fun isMoveDown(b: Boolean)
    }

}




