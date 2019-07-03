package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.onTouch
import org.json.JSONArray

class IndustryListFragment : Fragment() {

    private lateinit var itemSelected: ItemSelected
    private var mContext: Context? = null
    private var myDialog: MyDialog? = null

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: IndustryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        var dataList: MutableList<JobContainer> = mutableListOf()
        fun newInstance(): IndustryListFragment {
            val fragment = IndustryListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        itemSelected = activity as ItemSelected
        return fragmentView
    }


    fun createView(): View {


        var jobContainer: MutableList<JobContainer> = mutableListOf()

        var view = UI {
            linearLayout {
                recycler = recyclerView {
                    overScrollMode = View.OVER_SCROLL_NEVER
                    setLayoutManager(LinearLayoutManager(this.getContext()))
                    onTouch { v, event ->
                        itemSelected.hideSoftKeyboard()
                    }
                }.lparams {
                }
            }
        }.view


        showLoading("加载数据中...")

        adapter = IndustryListAdapter(recycler, jobContainer) { item, index ->
            adapter.selectData(index)
            itemSelected.getSelectedItem(item)
        }

        recycler.setAdapter(adapter)

        requestIndustryData()
        return view

    }

    interface ItemSelected {

        fun getSelectedItem(item: JobContainer)

        fun hideSoftKeyboard()
    }


    fun requestIndustryData() {


        if (dataList.size != 0) {
            adapter.addData(dataList)
            hideLoading()
        } else {
            var retrofitUils = RetrofitUtils(mContext!!, "https://industry.sk.cgland.top/")
            retrofitUils.create(JobApi::class.java)
                .getAllIndustries(
                    false
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    //成功
                    println("行业数据,请求成功")
                    println(it)
                    var array = JSONArray(it.toString())

                    var fatherList: MutableList<JobContainer> = mutableListOf()

                    for (i in 0..array.length() - 1) {
                        var father = array.getJSONObject(i)
                        if (!father.has("parentId")
                            || father.getString("parentId") == null
                            || "".equals(father.getString("parentId"))
                            || "null".equals(father.getString("parentId"))
                        ) {

                            //是父类
                            var fatherId = father.getString("id")
                            var fatherName = father.getString("name")

                            var sonList: MutableList<Job> = mutableListOf()
                            for (j in 0..array.length() - 1) {
                                var son = array.getJSONObject(j)
                                if (son.getString("parentId").equals(fatherId)) {
                                    //是子类

                                    var sonId = son.getString("id")
                                    var sonName = son.getString("name")
                                    var job = Job(sonName, 1, sonId)
                                    sonList.add(job)
                                }

                            }
                            var fatherJson = JobContainer(fatherName, 1, sonList)
                            fatherList.add(fatherJson)

                        }
                    }
                    dataList.addAll(fatherList)
                    adapter.addData(fatherList)
                    hideLoading()

                }, {
                    //失败
                    println("行业数据,请求失败")
                    println(it)
                })

        }


    }



    //关闭等待转圈窗口
    private fun hideLoading() {
        if(myDialog!=null){
            if (myDialog!!.isShowing()) {
                myDialog!!.dismiss()
                myDialog=null
            }
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
            myDialog=null
            val builder = MyDialog.Builder(context!!)
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(context!!)
                .setCancelable(false)
                .setCancelOutside(false)

            myDialog = builder.create()
        }
        myDialog!!.show()
    }





}



