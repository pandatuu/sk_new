package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.adapter.jobselect.CityShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonArray
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.lang.Thread.sleep

class CitySelectFragment : Fragment() {

    private var mContext: Context? = null
    lateinit var areaAdapter: ProvinceShowAdapter
    private lateinit var cityContainer: LinearLayout
    private var myDialog: MyDialog? = null

    var theWidth:Int = 0
    var cityDataList: JsonArray = JsonArray()

    var theSelectedCities:MutableList<City> = mutableListOf()//选中的城市,最多三个

    private lateinit var citySelected:CitySelected


    private var mostChooseNum=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {



        fun newInstance( w: Int,chooseNum:Int): CitySelectFragment {
            val fragment = CitySelectFragment()
            fragment.theWidth= w
            fragment. mostChooseNum=chooseNum
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
        var view=UI {
            var mainBodyId=11
            relativeLayout {
                id=mainBodyId
                verticalLayout {


                    var springbackRecyclerView =
                        LayoutInflater.from(context).inflate(R.layout.springback_recycler_view, null);
                    var recyclerView =
                        springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

                    recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
                    recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))


                    areaAdapter = ProvinceShowAdapter(recyclerView, areaList, height) { item, index ->


                        areaAdapter.selectData(index)

                        println("展示城市!")
                        showCity(item, theWidth - dip(125),index);
                    }


                    recyclerView.setAdapter(areaAdapter)
                    addView(springbackRecyclerView)


                }.lparams {
                    width = dip(125)
                    height = matchParent
                    alignParentLeft()
                }


//右边

                cityContainer = verticalLayout {
                    backgroundColorResource = R.color.originColor
                }.lparams {
                    width = theWidth - dip(125)
                    height = matchParent
                    alignParentRight()
                }

            }




        }.view

        showLoading("加载中...")

        Thread(Runnable {
            sleep(1)
            requestCityAreaInfo()

        }).start()

        return view

    }


    fun requestCityAreaInfo() {
        if (cityDataList != null && cityDataList.size() > 0) {
            showCityData(cityDataList)
            hideLoading()
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
                    cityDataList = it
                    showCityData(it)
                    hideLoading()
                }, {
                    //失败
                    println("城市数据,请求失败")
                    println(it)
                })
        }


    }




    fun showCityData(it: JsonArray) {
        var showFirst: Boolean = true
        for (i in 0..it.size() - 1) {

            var areaList: MutableList<Area> = mutableListOf()

            var provinceStr: String = it.get(i).asJsonObject.toString()
            var province: JSONObject = JSONObject(provinceStr)
            //是省份
            if (province.get("parentId") == null || province.getString("parentId").toString().equals("null")) {
                var provinceId = province.get("id").toString()
                var provinceName = province.get("name").toString()

                var cityList: MutableList<City> = mutableListOf()
                for (j in 0..it.size() - 1) {
                    var cityStr: String = it.get(j).asJsonObject.toString()
                    var city: JSONObject = JSONObject(cityStr)


                    if (city.get("parentId") != null && city.getString("parentId").toString().equals(provinceId)) {
                        cityList.add(City(city.getString("name").toString(), city.getString("id").toString(),false))
                    }
                }

                if (showFirst) {
                    areaList.add(Area(provinceName, ProvinceShowAdapter.SELECTED, cityList))
                    showCity(areaList.get(0), theWidth - dip(125),0);
                    showFirst = false
                } else {
                    areaList.add(Area(provinceName, ProvinceShowAdapter.NORMAL, cityList))

                }

            }
            if(activity!=null){
                activity!!.runOnUiThread(Runnable {
                    areaAdapter.appendData(areaList)
                })
            }

        }

    }
    private fun showCity(item: Area, w: Int,ind:Int) {
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
        recyclerView.setAdapter(CityShowAdapter(recyclerView, w, oneItemList,mostChooseNum) { city,index,selected ->

            if(selected!=null){
                areaAdapter.setSelectedCityItem(ind,index,selected)
                if(selected==true ){
                    theSelectedCities.add(city)
                }else{
                    theSelectedCities.remove(city)
                }
                citySelected.getCitySelectedItem(theSelectedCities)

            }else{
                toast("最多选三个哦!")
            }


        })
        activity!!.runOnUiThread(Runnable {
            cityContainer.addView(springbackRecyclerView)
        })
    }





     interface CitySelected {

        fun getCitySelectedItem(list:MutableList<City>)
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



    override fun onDestroy() {
        super.onDestroy()
        CityShowAdapter.selectedItemNumber=0

    }



}



