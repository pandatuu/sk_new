package com.example.sk_android.mvp.view.activity.jobSelect


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;

import android.view.*
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.custom.layout.*


import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil

import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.mvp.model.jobSelect.City
import com.example.sk_android.mvp.view.adapter.jobSelect.CityShowAdapter
import com.example.sk_android.mvp.view.adapter.jobSelect.ProvinceShowAdapter


class CitySelectActivity : AppCompatActivity() {

    private lateinit var cityContainer:LinearLayout


    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var professions: MutableList<City> = mutableListOf()
        var p0= City("京都",
            arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット","電子商取引","ソフトウエア","メディア","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット","インターネット","電子商取引","ソフトウエア","メディア","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット"))
        var p1= City("大阪",
            arrayOf("银行","保险","证券/期货","基金","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保"))
        var p2= City("秋田",
            arrayOf("汽车生产","汽车零部件","4S店/期后市场"))
        var p3= City("岩手",
            arrayOf("房地产开发","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务"))


        professions.add(p0)
        professions.add(p1)
        professions.add(p2)
        professions.add(p3)
        professions.add(p3)
        professions.add(p3)
        professions.add(p2)
        professions.add(p3)
        professions.add(p3)
        professions.add(p3)
        professions.add(p2)
        professions.add(p3)
        professions.add(p3)
        professions.add(p3)

        professions.add(p3)

        val defaultDisplay = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        val w = point.x
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
                        recyclerView.setAdapter(ProvinceShowAdapter(recyclerView,  professions,height) { item ->
                            showCity(item,w-dip(125));
                        })
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
        showCity(professions.get(0),w-dip(125));

    }

    private fun showCity(item:City,w:Int) {
            if(cityContainer.childCount>0){
                cityContainer.removeViewAt(0)
            }
            var springbackRecyclerView=  LayoutInflater.from(cityContainer.context).inflate(R.layout.springback_recycler_view, null);
            var recyclerView = springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

            recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
            recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))
            var oneItemList: MutableList<City> = mutableListOf()
            oneItemList.add(item)
            recyclerView.setAdapter(CityShowAdapter(recyclerView, w, oneItemList) { club ->
                toast("11")
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


}
