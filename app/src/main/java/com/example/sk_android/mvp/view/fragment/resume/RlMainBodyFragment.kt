package com.example.sk_android.mvp.view.fragment.resume

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.widget.Button
import android.widget.ListView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.adapter.resume.ResumeAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*
import android.view.*
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.mvp.view.activity.resume.ResumeListActivity
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.FileUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
import withTrigger


class RlMainBodyFragment : Fragment() {
    private lateinit var myDialog: MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var myList: ListView
    var mId = 2
    lateinit var mData: LinkedList<Resume>
    lateinit var resumeAdapter: ResumeAdapter
    lateinit var myTool: Tool
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var number = 0


    companion object {
        fun newInstance(): RlMainBodyFragment {
            val fragment = RlMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        myTool = activity as Tool
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            verticalLayout {
                myList = listView {
                    id = mId
                    overScrollMode = View.OVER_SCROLL_NEVER


                }.lparams(width = matchParent, height = wrapContent) {
                    weight = 1f
                }


                myList.setCacheColorHint(0)
                myList.setSelector(R.color.transparent)

                linearLayout {
                    gravity = Gravity.CENTER
                    backgroundColorResource = R.color.yellowFFB706
                    imageView {
                        imageResource = R.mipmap.add
                    }.lparams(width = dip(20), height = dip(20))
                    textView {
                        textResource = R.string.rlButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                    }

                    this.withTrigger().click { myTool.addVideo(number) }
                }.lparams(width = matchParent, height = dip(47)) {
                    topMargin = dip(10)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(30)
                }
            }
        }.view
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.cancel_interview, null)
        val mmLoading2 = MyDialog(this.mContext!!, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.withTrigger().click { myDialog.dismiss() }
        determineBtn.withTrigger().click { myDialog.dismiss() }
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        mContext = activity
        myList = this.find(mId)

        myDialog.show()
        var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))
        retrofitUils.create(RegisterApi::class.java)
            .getOnlineResume("ATTACHMENT")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                mData = LinkedList()

                number = it.get("total").asInt

                println(it)

                // status 0为正常，１为无效
                if(number > 0){
                    var result = it.get("data").asJsonArray
                    for(i in 0 until number){
                        var name = result[i].asJsonObject.get("name").toString().replace("\"","")
                        var mediaId = result[i].asJsonObject.get("mediaId").toString().replace("\"","")
                        var resumeId = result[i].asJsonObject.get("id").toString().replace("\"","")
                        var mediaUrl = result[i].asJsonObject.get("mediaURL").toString().replace("\"","")

                        var resumeRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.storageUrl))
                        resumeRetrofitUils.create(RegisterApi::class.java)
                            .getInformationByMediaId(mediaId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                var size = FileUtils.GetLength(it.get("size").asLong)
                                var createDate = tool.dateToStrLong(it.get("createdAt").asLong,"yyyy.MM.dd")
                                var mimeType = it.get("mimeType").toString()
                                var type = "word"
                                if(mimeType.indexOf("pdf")!=-1){
                                    type = "pdf"
                                }
                                if(mimeType.indexOf("word")!=-1){
                                    type = "word"
                                }
                                if(mimeType.indexOf("jpg")!=-1){
                                    type = "jpg"
                                }
                                var downloadURL = it.get("downloadURL").toString()

                                mData.add(Resume(R.mipmap.word,resumeId,size,name,type,createDate+"上传",downloadURL,0,mediaId,mediaUrl))

                                resumeAdapter = ResumeAdapter(mData, mContext,myTool)
                                myList.setAdapter(resumeAdapter)

                                myDialog.dismiss()
                            },{
                                if(it is HttpException){
                                    if(it.code() == 404){
                                        var size = "0KB"
                                        var createDate = tool.dateToStrLong(Date().time,"yyyy.MM.dd")
                                        var type = "word"
                                        var downloadURL = ""

                                        mData.add(Resume(R.mipmap.word,resumeId,size,name,type,createDate+"上传",downloadURL,1,mediaId,mediaUrl))

                                        resumeAdapter = ResumeAdapter(mData, mContext,myTool)
                                        myList.setAdapter(resumeAdapter)

                                        myDialog.dismiss()
                                    }
                                }else{
//                                    toast("系统出现问题，无法获取，请稍后重试！！")
                                    println(it)
                                    myDialog.dismiss()
                                }
                            })
                    }
                }else{
                    myDialog.dismiss()
                }
            }, {
                myDialog.dismiss()
                toast("获得简历信息失败！！")
                println(it)
            })

    }

    interface Tool {
        fun addList(resume: Resume)
        fun addVideo(number: Int)
        fun dResume(id:String)
    }

    @SuppressLint("CheckResult")
    fun submitResume(mediaId: String, mediaUrl: String) {
        myDialog.show()
        val mPerferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        val name = mPerferences.getString("name", "")
        var resumeName = name+ this.getString(R.string.rlResumeName) + (number + 1)
        val resumeParams = mapOf(
            "name" to resumeName,
            "isDefault" to true,
            "mediaId" to mediaId,
            "mediaUrl" to mediaUrl,
            "type" to "ATTACHMENT"
        )
        val resumeJson = JSON.toJSONString(resumeParams)
        val resumeBody = RequestBody.create(json, resumeJson)

        var jobRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.jobUrl))
        jobRetrofitUils.create(RegisterApi::class.java)
            .createOnlineResume(resumeBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                myDialog.dismiss()
                println("++++++++++++++")
                println(it)
                toast("创建简历成功！")
                startActivity<ResumeListActivity>()
                activity!!.finish()//返回
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
            },{
                println("------------------")
                println(it)
                myDialog.dismiss()
                toast("创建简历失败！")
            })
    }


}

