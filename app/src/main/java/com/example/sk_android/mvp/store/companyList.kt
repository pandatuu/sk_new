package com.example.sk_android.mvp.store

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.model.company.CompanyBriefInfo
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import zendesk.suas.*




class CompanyInfoListData{

    companion object {

        private var chacheDatas: MutableList<CompanyBriefInfo> = mutableListOf()
        fun setChacheData(data: MutableList<CompanyBriefInfo>){

            chacheDatas=data

        }


        fun getChacheData(): MutableList<CompanyBriefInfo>{

            return chacheDatas

        }


    }




}






class getCompanyListReducer : Reducer<CompanyListData>() {

    override fun reduce(
        state: CompanyListData,
        action: Action<*>
    ): CompanyListData? {

        return if (action is CompanyListFetchedAction) {
            CompanyListData(action.getData<MutableList<CompanyBriefInfo>>()!!)
        } else null

    }

    override fun getInitialState(): CompanyListData {
        return CompanyListData(ArrayList())
    }
}
class CompanyListData(val data: MutableList<CompanyBriefInfo> = mutableListOf()) {

    fun getCompanyList(): MutableList<CompanyBriefInfo> {
        return data
    }
}
class CompanyListFetchedAction(provinces: MutableList<CompanyBriefInfo>) :
    Action<MutableList<CompanyBriefInfo>>(ACTION_TYPE, provinces) {
    companion object {
        private val ACTION_TYPE = "ProvincesFetchedAction"
    }
}

//异步请求
class FetchCompanyListAsyncAction(val context: Context,val companyName:String?,val areaId:String?) : AsyncAction {

    override fun execute(dispatcher: Dispatcher, getState: GetState) {


        //用来装请求得到的数据，传递给adapter
        var companyBriefInfoList: MutableList<CompanyBriefInfo> = mutableListOf()

        var retrofitUils = RetrofitUtils(context!!, "https://org.sk.cgland.top/")
        retrofitUils.create(CompanyInfoApi::class.java)
            .getCompanyInfoList(
                1, 10, companyName, null, null, null, null, null, null, null, areaId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                //成功
                println("公司信息请求成功!!!拉数据")
                println(it)

                var response = org.json.JSONObject(it.toString())
                var data = response.getJSONArray("data")


                //数据
                println("公司信息请求成功 大小")
                println(data.length())

                var requestFlag = mutableListOf<Boolean>()


                for (i in 0..data.length() - 1) {
                    requestFlag.add(false)
                    companyBriefInfoList.add(CompanyBriefInfo("", "", "", "", "", "", "", "", false, "", "", "", 0))

                    var item = data.getJSONObject(i)
                    var id = item.getString("id")
                    //公司名
                    var name = item.getString("name")
                    //公司简称
                    var acronym = item.getString("acronym")
                    //公司logo
                    var logo = item.getString("logo")
                    if (logo != null) {
                        var arra = logo.split(",")
                        if (arra != null && arra.size > 0) {
                            logo = arra[0]
                        }
                    }


                    //公司规模
                    val size = item.getString("size")
                    //公司的融资状态
                    val financingStage = item.getString("financingStage")
                    //公司类型
                    var type = item.getString("type")

                    var typeIndex =
                        mutableListOf("NON_PROFIT", "STATE_OWNED", "SOLE", "JOINT", "FOREIGN").indexOf(type)

                    if (typeIndex >= 0) {
                        type = mutableListOf("非営利", "国営", "独資", "合資", "外資").get(typeIndex)
                    }


                    //视频路径
                    val videoUrl = item.getString("videoUrl")
                    //审查状态：待审查，已通过，未通过
                    var auditState = item.getString("auditState")

                    var haveVideo = false
                    if (videoUrl != null && !videoUrl.equals("")) {
                        haveVideo = true
                    }
                    var positionNum = 0


                    var positionNameRequest =
                        RetrofitUtils(context!!, "https://organization-position.sk.cgland.top/")
                    positionNameRequest.create(CompanyInfoApi::class.java)
                        .getPositionNumberOfCompany(
                            id
                        )
                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("公司的职位个数请求成功!!!")
                            println(it)


                            var json = JSONObject(it.toString())
                            positionNum = json.getInt("positionCount")


                            //
                            //组装数据
                            //
                            var companyBriefInfo = CompanyBriefInfo(
                                id,
                                name,
                                acronym,
                                logo,
                                size,
                                financingStage,
                                type,
                                "",
                                haveVideo,
                                "",
                                "",
                                "",
                                positionNum

                            )
                            companyBriefInfoList.set(i, companyBriefInfo)
                            requestFlag.set(i, true)
                            for (i in 0..requestFlag.size - 1) {
                                if (!requestFlag.get(i)) {
                                    break
                                }
                                if (i == requestFlag.size - 1) {
                                    //处理完
                                    val companyListFetchedAction = CompanyListFetchedAction(companyBriefInfoList)
                                    dispatcher.dispatch(companyListFetchedAction)
                                }
                            }

                        }, {

                            println("公司的职位个数请求失败!!!")
                            println(it)
                            //
                            //组装数据
                            //


                            var companyBriefInfo = CompanyBriefInfo(
                                id,
                                name,
                                acronym,
                                logo,
                                size,
                                financingStage,
                                type,
                                "",
                                haveVideo,
                                "",
                                "",
                                "",
                                positionNum

                            )
                            companyBriefInfoList.set(i, companyBriefInfo)
                            requestFlag.set(i, true)
                            for (i in 0..requestFlag.size - 1) {
                                if (!requestFlag.get(i)) {
                                    break
                                }
                                if (i == requestFlag.size - 1) {
                                    //处理完
                                    val companyListFetchedAction = CompanyListFetchedAction(companyBriefInfoList)
                                    dispatcher.dispatch(companyListFetchedAction)


                                }
                            }
                        })


                }


            }, {
                //失败
                println("公司信息请求失败!!!!!")
                println(it)
                if (companyBriefInfoList.size > 0) {

                    //处理完

                    val companyListFetchedAction = CompanyListFetchedAction(companyBriefInfoList)
                    dispatcher.dispatch(companyListFetchedAction)

                }
            })










    }
}


