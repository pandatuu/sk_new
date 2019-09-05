package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.view.fragment.common.BottomMenuFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeBackgroundFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeEditJob
import com.example.sk_android.mvp.view.fragment.person.JobListFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PsMainBodyFragment
import com.example.sk_android.utils.IPermissionResult
import com.example.sk_android.utils.PermissionConsts
import com.example.sk_android.utils.PermissionManager
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.github.sac.Socket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent
import retrofit2.adapter.rxjava2.HttpException
import java.util.ArrayList

class PersonSetActivity : AppCompatActivity(), PsMainBodyFragment.JobWanted, JobListFragment.CancelTool,
    ShadowFragment.ShadowClick,
    BottomMenuFragment.RecruitInfoBottomMenu,
    BottomSelectDialogFragment.BottomSelectDialogSelect {

    lateinit var baseFragment: FrameLayout
    private var wsBackgroundFragment: ResumeBackgroundFragment? = null
    private var wsListFragment: JobListFragment? = null
    var mTransaction: FragmentTransaction? = null
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    lateinit var psMainBodyFragment:PsMainBodyFragment

    private val statusList: MutableList<String> = mutableListOf()

    private var psActionBarFragment: PsActionBarFragment? = null
    var application: App? = null
    var socket: Socket? = null
    private val groupArray: JSONArray = JSONArray()
    var isFirstGotGroup: Boolean = true
    val map: MutableMap<String, Int> = mutableMapOf()
    var chatRecordList: MutableList<String> = mutableListOf()

    private val REQUEST_CODE = 101



    companion object {
        lateinit var json: JSONObject
    }



    private val Listhandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            println("+++++++++++++++++++++++")
            println(json)
            println("+++++++++++++++++++++++")
            val type = json.getString("type")
            if (type != null && type.equals("contactList")) {
                val array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                var members: JSONArray = JSONArray()
                for (i in array) {
                    val item = (i as JSONObject)
                    val id = item.getIntValue("id")
                    val name = item.getString("name")
                    if (id == 0) {
                        members = item.getJSONArray("members")
                    }

                }
                isFirstGotGroup=false
                chatRecordList = mutableListOf()

                for (i in members) {
                    val item = (i as JSONObject)
                    println(item)
                    // 显示的职位的id
                    val lastPositionId = item.getString("lastPositionId")
                    if(lastPositionId!=null && !"".equals(lastPositionId)){
                        chatRecordList.add(lastPositionId)
                    }
                }

            }

            val oneNumber = chatRecordList.size
            psMainBodyFragment.initOne(oneNumber.toString())

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionManager.init().checkPermissions(this,  REQUEST_CODE, object : IPermissionResult {
            override fun getPermissionFailed(
                activity: Activity?,
                requestCode: Int,
                deniedPermissions: Array<out String>?
            ) {
                //获取权限失败
            }

            override fun getPermissionSuccess(activity: Activity, requestCode: Int) {
                //获取权限成功
            }
        }, PermissionConsts.STORAGE)


        //接受
        application = App.getInstance()
        socket = application?.socket

        //消息回调
//        application!!.setChatRecord(object : ChatRecord {
//            override fun requestContactList() {
//                socket.emit("queryContactList", application!!.getMyToken())
//            }
//
//            override fun getContactList(str: String) {
//                json = JSON.parseObject(str)
//                val message = Message()
//                Listhandler.sendMessage(message)
//            }
//        })


        statusList.add(this.getString(R.string.IiStatusOne))
        statusList.add(this.getString(R.string.IiStatusTwo))
        statusList.add(this.getString(R.string.IiStatusThree))
        statusList.add(this.getString(R.string.IiStatusFour))


        val mainScreenId = 1

        PushAgent.getInstance(this).onAppStart()

        baseFragment = frameLayout {

            id = mainScreenId
            verticalLayout {
                //ActionBar
                val actionBarId = 2
                frameLayout {
                    id = actionBarId
                    psActionBarFragment = PsActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().add(id, psActionBarFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                val newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    psMainBodyFragment = PsMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id, psMainBodyFragment).commit()
                }.lparams(width = matchParent, height = 0){
                    weight = 1f
                }

                //menu
                val bottomMenuId=5
                frameLayout {
                    id=bottomMenuId
                    val recruitInfoBottomMenuFragment= BottomMenuFragment.newInstance(3,false)
                    supportFragmentManager.beginTransaction().replace(id,recruitInfoBottomMenuFragment!!).commit()

                    recruitInfoBottomMenuFragment.Listhandler=Listhandler
                }.lparams {
                    height=wrapContent
                    width= matchParent
                }


            }.lparams {
                width = matchParent
                height = matchParent
            }

        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(psActionBarFragment!!.toolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonSetActivity, 0, psActionBarFragment!!.toolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        isFirstGotGroup=true
        groupArray.clear()

        Handler().postDelayed({
            socket?.emit("queryContactList", application!!.getMyToken())
        }, 200)
    }

    override fun onResume() {
        super.onResume()
        initData()
        //消息回调
//        application!!.setChatRecord(object : ChatRecord {
//            override fun requestContactList() {
//                socket.emit("queryContactList", application!!.getMyToken())
//
//            }
//
//            override fun getContactList(str: String) {
//                json = JSON.parseObject(str)
//                val message = Message()
//                Listhandler.sendMessage(message)
//            }
//        })
    }

    override fun jobItem() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        editAlertDialog = BottomSelectDialogFragment.newInstance(this.getString(R.string.jobSearchStatus), statusList)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
    }

    override fun cancelList() {
        mTransaction = supportFragmentManager.beginTransaction()
        if (wsListFragment != null) {
            mTransaction!!.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction!!.remove(wsListFragment!!)
            wsListFragment = null
        }
        if (wsBackgroundFragment != null) {
            mTransaction!!.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction!!.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null
        }
        mTransaction!!.commit()
        mTransaction = null
    }

    //关闭弹窗
    fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }


    @SuppressLint("CheckResult")
    private fun initData() {
        val personMap = mapOf<String, String>()
        val workStatu = ""
        val retrofitUils = RetrofitUtils(this, this.getString(R.string.userUrl))
//        retrofitUils.create(PersonApi::class.java)
//            .information
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//            .subscribe({
//                val imageUrl: String
//                val name: String
//                val gender: String
//
//                val statu = it.get("auditState").toString().replace("\"","")
//                if(statu.equals("PENDING")){
//                    imageUrl = it.get("changedContent").asJsonObject.get("avatarURL").toString().replace("\"","").split(";")[0]
//                    name = it.get("changedContent").asJsonObject.get("displayName").toString().replace("\"","")
//                    gender = it.get("changedContent").asJsonObject.get("gender").toString().replace("\"","")
//                }else{
//                    imageUrl = it.get("avatarURL").toString().replace("\"","").split(";")[0]
//                    name = it.get("displayName").toString().replace("\"", "")
//                    gender =  it.get("gender").toString().replace("\"", "")
//                }
//
//                // 测试图片  "https://sk-user-head.s3.ap-northeast-1.amazonaws.com/19d14846-a932-43ed-b04b-88245846c587"
//                psActionBarFragment!!.changePage(imageUrl, name,gender)
//            }, {
//                println("123456")
//                println(it)
//                if(it is HttpException){
//                    if(it.code() == 401){
//
//                    }
//                }
//            })

        retrofitUils.create(PersonApi::class.java)
            .jobStatu
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initStatu(it)
            }, {

            })

        val jobRetrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))
        jobRetrofitUils.create(PersonApi::class.java)
            .getPersonFavorites("ORGANIZATION_POSITION")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println(it)
                psMainBodyFragment.initFour(it.get("total").toString())
            }, {

            })

        val interRetrofitUtils = RetrofitUtils(this,this.getString(R.string.interUrl))
        interRetrofitUtils.create(PersonApi::class.java)
            .getPersonInformation(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initTwo(it.get("total").toString())
            }, {

            })

        val interViewRetrofitUtils = RetrofitUtils(this,this.getString(R.string.interUrl))
        interViewRetrofitUtils.create(PersonApi::class.java)
            .getExchangedInformation("EXCHANGED")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var i = 0
                val data= it.get("data").asJsonArray
                for (item in data){
                    if("RESUME" == item.asJsonObject.get("type").asString){
                        i++
                    }
                }
                psMainBodyFragment.initThree(i.toString())
            }, {
                println("1111")
            })



    }

    override fun shadowClicked() {
        closeAlertDialog()
    }

    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    override fun getback(index: Int, list: MutableList<String>) {
        println(list)
        println(list[index])

        psMainBodyFragment.setData(list[index])
        closeAlertDialog()
    }

    override fun getSelectedMenu() {
    }

    // 权限请求，重写方法 onRequestPermissionsResult方法
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println(requestCode)
        println(resultCode)

        if(resultCode == 102){
            var result = 2
            psActionBarFragment = PsActionBarFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(result, psActionBarFragment!!).commit()
        }

    }


}