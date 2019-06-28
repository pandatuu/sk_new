package com.example.sk_android.mvp.view.fragment.company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.company.CompanyBriefInfo
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.adapter.company.CompanyInfoListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.utils.RetrofitUtils
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompanyInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var adapter: CompanyInfoListAdapter? = null
    lateinit var recycler: RecyclerView
    private var myDialog: MyDialog? = null

    var pageNum: Int = 1
    var pageLimit: Int = 20

    var haveData = true

    var requestDataFinish = true


    private var theCompanyName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(companyName: String?): CompanyInfoListFragment {
            val fragment = CompanyInfoListFragment()
            fragment.theCompanyName = companyName
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {


        var view = UI {
            linearLayout {
                linearLayout {
                    backgroundColorResource = R.color.originColor
                    recycler = recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager = LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view




        recycler.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (!recycler.canScrollVertically(1)) {
                    if (haveData) {
                        showLoading("加载中...")
                        reuqestCompanyInfoListData(
                            pageNum, pageLimit, theCompanyName, null, null, null, null, null,
                            null
                        )
                    } else {
                        showNormalDialog("没有数据了")
                    }
                }

            }

        })

        showLoading("加载中...")
        //请求数据
        reuqestCompanyInfoListData(
            pageNum, pageLimit, theCompanyName, null, null, null, null, null,
            null
        )
        return view
    }

    //请求获取数据
    private fun reuqestCompanyInfoListData(
        _page: Int?, _limit: Int?, name: String?, acronym: String?,
        size: String?, financingStage: String?, type: String?,
        coordinate: String?, radius: Number?
    ) {
        if (requestDataFinish) {
            requestDataFinish = false
            println("公司信息请求.....")

            var retrofitUils = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
            retrofitUils.create(CompanyInfoApi::class.java)
                .getCompanyInfoList(
                    _page, _limit, name, acronym, size, financingStage, type, coordinate, radius
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    //成功
                    println("公司信息请求成功!!!")
                    println(it)

                    var response = org.json.JSONObject(it.toString())
                    var data = response.getJSONArray("data")
                    if (data.length() > 0) {
                        pageNum = 1 + pageNum
                    } else {
                        haveData = false
                    }
                    //数据
                    println("公司信息请求成功 大小")
                    println(data.length())
                    for (i in 0..data.length() - 1) {
                        var item = data.getJSONObject(i)
                        var id = item.getString("id")
                        //公司名
                        var name = item.getString("name")
                        //公司简称
                        var acronym = item.getString("acronym")
                        //公司logo
                        val logo = item.getString("logo")
                        //公司规模
                        val size = item.getString("size")
                        //公司的融资状态
                        val financingStage = item.getString("financingStage")
                        //公司类型
                        var type = item.getString("type")
                        //视频路径
                        val videoUrl = item.getString("videoUrl")
                        //审查状态：待审查，已通过，未通过
                        var auditState = item.getString("auditState")

                        var haveVideo = false
                        if (videoUrl != null && !videoUrl.equals("")) {
                            haveVideo = true
                        }


                        //
                        //组装数据
                        //
                        appendRecyclerData(
                            id,
                            name,
                            acronym,
                            logo,
                            size,
                            financingStage,
                            type,
                            "",
                            haveVideo,
                            "",
                            "",
                            ""
                        )

                    }

                    hideLoading()

                }, {
                    //失败
                    println("公司信息请求失败!!!!!")
                    println(it)
                    hideLoading()
                })
        }
    }


    fun appendRecyclerData(
        id: String,
        name: String,
        acronym: String,
        logo: String,
        size: String,
        financingStage: String,
        type: String,
        industry: String,
        haveVideo: Boolean,
        cityName: String,
        countyName: String,
        streetName: String
    ) {
        requestDataFinish=true
        var list: MutableList<CompanyBriefInfo> = mutableListOf()
        var companyBriefInfo = CompanyBriefInfo(
            id,
            name,
            acronym,
            logo,
            size,
            financingStage,
            type,
            industry,
            haveVideo,
            cityName,
            countyName,
            streetName

        )
        list.add(companyBriefInfo)

        if (adapter == null) {
            //适配器
            adapter = CompanyInfoListAdapter(recycler, list) {
                //跳转到公司详情界面
                var intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
                intent.putExtra("companyId",id)
                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            }
            //设置适配器
            recycler.setAdapter(adapter)


        } else {
            adapter!!.addCompanyInfoList(list)

        }
    }


    //关闭等待转圈窗口
    private fun hideLoading() {
        if (myDialog!!.isShowing()) {
            myDialog!!.dismiss()
        }
    }


    private fun showNormalDialog(str: String) {
        showLoading(str)
        //延迟3秒关闭
        Handler().postDelayed({ hideLoading() }, 800)
    }

    //弹出等待转圈窗口
    private fun showLoading(str: String) {
        if (myDialog != null && myDialog!!.isShowing()) {
            myDialog!!.dismiss()
            val builder = MyDialog.Builder(context!!)
                .setMessage(str)
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(context!!)
                .setMessage(str)
                .setCancelable(false)
                .setCancelOutside(false)

            myDialog = builder.create()
        }
        myDialog!!.show()
    }


}

