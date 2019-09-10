package com.example.sk_android.mvp.store

import android.annotation.SuppressLint
import android.content.Context
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.HttpException
import zendesk.suas.*

class getInformationReducer : Reducer<InformationData>() {

    override fun reduce(
        state: InformationData,
        action: Action<*>
    ): InformationData? {

        return if (action is InformationFetchedAction) {
            InformationData(action.getData<ArrayList<UserBasicInformation>>()!!)
        } else null

    }

    override fun getInitialState(): InformationData {
        return InformationData(ArrayList())
    }
}

class InformationData(val data: ArrayList<UserBasicInformation> = arrayListOf()) {

    fun getInformation(): ArrayList<UserBasicInformation> {
        return data
    }
}


class InformationFetchedAction(something: ArrayList<UserBasicInformation>) :
    Action<ArrayList<UserBasicInformation>>(ACTION_TYPE, something) {
    companion object {
        private const val ACTION_TYPE = "InformationFetchedAction"
    }
}



//异步请求
class FetchInformationAsyncAction(val context: Context) : AsyncAction {

    companion object {
        fun create(context: Context) = AsyncMiddleware.create(
            FetchInformationAsyncAction(context)
        )
    }

    @SuppressLint("CheckResult")
    override fun execute(dispatcher: Dispatcher, getState: GetState) {
        val retrofitUils = RetrofitUtils(context, context.getString(R.string.userUrl))
        retrofitUils.create(PersonApi::class.java)
            .information
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                val basicList : ArrayList<UserBasicInformation> = arrayListOf()
//                val statu = it.get("auditState").toString().replace("\"","")
//                if(statu == "PENDING"){
//                    val url = it.get("changedContent").asJsonObject.get("avatarURL").toString()
//                    imageUrl = if(url.indexOf(";")!=-1) url.replace("\"","").split(";")[0] else url.replace("\"","")
//                    name = it.get("changedContent").asJsonObject.get("displayName").toString().replace("\"","")
//                }else{
//                    val url = it.get("avatarURL").toString()
//                    imageUrl = if(url.indexOf(";")!=-1) url.replace("\"","").split(";")[0] else url.replace("\"","")
//                    name = it.get("displayName").toString().replace("\"", "")
//                }
                basicList.add(Gson().fromJson<UserBasicInformation>(it, UserBasicInformation::class.java))

                // 测试图片  "https://sk-user-head.s3.ap-northeast-1.amazonaws.com/19d14846-a932-43ed-b04b-88245846c587"
//                psActionBarFragment!!.changePage(imageUrl, name)
//                val fatherList: ArrayList<String> = arrayListOf()
//                fatherList.add(imageUrl)
//                fatherList.add(name)

                val informationFetchedAction = InformationFetchedAction(basicList)
                dispatcher.dispatch(informationFetchedAction)
            }, {
                println("123456")
                println(it)
                if(it is HttpException){
                    if(it.code() == 401){

                    }
                }
            })
    }
}