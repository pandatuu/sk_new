package com.example.sk_android.service


import io.github.sac.Socket
import android.system.Os.socket
import io.github.sac.Ack
import io.github.sac.Emitter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import io.github.sac.BasicListener
import android.system.Os.socket
import android.R.id.message
import android.system.Os.socket












class Socketcluster{

    companion object {
        lateinit var socket:Socket

        var channel:Socket.Channel? = null
        var socketclusterAndActivityBridge:SocketclusterAndActivityBridge?=null
        fun newInstance(
             url: String,
             channelName:String,
             socketclusterAndActivityBridge:SocketclusterAndActivityBridge
        )  : Socketcluster {
            this.socketclusterAndActivityBridge=socketclusterAndActivityBridge
            socket = Socket("http://192.168.2.159:8000/pmc/")









//
//            channel=socket!!.createChannel(channelName)
//
//
//
//
//
//            channel!!.subscribe();
//
//            receiveMessage()
            return Socketcluster()
        }


        fun receiveMessage(){

            if(channel!=null)
             channel!!.onMessage(Emitter.Listener { channelName, data -> socketclusterAndActivityBridge!!.getMessage(data.toString()) })

        }


        fun sendMessage(){

            var socket = Socket("http://192.168.2.159:8000/pmc/")
            if(socket!=null){
                socket.connect();


                socket.setListener(object : BasicListener {

                    override fun onConnected(socket: Socket, headers: Map<String, List<String>>) {
                        println("Connected to endpoint")
                    }

                    override fun onDisconnected(
                        socket: Socket,
                        serverCloseFrame: WebSocketFrame,
                        clientCloseFrame: WebSocketFrame,
                        closedByServer: Boolean
                    ) {
                        println("Disconnected from end-point")
                    }

                    override fun onConnectError(socket: Socket, exception: WebSocketException) {
                        println("Got connect error $exception")
                    }

                    override fun onSetAuthToken(token: String, socket: Socket) {
                        println("Token is $token")
                    }

                    override fun onAuthentication(socket: Socket, status: Boolean?) {
                        if (status!!) {
                            println("socket is authenticated")
                        } else {
                            println("Authentication is required (optional)")
                        }
                    }

                })


                socket.emit("verifyJWT",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlYjBkNDRlNS0yNGEzLTRhMWMtOTU2MC1hMmY3M2VhMzE4ODAiLCJ1c2VybmFtZSI6ImRhd2VueGkiLCJ0aW1lc3RhbXAiOjE1NTcxMzcyMDM0MjMsImRldmljZVR5cGUiOiJXRUIiLCJpYXQiOjE1NTcxMzcyMDN9.Y62t8920zrdidBO1STFtYhSK1aS2mQNXd5wR4weCJbSQHEtbZ8GVF_8T-uwccD1C_qq1mOIc2JiXS3uH1qQYu9teg6IOkHtHooSzUUC2hxmtncagxuL7ad7zSeopeM3MIX_mNhSMKcR8JDiYixx5tdC_jfZHhX-J_P2Q6TQlrocu5LB4SFwAwYPaewW-OTeTdMIblGY3B121yea3erYlKF9lKZGHnlC691mv7nEOc3ohgHMZ2dunKxlr-IoX2w1J9GDYeRd5_MrZWYumLM8UalRty9TmLVRw5ElzcZj8v6QtG8I-FJcWEafpTiobPTTRCWP5ni8hSo6wMHev94zZKA", Ack { eventName, error, data ->
                        //If error and data is String
                        println("Got message for :$eventName error is :$error data is :$data")
                    })








//
//            if(channel!=null)
//            channel!!.publish("Hi sachin", Ack { channelName, error, data ->
//                if (error == null) {
//                    println("Published message to channel $channelName successfully")
//                }
//            })
            }

        }

        fun closeChannel(){
            if(channel!=null)
            channel!!.unsubscribe(Ack { name, error, data -> println("Unsubscribed successfully") })
        }
    }


    interface SocketclusterAndActivityBridge{
        fun getMessage(str:String)

    }




}