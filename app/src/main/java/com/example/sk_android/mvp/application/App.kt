package com.example.sk_android.mvp.application

import android.R
import android.app.Application
import android.util.Log
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.yatoooon.screenadaptation.ScreenAdapterTools
import io.github.sac.BasicListener
import io.github.sac.Emitter
import io.github.sac.ReconnectStrategy
import io.github.sac.Socket
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.toast
import org.json.JSONObject

class App : Application() {


    companion object {
        private  var  instance: App? = null
        fun getInstance(): App? {
            return instance
        }
    }


    private var socket = Socket("https://im.sk.cgland.top/sk/")
    var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1ODlkYWE4Yi03OWJkLTRjYWUtYmY2Ny03NjVlNmU3ODZhNzIiLCJ1c2VybmFtZSI6Ijg2MTU4ODIzMzUwMDciLCJ0aW1lc3RhbXAiOjE1NTkyMDA1MjM5MzgsImRldmljZVR5cGUiOiJXRUIiLCJpYXQiOjE1NTkyMDA1MjN9.4FLkAZr8vlYkHLmHvzcTt2chWNX5aXt93PE9GNfEsKKCEfgJET7ceoBN6XRkDlbUTuIgCf5pKqJmxbvvqiC3nSYpYnY5liZ7V0bnra-ZOBHDsK5_tmsJdNHERQn23y3mGMp6hAiAJHso2JMp53nMPsNYv7A4e3xomFHZ8Fue_5KBCjjmgsd-T3Rxk0PhvxEhVMeTHDPIHMIx8TpoPVA0t_N8UYJsT46JLLmzZvHII8VMnWjx0IVwn7tIVCO08r-pRqwVLTuwoPmgphsCBOT__KZpCJYy2NMRnyIPlzcROj87WU0dKb-NUQf0jJGt5ZZl5c0v7RGey4cxhwthwFPEWA"
    private  var chatRecord: ChatRecord?=null

    override fun onCreate() {
        super.onCreate()
        instance = this;
        initMessage()

        ScreenAdapterTools.init(this)

        //注册消息推送
        UMConfigure.init(this, "5cdcc324570df3ffc60009c3", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "5a9ab9f2665729e47028fa713d560668");
        val mPushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken1: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("MessageChatRecordAc", "注册成功：deviceToken：-------->  $deviceToken1")

            }

            override fun onFailure(s: String, s1: String) {
                Log.e("MessageChatRecordAct", "注册失败：-------->  s:$s,s1:$s1")
                // toast("-----------------------------")
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


    fun initMessage(){
        socket.setListener(object : BasicListener {
            override  fun onConnected(socket: Socket, headers: Map<String, List<String>>) {
                println(socket.currentState)

                val obj = JSONObject("{\"token\":\""+token+"\"}")
                socket.emit("login",obj ) { eventName, error, data ->
                    //If error and data is String
                    println("Got message for :$eventName error is :$error data is :$data")



                    //订阅通道
                    val channel = socket.createChannel("p_589daa8b-79bd-4cae-bf67-765e6e786a72")
                    channel.subscribe { channelName, error, data ->
                        if (error == null) {
                            println("Subscribed to channel $channelName successfully")
                        }
                    }

                    val contact = JSONObject("{\"contact_id\":\""+"e42c10f3-f005-403d-81d6-bac73edc6673"+"\"}")
                    //socket.emit("addContact",contact)

                    //接受
//                    channel.onMessage(object : Emitter.Listener {
//                        override fun call(channelName: String, obj: Any) {
//                            println("------------------------------->>>>Got message for channel $channelName data is ${R.attr.data}")
//                            chatRecord!!.queryContactList(obj.toString())
//                        }
//                    })

                }


            }

            override fun onDisconnected(
                    socket: Socket,
                    serverCloseFrame: WebSocketFrame,
                    clientCloseFrame: WebSocketFrame,
                    closedByServer: Boolean
            ) {
                Log.i("Success ", "Disconnected from end-point")
                print("")

            }

            override fun onConnectError(socket: Socket, exception: WebSocketException) {
                Log.i("Success ", "Got connect error $exception")
                print("")

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

    fun setChatRecord(chat:ChatRecord){
        chatRecord=chat
    }

    fun getSocket():Socket{
        return socket
    }

    fun sendRequest(str:String){
        //请求
        println("")
        socket.emit(str,token)
        println("")

    }




}