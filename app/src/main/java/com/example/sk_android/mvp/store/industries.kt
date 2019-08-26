package com.example.sk_android.mvp.store

import android.content.Context
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import zendesk.suas.*

class getIndustryReducer : Reducer<IndustryData>() {

    override fun reduce(
        state: IndustryData,
        action: Action<*>
    ): IndustryData? {

        return if (action is IndustryFetchedAction) {
            IndustryData(action.getData<MutableList<SelectedItem>>()!!)
        } else null

    }

    override fun getInitialState(): IndustryData {
        return IndustryData(ArrayList())
    }
}

class IndustryData(val data: MutableList<SelectedItem> = mutableListOf()) {

    fun getIndustries(): MutableList<SelectedItem> {
        return data
    }
}


class IndustryFetchedAction(cities: MutableList<SelectedItem>) :
    Action<MutableList<SelectedItem>>(ACTION_TYPE, cities) {
    companion object {
        private val ACTION_TYPE = "IndustryFetchedAction"
    }
}



//异步请求
class FetchIndustryAsyncAction(val context: Context) : AsyncAction {

    override fun execute(dispatcher: Dispatcher, getState: GetState) {
        var retrofitUils = RetrofitUtils(context!!, "https://industry.sk.cgland.top/")
        retrofitUils.create(JobApi::class.java)
            .getAllIndustries(
                false
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                //成功
                println("行业数据,请求成功")
                println(it)
                var array = JSONArray(it.toString())
                var list: MutableList<SelectedItem> = mutableListOf()
                val firstItem=SelectedItem("全て",false,"ALL")
                list.add(firstItem)
                for (i in 0..array.length() - 1) {
                    var father = array.getJSONObject(i)
                    if (!father.has("parentId")
                        || father.getString("parentId") == null
                        || "".equals(father.getString("parentId"))
                        || "null".equals(father.getString("parentId"))
                    ) {

                        //是父类
                        var fatherId = father.getString("id")
                        var fatherName = father.getString("name")
                        var item:SelectedItem=SelectedItem(fatherName,false,fatherId)
                        list.add(item)
                    }
                }


                val industryFetchedAction = IndustryFetchedAction(list)
                dispatcher.dispatch(industryFetchedAction)

            }, {
                //失败
                println("行业数据,请求失败")
                println(it)

            })


    }
}


