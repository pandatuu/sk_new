package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import withTrigger
import android.content.Intent
import android.net.Uri
import android.text.TextUtils


class CompanyCityAddressAdapter(
    private val list: MutableList<ArrayList<String>>,
    private val jinweis: List<List<String>>

) : RecyclerView.Adapter<CompanyCityAddressAdapter.ViewHolder>() {


    lateinit var image: ImageView
    var index = 0

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var jinWei = listOf<String>()
        var jindu = ""
        var weidu = ""
        if(jinweis.isNotEmpty())
            if(index<jinweis.size)
                jinWei = jinweis[index]
        val view = with(parent.context) {
            relativeLayout() {
                linearLayout {
                    if(jinweis.isNotEmpty() && index<jinweis.size){
                        // 经度
                        jindu = jinWei[0]
                        // 纬度
                        weidu = jinWei[1]
                    }
                    orientation=LinearLayout.HORIZONTAL
                    image=imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.face_address)
                    }.lparams {
                        width=dip(11)
                        height=dip(15)
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        textView {
                            text=list[index][0]
                            ellipsize = TextUtils.TruncateAt.END
                            maxLines = 1
                            textSize=14f
                            letterSpacing=0.05f
                            textColorResource=R.color.black33
                            gravity=Gravity.CENTER
                        }.lparams {
                            width = wrapContent
                            height = dip(20)
                        }

                        textView {
//                            text = if(list[index][1].length<40)
//                                list[index][1]
//                            else
//                                "${list[index][1].substring(0,37)}..."
                            text = list[index][1]
                            ellipsize = TextUtils.TruncateAt.END
                            maxLines = 1
                            textSize=11f
                            letterSpacing=0.05f
                            textColorResource=R.color.gray89
                            gravity=Gravity.CENTER
                        }.lparams {
                            width = wrapContent
                            height = dip(15)
                            rightMargin = dip(10)
                        }
                    }.lparams {
                        leftMargin=dip(11)
                        width=0
                        weight=1f
                    }


                    verticalLayout {
                        image=imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            width=dip(6)
                            height=dip(11)
                        }
                    }.lparams {
                        width= wrapContent
                        height= wrapContent
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    this.withTrigger().click {
                        if(jinWei.isNotEmpty() && jindu!="" && weidu!=""){
                            val uri = Uri.parse("geo:$jindu,$weidu")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                    }


                }.lparams {
                    topMargin=dip(5)
                    width = matchParent
                    height= wrapContent
                    bottomMargin = dip(5)
                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        index++
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem( item: String,view:View?,listener: (item: String) -> Unit) {

                listener(item)
            }
    }




}