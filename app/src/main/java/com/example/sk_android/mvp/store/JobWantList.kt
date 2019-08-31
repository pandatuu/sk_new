package com.example.sk_android.mvp.store

import android.content.Context
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.view.adapter.jobselect.JobWantAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import imui.jiguang.cn.imuisample.messages.JitsiMeetActivitySon.launch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import zendesk.suas.*


//职位页面的求职意向
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

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

//求职意向页面的列表数据



class getJobWantedListReducer : Reducer<JobWantedListData>() {

    override fun reduce(
        state: JobWantedListData,
        action: Action<*>
    ): JobWantedListData? {

        return if (action is JobWantedListFetchedAction) {
            JobWantedListData(action.getData<ArrayList<UserJobIntention>>()!!)
        } else null

    }

    override fun getInitialState(): JobWantedListData {
        return JobWantedListData(ArrayList())
    }
}

class JobWantedListData(val data: ArrayList<UserJobIntention> = arrayListOf() ) {

    fun getJobWantedList():ArrayList<UserJobIntention> {
        return data
    }
}


class JobWantedListFetchedAction(list: ArrayList<UserJobIntention>) :
    Action<ArrayList<UserJobIntention>>(ACTION_TYPE, list) {
    companion object {
        private val ACTION_TYPE = "JobWantedListFetchedAction"
    }
}





//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

//求职意向页面的列表数据  个人页面



class getJobWantedListPersonalReducer : Reducer<JobWantedListPersonalData>() {

    override fun reduce(
        state: JobWantedListPersonalData,
        action: Action<*>
    ): JobWantedListPersonalData? {

        return if (action is JobWantedListPersonalFetchedAction) {
            JobWantedListPersonalData(action.getData<ArrayList<UserJobIntention>>()!!)
        } else null

    }

    override fun getInitialState(): JobWantedListPersonalData {
        return JobWantedListPersonalData(ArrayList())
    }
}

class JobWantedListPersonalData(val data: ArrayList<UserJobIntention> = arrayListOf() ) {

    fun getJobWantedListPersonal():ArrayList<UserJobIntention> {
        return data
    }
}


