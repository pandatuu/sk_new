package com.example.sk_android.mvp.view.adapter.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.Company
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.pingerx.imagego.core.strategy.loadImage
import org.jetbrains.anko.*

class CompanyInfoListAdapter(
    private val context: RecyclerView,
    private val mData: MutableList<Company>,
    private val listener: (Company) -> Unit
) : RecyclerView.Adapter<CompanyInfoListAdapter.ViewHolder>() {




    fun addCompanyInfoList(list: MutableList<Company>){
        mData.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        lateinit var companyName:TextView
        lateinit var companyLogo: ImageView
        lateinit var video: ImageView
        lateinit var cityName:TextView
        lateinit var countyName:TextView
        lateinit var  streetName:TextView

        lateinit var  financing:TextView
        lateinit var  companySize:TextView
        lateinit var  companyType:TextView



        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    backgroundColor=Color.WHITE
                    linearLayout{
                        orientation = LinearLayout.HORIZONTAL
                        companyLogo=imageView{
                            imageResource=R.mipmap.logo_company
                        }.lparams {
                            width=dip(50)
                            height=dip(50)
                        }

                        verticalLayout {
                            gravity=Gravity.BOTTOM

                            linearLayout {
                                gravity=Gravity.CENTER_VERTICAL
                                video=imageView{
                                    imageResource=R.mipmap.ico_video
                                }.lparams {
                                    width=dip(15)
                                    height=dip(15)
                                }

                                companyName = textView {
                                    gravity = Gravity.CENTER_VERTICAL
                                    textColorResource = R.color.normalTextColor
                                    textSize = 16f
                                    text = "任天堂株式会社東京本社"
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                }.lparams {
                                    leftMargin=dip(5)
                                }
                            }.lparams(){
                                bottomMargin = dip(3)
                            }

                            linearLayout {
                                orientation=LinearLayout.HORIZONTAL
                                cityName=textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="東京都"
                                }.lparams {
                                }

                                countyName=textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="中央区"
                                }.lparams {
                                    leftMargin=dip(2)
                                }

                                streetName=textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="ｘｘｘｘ"
                                }.lparams {
                                    leftMargin=dip(2)
                                }

                            }



                        }.lparams {
                            height= matchParent
                            width=wrapContent
                            leftMargin=dip(11)
                        }



                    }.lparams {
                        height= wrapContent
                        width= matchParent
                        topMargin=dip(25)
                        leftMargin=dip(15)
                    }


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        financing=textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="上場してる"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                        }

                        companySize= textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="500-999人"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                            leftMargin=dip(10)
                        }

                        companyType=textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="インターネット"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                            leftMargin=dip(10)
                        }

                    }.lparams {
                        height=dip(18)
                        topMargin=dip(10)
                        leftMargin=dip(15)
                    }


                    relativeLayout {
                        backgroundResource=R.drawable.border_top_ebeaeb
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL

                            textView {
                                textColorResource=R.color.gray89
                                textSize=12f
                                text="大ヒット:"
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                            }

                            textView {
                                textColorResource=R.color.gray5c
                                textSize=12f
                                text="phpエンジニアなど"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                                leftMargin=dip(2)
                            }

                            textView {
                                textColorResource=R.color.gray89
                                textSize=12f
                                text="職位20"
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                            }



                        }.lparams {
                            height= matchParent
                            alignParentLeft()

                        }

                        imageView{
                            var flag=true
                            imageResource=R.mipmap.icon_go_position
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        height=dip(55)
                        width= matchParent
                        rightMargin=dip(15)
                        leftMargin=dip(15)
                        topMargin=dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(170)
                    topMargin=dip(8)
                }
            }

        }
        return ViewHolder(view,companyName,companyLogo,cityName,countyName,streetName,financing,companySize,companyType,video)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //公司名
        holder.companyName.text=mData[position].name
        //城市名
        holder.cityName.text=mData[position].cityName
        //区县名
        holder.countyName.text=mData[position].countyName
        //街道名
        holder.streetName.text=mData[position].streetName
        //是否融资
        holder.financing.text=mData[position].financing
        //公司人员数量
        holder.companySize.text=mData[position].companySize
        //公司类型
        holder.companyType.text=mData[position].companyType
        //公司logo
        if(mData[position].logo!=null && !mData[position].logo.equals("")){
            var imageUri=mData[position].logo
            loadImage(imageUri,holder.companyLogo)
        }
        //是够有视频
        if(mData[position].video){
            holder.video.visibility=View.VISIBLE
        }else{
            holder.video.visibility=View.GONE
        }


        holder.bindItem(mData[position],position,listener,context)
    }


    override fun getItemCount(): Int = mData.size

    class ViewHolder(view: View,
                     val companyName:TextView,
                     val companyLogo:ImageView,
                     val cityName:TextView,
                     val countyName:TextView,
                     val streetName:TextView,
                     val financing:TextView,
                     val companySize:TextView,
                     val companyType:TextView,
                     val video:ImageView
                     ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(company:Company,position:Int,listener: (Company) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                listener(company)
            }
        }
    }



}