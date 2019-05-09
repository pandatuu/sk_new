package com.example.sk_android.mvp.view.activity.jobSelect

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.mvp.view.fragment.jobSelect.*
import com.example.sk_android.service.Socketcluster
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import io.github.sac.Ack
import io.github.sac.BasicListener
import android.system.Os.socket
import Main.url
import android.util.Log
import io.github.sac.Socket
import android.system.Os.socket
import io.github.sac.ReconnectStrategy
import android.R.id.message
import android.system.Os.socket
import android.R.attr.data
import io.github.sac.Emitter
import android.system.Os.socket








class MessageActivity : AppCompatActivity() , Socketcluster.SocketclusterAndActivityBridge {


    lateinit var textViewReceive:TextView



    override fun getMessage(str: String) {
        textViewReceive.text=str
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       Socketcluster.newInstance("http://192.168.2.159:8000/pmc/","xxx",this)




        var socket = Socket("http://192.168.2.159:8000/pmc/")
        socket.setListener(object : BasicListener {

           override  fun onConnected(socket: Socket, headers: Map<String, List<String>>) {


               socket.emit("verifyJWT", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlYjBkNDRlNS0yNGEzLTRhMWMtOTU2MC1hMmY3M2VhMzE4ODAiLCJ1c2VybmFtZSI6ImRhd2VueGkiLCJ0aW1lc3RhbXAiOjE1NTcxMzcyMDM0MjMsImRldmljZVR5cGUiOiJXRUIiLCJpYXQiOjE1NTcxMzcyMDN9.Y62t8920zrdidBO1STFtYhSK1aS2mQNXd5wR4weCJbSQHEtbZ8GVF_8T-uwccD1C_qq1mOIc2JiXS3uH1qQYu9teg6IOkHtHooSzUUC2hxmtncagxuL7ad7zSeopeM3MIX_mNhSMKcR8JDiYixx5tdC_jfZHhX-J_P2Q6TQlrocu5LB4SFwAwYPaewW-OTeTdMIblGY3B121yea3erYlKF9lKZGHnlC691mv7nEOc3ohgHMZ2dunKxlr-IoX2w1J9GDYeRd5_MrZWYumLM8UalRty9TmLVRw5ElzcZj8v6QtG8I-FJcWEafpTiobPTTRCWP5ni8hSo6wMHev94zZKA") { eventName, error, data ->
                   //If error and data is String
                   println("Got message for :$eventName error is :$error data is :$data")
               }


               socket.on("verifyJWT") { eventName, objec ->
                   // Cast object to its proper datatype
                   System.out.println("Got message for :"+eventName+" data is :"+data);
                   textViewReceive.text=objec.toString()
               }


//                socket.createChannel("MyClassroom").subscribe(Ack { name, error, data ->
//                    if (error == null) {
//                        Log.i("Success", "subscribed to channel $name")
//                    }
//                })
//                Log.i("Success ", "Connected to endpoint")
//
//               print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            }

            override fun onDisconnected(
                socket: Socket,
                serverCloseFrame: WebSocketFrame,
                clientCloseFrame: WebSocketFrame,
                closedByServer: Boolean
            ) {
                Log.i("Success ", "Disconnected from end-point")
                print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

            }

            override fun onConnectError(socket: Socket, exception: WebSocketException) {
                Log.i("Success ", "Got connect error $exception")
                print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

            }

            override fun onSetAuthToken(token: String, socket: Socket) {
                socket.setAuthToken(token)
                print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

            }

            override fun onAuthentication(socket: Socket, status: Boolean?) {
                if (status!!) {
                    Log.i("Success ", "socket is authenticated")
                } else {
                    Log.i("Success ", "Authentication is required (optional)")
                }

                print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

            }

        })






        // socket.connect()
        socket.setReconnection(ReconnectStrategy().setMaxAttempts(10).setDelay(3000))
        socket.connectAsync();






        verticalLayout {

            textViewReceive=textView{
                text="xx"
            }



            editText {




            }


            button{
                text="ennnnnnnnnnd"
                onClick {
                    Socketcluster.sendMessage()
                }
            }


        }


    }
}
