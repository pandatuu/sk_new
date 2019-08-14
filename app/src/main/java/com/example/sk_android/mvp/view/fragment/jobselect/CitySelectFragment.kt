package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.Toast
import com.biao.pulltorefresh.OnRefreshListener
import com.biao.pulltorefresh.PtrHandler
import com.biao.pulltorefresh.PtrLayout
import com.biao.pulltorefresh.header.DefaultRefreshView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.adapter.jobselect.CityShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.io.IOException
import java.lang.Thread.sleep

class CitySelectFragment : Fragment() {

    private var mContext: Context? = null


    lateinit var recyclerView: RecyclerView

    lateinit var areaAdapter: ProvinceShowAdapter
    var cityAdapter: CityShowAdapter? = null

    private lateinit var cityContainer: LinearLayout
    private var dialogLoading: DialogLoading? = null

    var theWidth: Int = 0

    var theSelectedCities: MutableList<City> = mutableListOf()//选中的城市,最多三个

    private lateinit var citySelected: CitySelected


    private var mostChooseNum = 1


    //加载中的图标
    var thisDialog: MyDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {

        var cityDataList :MutableList<Area> = mutableListOf()


        fun newInstance(w: Int, chooseNum: Int): CitySelectFragment {
            val fragment = CitySelectFragment()
            fragment.theWidth = w
            fragment.mostChooseNum = chooseNum
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        citySelected = activity as CitySelected
        return fragmentView
    }


    fun createView(): View {

        var areaList: MutableList<Area> = mutableListOf()
        var view = UI {
            var mainBodyId = 11
            frameLayout {
                id = mainBodyId
                verticalLayout {


                    var springbackRecyclerView =
                        LayoutInflater.from(context).inflate(R.layout.springback_recycler_view, null);


                    recyclerView =
                        springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

                    recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
                    recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))



                    addView(springbackRecyclerView)


                }.lparams {
                    width = dip(125)
                    height = matchParent
                    gravity = Gravity.LEFT
                }


//右边

                cityContainer = verticalLayout {
                    backgroundColorResource = R.color.originColor
                }.lparams {
                    width = theWidth - dip(125)
                    height = matchParent
                    gravity = Gravity.RIGHT
                }

            }


        }.view

        thisDialog=DialogUtils.showLoading(context!!)

        Thread(Runnable {
            sleep(1)
            requestCityAreaInfo()

        }).start()

        return view

    }


    fun requestCityAreaInfo() {
        if (cityDataList != null && cityDataList.size > 0) {

            for(i in 0..cityDataList.size-1){
                if(i==0){
                    cityDataList.get(i).type=ProvinceShowAdapter.SELECTED
                }else{
                    cityDataList.get(i).type=ProvinceShowAdapter.NORMAL
                }
                for(city in cityDataList.get(i).city){
                    city.selected=false
                }
            }

            activity!!.runOnUiThread(Runnable {
                areaAdapter = ProvinceShowAdapter(recyclerView, cityDataList) { item, index ->
                    areaAdapter.selectData(index)
                    println("展示城市!")
                    showCity(item, theWidth - dip(125), index);
                }
                recyclerView.setAdapter(areaAdapter)

                showCity(cityDataList.get(0), theWidth - dip(125), 0);

            })

            DialogUtils.hideLoading(thisDialog)
        } else {
            var retrofitUils = RetrofitUtils(mContext!!, "https://basic-info.sk.cgland.top/")
            retrofitUils.create(CityInfoApi::class.java)
                .getAllAreaInfo(
                    false
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    //成功
                    println("城市数据,请求成功")
                    println(it)

                    activity!!.runOnUiThread(Runnable {
                        showCityData(it)
                    })


                    DialogUtils.hideLoading(thisDialog)
                }, {
                    //失败
                    println("城市数据,请求失败")
                    println(it)
                    DialogUtils.hideLoading(thisDialog)
                })
        }


    }


    fun showCityData(it: JsonArray) {
        var showFirst: Boolean = true
        var areaList: MutableList<Area> = mutableListOf()

        for (i in 0..it.size() - 1) {


            var provinceStr: String = it.get(i).asJsonObject.toString()
            var province: JSONObject = JSONObject(provinceStr)
            //是省份
            if (province.get("parentId") == null || province.getString("parentId").toString().equals("null")) {
                var provinceId = province.get("id").toString()
                var provinceName = province.get("name").toString()

                var cityList: MutableList<City> = mutableListOf()

                var end = it.size() - 1
                for (j in 0..end) {
                    var cityStr: String = it.get(j).asJsonObject.toString()
                    var city: JSONObject = JSONObject(cityStr)


                    if (city.get("parentId") != null && city.getString("parentId").toString().equals(provinceId)) {
                        cityList.add(City(city.getString("name").toString(), city.getString("id").toString(), false))

                    }
                }

                if (showFirst) {
                    areaList.add(Area(provinceName, ProvinceShowAdapter.SELECTED, cityList))
                    showCity(areaList.get(0), theWidth - dip(125), 0);
                    showFirst = false
                } else {
                    areaList.add(Area(provinceName, ProvinceShowAdapter.NORMAL, cityList))

                }

            }
//            if (activity != null) {
//                activity!!.runOnUiThread(Runnable {
//                    areaAdapter.appendData(areaList)
//                })
//            }

        }
        cityDataList.addAll(areaList)
        activity!!.runOnUiThread(Runnable {
            areaAdapter = ProvinceShowAdapter(recyclerView, areaList) { item, index ->
                areaAdapter.selectData(index)
                println("展示城市!")
                showCity(item, theWidth - dip(125), index);
            }
            recyclerView.setAdapter(areaAdapter)
        })

    }

    private fun showCity(item: Area, w: Int, ind: Int) {
        if (cityContainer.childCount > 0) {
            cityContainer.removeViewAt(0)
        }
        var springbackRecyclerView =
            LayoutInflater.from(cityContainer.context).inflate(R.layout.springback_recycler_view, null);
        var recyclerView = springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))
        var oneItemList: MutableList<Area> = mutableListOf()
        oneItemList.add(item)
        cityAdapter = CityShowAdapter(recyclerView, w, oneItemList, mostChooseNum) { city, index, selected ->

            if (selected != null) {
                areaAdapter.setSelectedCityItem(ind, index, selected)
                if (selected == true) {
                    theSelectedCities.add(city)
                } else {
                    theSelectedCities.remove(city)
                }
                citySelected.getCitySelectedItem(theSelectedCities)

            } else {
                val toast = Toast.makeText(activity, "最大" + mostChooseNum.toString() + "を選択できます", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                //toast("最大" + mostChooseNum.toString() + "を選択できます")
            }
        }
        recyclerView.setAdapter(cityAdapter)
        activity!!.runOnUiThread(Runnable {
            cityContainer.addView(springbackRecyclerView)
        })
    }


    fun setNowAddress(add: String, id: String) {

        if (cityAdapter != null) {
            cityAdapter!!.setNowAddress(add, id)
            return
        } else {
            runOnUiThread(Runnable {
                sleep(200)
                setNowAddress(add, id)
            })


        }
    }

    fun setEnAble() {
        if (cityAdapter != null) {
            cityAdapter!!.setEnAble()
            return
        }
    }


    interface CitySelected {

        fun getCitySelectedItem(list: MutableList<City>)
    }


    override fun onDestroy() {
        super.onDestroy()
        CityShowAdapter.selectedItemNumber = 0

    }


}



