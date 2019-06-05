package com.example.sk_android.mvp.view.adapter.message

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.message.ChatRecordModel
import org.jetbrains.anko.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import com.pingerx.imagego.core.strategy.loadImage
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL



class MessageChatRecordListAdapter(
        private val context: RecyclerView,
        private val chatRecord: MutableList<ChatRecordModel>,
        private val listener: (ChatRecordModel) -> Unit
) : RecyclerView.Adapter<MessageChatRecordListAdapter.ViewHolder>() {

    fun setChatRecords(chatRecords: List<ChatRecordModel>) {
        chatRecord.clear()
        chatRecord.addAll(chatRecords)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var imageV: ImageView? = null
        var userName: TextView? = null
        var message: TextView? = null
        var number: TextView? = null

        var view = with(parent.context) {
            relativeLayout {
                linearLayout {
                    backgroundResource=R.drawable.text_view_bottom_border
                    imageV = imageView {
                    }.lparams {
                        width=dip(44)
                        height=dip(44)
                        topMargin=dip(15)
                    }

                    linearLayout{
                        gravity=Gravity.CENTER_VERTICAL
                        orientation=LinearLayout.HORIZONTAL
                        verticalLayout {
                            linearLayout{
                                userName=textView {
                                    text="清水さん"
                                    textSize=16f
                                    textColorResource=R.color.normalTextColor
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }

                                textView {
                                    text="ジャさん·社長"
                                    textSize=12f
                                    textColorResource=R.color.grayb3
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }.lparams {
                                    leftMargin=dip(7)
                                }
                            }

                            linearLayout{
                                gravity=Gravity.CENTER_VERTICAL
                                textView {
                                    visibility=View.GONE
                                    backgroundResource=R.drawable.ellipse_border_grayb3
                                    text="已读"
                                    textSize=11f
                                    textColorResource=R.color.gray99
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                    topPadding=dip(2)
                                    bottomPadding=dip(2)
                                    leftPadding=dip(5)
                                    rightPadding=dip(5)
                                }

                                message=textView {
                                    text="是非御社で働きたいと思います。"
                                    textSize=14f
                                    textColorResource=R.color.black33
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }.lparams {
                                    leftMargin=dip(7)
                                }
                            }.lparams {
                                topMargin=dip(12)
                            }
                        }.lparams {
                            width=0
                            weight=1f
                        }

                        number=textView {
                            backgroundResource=R.drawable.circle_button_red
                            textColor=Color.WHITE
                            text="2"
                            leftPadding=dip(8)
                            rightPadding=dip(8)
                            topPadding=dip(3)
                            bottomPadding=dip(3)
                        }.lparams {
                            width= wrapContent
                            height= wrapContent

                        }


                    }.lparams {
                        height= matchParent
                        width= matchParent
                        leftMargin=dip(14)
                        rightMargin=dip(14)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(95)
                }
            }

        }
        return ViewHolder(view, userName, message, number, imageV)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.message?.text= chatRecord[position].massage
        holder.userName?.text=chatRecord[position].userName
        holder.number?.text=chatRecord[position].number

        imageUri="https://static.dingtalk.com/media/lALPDgQ9qdWUaQfMyMzI_200_200.png_200x200q100.jpg"

        loadImage(imageUri,holder.imageView)

        holder.bindItem(chatRecord[position],position,listener,context)
    }

    override fun getItemCount(): Int = chatRecord.size

    class ViewHolder(
            view: View,
            val userName: TextView?,
            val message: TextView?,
            val number: TextView?,
            val imageView: ImageView?
    ) : RecyclerView.ViewHolder(view) {



        fun bindImage(str:String){
            lateinit var  bitmap:Bitmap

            var id=1
            var image=itemView.findViewById<ImageView>(id)

            var handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    image.setImageBitmap(bitmap)
                }
            }

            var networkTask: Runnable = Runnable {
                // TODO
                // 在这里进行 http request.网络请求相关操作
                try {
                    val myFileUrl = URL(str)
                    val conn = myFileUrl.openConnection() as HttpURLConnection
                    conn.setDoInput(true)
                    conn.connect()
                    val `is` = conn.getInputStream()
                    bitmap = BitmapFactory.decodeStream(`is`)
                    val message = Message()
                    handler.sendMessage(message)
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }


            Thread(networkTask).start()

        }


        @SuppressLint("ResourceType")
        fun bindItem(chatRecord:ChatRecordModel,position:Int,listener: (ChatRecordModel) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                listener(chatRecord)
            }
        }
    }





    var imageUri:String=""






}