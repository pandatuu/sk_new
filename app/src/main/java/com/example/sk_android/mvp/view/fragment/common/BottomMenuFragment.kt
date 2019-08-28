package com.example.sk_android.mvp.view.fragment.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.activity.company.CompanyInfoShowActivity
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatWithoutLoginActivity
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.facebook.react.bridge.UiThreadUtil
import io.github.sac.Ack
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.runOnUiThread
import org.json.JSONArray
import org.json.JSONObject
import withTrigger


class BottomMenuFragment : Fragment() {


    private var mContext: Context? = null
    private lateinit var recruitInfoBottomMenu: RecruitInfoBottomMenu

    lateinit var numberShowContainer: FrameLayout
    lateinit var numberShow: TextView

    var index: Int? = null
    var groupId = 0;

    var thisDialog: MyDialog? = null

    lateinit var json: JSONObject
    var isMessageList = false



    var Listhandler:Handler?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        var theNumber=0

        fun newInstance(ind: Int, isMessageList: Boolean): BottomMenuFragment {
            val fragment = BottomMenuFragment()
            fragment.index = ind
            fragment.isMessageList = isMessageList
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentView = createView()
        recruitInfoBottomMenu = activity as RecruitInfoBottomMenu
        return fragmentView
    }


    fun createView(): View {

        var view = UI {
            linearLayout {
                linearLayout {

                    backgroundResource = R.drawable.border_top_f2_ba

                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout {

                        this.withTrigger().click {


                            if (index != 0) {
                                var intent = Intent(mContext, RecruitInfoShowActivity::class.java)
                                startActivity(intent)
                                activity!!.finish()
                                activity!!.overridePendingTransition(
                                    R.anim.fade_in_out,
                                    R.anim.fade_in_out
                                )

                            }


                        }


                        verticalLayout {
                            gravity = Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                if (index == 0) {
                                    setImageResource(R.mipmap.icon_position_h_home_clicked)
                                } else {
                                    setImageResource(R.mipmap.icon_position_h_home_unclicked)
                                }
                            }.lparams() {
                                height = dip(24)
                                width = dip(24)
                            }

                            textView {
                                text = "役職"
                                textSize = 10f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.gray66
                            }.lparams {
                                height = wrapContent
                                topMargin = dip(3)
                            }

                        }.lparams {
                            height = wrapContent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {


                        this.withTrigger().click {

                            if (index != 1) {
                                var intent = Intent(mContext, CompanyInfoShowActivity::class.java)
                                startActivity(intent)
                                activity!!.finish()

                                activity!!.overridePendingTransition(
                                    R.anim.fade_in_out,
                                    R.anim.fade_in_out
                                )
                            }


                        }


                        verticalLayout {
                            gravity = Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                if (index == 1) {
                                    setImageResource(R.mipmap.icon_company)
                                } else {
                                    setImageResource(R.mipmap.icon_company_unclicked)
                                }
                            }.lparams() {
                                height = dip(24)
                                width = dip(24)
                            }

                            textView {
                                text = "会社"
                                textSize = 10f
                                gravity = Gravity.CENTER_VERTICAL
                                textColorResource = R.color.gray66
                            }.lparams {
                                height = wrapContent
                                topMargin = dip(3)
                            }


                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {

                        this.withTrigger().click {


                            if (index != 2) {
                                lateinit var intent: Intent
                                if (App.getInstance()!!.getMessageLoginState()) {
                                    intent = Intent(mContext, MessageChatRecordActivity::class.java)
                                    startActivity(intent)
                                    activity!!.finish()
                                } else {
                                    MessageChatWithoutLoginActivity.fatherActivity = activity
                                    intent = Intent(
                                        mContext,
                                        MessageChatWithoutLoginActivity::class.java
                                    )
                                    startActivity(intent)
                                }

                                activity!!.overridePendingTransition(
                                    R.anim.fade_in_out,
                                    R.anim.fade_in_out
                                )

                            }


                        }

                        verticalLayout {
                            gravity = Gravity.CENTER

                            relativeLayout() {
                                var imageId = 9

                                var image = imageView {
                                    id = imageId
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER
                                    if (index == 2) {
                                        setImageResource(R.mipmap.icon_message_home_clicked)
                                    } else {
                                        setImageResource(R.mipmap.icon_message_home_unclicked)
                                    }
                                }.lparams() {
                                    centerInParent()
                                    height = dip(24)
                                    width = dip(24)
                                }
                                numberShowContainer = frameLayout {

                                    backgroundColor = Color.TRANSPARENT
                                    imageView() {
                                        visibility = View.INVISIBLE
                                        scaleType = ImageView.ScaleType.CENTER
                                        setImageResource(R.mipmap.icon_circle)
                                    }.lparams {

                                        width = matchParent
                                        leftMargin = dip(20)
                                        bottomMargin = dip(5)
                                    }

                                    numberShow = textView {
                                        text = "99+"
                                        textSize = 10f
                                        gravity = Gravity.CENTER
                                        textColorResource = R.color.white
                                        // backgroundResource = R.mipmap.icon_circle
                                        backgroundResource = R.drawable.circle_button_red

                                        leftPadding = dip(5)
                                        rightPadding = dip(5)
                                    }.lparams {
                                        height = dip(15)
                                        width = wrapContent
                                        leftMargin = dip(20)

                                    }


                                }.lparams {
                                    centerHorizontally()
                                    bottomMargin = dip(5)
                                    height = dip(25)
                                    width = wrapContent
                                }

                                if(theNumber>0){
                                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                    println(theNumber)
                                    numberShow.text=theNumber.toString()
                                }else{
                                    numberShow.text="0"
                                    numberShowContainer.visibility = View.INVISIBLE
                                }

                            }.lparams {
                                width = matchParent

                            }


                            textView {
                                text = "メッセージ"
                                textSize = 10f
                                gravity = Gravity.CENTER_VERTICAL
                                textColorResource = R.color.gray66

                            }.lparams {
                                height = wrapContent
                                topMargin = dip(-2)
                            }


                        }.lparams {
                            height = matchParent
                            width = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {


                        this.withTrigger().click {

                            if (index != 3) {
                                var intent = Intent(mContext, PersonSetActivity::class.java)
                                startActivityForResult(intent, 101)
                                activity!!.finish()

                                activity!!.overridePendingTransition(
                                    R.anim.fade_in_out,
                                    R.anim.fade_in_out
                                )
                            }

                        }


                        verticalLayout {
                            gravity = Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                if (index == 3) {
                                    setImageResource(R.mipmap.icon_person_clicked)
                                } else {
                                    setImageResource(R.mipmap.icon_me_home_unclicked)
                                }
                            }.lparams() {
                                height = dip(24)
                                width = dip(24)
                            }

                            textView {
                                text = "マイ"
                                textSize = 10f
                                gravity = Gravity.CENTER_VERTICAL
                                textColorResource = R.color.gray66
                            }.lparams {
                                height = wrapContent
                                topMargin = dip(3)
                            }


                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }
                }.lparams {
                    width = matchParent
                    height = dip(51)
                }
            }
        }.view





        //接受
        var application = App.getInstance()
        var socket = application!!.getSocket()



        //消息回调
        application!!.setChatRecord(object : ChatRecord {
            override fun requestContactList() {
                socket.emit("queryContactList", application!!.getMyToken())
            }

            override fun getContactList(str: String) {




                PersonSetActivity.json= JSON.parseObject(str)
                val message = Message()
                Listhandler?.sendMessage(message)

                json = JSONObject(str)
                var type = json.getString("type")
                if (type != null && type.equals("contactList")) {
                    var array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                    for (i in 0..array.length() - 1) {
                        var item = array.getJSONObject(i)
                        var id = item.getString("id")

                        if (id.equals("0")) {
                            println("hhhhhhhhhh")



                            var allUnReads = item.getString("allUnReads")
                            println(item)
                            println(allUnReads)


                            activity!!.runOnUiThread(Runnable {
                                setNumberShow(allUnReads.toInt())
                            })
                        }
                    }
                }


            }
        })



        Handler().postDelayed({
            socket.emit("queryContactList", application!!.getMyToken(),
                object : Ack {
                    override fun call(name: String?, error: Any?, data: Any?) {
                        println("Got message for :" + name + " error is :" + error + " data is :" + data)
                    }

                })
        }, 200)


        return view

    }

    public interface RecruitInfoBottomMenu {

        fun getSelectedMenu()
    }


    fun setNumberShow(num: Int) {
        theNumber=num

        if (num > 0) {
            numberShowContainer.visibility = View.VISIBLE
            if (num >= 100) {
                numberShow.text = "99+"
            } else {
                numberShow.text = num.toString()
            }
        } else {
            numberShowContainer.visibility = View.INVISIBLE
            numberShow.text = num.toString()
        }

    }


}

