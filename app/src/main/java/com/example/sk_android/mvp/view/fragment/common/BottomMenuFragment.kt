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
import android.os.Message
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.activity.company.CompanyInfoShowActivity
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatWithoutLoginActivity
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.utils.DialogUtils
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


    var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
    var groupArray: JSONArray = JSONArray()
    var map: MutableMap<String, Int> = mutableMapOf()
    var isFirstGotGroup: Boolean = true

    var isMessageList = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(ind: Int, isMessageList: Boolean): BottomMenuFragment {
            val fragment = BottomMenuFragment()
            fragment.index = ind
            fragment.isMessageList = isMessageList
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                                activity!!.overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)

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
                                text = "職種"
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

                                activity!!.overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)
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
                                    intent = Intent(mContext, MessageChatWithoutLoginActivity::class.java)
                                    startActivity(intent)
                                }

                                activity!!.overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)

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
                                    visibility = View.INVISIBLE
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
                                        text = "1"
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

                                activity!!.overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)
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

                activity!!.runOnUiThread(Runnable {
                    Handler().postDelayed({
                        socket.emit("queryContactList", application!!.getMyToken(),
                            object : Ack {
                                override fun call(name: String?, error: Any?, data: Any?) {
                                    println("Got message for :" + name + " error is :" + error + " data is :" + data)
                                }

                            })
                    }, 20)
                })

            }

            override fun getContactList(str: String) {
                var json = JSONObject(str)
                var type = json.getString("type")
                if (type != null && type.equals("contactList")) {
                    var array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                    for (i in 0..array.length() - 1) {
                        var item = array.getJSONObject(i)
                        var id = item.getString("id")

                        if (id.equals("0")) {
                            println("hhhhhhhhhh")

                            println(item)

                            var allUnReads = item.getString("allUnReads")

                            activity!!.runOnUiThread(Runnable {
                                setNumberShow(allUnReads.toInt())
                            })
                        }
                    }
                }



                if (isMessageList) {
                    println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")


                    if (type != null && type.equals("contactList")) {
                        var array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                        var members: JSONArray = JSONArray()
                        for (i in 0..array.length() - 1) {
                            var item = array.getJSONObject(i)
                            var id = item.getInt("id")
                            var name = item.getString("name")
                            map.put(name, id)
                            if (id == 0) {
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
                        isFirstGotGroup = false
                        chatRecordList = mutableListOf()
                        for (i in 0..members.length() - 1) {
                            var item = members.getJSONObject(i)
                            println(item)
                            //未读条数
                            var unreads = item.getInt("unreads").toString()
                            //对方名
                            var name = item["name"].toString()
                            //最后一条消息
                            var lastMsg:JSONObject?=null
                            if(item.has("lastMsg") && !item.getString("lastMsg").equals("") && !item.getString("lastMsg").equals("null")){
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

                    }


                    activity!!.runOnUiThread(Runnable {
                        (activity as MessageChatRecordActivity).chatRecordList = chatRecordList
                        (activity as MessageChatRecordActivity).groupArray = groupArray
                        (activity as MessageChatRecordActivity).messageChatRecordListFragment.setRecyclerAdapter(
                            chatRecordList,
                            groupArray
                        )
                        (activity as MessageChatRecordActivity).map = map
                    })

                    DialogUtils.hideLoading()

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

