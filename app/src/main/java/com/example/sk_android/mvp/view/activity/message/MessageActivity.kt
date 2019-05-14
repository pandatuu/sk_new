package com.example.sk_android.mvp.view.activity.message

import android.R
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.service.Socketcluster
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import android.util.Log
import android.R.attr.data
import android.system.Os.socket
import android.R.id.message
import android.R.attr.data
import com.google.gson.Gson
import io.github.sac.*
import org.json.JSONObject
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

      // Socketcluster.newInstance("wss://im.geili.me/","xxx",this)




        var socket = Socket("https://im.sk.cgland.top/sk/")
       // var socket = Socket("192.168.2.159:8000")


        socket.setListener(object : BasicListener {

           override  fun onConnected(socket: Socket, headers: Map<String, List<String>>) {

               println(socket.currentState)


               val obj = JSONObject("{\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlN2FhZTg0OS0zYWM2LTQ0OGUtYjAxYy1jY2JhOWRjNTYxMTciLCJ1c2VybmFtZSI6IjE1ODgyMzM1MDA3IiwidGltZXN0YW1wIjoxNTU3Nzk4ODkwNjgzLCJkZXZpY2VUeXBlIjoiQU5EUk9JRCIsImlhdCI6MTU1Nzc5ODg5MH0.SMQtNNXKTB-bBUv-qVbRl9kitlgvqhXlSK2szoBrHAfV6JAaTA-CsjLDLGWETdcUX6jptiBiITe7acGnHcJol6R8OM4O-Mek0hIyRpbVA9rJhvD6CO577CJQTXdTeNUsLM3Gu4PkWbkX-zyrjHsd-Qg6NC7jX0_3BGaS2oIPamRC-ZRLFkX-VE2xHyLQku150c7wskdqjS_0yZq87w-RK84rEp5wJwvHuQyg_f2q_VdkWgh6gYxsPnChEtyW62Bj6n8RBy0tuRtqmINIAUdPfdrOrvOqBR-5iuCcGzD0xCfirkTy8U_7QckuWeSrN-DRau31JaXYTs5mempkvfl6Ew\"}")


               socket.emit("login",obj ) { eventName, error, data ->
                   //If error and data is String
                   println("Got message for :$eventName error is :$error data is :$data")


                   val channel = socket.createChannel("p_e7aae849-3ac6-448e-b01c-ccba9dc56117")
                   channel.subscribe { channelName, error, data ->
                       if (error == null) {
                           println("Subscribed to channel $channelName successfully")
                       }
                   }

                   channel.onMessage(object : Emitter.Listener {
                       override fun call(channelName: String, obj: Any) {

                           println("Got message for channel $channelName data is ${R.attr.data}")
                           textViewReceive.text=obj.toString()

                       }
                   })

               }




   //           val channel = socket.getChannelByName("p_eb0d44e5-24a3-4a1c-9560-a2f73ea31880")



//               channel.publish(obj2) { channelName, error, data ->
//                   if (error == null) {
//                       println("Published message to channel $channelName successfully")
//                   }
//                   println(" oooooooooooooooooooooooo  $data")
//
//               }








//               socket.on("login") { eventName, objec ->
//                   // Cast object to its proper datatype
//                   System.out.println("Got message for :"+eventName+" data is :"+data);
//                   textViewReceive.text=objec.toString()
//               }


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




        socket.onSubscribe("p_e7aae849-3ac6-448e-b01c-ccba9dc56117" ,object : Emitter.Listener {
            override fun call(channelName: String, obj: Any) {

                println("Got message for channel $channelName data is $data")
                textViewReceive.text=obj.toString()

            }
        })



        verticalLayout {

            textViewReceive=textView{
                text="xx"
            }



            editText {




            }


            button{



                text="ennnnnnnnnnd"
                onClick {
                    //Socketcluster.sendMessage()
                    val channel= socket.createChannel("p_eb0d44e5-24a3-4a1c-9560-a2f73ea31880")
                   // val channel = socket.getChannelByName("p_eb0d44e5-24a3-4a1c-9560-a2f73ea31880")
                    print(channel)
                    val obj2 = JSONObject("{"+
                            "\"sender\":{ \"id\":\"xxxxxx\", \"name\": \"xxxxx\" },"+
                            "\"receiver\":{ \"id\":\"ssss\", \"name\":\"asdasd\" },"+
                            "\"content\":{ \"type\":\"text\", \"msg\":\"newMessage\" },"+
                            "\"type\":\"P2P\"}")


                    channel.publish(obj2) { channelName, error, data ->
                        if (error == null) {
                            println("Published message to channel $channelName successfully")
                        }
                        println(" oooooooooooooooooooooooo  $data")

                    }

                }
            }


        }


    }
}
