package com.example.sk_android.mvp.store

import android.content.Context
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import zendesk.suas.*

class getJobWantedReducer : Reducer<JobWantedData>() {

    override fun reduce(
        state: JobWantedData,
        action: Action<*>
    ): JobWantedData? {

        return if (action is JobWantedFetchedAction) {
            JobWantedData(action.getData<MutableList<JSONObject>>()!!)
        } else null

    }

    override fun getInitialState(): JobWantedData {
        return JobWantedData(ArrayList())
    }
}

class JobWantedData(val data: MutableList<JSONObject> = mutableListOf()) {

    fun getJobWanteds(): MutableList<JSONObject> {
        return data
    }
}


class JobWantedFetchedAction(cities: MutableList<JSONObject>) :
    Action<MutableList<JSONObject>>(ACTION_TYPE, cities) {
    companion object {
        private val ACTION_TYPE = "JobWantedFetchedAction"
    }
}



//异步请求
class FetchJobWantedAsyncAction(val context: Context) : AsyncAction {

    override fun execute(dispatcher: Dispatcher, getState: GetState) {

        var titleList = mutableListOf<JSONObject>()


        var retrofitUils = RetrofitUtils(context!!, "https://user.sk.cgland.top/")
        // 获取用户的求职列表
        retrofitUils.create(RegisterApi::class.java)
            .jobIntentIons
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("获取求职意向成功")
                    println(it)
                    println("---------------------"+it.size())

                    var array = JSONArray(it.toString())

                    var requestComplete = mutableListOf<Boolean>()

                    for (i in 0..array.length() - 1) {
                        requestComplete.add(false)
                        titleList.add(JSONObject())
                    }


                    for (i in 0..array.length() - 1) {
                        if (i > 2) {
                            break
                        }
                        var item = array.getJSONObject(i)
                        var industryIds = item.getJSONArray("industryIds")
                        if (industryIds.length() > 0) {
                            var industryId = industryIds.getString(0)


                            var industryRetrofitUils = RetrofitUtils(context!!, "https://industry.sk.cgland.top/")
                            industryRetrofitUils.create(RegisterApi::class.java)
                                .getIndusTryById(industryId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    println("获取求职意向的行业成功")
                                    println(it)
                                    var industryName = it.get("name").toString().replace("\"", "")

                                    var json = JSONObject()
                                    json.put("id",industryId)
                                    json.put("name",industryName)

                                    titleList.set(i, json)



                                    requestComplete.set(i, true)
                                    for (k in 0..requestComplete.size - 1) {
                                        if (requestComplete.get(k) == false) {
                                            break
                                        }
                                        if (k == requestComplete.size - 1) {




                                            val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                            dispatcher.dispatch(jobWantedFetchedAction)


                                        }
                                    }


                                }, {
                                    println("获取求职意向的行业错误")
                                    println(it)
                                    requestComplete.set(i, true)
                                    for (k in 0..requestComplete.size - 1) {
                                        if (requestComplete.get(k) == false) {
                                            break
                                        }
                                        if (k == requestComplete.size - 1) {

                                            val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                            dispatcher.dispatch(jobWantedFetchedAction)


                                        }
                                    }
                                })


                        } else {
                            titleList.removeAt(i)
                            requestComplete.set(i, true)
                            for (k in 0..requestComplete.size - 1) {
                                if (requestComplete.get(k) == false) {
                                    break
                                }
                                if (k == requestComplete.size - 1) {

                                    val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                    dispatcher.dispatch(jobWantedFetchedAction)


                                }
                            }
                        }
                    }
                },

                {
                    println("获取求职意向错误")
                }
            )


    }
}


