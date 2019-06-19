package com.example.sk_android.mvp.view.activity.jobselect


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;

import android.view.*
import android.widget.*


import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil

import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.R
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.model.jobselect.RecruitInfo
import com.example.sk_android.mvp.model.jobselect.SalaryType
import com.example.sk_android.mvp.view.adapter.jobselect.CityShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


class CitySelectActivity : AppCompatActivity() {

    private lateinit var cityContainer:LinearLayout
    lateinit var areaAdapter:ProvinceShowAdapter
    var w:Int = 0
    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var areaList: MutableList<Area> = mutableListOf()

        val defaultDisplay = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        w = point.x
        val h=point.y
        relativeLayout {
            verticalLayout {
                backgroundColor = Color.WHITE
                relativeLayout() {
                    textView() {
                        backgroundResource = R.drawable.actionbar_bottom_border
                    }.lparams() {
                        width = matchParent
                        height = dip(65)

                    }

                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back


                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }

                        textView {
                            text = "勤務地を選択"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColorResource = R.color.toolBarTextColor
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height = dip(65 - getStatusBarHeight(this@CitySelectActivity))
                            alignParentBottom()
                        }

                        textView {
                            text = "セーブ"
                            textColorResource = R.color.saveButtonTextColor
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER_VERTICAL
                            textSize = 13f
                            onClick {
                                toast("bbb")
                            }
                        }.lparams() {
                            width = dip(52)
                            height = dip(65 - getStatusBarHeight(this@CitySelectActivity))
                            alignParentRight()
                            alignParentBottom()
                        }
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }

//左边

                relativeLayout {

                    verticalLayout {


                        var springbackRecyclerView=  LayoutInflater.from(context).inflate(R.layout.springback_recycler_view, null);
                        var recyclerView = springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

                        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
                        recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))


                        areaAdapter=ProvinceShowAdapter(recyclerView,  areaList,height) { item,index ->


                            areaAdapter.selectData(index)

                            println("展示城市!")
                            showCity(item,w-dip(125));
                        }


                        recyclerView.setAdapter(areaAdapter)


                        requestCityAreaInfo()
                        addView(springbackRecyclerView)






                    }.lparams {
                        width=dip(125)
                        height = matchParent
                        alignParentLeft()
                    }


//右边

                    cityContainer=verticalLayout {
                        backgroundColorResource=R.color.originColor
                    }.lparams {
                        width=w-dip(125)
                        height = matchParent
                        alignParentRight()
                    }

                }.lparams() {
                    width = matchParent
                    height = matchParent
                }


            }.lparams() {
                alignParentTop()
                width = matchParent
                height = matchParent
            }
        }
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@CitySelectActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
       // showCity(areaList.get(0),w-dip(125));

    }

    private fun showCity(item:Area,w:Int) {
            if(cityContainer.childCount>0){
                cityContainer.removeViewAt(0)
            }
            var springbackRecyclerView=  LayoutInflater.from(cityContainer.context).inflate(R.layout.springback_recycler_view, null);
            var recyclerView = springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

            recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
            recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))
            var oneItemList: MutableList<Area> = mutableListOf()
            oneItemList.add(item)
            recyclerView.setAdapter(CityShowAdapter(recyclerView, w, oneItemList) { city ->

                var mIntent= Intent()
                mIntent.putExtra("cityName",city.name)
                mIntent.putExtra("cityId",city.id)
                setResult(RESULT_OK,mIntent);
                finish()
                overridePendingTransition(R.anim.right_out,R.anim.right_out)


            })
            cityContainer.addView(springbackRecyclerView)
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }





    fun requestCityAreaInfo(){

        var retrofitUils = RetrofitUtils(this@CitySelectActivity,"https://basic-info.sk.cgland.top/")
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
                var showFirst:Boolean=true
                for(i in 0..it.size()-1){
                    var areaList:MutableList<Area> = mutableListOf()

                    var provinceStr: String =it.get(i).asJsonObject.toString()
                    var province:JSONObject=JSONObject(provinceStr)
                    //是省份
                    if(province.get("parentId")==null || province.getString("parentId").toString().equals("null")){
                        var provinceId=province.get("id").toString()
                        var provinceName=province.get("name").toString()

                        var cityList:MutableList<City> = mutableListOf()
                        for(j in 0..it.size()-1){
                            var cityStr: String =it.get(j).asJsonObject.toString()
                            var city:JSONObject=JSONObject(cityStr)


                            if(city.get("parentId")!=null && city.getString("parentId").toString().equals(provinceId)){
                                cityList.add(City(city.getString("name").toString(),city.getString("id").toString()))
                            }
                        }

                        if(showFirst){
                            areaList.add(Area(provinceName,ProvinceShowAdapter.SELECTED,cityList))
                            showCity(areaList.get(0),w-dip(125));
                            showFirst=false
                        }else{
                            areaList.add(Area(provinceName,ProvinceShowAdapter.NORMAL,cityList))

                        }


                        areaAdapter.appendData(areaList)


                    }
                }


            }, {
                //失败
                println("城市数据,请求失败")
                println(it)
            })


    }


}
