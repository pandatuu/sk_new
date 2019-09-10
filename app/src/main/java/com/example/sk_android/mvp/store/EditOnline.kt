package com.example.sk_android.mvp.store

import android.annotation.SuppressLint
import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditBackground
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditEdu
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditJob
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditProject
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import retrofit2.adapter.rxjava2.HttpException
import zendesk.suas.*

//===========在线简历===================================================================================================

class getOnlineReducer : Reducer<OnlineData>() {

    override fun reduce(
        state: OnlineData,
        action: Action<*>
    ): OnlineData? {

        return if (action is OnlineFetchedAction) {
            OnlineData(action.getData<String>()!!)
        } else null

    }

    override fun getInitialState(): OnlineData {
        return OnlineData("")
    }
}

class OnlineData(val data: String = "") {

    fun getOnline(): String {
        return data
    }
}


class OnlineFetchedAction(something: String) :
    Action<String>(ACTION_TYPE, something) {
    companion object {
        private const val ACTION_TYPE = "OnlineFetchedAction"
    }
}

//===========工作经历===================================================================================================

class getJobReducer : Reducer<JobData>() {

    override fun reduce(
        state: JobData,
        action: Action<*>
    ): JobData? {

        return if (action is JobFetchedAction) {
            JobData(action.getData<ArrayList<JobExperienceModel>>()!!)
        } else null

    }

    override fun getInitialState(): JobData {
        return JobData(ArrayList())
    }
}

class JobData(val data: ArrayList<JobExperienceModel> = arrayListOf<JobExperienceModel>()) {

    fun getJob(): ArrayList<JobExperienceModel> {
        return data
    }
}


class JobFetchedAction(something: ArrayList<JobExperienceModel>) :
    Action<ArrayList<JobExperienceModel>>(ACTION_TYPE, something) {
    companion object {
        private const val ACTION_TYPE = "JobFetchedAction"
    }
}
//============项目经历===================================================================================================

class getProjectReducer : Reducer<ProjectData>() {

    override fun reduce(
        state: ProjectData,
        action: Action<*>
    ): ProjectData? {

        return if (action is ProjectFetchedAction) {
            ProjectData(action.getData<ArrayList<ProjectExperienceModel>>()!!)
        } else null

    }

    override fun getInitialState(): ProjectData {
        return ProjectData(ArrayList())
    }
}

class ProjectData(val data: ArrayList<ProjectExperienceModel> = arrayListOf()) {

    fun getProject(): ArrayList<ProjectExperienceModel> {
        return data
    }
}


class ProjectFetchedAction(something: ArrayList<ProjectExperienceModel>) :
    Action<ArrayList<ProjectExperienceModel>>(ACTION_TYPE, something) {
    companion object {
        private const val ACTION_TYPE = "ProjectFetchedAction"
    }
}
//==============教育经历===============================================================================================

class getEduReducer : Reducer<EduData>() {

    override fun reduce(
        state: EduData,
        action: Action<*>
    ): EduData? {

        return if (action is EduFetchedAction) {
            EduData(action.getData<ArrayList<EduExperienceModel>>()!!)
        } else null

    }

    override fun getInitialState(): EduData {
        return EduData(ArrayList())
    }
}

class EduData(val data: ArrayList<EduExperienceModel> = arrayListOf()) {

    fun getEdu(): ArrayList<EduExperienceModel> {
        return data
    }
}


class EduFetchedAction(something: ArrayList<EduExperienceModel>) :
    Action<ArrayList<EduExperienceModel>>(ACTION_TYPE, something) {
    companion object {
        private const val ACTION_TYPE = "EduFetchedAction"
    }
}



//异步请求
class FetchEditOnlineAsyncAction(val context: Context) : AsyncAction {

    companion object {
        fun create(context: Context) = AsyncMiddleware.create(
            FetchEditOnlineAsyncAction(context)
        )
    }

    @SuppressLint("CheckResult")
    override fun execute(dispatcher: Dispatcher, getState: GetState) {
            try {
                //获取在线简历
                val retrofitUils = RetrofitUtils(context, context.getString(R.string.jobUrl))
                retrofitUils.create(OnlineResumeApi::class.java)
                    .getUserResume("ONLINE")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        val page = Gson().fromJson(it.body(), PagedList::class.java)
                        val imageUrl : String
                        if (page.data.size > 0) {
                            val resumeId = page.data[0].get("id").asString
                            val changedContent = page.data[0].get("changedContent").asJsonObject
                            if (changedContent != null && changedContent.size() > 0) {
                                imageUrl =   page.data[0].get("changedContent")!!.asJsonObject.get("videoThumbnailURL").asString
                            } else {
                                imageUrl = page.data[0].get("videoThumbnailURL").asString
                            }
                            val onlineFetchedAction = OnlineFetchedAction(imageUrl)
                            dispatcher.dispatch(onlineFetchedAction)

                            //工作经历
                            val jobretrofitUils = RetrofitUtils(context, context.getString(R.string.jobUrl))
                            jobretrofitUils.create(OnlineResumeApi::class.java)
                                .getJobById(resumeId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                .subscribe({
                                    val jobExpList = arrayListOf<JobExperienceModel>()
                                    for (item in it.body()!!.asJsonArray) {
                                        jobExpList.add(Gson().fromJson(item, JobExperienceModel::class.java))
                                    }
                                    val jobFetchedAction = JobFetchedAction(jobExpList)
                                    dispatcher.dispatch(jobFetchedAction)
                                },{})

                            //项目经历
                            val projectretrofitUils = RetrofitUtils(context, context.getString(R.string.jobUrl))
                            projectretrofitUils.create(OnlineResumeApi::class.java)
                                .getProjectById(resumeId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                .subscribe({
                                    val projectList = arrayListOf<ProjectExperienceModel>()
                                    for (item in it.body()!!.asJsonArray) {
                                        projectList.add(Gson().fromJson(item, ProjectExperienceModel::class.java))
                                    }
                                    val projectFetchedAction = ProjectFetchedAction(projectList)
                                    dispatcher.dispatch(projectFetchedAction)
                                },{})

                            //教育经历
                            val eduretrofitUils = RetrofitUtils(context, context.getString(R.string.jobUrl))
                            eduretrofitUils.create(OnlineResumeApi::class.java)
                                .getEduById(resumeId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                .subscribe({
                                    val eduList = arrayListOf<EduExperienceModel>()
                                    for (item in it.body()!!.asJsonArray) {
                                        eduList.add(Gson().fromJson(item, EduExperienceModel::class.java))
                                    }
                                    val eduFetchedAction = EduFetchedAction(eduList)
                                    dispatcher.dispatch(eduFetchedAction)
                                },{})

                        }
                    },{

                    })
            } catch (throwable: Throwable) {

            }
    }
}