package com.example.sk_android.mvp.view.fragment.company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.model.jobselect.SelectedItemContainer
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyInfoSelectBarMenuSelectItemAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

class CompanyInfoSelectBarMenuFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var selectBarMenuSelect: SelectBarMenuSelect
    var resultMap: MutableList<SelectedItem> = mutableListOf()
    var index = -1
    var list: MutableList<SelectedItem> = mutableListOf()
    val mainId = 1

    private var dialogLoading: DialogLoading? = null
    private var theSelectedItems: MutableList<String>? = null

    private lateinit var recycler: RecyclerView
    var adapter: CompanyInfoSelectBarMenuSelectItemAdapter? = null



    var thisDialog: MyDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        var cityDataList: MutableList<SelectedItem> = mutableListOf()


        fun newInstance(index: Int, selectedItems: MutableList<String>): CompanyInfoSelectBarMenuFragment {
            val fragment = CompanyInfoSelectBarMenuFragment()
            fragment.index = index
            var list: MutableList<SelectedItem> = mutableListOf()
            var count = -1
            if (index == 0) {

                var valueList1 = mutableListOf<String>("ALL", "TSE_1_APP", "NONE")
                list =
                    listOf("全て", "上場企業", "非上場企業")
                        .map {
                            count++
                            var flag = false
                            for (item in selectedItems) {
                                if (item.equals(it)) {
                                    flag = true
                                    break
                                }
                            }
                            if (flag) {
                                fragment.resultMap.add(SelectedItem(it, true, valueList1.get(count)))
                                SelectedItem(it, true, valueList1.get(count))
                            } else {
                                SelectedItem(it, false, valueList1.get(count))
                            }
                        }.toMutableList()


            } else if (index == 1) {

                var valueList2 = mutableListOf<String>("ALL", "TINY", "SMALL", "MEDIUM", "BIG", "HUGE", "SUPER")
                list =
                    mutableListOf("全部", "0~20人", "20~99人", "100~499人", "500~999人", "1000-9999人", "10000人以上")
                        .map {
                            count++
                            var flag = false
                            for (item in selectedItems) {
                                if (item.equals(it)) {
                                    flag = true
                                    break
                                }
                            }
                            if (flag) {
                                fragment.resultMap.add(SelectedItem(it, true, valueList2.get(count)))
                                SelectedItem(it, true, valueList2.get(count))
                            } else {
                                SelectedItem(it, false, valueList2.get(count))
                            }
                        }
                        .toMutableList()
            } else if (index == 2) {

                fragment.theSelectedItems = selectedItems
            } else if (index == 3) {
                var valueList3 = mutableListOf<String>("ALL", "NON_PROFIT", "STATE_OWNED", "SOLE", "JOINT", "FOREIGN")

                list =
                    mutableListOf("全て", "非営利", "国営", "独資", "合資", "外資")
                        .map {
                            count++
                            var flag = false
                            for (item in selectedItems) {
                                if (item.equals(it)) {
                                    flag = true
                                    break
                                }
                            }
                            if (flag) {
                                fragment.resultMap.add(SelectedItem(it, true, valueList3.get(count)))
                                SelectedItem(it, true, valueList3.get(count))
                            } else {
                                SelectedItem(it, false, valueList3.get(count))
                            }
                        }
                        .toMutableList()
            }

            fragment.list = list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        selectBarMenuSelect = activity as SelectBarMenuSelect
        return fragmentView
    }

    fun createView(): View {

        var view = UI {
            frameLayout {
                frameLayout {
                    id = mainId
                    verticalLayout {
                        backgroundResource = R.drawable.border_top_97

                        recycler = recyclerView {
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))

                            topPadding = dip(10)
                        }.lparams {
                            height = dip(0)
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
                                            var list: MutableList<SelectedItem> = mutableListOf()
                                            selectBarMenuSelect.getSelectedItems(index, list)
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
                                            selectBarMenuSelect.getSelectedItems(index, resultMap)
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

                    }.lparams(width = matchParent) {
                        height = wrapContent
                        if (list.size == 0) {
                            height = dip(350)
                        }

                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view

        if (list.size != 0) {
            setRecyclerAdapter(list)
        } else {
            requestIndustryData()
        }

        return view
    }


    fun requestIndustryData() {

        if (cityDataList.size > 0) {

            for (item in cityDataList) {
                item.selected = false
                for (i in 0..theSelectedItems!!.size - 1) {
                    if (item.name.equals(theSelectedItems!!.get(i))) {
                        item.selected = true
                    }
                }
            }

            setRecyclerAdapter(cityDataList)
            return
        } else {
            thisDialog=DialogUtils.showLoading(context!!)
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
                    val firstItem = SelectedItem("全て", false, "ALL")
                    cityDataList.add(firstItem)
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

                            var selected = false
                            if (theSelectedItems != null) {
                                for (i in 0..theSelectedItems!!.size - 1) {
                                    if (fatherName.equals(theSelectedItems!!.get(i))) {
                                        selected = true
                                    }
                                }
                            }


                            var item = SelectedItem(fatherName, selected, fatherId)
                            cityDataList.add(item)

                            setRecyclerAdapter(cityDataList)

                            DialogUtils.hideLoading(thisDialog)


                        }
                    }

                }, {
                    //失败
                    println("行业数据,请求失败")
                    println(it)
                    DialogUtils.hideLoading(thisDialog)
                })
        }
    }

    fun setRecyclerAdapter(theList: MutableList<SelectedItem>) {


        adapter = CompanyInfoSelectBarMenuSelectItemAdapter(recycler, theList, { item ->
            //recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
//                                if(!resultMap.contains(item)){
//                                    resultMap.add(item)
//                                }else{
//                                    resultMap.remove(item)
//                                }
            resultMap.clear()
            if (item != null) {
                resultMap.add(item)
            }

        })
        recycler.setAdapter(adapter)
    }


    interface SelectBarMenuSelect {
        fun getSelectedItems(index: Int, list: MutableList<SelectedItem>)
    }


}

