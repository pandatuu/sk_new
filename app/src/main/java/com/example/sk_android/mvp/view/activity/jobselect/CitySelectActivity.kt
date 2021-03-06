package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.app.Activity
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
import java.util.*
import com.jaeger.library.StatusBarUtil
import android.graphics.Point
import android.location.Geocoder
import android.util.Log
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.fragment.jobselect.CitySelectFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.utils.*
import com.google.gson.JsonObject
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import withTrigger
import java.io.IOException


class CitySelectActivity : AppCompatActivity(), CitySelectFragment.CitySelected {


    override fun getCitySelectedItem(list: MutableList<City>) {
        SelectedCityItem = list
    }

    private var SelectedCityItem: MutableList<City> = mutableListOf()
    var w: Int = 0
    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()
    var addressName = "取得中..."
    lateinit var citySelectFragment: CitySelectFragment
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var defaultAddressId: String

    var thisDialog: MyDialog?=null

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
        val h = point.y

        var TAG = "CitySelectActicity"


        var mostChooseNum = intent.getIntExtra("mostChooseNum", 3)

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

                            this.withTrigger().click {

                                if (SelectedCityItem.size == 0) {
                                    toast("選択してください")
                                } else {
                                    var mIntent = Intent()
                                    var array = JSONArray()
                                    for (i in 0..SelectedCityItem.size - 1) {
                                        var it = JSONObject()
                                        it.put("name", SelectedCityItem.get(i).name)
                                        it.put("id", SelectedCityItem.get(i).id)
                                        println(it)
                                        array.put(it)
                                    }
                                    mIntent.putExtra("cityModel", array.toString())
                                    setResult(AppCompatActivity.RESULT_OK, mIntent);
                                    finish()
                                    overridePendingTransition(R.anim.left_in, R.anim.right_out)
                                }
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


                var mainBodyId = 11
                frameLayout() {
                    id = mainBodyId
                    citySelectFragment = CitySelectFragment.newInstance(w, mostChooseNum);
                    supportFragmentManager.beginTransaction().replace(id, citySelectFragment).commit()

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
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        toolbar1.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }


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

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        LocationUtils.getInstance(this).removeLocationUpdatesListener();
        DialogUtils.hideLoading(thisDialog)
    }


}