class JobWantedListPersonalFetchedAction(list: ArrayList<UserJobIntention>) :
    Action<ArrayList<UserJobIntention>>(ACTION_TYPE, list) {
    companion object {
        private val ACTION_TYPE = "JobWantedListPersonalFetchedAction"
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
                    var gson = Gson()
                    var myJobWantedList: ArrayList<UserJobIntention> = arrayListOf()


                    var myJobWantedListPersonal: ArrayList<UserJobIntention> = arrayListOf()


                    var array = JSONArray(it.toString())

                    var requestComplete = mutableListOf<Boolean>()
                    var areaComplete = mutableListOf<Boolean>()
                    var myJobWantedListPersonalComplete = mutableListOf<Boolean>()




                    for (i in 0..array.length() - 1) {
                        requestComplete.add(false)
                        areaComplete.add(false)
                        myJobWantedListPersonalComplete.add(false)

                        titleList.add(JSONObject())
                    }


                    for (i in 0..array.length() - 1) {

                        var result = it[i].asJsonObject
                        var jobWanteditem: UserJobIntention = gson.fromJson(result, UserJobIntention::class.java)
                        jobWanteditem.areaName = mutableListOf()
                        jobWanteditem.industryName = mutableListOf()


                        var jobWanteditemPersonal: UserJobIntention = gson.fromJson(result, UserJobIntention::class.java)
                        jobWanteditemPersonal.areaName = mutableListOf()
                        jobWanteditemPersonal.industryName = mutableListOf()

                        if (i > 2) {
                            break
                        }

                        var areaIds=array.getJSONObject(i).getJSONArray("areaIds")
                        var areaRequstFlag: MutableList<Boolean> = mutableListOf()
                        for (j in 0 until areaIds.length()) {
                            areaRequstFlag.add(false)
                        }

                        var areaName=""
                        //获取地区名
                        for (j in 0 until areaIds.length()) {
                            var baseRetrofitUils = RetrofitUtils(context!!, "https://basic-info.sk.cgland.top/")
                            baseRetrofitUils.create(RegisterApi::class.java)
                                .getAreaById(areaIds.getString(j))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    var areaName = it.get("name").toString().replace("\"", "")
                                    println(areaName)
                                    areaRequstFlag.set(j, true)

                                    jobWanteditem.areaName.add(areaName)
                                    jobWanteditemPersonal.areaName.add(areaName)


                                    for (k in 0 until areaRequstFlag.size) {
                                        if (areaRequstFlag.get(k) != true) {
                                            break
                                        }
                                        if (k == areaRequstFlag.size - 1) {
                                            areaComplete.set(i, true)
                                            if(requestComplete.get(i)){
                                                myJobWantedList.add(jobWanteditem)
                                                myJobWantedListPersonal.add(jobWanteditemPersonal)
                                            }

                                            for (kk in 0..array.length() - 1) {
                                                if (requestComplete.get(kk) == false || areaComplete.get(kk) == false ) {
                                                    break
                                                }
                                                if (kk == array.length() - 1) {
                                                    //都请求完了
                                                    val jobWantedListFetchedAction = JobWantedListFetchedAction(myJobWantedList)
                                                    dispatcher.dispatch(jobWantedListFetchedAction)
                                                }
                                            }

                                        }
                                    }

                                }, {
                                    println("地址错误")
                                    println(it)
                                })
                        }


                        ///////////////////////////////////////////////////////////////////////////////////
                        //获取求职意向（职位列表）
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

                                    jobWanteditem.industryName.add(industryName)

                                    jobWanteditemPersonal.industryName.add(industryName)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    GlobalScope.launch{
                                        try {
                                            val retrofitUils = RetrofitUtils(context!!, "https://industry.sk.cgland.top/")
                                            val it = retrofitUils.create(OnlineResumeApi::class.java)
                                                .getUserJobName(industryId)
                                                .subscribeOn(Schedulers.io())
                                                .awaitSingle()

                                            if (it.code() in 200..299) {
                                                val model = it.body()!!.asJsonObject
                                                if (model.get("parentId") != null) {
                                                    val retrofitUils = RetrofitUtils(context!!, "https://industry.sk.cgland.top/")
                                                    val it = retrofitUils.create(OnlineResumeApi::class.java)
                                                        .getUserJobName(model.get("parentId").asString)
                                                        .subscribeOn(Schedulers.io())
                                                        .awaitSingle()
                                                    if (it.code() in 200..299) {
                                                        if (it.body() != null){
                                                            var pName=it.body()!!.get("name").asString


                                                            jobWanteditemPersonal.industryName.clear()
                                                            jobWanteditemPersonal.industryName.add(pName+"-"+industryName)




                                                            myJobWantedListPersonalComplete.set(i, true)
                                                            if(areaComplete.get(i)){
                                                                myJobWantedListPersonal.add(jobWanteditemPersonal)
                                                            }
                                                            for (k in 0..myJobWantedListPersonalComplete.size - 1) {
                                                                if (myJobWantedListPersonalComplete.get(k) == false) {
                                                                    break
                                                                }
                                                                if (k == myJobWantedListPersonalComplete.size - 1) {



                                                                    for (kk in 0..areaComplete.size - 1) {

                                                                        if ( areaComplete.get(kk) == false ) {
                                                                            break
                                                                        }
                                                                        if (kk == areaComplete.size - 1) {
                                                                            //都请求完了


                                                                            val jobWantedListPersonalFetchedAction = JobWantedListPersonalFetchedAction(myJobWantedListPersonal)
                                                                            dispatcher.dispatch(jobWantedListPersonalFetchedAction)



                                                                        }
                                                                    }


                                                                }
                                                            }





                                                        }
                                                    }
                                                }
                                            }
                                        } catch (throwable: Throwable) {
                                            if (throwable is HttpException) {
                                                println(throwable.code())
                                            }
                                        }




                                    }




















































































//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                    var json = JSONObject()
                                    json.put("id",industryId)
                                    json.put("name",industryName)

                                    titleList.set(i, json)



                                    requestComplete.set(i, true)
                                    if(areaComplete.get(i)){
                                        myJobWantedList.add(jobWanteditem)
                                    }

                                    for (k in 0..requestComplete.size - 1) {
                                        if (requestComplete.get(k) == false) {
                                            break
                                        }
                                        if (k == requestComplete.size - 1) {




                                            val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                            dispatcher.dispatch(jobWantedFetchedAction)



                                            for (kk in 0..array.length() - 1) {


                                                if ( areaComplete.get(kk) == false ) {
                                                    break
                                                }
                                                if (kk == array.length() - 1) {
                                                    //都请求完了
                                                    val jobWantedListFetchedAction = JobWantedListFetchedAction(myJobWantedList)
                                                    dispatcher.dispatch(jobWantedListFetchedAction)
                                                }
                                            }




                                        }
                                    }


                                }, {
                                    println("获取求职意向的行业错误")
                                    println(it)
                                    requestComplete.set(i, true)
                                    if(areaComplete.get(i)){
                                        myJobWantedList.add(jobWanteditem)
                                    }
                                    for (k in 0..requestComplete.size - 1) {
                                        if (requestComplete.get(k) == false) {
                                            break
                                        }
                                        if (k == requestComplete.size - 1) {

                                            val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                            dispatcher.dispatch(jobWantedFetchedAction)

                                            for (kk in 0..array.length() - 1) {


                                                if ( areaComplete.get(kk) == false ) {
                                                    break
                                                }
                                                if (kk == array.length() - 1) {
                                                    //都请求完了
                                                    val jobWantedListFetchedAction = JobWantedListFetchedAction(myJobWantedList)
                                                    dispatcher.dispatch(jobWantedListFetchedAction)
                                                }
                                            }
                                        }
                                    }
                                })


                        } else {
                            titleList.removeAt(i)
                            requestComplete.set(i, true)
                            if(areaComplete.get(i)){
                                myJobWantedList.add(jobWanteditem)
                            }
                            for (k in 0..requestComplete.size - 1) {
                                if (requestComplete.get(k) == false) {
                                    break
                                }
                                if (k == requestComplete.size - 1) {

                                    val jobWantedFetchedAction = JobWantedFetchedAction(titleList)
                                    dispatcher.dispatch(jobWantedFetchedAction)


                                    for (kk in 0..array.length() - 1) {


                                        if ( areaComplete.get(kk) == false ) {
                                            break
                                        }
                                        if (kk == array.length() - 1) {
                                            //都请求完了
                                            val jobWantedListFetchedAction = JobWantedListFetchedAction(myJobWantedList)
                                            dispatcher.dispatch(jobWantedListFetchedAction)

                                        }
                                    }


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


