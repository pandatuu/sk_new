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
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.fragment.jobselect.CitySelectFragment
import com.example.sk_android.utils.*
import com.umeng.message.PushAgent
import org.json.JSONArray
import org.json.JSONObject
import withTrigger
import java.io.IOException


class CitySelectActivity : AppCompatActivity(), CitySelectFragment.CitySelected {


    override fun getCitySelectedItem(list: MutableList<City>) {
        SelectedCityItem = list
    }

    private lateinit var SelectedCityItem: MutableList<City>
    var w: Int = 0
    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()
    var addressName = "东京"
    lateinit var citySelectFragment:CitySelectFragment


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

        var REQUEST_CODE = 101
        var TAG = "CitySelectActicity"

        PermissionManager.init().checkPermissions(this, REQUEST_CODE, object : IPermissionResult {

            override fun getPermissionFailed(
                activity: Activity?,
                requestCode: Int,
                deniedPermissions: Array<out String>?
            ) {
                // 获取权限失败
                Log.e(TAG, "获取权限失败！")
            }

            override fun getPermissionSuccess(activity: Activity, requestCode: Int) {
                // 获取权限成功
                Log.e(TAG, "获取权限成功！")

                val location = LocationUtils.getInstance(this@CitySelectActivity).showLocation()
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
//                    val address = location!!.getLatitude().toString() +"," location!!.getLongitude().toString()
                    Log.d("FLY.LocationUtils", latitude.toString())
                    Log.d("FLY.LocationUtils", longitude.toString())
                    success(latitude, longitude)
//                    addressText.text = address
                }
            }
        }, PermissionConsts.LOCATION)


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
                                    toast("你还没有选择")
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
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        toolbar1!!.setNavigationOnClickListener {
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

    //首先重写onRequestPermissionsResult方法
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    fun success(latitude: Double, longitude: Double) {
        // android 获取当前 语言环境：getResources().getConfiguration().locale.getLanguage()

        //  设置环境语句为日文，仅仅在此处使用
        val local = Locale.JAPAN
        var geocoder = Geocoder(this@CitySelectActivity, local)

        // 使用此句，默认为中文，方便测试
//         var geocoder = Geocoder(this@CitySelectActivity)
        Thread(Runnable {
            try {
                var res = geocoder.getFromLocation(latitude, longitude, 1)
                addressName = res[0].locality.toString()
                citySelectFragment.setNowAddress(addressName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationUtils.getInstance(this).removeLocationUpdatesListener();
        DialogUtils.hideLoading()
    }


}
