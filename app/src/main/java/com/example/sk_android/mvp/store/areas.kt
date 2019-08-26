package com.example.sk_android.mvp.store

import android.content.Context
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import zendesk.suas.*

class getCitiesReducer : Reducer<CitiesData>() {

    override fun reduce(
        state: CitiesData,
        action: Action<*>
    ): CitiesData? {

        return if (action is CitiesFetchedAction) {
            CitiesData(action.getData<MutableList<Area>>()!!)
        } else null

    }

    override fun getInitialState(): CitiesData {
        return CitiesData(ArrayList())
    }
}

class CitiesData(val data: MutableList<Area> = mutableListOf()) {

    fun getCities(): MutableList<Area> {
        return data
    }
}


class CitiesFetchedAction(cities: MutableList<Area>) :
    Action<MutableList<Area>>(ACTION_TYPE, cities) {
    companion object {
        private val ACTION_TYPE = "CitiesFetchedAction"
    }
}

//异步请求
class FetchCityAsyncAction(val context: Context) : AsyncAction {

    override fun execute(dispatcher: Dispatcher, getState: GetState) {

        var retrofitUils = RetrofitUtils(context, "https://basic-info.sk.cgland.top/")
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

                var showFirst: Boolean = true
                var areaList: MutableList<Area> = mutableListOf()

                for (i in 0..it.size() - 1) {


                    var provinceStr: String = it.get(i).asJsonObject.toString()
                    var province: JSONObject = JSONObject(provinceStr)
                    //是省份
                    if (province.get("parentId") == null || province.getString("parentId").toString().equals(
                            "null"
                        )
                    ) {
                        var provinceId = province.get("id").toString()
                        var provinceName = province.get("name").toString()

                        var cityList: MutableList<City> = mutableListOf()

                        var end = it.size() - 1
                        for (j in 0..end) {
                            var cityStr: String = it.get(j).asJsonObject.toString()
                            var city: JSONObject = JSONObject(cityStr)


                            if (city.get("parentId") != null && city.getString("parentId").toString().equals(
                                    provinceId
                                )
                            ) {
                                cityList.add(
                                    City(
                                        city.getString("name").toString(),
                                        city.getString("id").toString(),
                                        false
                                    )
                                )

                            }
                        }

                        if (showFirst) {
                            areaList.add(Area(provinceName, ProvinceShowAdapter.SELECTED, cityList))
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

                val citiesFetchedAction = CitiesFetchedAction(areaList)
                dispatcher.dispatch(citiesFetchedAction)

            }, {
                //失败
                println("城市数据,请求失败")
                println(it)
            })


//        SearchCitiesService.searchCitiesAsync(query) { cities ->
//            val citiesFetchedAction = CitiesFetchedAction(cities)
//            dispatcher.dispatch(citiesFetchedAction)
//        }
    }
}


