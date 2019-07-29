package com.example.sk_android.mvp.application

import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.preference.PreferenceManager
import android.support.multidex.MultiDexApplication
import android.util.Log
import anet.channel.util.Utils.context
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.listener.message.RecieveMessageListener
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
import io.github.sac.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.URL
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

class App : MultiDexApplication() {

    companion object {
        private var instance: App? = null
        fun getInstance(): App? {
            return instance
        }
    }


    private var socket = Socket("https://im.sk.cgland.top/sk/")
    private var chatRecord: ChatRecord? = null
    private lateinit var mRecieveMessageListener: RecieveMessageListener
    private lateinit var mPushAgent: PushAgent

    private lateinit var channelRecieve: Socket.Channel
    private var thisContext = this
    private var messageLoginState = false
    private lateinit var deviceToken:String

    override fun onCreate() {
        super.onCreate()


        ImageGo.setDebug(true)   // 开发模式
            .setStrategy(GlideImageStrategy())  // 图片加载策略
            .setDefaultBuilder(ImageOptions.Builder())  // 图片加载配置属性，可使用默认属性

        instance = this;

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
        );
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

        println("初始化消息系统")

        var token = getMyToken()
        println("token:" + token)


        if (socket.isconnected()) {
            println("初始化消息系统")
            socket.disconnect()
        }
        socket.setListener(object : BasicListener {
            override fun onConnected(socket: Socket, headers: Map<String, List<String>>) {
                println(socket.currentState)

                val obj = JSONObject("{\"token\":\"" + token + "" + "\"}")
                socket.emit("login", obj) { eventName, error, data ->
                    //If error and data is String
                    if (error != null) {

                        messageLoginState = false

                    } else {
                        messageLoginState = true
                    }
                    println("Got message for :$eventName error is :$error data is :$data")
                    //订阅通道
                    var uId = getMyId()

                    println("用户id:" + uId)
                    println("用户id:" + token)
                    channelRecieve = socket.createChannel("p_${uId.replace("\"", "")}")
                    channelRecieve.subscribe { channelName, error, data ->
                        if (error == null) {
                            println("Subscribed to channel $channelName successfully")
                        } else {

                        }
                    }

                    //接受
                    channelRecieve.onMessage(object : Emitter.Listener {
                        override fun call(channelName: String, obj: Any) {
                            println("app收到消息内容")
                            println(obj)
                            println("app收到消息")

                            var json = JSON.parseObject(obj.toString())
                            var type = json.getString("type")
                            try {
                                if (type != null && type.equals("contactList")) {
                                    println("准备发送contactList")
                                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx================xxx")
                                    println((chatRecord == null).toString())
                                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx================xxx")

                                    chatRecord?.getContactList(obj.toString())
                                    println("发送contactList完毕")
                                } else if (type != null && type.equals("setStatus")) {


                                } else if (type != null && type.equals("historyMsg")) {
                                    if (mRecieveMessageListener != null) {
                                        mRecieveMessageListener.getHistoryMessage(obj.toString())
                                    }
                                } else {
                                    if (mRecieveMessageListener != null) {
                                        mRecieveMessageListener.getNormalMessage(obj.toString())
                                        socket.emit("queryContactList", token)

                                    }
                                }
                            } catch (e: UninitializedPropertyAccessException) {

                                println("请求联系人列表")
                                chatRecord?.requestContactList()

                            }
                        }
                    })
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
                toast("onDisconnectedonDisconnectedonDisconnected")

            }

            override fun onConnectError(socket: Socket, exception: WebSocketException) {
                Log.i("Success ", "Got connect error $exception")

//                出现这种情况的原因有很多，其中包括：
//
//                颁发服务器证书的 CA 未知
//                服务器证书不是 CA 签名的，而是自签名的
//                服务器配置缺少中间 CA
//

                toast("onConnectErroronConnectErroronConnectError")
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
        socket.connectAsync();

    }

    infix fun setChatRecord(chat: ChatRecord) {
        chatRecord = chat
    }

    fun setRecieveMessageListener(listener: RecieveMessageListener) {
        mRecieveMessageListener = listener
    }

    fun getSocket(): Socket {
        return socket
    }

    fun getPushAgent(): PushAgent{
        return mPushAgent
    }
    fun getDeviceToken(): String{
        return deviceToken
    }
    fun getMyToken(): String {

        var count = 0
        var token = PreferenceManager.getDefaultSharedPreferences(thisContext).getString("token", "").toString()
        println("--------------------------------------------------------")
        println("token->" + "Bearer ${token.replace("\"", "")}")

        if (token == null || token.equals("")) {
//            Thread(Runnable {
//                sleep(200)
//                if(count>15){
//                }else{
//                    getMyToken()
//                    count++
//                }
//            }).start()
        }



        return "${token.replace("\"", "")}"
    }

    fun getMyId(): String {
        var id = PreferenceManager.getDefaultSharedPreferences(thisContext).getString("id", "").toString()
        return id
    }


    fun getMyLogoUrl(): String {
        var avatarURL = PreferenceManager.getDefaultSharedPreferences(thisContext).getString("avatarURL", "").toString()
        if (avatarURL != null) {
            var arra = avatarURL.split(",")
            if (arra != null && arra.size > 0) {
                avatarURL = arra[0]
            }
        }
        return avatarURL
    }


    fun getMessageLoginState(): Boolean {
        return messageLoginState
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
            System.out.println("ca=" + (ca as X509Certificate).getSubjectDN())
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
        context.init(null, tmf.getTrustManagers(), null)

// Tell the URLConnection to use a SocketFactory from our SSLContext
        val url = URL("https://certs.cac.washington.edu/CAtest/")
        val urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.setSSLSocketFactory(context.getSocketFactory())
        val `in` = urlConnection.getInputStream()
        //copyInputStreamToOutputStream(`in`, System.out)
        IOUtils.copy(`in`, System.out)
    }

}