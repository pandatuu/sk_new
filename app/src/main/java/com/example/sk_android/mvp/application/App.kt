package com.example.sk_android.mvp.application

import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.listener.message.RecieveMessageListener
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.store.*
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordListFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.google.api.client.util.IOUtils
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import com.pingerx.imagego.core.ImageGo
import com.pingerx.imagego.core.strategy.ImageOptions
import com.pingerx.imagego.glide.GlideImageStrategy
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.yatoooon.screenadaptation.ScreenAdapterTools
import io.github.sac.BasicListener
import io.github.sac.ReconnectStrategy
import io.github.sac.Socket
import org.json.JSONArray
import org.json.JSONObject
import zendesk.suas.AsyncMiddleware
import zendesk.suas.Store
import zendesk.suas.Suas
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class App : MultiDexApplication() {


    val store: Store = Suas
        .createStore(
            getCitiesReducer(),
            getProvincesReducer(),
            getIndustryReducer(),
            getJobWantedReducer(),
            getJobWantedListReducer(),
            getIndustryPageReducer(),
            getInformationReducer(),
            getOnlineReducer(),
            getJobReducer(),
            getProjectReducer(),
            getEduReducer(),
            getJobWantedListPersonalReducer()
        )
        .withMiddleware(
            AsyncMiddleware()
            //           LoggerMiddleware()//,
//            MonitorMiddleware(this)
        )
        .build()


    companion object {
        private var instance: App? = null
        fun getInstance(): App? {
            return instance
        }
    }

    //消息socket创建
    //测试地址 https://im.sk.cgland.top/
    //正式地址 https://im.sk.skjob.jp/
    lateinit var socket: Socket
    private var chatRecord: ChatRecord? = null
    var mRecieveMessageListener: RecieveMessageListener? = null
    private lateinit var mPushAgent: PushAgent

    private lateinit var channelRecieve: Socket.Channel
    private var messageLoginState = false
    private lateinit var deviceToken: String

    private var citySelectFragment: CitySelectFragment? = null
    private var recruitInfoSelectBarMenuPlaceFragment: RecruitInfoSelectBarMenuPlaceFragment? = null
    private var recruitInfoSelectBarMenuCompanyFragment: RecruitInfoSelectBarMenuCompanyFragment? =
        null
    private var recruitInfoActionBarFragment: RecruitInfoActionBarFragment? = null
    private var companyInfoSelectBarMenuFragment: CompanyInfoSelectBarMenuFragment? = null
    private var jlMainBodyFragment: JlMainBodyFragment? = null
    private var industryListFragment: IndustryListFragment? = null
    private var messageChatRecordListFragment: MessageChatRecordListFragment? = null
    private var psActionBarFragment: PsActionBarFragment? = null
    private var resumeEditBackground: ResumeEditBackground? = null
    private var resumeEditBasic: ResumeEditBasic? = null
    private var resumeEditJob: ResumeEditJob? = null
    private var resumeEditProject: ResumeEditProject? = null
    private var resumeEditEdu: ResumeEditEdu? = null

    private val defaultPreferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(this)

    override fun onCreate() {
        super.onCreate()

        //


        initData()

        store.addListener(CitiesData::class.java) {
            CitySelectFragment.cityDataList = it.getCities()

            if (citySelectFragment != null) {
                citySelectFragment?.requestCityAreaInfo(-1)
            }
            citySelectFragment = null

            println("CitiesData changed to ${it.getCities()}")
        }

        store.addListener(ProvincesData::class.java) {
            RecruitInfoSelectBarMenuPlaceFragment.cityDataList = it.getProvinces()
            if (recruitInfoSelectBarMenuPlaceFragment != null) {
                recruitInfoSelectBarMenuPlaceFragment?.requestCityAreaInfo(-1)
            }
            println("ProvincesData changed to ${it.getProvinces()}")
        }

        store.addListener(IndustryData::class.java) {
            RecruitInfoSelectBarMenuCompanyFragment.theIndustry = it.getIndustries()
            CompanyInfoSelectBarMenuFragment.cityDataList = it.getIndustries()


            if (recruitInfoSelectBarMenuCompanyFragment != null) {
                recruitInfoSelectBarMenuCompanyFragment?.requestIndustryData(-1)
            }
            recruitInfoSelectBarMenuCompanyFragment = null


            if (companyInfoSelectBarMenuFragment != null) {
                companyInfoSelectBarMenuFragment?.requestIndustryData(-1)
            }
            companyInfoSelectBarMenuFragment = null

            println("IndustryData changed to ${it.getIndustries()}")
        }

        store.addListener(IndustryPageData::class.java) {
            IndustryListFragment.dataList = it.getIndustries()


            if (industryListFragment != null) {
                industryListFragment?.requestIndustryData(-1)
            }
            industryListFragment = null
            println("IndustryPageData changed to ${it.getIndustries()}")
        }


        store.addListener(JobWantedData::class.java) {
            RecruitInfoActionBarFragment.jobWanted = it.getJobWanteds()
            if (recruitInfoActionBarFragment != null) {
                recruitInfoActionBarFragment?.getJobWantedInfo(-1)
            }
            recruitInfoActionBarFragment = null
            println("JobWantedData changed to ${it.getJobWanteds()}")
        }

        store.addListener(JobWantedListData::class.java) {

            JlMainBodyFragment.myResult = it.getJobWantedList()
            JlMainBodyFragment.totalNum = it.getJobWantedList().size

            if (jlMainBodyFragment != null) {
                jlMainBodyFragment?.initView(-1)
            }
            jlMainBodyFragment = null
            println("JobWantedListData changed to ${it.getJobWantedList()}")
        }

        store.addListener(InformationData::class.java) {

            PsActionBarFragment.myResult=it.getInformation()
            ResumeEditBasic.myResult=it.getInformation()

            if (psActionBarFragment != null) {
                psActionBarFragment?.initView(-1)
            }
            if (resumeEditBasic != null) {
                resumeEditBasic?.initView(-1)
            }

            psActionBarFragment=null
            resumeEditBasic=null
            println("XXXXXXXPersonInformation changed to ${it.getInformation()}")
        }

        //在线简历
        store.addListener(OnlineData::class.java) {

            ResumeEditBackground.myResult=it.getOnline()
            if (resumeEditBackground != null) {
                resumeEditBackground?.initView(-1)
            }
            resumeEditBackground=null
            println("resume_online changed to ${it.getOnline()}")
        }

        store.addListener(JobData::class.java) {

            ResumeEditJob.myResult=it.getJob()
            if (resumeEditJob != null) {
                resumeEditJob?.initView(-1)
            }
            resumeEditJob=null
            println("XXXXXXXPersonInformation changed to ${it.getJob()}")
        }

        store.addListener(ProjectData::class.java) {

            ResumeEditProject.myResult=it.getProject()
            if (resumeEditProject != null) {
                resumeEditProject?.initView(-1)
            }
            resumeEditProject=null
            println("XXXXXXXPersonInformation changed to ${it.getProject()}")
        }

        store.addListener(EduData::class.java) {

            ResumeEditEdu.myResult=it.getEdu()
            if (resumeEditEdu != null) {
                resumeEditEdu?.initView(-1)
            }
            resumeEditEdu=null
            println("XXXXXXXPersonInformation changed to ${it.getEdu()}")
        }

        store.addListener(JobWantedListPersonalData::class.java) {


            println("XXXXXXXJobWantedListDataPersonal changed to ${it.getJobWantedListPersonal()}")
        }



        ImageGo.setDebug(true)   // 开发模式
            .setStrategy(GlideImageStrategy())  // 图片加载策略
            .setDefaultBuilder(ImageOptions.Builder())  // 图片加载配置属性，可使用默认属性

        instance = this

        println("版本!")
        println(Build.VERSION.SDK_INT)



        initMessage()

        ScreenAdapterTools.init(this)

        //注册消息推送
        UMConfigure.init(
            this,
            "5cdcc324570df3ffc60009c3",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            "5a9ab9f2665729e47028fa713d560668"
        )
        mPushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken1: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("MessageChatRecordAc", "注册成功：deviceToken：-------->  $deviceToken1")
                deviceToken = deviceToken1
            }

            override fun onFailure(s: String, s1: String) {
                Log.e("MessageChatRecordAct", "注册失败：-------->  s:$s,s1:$s1")
                // toast("-----------------------------")
                deviceToken = ""
            }
        })

    }

    override fun onTerminate() {
        // 程序终止的时候执行
        Log.d("onTerminate", "onTrimMemory")
        super.onTerminate()
    }

    override fun onLowMemory() {
        // 低内存的时候执行
        Log.d("onLowMemory", "onTrimMemory")
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        // 程序在内存清理的时候执行
        Log.d("onTrimMemory", "onTrimMemory")
        super.onTrimMemory(level)
    }


    fun initMessage() {
        socket = Socket("${getString(R.string.imUrl)}sk/")

        println("初始化消息系统")

        val token = getMyToken()
        println("token:$token")


        if (socket.isconnected()) {
            println("初始化消息系统")
            socket.disconnect()
        }
        socket.setListener(object : BasicListener {
            override fun onConnected(socket: Socket, headers: Map<String, List<String>>) {
                println(socket.currentState)

                val obj = JSONObject("{\"token\":\"$token\"}")
                socket.emit("login", obj) { eventName, error, data ->
                    //If error and data is String
                    messageLoginState = error == null
                    println("Got message for :$eventName error is :$error data is :$data")
                    //订阅通道
                    val uId = getMyId()

                    println("用户id:$uId")
                    println("用户id:$token")

                    if (uId.isBlank()) {
//                        val toast = Toast.makeText(applicationContext, "ID取得失敗", Toast.LENGTH_SHORT)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()
                    }

                    channelRecieve = socket.createChannel("p_${uId.replace("\"", "")}")
                    channelRecieve.subscribe { channelName, error, _ ->
                        if (error == null) {
                            println("Subscribed to channel $channelName successfully")
                        } else {

                        }
                    }

                    //接受
                    channelRecieve.onMessage { _, obj ->
                        println("app收到消息内容")
                        println(obj)
                        println("app收到消息")

                        val json = JSON.parseObject(obj.toString())
                        val type = json.getString("type")
                        try {
                            if (type != null && type == "contactList") {
                                println("准备发送contactList")
                                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx================xxx")
                                println((chatRecord == null).toString())
                                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx================xxx")
                                getContactList(obj.toString())
                                chatRecord?.getContactList(obj.toString())
                                println("发送contactList完毕")
                            } else if (type != null && type == "setStatus") {


                            } else if (type != null && type == "historyMsg") {
                                mRecieveMessageListener?.getHistoryMessage(obj.toString())
                            } else {
                                mRecieveMessageListener?.getNormalMessage(obj.toString())
                                socket.emit("queryContactList", token)

                            }
                        } catch (e: UninitializedPropertyAccessException) {
                            //e.printStackTrace()
                            println("请求联系人列表")
                            chatRecord?.requestContactList()

                        }
                    }
                    socket.emit("queryContactList", token)
                }
            }

            override fun onDisconnected(
                socket: Socket,
                serverCloseFrame: WebSocketFrame,
                clientCloseFrame: WebSocketFrame,
                closedByServer: Boolean
            ) {
                Log.i("Success ", "Disconnected from end-point")
                println("onDisconnectedonDisconnectedonDisconnected")
//                toast("onDisconnectedonDisconnectedonDisconnected")

            }

            override fun onConnectError(socket: Socket, exception: WebSocketException) {
                Log.i("Success ", "Got connect error $exception")

//                出现这种情况的原因有很多，其中包括：
//
//                颁发服务器证书的 CA 未知
//                服务器证书不是 CA 签名的，而是自签名的
//                服务器配置缺少中间 CA
//

//                toast("onConnectErroronConnectErroronConnectError")
                println("可能没网onConnectErroronConnectErroronConnectError")

            }

            override fun onSetAuthToken(token: String, socket: Socket) {
                socket.setAuthToken(token)
                print("")

            }

            override fun onAuthentication(socket: Socket, status: Boolean?) {
                if (status!!) {
                    Log.i("Success ", "socket is authenticated")
                } else {
                    Log.i("Success ", "Authentication is required (optional)")
                }

                print("")

            }

        })

        // socket.connect()
        socket.setReconnection(ReconnectStrategy().setMaxAttempts(10).setDelay(3000))
        socket.connectAsync()


    }

    infix fun setChatRecord(chat: ChatRecord) {
        chatRecord = chat
    }


    //解析联系人列表数据
    fun getContactList(s: String) {

        var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
        var groupArray: JSONArray = JSONArray()
        var map: MutableMap<String, Int> = mutableMapOf()
        var json: JSONObject = JSONObject(s)
        var isFirstGotGroup = true


        var array: JSONArray =
            json.getJSONObject("content").getJSONArray("groups")

        var members: JSONArray = JSONArray()
        if (isFirstGotGroup) {
            groupArray = JSONArray()
        }
        for (i in 0..array.length() - 1) {
            var item = array.getJSONObject(i)
            var id = item.getInt("id")
            var name = item.getString("name")
            if (name == "全部") {
                name = "全て"
            }
            if (name != null && !name.equals("約束済み")) {
                map.put(name, id.toInt())
            }

            if (id == MessageChatRecordActivity.groupId) {
                println("现在groupId")

                members = item.getJSONArray("members")
            }

            if (isFirstGotGroup) {
                if (id == 4) {
                    var group1 = item.getJSONArray("members")
                    groupArray.put(group1)
                }
                if (id == 5) {
                    var group2 = item.getJSONArray("members")
                    groupArray.put(group2)
                }
                if (id == 6) {
                    var group3 = item.getJSONArray("members")
                    groupArray.put(group3)
                }


            }
        }
        isFirstGotGroup = true
        chatRecordList = mutableListOf()
        for (i in 0..members.length() - 1) {
            var item = members.getJSONObject(i)
            println(item)
            //未读条数
            var unreads = item.getInt("unreads").toString()
            //对方名
            var name = item["name"].toString()
            //最后一条消息
            var lastMsg: JSONObject? = null
            if (item.has("lastMsg") && !item.getString("lastMsg").equals("") && !item.getString(
                    "lastMsg"
                ).equals(
                    "null"
                )
            ) {
                lastMsg = (item.getJSONObject("lastMsg"))
            }

            var msg = ""
            //对方ID
            var uid = item["uid"].toString()
            //对方职位
            var position = item["position"].toString()
            //对方头像
            var avatar = item["avatar"].toString()
            if (avatar != null) {
                var arra = avatar.split(";")
                if (arra != null && arra.size > 0) {
                    avatar = arra[0]
                }
            }

            //公司
            var companyName = item["companyName"].toString()
            // 显示的职位的id
            var lastPositionId = item.getString("lastPositionId")
            if (lastPositionId == null) {
                println("联系人信息中没有lastPositionId")
                lastPositionId = ""
            }

            if (lastMsg == null) {
            } else {
                var content = lastMsg.getJSONObject("content")
                var contentType = content.getString("type")
                if (contentType.equals("image")) {
                    msg = "[图片]"
                } else if (contentType.equals("voice")) {
                    msg = "[语音]"
                } else {
                    msg = content.getString("msg")
                }
            }
            var ChatRecordModel = ChatRecordModel(
                uid,
                name,
                position,
                avatar,
                msg,
                unreads,
                companyName,
                lastPositionId
            )
            chatRecordList.add(ChatRecordModel)
        }


        MessageChatRecordActivity.chatRecordList = chatRecordList
        MessageChatRecordActivity.groupArray = groupArray
        MessageChatRecordActivity.map = map
        MessageChatRecordActivity.json = json


        MessageChatRecordListFragment.thisGroupArray = groupArray

        if (messageChatRecordListFragment != null) {
            messageChatRecordListFragment?.setRecyclerAdapter(
                chatRecordList,
                groupArray
            )
        }
    }

    fun setRecieveMessageListener(listener: RecieveMessageListener) {
        mRecieveMessageListener = listener
    }

    fun getPushAgent(): PushAgent {
        return mPushAgent
    }

    fun getDeviceToken(): String {
        return deviceToken
    }

    fun getMyToken(): String {
        val token = defaultPreferences.getString("token", "") ?: ""
        println("--------------------------------------------------------")
        println("token->" + "Bearer ${token.replace("\"", "")}")

        if (token.isEmpty()) {
//            Thread(Runnable {
//                sleep(200)
//                if(count>15){
//                }else{
//                    getMyToken()
//                    count++
//                }
//            }).start()
        }

        return token.replace("\"", "")
    }

    fun getMyId(): String {
        return defaultPreferences.getString("id", "") ?: ""
    }


    fun getMyLogoUrl(): String {
        var avatarURL = defaultPreferences.getString("avatarURL", "") ?: ""
        val arra = avatarURL.split(",")
        if (arra.isNotEmpty()) {
            avatarURL = arra[0]
        }
        return avatarURL
    }


    fun getMessageLoginState(): Boolean {
        return messageLoginState
    }


    fun setCitySelectFragment(con: CitySelectFragment?) {
        citySelectFragment = con
    }


    fun setRecruitInfoSelectBarMenuPlaceFragment(con: RecruitInfoSelectBarMenuPlaceFragment?) {
        recruitInfoSelectBarMenuPlaceFragment = con
    }


    fun setRecruitInfoSelectBarMenuCompanyFragment(con: RecruitInfoSelectBarMenuCompanyFragment?) {
        recruitInfoSelectBarMenuCompanyFragment = con
    }


    fun setCompanyInfoSelectBarMenuFragment(con: CompanyInfoSelectBarMenuFragment?) {
        companyInfoSelectBarMenuFragment = con
    }


    fun setRecruitInfoActionBarFragment(con: RecruitInfoActionBarFragment?) {
        recruitInfoActionBarFragment = con
    }

    fun setJlMainBodyFragment(con: JlMainBodyFragment?) {
        jlMainBodyFragment = con
    }

    fun setIndustryListFragment(con: IndustryListFragment?) {
        industryListFragment = con
    }

    fun setMessageChatRecordListFragment(con: MessageChatRecordListFragment?) {
        messageChatRecordListFragment = con
    }

    fun setPsActionBarFragment(con:PsActionBarFragment?){
        psActionBarFragment=con
    }

    fun setResumeEditBackground(con:ResumeEditBackground?){
        resumeEditBackground=con
    }
    fun setResumeEditBasic(con:ResumeEditBasic?){
        resumeEditBasic=con
    }
    fun setResumeEditJob(con:ResumeEditJob?){
        resumeEditJob=con
    }
    fun setResumeEditProject(con:ResumeEditProject){
        resumeEditProject=con
    }
    fun setResumeEditEdu(con:ResumeEditEdu?){
        resumeEditEdu=con
    }

    fun initData(){

        if (getMyToken() == "") {
            println("暂时没有 token")
        }else{
            val searchCitiesAction = AsyncMiddleware.create(FetchCityAsyncAction(this))
            val searchIndustiesAction = AsyncMiddleware.create(FetchIndustryAsyncAction(this))
            val fetchJobWantedAsyncAction =
                AsyncMiddleware.create(FetchJobWantedAsyncAction(this))

            val fetchInformationAsyncAction = AsyncMiddleware.create(FetchInformationAsyncAction(this))
            val fetchEditOnlineAsyncAction = AsyncMiddleware.create(FetchEditOnlineAsyncAction(this))

            store.dispatch(searchCitiesAction)
            store.dispatch(searchIndustiesAction)
            store.dispatch(fetchJobWantedAsyncAction)
            store.dispatch(fetchInformationAsyncAction)
            store.dispatch(fetchEditOnlineAsyncAction)
        }

    }

    //2.3以下的版本
    fun certificate() {
        // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
        val cf = CertificateFactory.getInstance("X.509")
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
        val caInput = BufferedInputStream(FileInputStream("load-der.crt"))
        val ca: Certificate
        try {
            ca = cf.generateCertificate(caInput)
            System.out.println("ca=" + (ca as X509Certificate).subjectDN)
        } finally {
            caInput.close()
        }

// Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

// Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

// Create an SSLContext that uses our TrustManager
        val context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)

// Tell the URLConnection to use a SocketFactory from our SSLContext
        val url = URL("https://certs.cac.washington.edu/CAtest/")
        val urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.sslSocketFactory = context.socketFactory
        val `in` = urlConnection.inputStream
        //copyInputStreamToOutputStream(`in`, System.out)
        IOUtils.copy(`in`, System.out)
    }

}