package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.jobSelect.RecruitInfoSelectBarMenuSelectListAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonArray
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class RecruitInfoSelectBarMenuPlaceFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuPlaceSelect:RecruitInfoSelectBarMenuPlaceSelect

    private lateinit var selectedString: String

    var adapter:RecruitInfoSelectBarMenuSelectListAdapter?=null
    private lateinit var recycler:RecyclerView

    private var dialogLoading: DialogLoading? = null
    val main = 1



    //加载中的图标
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        toast("ネットワークエラー") //网路出现问题
        DialogUtils.hideLoading(thisDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        private  var cityDataList= mutableListOf<SelectedItem>( )

        fun newInstance(str:String): RecruitInfoSelectBarMenuPlaceFragment {
            val fragment = RecruitInfoSelectBarMenuPlaceFragment()
            fragment.selectedString=""
            if(str!=null  && !str.equals("")){
                fragment.selectedString=str
            }

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuPlaceSelect =  activity as RecruitInfoSelectBarMenuPlaceSelect
        return fragmentView
    }

    fun createView(): View {



        var view= UI {
            frameLayout {
                id = main
                verticalLayout {
                    relativeLayout  {
                        backgroundColor=Color.WHITE
                        recycler=recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                        }.lparams {
                            leftMargin=dip(15)
                            rightMargin=dip(15)
                        }
                    }.lparams(width =matchParent, height =dip(288)){

                    }
                }.lparams {
                    width =matchParent
                    height =matchParent
                }
            }
        }.view
        requestCityAreaInfo()

        return view

    }

    interface RecruitInfoSelectBarMenuPlaceSelect{
        fun getPlaceSelected(item:SelectedItem)
    }



    fun requestCityAreaInfo() {
        if (cityDataList != null && cityDataList.size > 0) {
            for (item  in cityDataList){
               if(item.name.equals(selectedString)) {
                   item.selected=true
               }else{
                   item.selected=false
               }
            }
            if(adapter==null){
                adapter=RecruitInfoSelectBarMenuSelectListAdapter(recycler,  cityDataList) { item ->
                    recruitInfoSelectBarMenuPlaceSelect.getPlaceSelected(item)
                }
                recycler.setAdapter(adapter)
            }
            return
        } else {
            thisDialog=DialogUtils.showLoading(context!!)
            mHandler.postDelayed(r, 12000)
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
                    showCityData(it)
                    DialogUtils.hideLoading(thisDialog!!)
                }, {
                    //失败
                    println("城市数据,请求失败")
                    println(it)
                })
        }


    }



    fun showCityData(it: JsonArray) {
        for (i in 0..it.size() - 1) {

            var areaList: MutableList<SelectedItem> = mutableListOf()

            var provinceStr: String = it.get(i).asJsonObject.toString()
            var province: JSONObject = JSONObject(provinceStr)
            //是省份
            if (province.get("parentId") == null || province.getString("parentId").toString().equals("null")) {
                var provinceId = province.get("id").toString()
                var provinceName = province.get("name").toString()
                var item:SelectedItem=SelectedItem(provinceName,false,provinceId)
                areaList.add(item)
                cityDataList.add(item)
            }
            if(activity!=null){
                activity!!.runOnUiThread(Runnable {
                    if(adapter==null){
                        val firstItem=SelectedItem("全て",false,"ALL")
                        areaList.add(0,firstItem)
                        cityDataList.add(0,firstItem)
                        adapter=RecruitInfoSelectBarMenuSelectListAdapter(recycler,  areaList) { item ->
                            recruitInfoSelectBarMenuPlaceSelect.getPlaceSelected(item)
                        }
                        recycler.setAdapter(adapter)
                    }else{
                        adapter!!.appendData(areaList)
                    }
                })
            }

        }

    }



}

