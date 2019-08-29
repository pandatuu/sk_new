package com.example.sk_android.mvp.store

import android.content.Context
import com.example.sk_android.R
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.fragment.jobselect.IndustryListFragment
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import zendesk.suas.*


//////////////////////////////////////////////////////////////////////
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




//////////////////////////////////////////////////////////////////////


class getIndustryPageReducer : Reducer<IndustryPageData>() {

    override fun reduce(
        state: IndustryPageData,
        action: Action<*>
    ): IndustryPageData? {

        return if (action is IndustryPageFetchedAction) {
            IndustryPageData(action.getData<MutableList<JobContainer>>()!!)
        } else null

    }

    override fun getInitialState(): IndustryPageData {
        return IndustryPageData(ArrayList())
    }
}

class IndustryPageData(val data: MutableList<JobContainer> = mutableListOf()) {

    fun getIndustries(): MutableList<JobContainer> {
        return data
    }
}


class IndustryPageFetchedAction(cities: MutableList<JobContainer>) :
    Action<MutableList<JobContainer>>(ACTION_TYPE, cities) {
    companion object {
        private val ACTION_TYPE = "IndustryPageFetchedAction"
    }
}



//异步请求
class FetchIndustryAsyncAction(val context: Context) : AsyncAction {

    override fun execute(dispatcher: Dispatcher, getState: GetState) {
        var retrofitUils = RetrofitUtils(context!!, context.getString(R.string.industryUrl))
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

                //列表页面筛选
                //////////////////////////////////////////////////////////////////////
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


                //行业选择页面
                ///////////////////////////////////////////////////////////////////////
                var fatherList: MutableList<JobContainer> = mutableListOf()

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

                        var sonList: MutableList<Job> = mutableListOf()
                        for (j in 0..array.length() - 1) {
                            var son = array.getJSONObject(j)
                            if (son.getString("parentId").equals(fatherId)) {
                                //是子类

                                var sonId = son.getString("id")
                                var sonName = son.getString("name")
                                var job = Job(sonName, 1, sonId)
                                sonList.add(job)
                            }

                        }
                        var fatherJson = JobContainer(fatherName, 1, sonList)
                        fatherList.add(fatherJson)

                    }
                }

                val industryPageFetchedAction = IndustryPageFetchedAction(fatherList)
                dispatcher.dispatch(industryPageFetchedAction)




            }, {
                //失败
                println("行业数据,请求失败")
                println(it)

            })


    }
}


