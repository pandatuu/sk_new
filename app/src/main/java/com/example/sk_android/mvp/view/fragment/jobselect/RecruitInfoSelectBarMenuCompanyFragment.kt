package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.model.jobselect.SelectedItemContainer
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoSelectBarMenuSelectItemAdapter
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class RecruitInfoSelectBarMenuCompanyFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuCompanySelect: RecruitInfoSelectBarMenuCompanySelect

    private lateinit var selectedJson: JSONObject
    private lateinit var recycler: RecyclerView
    private var myDialog: MyDialog? = null


    private  var adatper: RecruitInfoSelectBarMenuSelectItemAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        var dataList: MutableList<SelectedItemContainer> = mutableListOf()

        fun newInstance(j: JSONObject): RecruitInfoSelectBarMenuCompanyFragment {
            val fragment = RecruitInfoSelectBarMenuCompanyFragment()
            val json = j
            fragment.selectedJson = JSONObject()

            var item1 = JSONObject()
            item1.put("name", "")
            item1.put("index", -1)
            item1.put("value", "")
            fragment.selectedJson.put("融資段階", item1)

            var item2 = JSONObject()
            item2.put("name", "")
            item2.put("index", -1)
            item2.put("value", "")

            fragment.selectedJson.put("会社規模", item2)

            var item3 = JSONObject()
            item3.put("name", "")
            item3.put("index", -1)
            item3.put("value", "")

            fragment.selectedJson.put("業種", item3)

            var item4 = JSONObject()
            item4.put("name", "")
            item4.put("index", -1)
            item4.put("value", "")

            fragment.selectedJson.put("求人手段", item4)


            var iterator = json!!.keys().iterator()
            while (iterator.hasNext()) {
                var key = iterator.next()
                fragment.selectedJson.put(key, json.getJSONObject(key))
            }

            println(fragment.selectedJson)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        recruitInfoSelectBarMenuCompanySelect = activity as RecruitInfoSelectBarMenuCompanySelect
        return fragmentView
    }

    fun createView(): View {



        var view= UI {
            linearLayout {
                relativeLayout {
                    verticalLayout {
                        backgroundColor = Color.WHITE
                        recycler = recyclerView {
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))

                        }.lparams {
                            height = 0
                            weight = 1f
                            width = matchParent
                        }


                        verticalLayout {
                            setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {

                                }

                            })
                            gravity = Gravity.CENTER_HORIZONTAL
                            relativeLayout {
                                textView {
                                    text = "リセット"
                                    gravity = Gravity.CENTER
                                    backgroundResource = R.drawable.radius_button_gray_cc
                                    setOnClickListener(object : View.OnClickListener {
                                        override fun onClick(v: View?) {
                                            var j = JSONObject()
                                            recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(j)
                                        }

                                    })
                                }.lparams {
                                    height = dip(44)
                                    width = dip(163)
                                    alignParentLeft()
                                    centerVertically()
                                }

                                textView {
                                    text = "確定"
                                    gravity = Gravity.CENTER
                                    backgroundResource = R.drawable.radius_button_theme
                                    setOnClickListener(object : View.OnClickListener {
                                        override fun onClick(v: View?) {
                                            recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(selectedJson)
                                        }

                                    })
                                }.lparams {
                                    height = dip(44)
                                    width = dip(163)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                height = matchParent
                                width = dip(338)
                            }
                        }.lparams {
                            height = dip(72)
                            width = matchParent
                        }

                    }.lparams(width = matchParent, height = dip(462)) {

                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view


        requestIndustryData()

        return view
    }

    interface RecruitInfoSelectBarMenuCompanySelect {
        fun getCompanySelectedItems(json: JSONObject?)
    }


    fun requestIndustryData() {

        if (dataList != null && dataList.size != 0) {

            for  (item in dataList){
                val selectedIndex=selectedJson.getJSONObject(item.containerName).getInt("index")
                for(i in 0..item.item.size-1){
                    if(selectedIndex==i){
                        item.item.get(i).selected=true
                    }else{
                        item.item.get(i).selected=false
                    }
                }
            }
            if(adatper==null){
                adatper = RecruitInfoSelectBarMenuSelectItemAdapter(recycler, dataList) { title, item, index ->
                    //                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                    var selectItem = JSONObject()
                    selectItem.put("name", item.name)
                    selectItem.put("index", index)
                    selectItem.put("value", item.value)

                    selectedJson.put(title, selectItem)
                }
                recycler.setAdapter(adatper)
                hideLoading()
            }

            return
        } else {
            showLoading("")
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
                    var list: MutableList<SelectedItem> = mutableListOf()
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
                            var item:SelectedItem=SelectedItem(fatherName,false,fatherId)
                            list.add(item)
                        }
                    }
                    var SelectedItemContainerItem=SelectedItemContainer("業種",list)




                    var count = -1
                    var valueList1 = mutableListOf<String>("ALL","TSE_1_APP","NONE")
                    var showList1 = SelectedItemContainer("融資段階",
                        listOf("全て",  "上場企業", "未上場企業")
                            .map {
                                count++
                                if (selectedJson.has("融資段階") && selectedJson.getJSONObject("融資段階").getInt("index") == count) {
                                    SelectedItem(it, true,valueList1.get(count))
                                } else {
                                    SelectedItem(it, false,valueList1.get(count))
                                }

                            }
                            .toMutableList()
                    )

                    count = -1
                    var valueList2 = mutableListOf<String>("ALL","TINY","SMALL","MEDIUM","BIG","HUGE")
                    var showList2 = SelectedItemContainer("会社規模",

                        listOf("全て", "0~20人", "20~99人", "100~499人", "500~999人", "1000~9999人")
                            .map {
                                count++
                                if (selectedJson.has("会社規模") && selectedJson.getJSONObject("会社規模").getInt("index") == count) {
                                    SelectedItem(it, true,valueList2.get(count))
                                } else {
                                    SelectedItem(it, false,valueList2.get(count))
                                }

                            }
                            .toMutableList()
                    )


                    count = -1
                    var valueList3 = mutableListOf<String>("ALL","REGULAR","CONTRACT","DISPATCH","SHORT_TERM","OTHER")
                    var showList3 = SelectedItemContainer("求人手段",
                        arrayOf("全て", "正社員", "契約社員", "派遣社員","有期社員","その他")
                            .map {
                                count++
                                if (selectedJson.has("求人手段") && selectedJson.getJSONObject("求人手段").getInt("index") == count) {
                                    SelectedItem(it, true,valueList3.get(count))
                                } else {
                                    SelectedItem(it, false,valueList3.get(count))
                                }
                            }
                            .toMutableList()
                    )

                    dataList.add(0,showList1)
                    dataList.add(1,showList2)
                    dataList.add(2,SelectedItemContainerItem)
                    dataList.add(3,showList3)

                    if(adatper==null){
                        adatper = RecruitInfoSelectBarMenuSelectItemAdapter(recycler, dataList) { title, item, index ->
                            //                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                            var selectItem = JSONObject()
                            selectItem.put("name", item.name)
                            selectItem.put("index", index)
                            selectItem.put("value", item.value)

                            selectedJson.put(title, selectItem)
                        }
                        recycler.setAdapter(adatper)
                        hideLoading()
                    }else{

                    }


                }, {
                    //失败
                    println("行业数据,请求失败")
                    println(it)
                })

        }


    }
    //关闭等待转圈窗口
    private fun hideLoading() {
        if (myDialog != null) {
            if (myDialog!!.isShowing()) {
                myDialog!!.dismiss()
                myDialog = null
            }
        }
    }


    //弹出等待转圈窗口
    private fun showLoading(str: String) {
        if (myDialog != null && myDialog!!.isShowing()) {
            myDialog!!.dismiss()
            myDialog = null
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

