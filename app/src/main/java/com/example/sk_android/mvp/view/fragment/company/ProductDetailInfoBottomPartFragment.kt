package com.example.sk_android.mvp.view.fragment.jobselect

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.mvp.view.activity.company.CompanyWebSiteActivity
import com.example.sk_android.mvp.view.adapter.company.LabelShowAdapter
import com.example.sk_android.mvp.view.adapter.company.ProductDetailInfoAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyCityAddressAdapter
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ProductDetailInfoBottomPartFragment : Fragment() {

    private var mContext: Context? = null
    var contentText: String = ""
    var mCompany: CompanyInfo? = null

    lateinit var desContent: TextView
    lateinit var addShow: RecyclerView
    private lateinit var recyView: RecyclerView
    private lateinit var becycle: RecyclerView
    private lateinit var startTime: TextView
    private lateinit var endTime: TextView
    private lateinit var webSite: TextView

    private var addresslist = mutableListOf<ArrayList<String>>()
    private var benifitlist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(company: CompanyInfo?): ProductDetailInfoBottomPartFragment {
            var f = ProductDetailInfoBottomPartFragment()
            f.mCompany = company
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    @SuppressLint("SetTextI18n")
    fun setInformation(company: CompanyInfo) {
        mCompany = company
        if (company.companyIntroduce == "") {
            desContent.text = "无公司说明"
        } else {
            desContent.text = company.companyIntroduce
        }

        if (company.address.size > 0) {
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                for (item in company.address)
                    getArea(item[0],item[1])

                addShow.adapter = CompanyCityAddressAdapter(addresslist)
                addShow.adapter?.notifyDataSetChanged()
            }
        } else {
            addresslist.add(arrayListOf("暂未提供公司地址",""))
            addShow.adapter = CompanyCityAddressAdapter(addresslist)
            addShow.adapter?.notifyDataSetChanged()
        }


        if (company.benifits.size > 0) {
            becycle.adapter = LabelShowAdapter(company.benifits) {

            }
            becycle.adapter?.notifyDataSetChanged()
        } else {
            benifitlist.add("暂未提供福利")
            becycle.adapter = LabelShowAdapter(benifitlist) {

            }
            becycle.adapter?.notifyDataSetChanged()
        }

        if (company.startTime != "") {
            startTime.text = "上班时间:${company.startTime}"
        } else {
            startTime.text = "上班时间:9:00(默认)"
        }
        if (company.endTime != "") {
            endTime.text = "上班时间:${company.endTime}"
        } else {
            endTime.text = "下班时间:17:00(默认)"
        }

        if(company.website != "" && Patterns.WEB_URL.matcher(company.website).matches())
            webSite.text = company.website


        recyView.adapter?.notifyDataSetChanged()
    }

    private fun createView(): View {

        val view = UI {
            linearLayout {

                scrollView {
                    verticalLayout {

                        verticalLayout {

                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(1)
                            }

                            textView {
                                text = "会社について"
                                textSize = 18f
                                textColorResource = R.color.black20
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(25)
                                bottomMargin = dip(15)
                            }


                            desContent = textView {

                                textSize = 14f
                                textColorResource = R.color.gray5c
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                var imageBool = false
                                toolbar {
                                    backgroundColor = Color.TRANSPARENT
                                    navigationIconResource = R.mipmap.icon_down
                                    onClick {
                                        if (!imageBool) {
                                            desContent.height = wrapContent
                                            navigationIconResource = R.mipmap.icon_up_home
                                            imageBool = true
                                        } else {
                                            desContent.height = dip(85)
                                            navigationIconResource = R.mipmap.icon_down
                                            imageBool = false
                                        }
                                    }
                                }.lparams(dip(20), dip(20))
                                desContent.addOnLayoutChangeListener(object: View.OnLayoutChangeListener{
                                    override fun onLayoutChange(
                                        v: View?,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int
                                    ) {
                                        if(v!!.height<dip(85)) {
                                            visibility = LinearLayout.INVISIBLE
                                        }
                                    }

                                })
                            }.lparams {
                                topMargin = dip(15)
                                width = matchParent
                                height = wrapContent
                                bottomMargin = dip(25)
                            }

                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(1)
                            }

                            textView {
                                textSize = 18f
                                textColorResource = R.color.black20
                                text = "会社住所"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                topMargin = dip(21)
                                bottomMargin = dip(5)
                            }

                            addShow = recyclerView {
                                overScrollMode = View.OVER_SCROLL_NEVER
                                var layoutManager = LinearLayoutManager(this.getContext())
                                setLayoutManager(layoutManager)
                                setAdapter(CompanyCityAddressAdapter(addresslist))
                            }.lparams(matchParent, wrapContent)
                            linearLayout {
                                gravity = Gravity.CENTER
                                var imageBool = false
                                toolbar {
                                    backgroundColor = Color.TRANSPARENT
                                    navigationIconResource = R.mipmap.icon_down
                                    onClick {
                                        if (!imageBool) {
                                            val a = LinearLayout.LayoutParams(addShow.layoutParams)
                                            a.height = wrapContent
                                            navigationIconResource = R.mipmap.icon_up_home
                                            imageBool = true
                                        } else {
                                            val a = LinearLayout.LayoutParams(addShow.layoutParams)
                                            a.height = dip(120)
                                            navigationIconResource = R.mipmap.icon_down
                                            imageBool = false
                                        }
                                    }

                                }.lparams(dip(20), dip(20))
                                addShow.addOnLayoutChangeListener(object: View.OnLayoutChangeListener{
                                    override fun onLayoutChange(
                                        v: View?,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int
                                    ) {
                                        if(v!!.height<dip(120)){
                                            visibility = LinearLayout.INVISIBLE
                                        }
                                    }

                                })
                            }.lparams {
                                topMargin = dip(15)
                                width = matchParent
                                height = wrapContent
                                bottomMargin = dip(25)
                            }



                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(1)
                            }

                            textView {
                                text = contentText
                                textSize = 18f
                                textColorResource = R.color.black20
                                text = "会社の福利"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                topMargin = dip(21)
                            }

                        }.lparams {
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                            width = matchParent
                        }


                        becycle = recyclerView {
                            overScrollMode = View.OVER_SCROLL_NEVER
                            var layoutManager = LinearLayoutManager(this.getContext())
                            setLayoutManager(layoutManager)
                            adapter = LabelShowAdapter(benifitlist) { str ->
                                toast("11")
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            bottomMargin = dip(10)
                        }




                        verticalLayout {
                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(1)
                            }

                            textView {
                                text = contentText
                                textSize = 18f
                                textColorResource = R.color.black20
                                text = "勤務時間"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                topMargin = dip(21)
                            }


                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.ico_time)

                                }.lparams() {
                                    height = dip(16)
                                    width = dip(16)
                                }

                                startTime = textView {
                                    text = "上班时间：9:00"
                                    textColorResource = R.color.black33
                                    letterSpacing = 0.05f
                                    textSize = 14f

                                }.lparams {
                                    height = matchParent
                                    leftMargin = dip(8)
                                }

                            }.lparams {
                                width = matchParent
                                height = dip(20)
                                topMargin = dip(17)
                            }

                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.ico_time)

                                }.lparams() {
                                    height = dip(16)
                                    width = dip(16)
                                }

                                endTime = textView {
                                    text = "下班时间：17:00"
                                    textColorResource = R.color.black33
                                    letterSpacing = 0.05f
                                    textSize = 14f

                                }.lparams {
                                    height = matchParent
                                    leftMargin = dip(8)
                                }

                            }.lparams {
                                width = matchParent
                                height = dip(20)
                                topMargin = dip(10)
                                bottomMargin = dip(21)

                            }


                            textView {
                                backgroundColorResource = R.color.originColor
                            }.lparams {
                                width = matchParent
                                height = dip(1)
                            }



                            textView {
                                text = contentText
                                textSize = 18f
                                textColorResource = R.color.black20
                                text = "ホームページ"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                topMargin = dip(21)
                            }


                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.ico_web)

                                }.lparams() {
                                    height = dip(15)
                                    width = dip(15)
                                }
                                webSite = textView {
                                    gravity = Gravity.CENTER_VERTICAL
                                    text = "暂未提供公司网址"
                                    textSize = 14f
                                    letterSpacing = 0.05f
                                    textColorResource = R.color.black33
                                }.lparams {
                                    width = 0
                                    weight = 1f
                                    height = dip(20)
                                    leftMargin = dip(10)
                                }
                                toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                   this.withTrigger().click {
                                        if("暂未提供公司网址" != webSite.text.toString()){
                                            toast(webSite.text.toString())
                                            val intent = Intent(context!!, CompanyWebSiteActivity::class.java)
                                            intent.putExtra("webUrl",webSite.text.toString())
                                            intent.putExtra("companyName",mCompany?.name)
                                            startActivity(intent)
                                            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                        }
                                    }
                                }.lparams(dip(20),dip(20))

                            }.lparams {
                                width = matchParent
                                height = dip(20)
                                topMargin = dip(15)
                                bottomMargin = dip(50)
                            }

                        }.lparams {
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                            width = matchParent
                        }


                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }
            }
        }.view

        val view2 = UI {
            linearLayout {
                recyView = recyclerView {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = ProductDetailInfoAdapter(view, listOf(1))
                }
            }
        }.view

        return view2


    }

    private suspend fun getArea(id: String, address: String){
        try {
            val retrofitUils = RetrofitUtils(context!!, "https://basic-info.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .getAreaById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                val model = it.body()!!.asJsonObject

                getAreaParent(model.get("parentId").asString,model.get("name").asString, address)
//                CompanyBriefInfo
            }
        } catch (e: Throwable) {
            println(e)
        }
    }

    private suspend fun getAreaParent(id: String, addr: String, address: String){
        try {
            val retrofitUils = RetrofitUtils(context!!, "https://basic-info.sk.cgland.top/")
            val it = retrofitUils.create(CompanyInfoApi::class.java)
                .getAreaParentById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                println(it)
                val model = it.body()!!.asJsonObject
                val parent = model.get("name").asString
                val area = "$parent  $addr"
                addresslist.add(arrayListOf(area,address))
            }
        } catch (e: Throwable) {
            println(e)
        }
    }


}




