package com.example.sk_android.mvp.view.adapter.company

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.model.company.CompanyBriefInfo
import com.example.sk_android.mvp.model.company.CompanySize
import com.example.sk_android.mvp.model.company.FinancingStage
import com.example.sk_android.mvp.model.jobselect.Company
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.pingerx.imagego.core.listener.OnImageListener
import com.pingerx.imagego.core.strategy.ImageOptions
import com.pingerx.imagego.core.strategy.loadCircle
import com.pingerx.imagego.core.strategy.loadImage
import org.jetbrains.anko.*
import withTrigger

class CompanyInfoListAdapter(
    private val context: RecyclerView,
    private val mData: MutableList<CompanyBriefInfo>,
    private val listener: (CompanyBriefInfo) -> Unit
) : RecyclerView.Adapter<CompanyInfoListAdapter.ViewHolder>() {


    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }


    fun addCompanyInfoList(list: MutableList<CompanyBriefInfo>) {
        var startIndex = mData.size
        var count = list.count()
        mData.addAll(list)
        notifyItemRangeChanged(startIndex, count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var id: String = ""

        lateinit var companyName: TextView
        lateinit var companyLogourl: TextView

        lateinit var companyLogo: ImageView
        lateinit var video: ImageView
        lateinit var cityName: TextView
        lateinit var countyName: TextView
        lateinit var streetName: TextView

        lateinit var financing: TextView
        lateinit var companySize: TextView
        lateinit var companyType: TextView


        lateinit var positionNum: TextView

        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    view {
                        backgroundColorResource = R.color.originColor
                    }.lparams {
                        height = dip(8)
                        width = matchParent
                    }
                    backgroundColor = Color.WHITE
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL


                        companyLogourl = textView {
                            visibility = View.GONE
                        }

                        companyLogo = imageView {
                            imageResource = R.mipmap.ico_company_default_logo
                        }.lparams {
                            width = dip(50)
                            height = dip(50)
                        }

                        verticalLayout {
                            gravity = Gravity.BOTTOM

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                video = imageView {
                                    imageResource = R.mipmap.ico_video
                                }.lparams {
                                    width = dip(15)
                                    height = dip(15)
                                }

                                companyName = textView {
                                    gravity = Gravity.CENTER_VERTICAL
                                    textColorResource = R.color.normalTextColor
                                    textSize = 16f
                                    text = "任天堂株式会社東京本社"
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                }.lparams {
                                    leftMargin = dip(5)
                                }
                            }.lparams() {
                                bottomMargin = dip(3)
                            }

                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                cityName = textView {
                                    textColorResource = R.color.gray99
                                    textSize = 13f
                                    text = "東京都"
                                }.lparams {
                                }

                                countyName = textView {
                                    textColorResource = R.color.gray99
                                    textSize = 13f
                                    text = "中央区"
                                }.lparams {
                                    leftMargin = dip(2)
                                }

                                streetName = textView {
                                    textColorResource = R.color.gray99
                                    textSize = 13f
                                    text = "ｘｘｘｘ"
                                }.lparams {
                                    leftMargin = dip(2)
                                }

                            }


                        }.lparams {
                            height = matchParent
                            width = wrapContent
                            leftMargin = dip(11)
                        }


                    }.lparams {
                        height = wrapContent
                        width = matchParent
                        topMargin = dip(25)
                        leftMargin = dip(15)
                    }


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        financing = textView {
                            backgroundResource = R.drawable.radius_border_blue_02b8f7
                            textColorResource = R.color.blue0097D6
                            textSize = 11f
                            text = "上場してる"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                        }

                        companySize = textView {
                            backgroundResource = R.drawable.radius_border_blue_02b8f7
                            textColorResource = R.color.blue0097D6
                            textSize = 11f
                            text = "500-999人"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                            leftMargin = dip(10)
                        }

                        companyType = textView {
                            backgroundResource = R.drawable.radius_border_blue_02b8f7
                            textColorResource = R.color.blue0097D6
                            textSize = 11f
                            text = "インターネット"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                            leftMargin = dip(10)
                        }

                    }.lparams {
                        height = dip(18)
                        topMargin = dip(10)
                        leftMargin = dip(15)
                    }


                    relativeLayout {
                        backgroundResource = R.drawable.border_top_ebeaeb
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                textColorResource = R.color.gray89
                                textSize = 12f
                                text = "大ヒット:"
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                            }

                            textView {
                                textColorResource = R.color.gray5c
                                textSize = 12f
                                //  text = "phpエンジニアなど"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                leftMargin = dip(2)
                            }

                            positionNum = textView {
                                textColorResource = R.color.gray89
                                textSize = 12f
                                text = "職位20"
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                            }


                        }.lparams {
                            height = matchParent
                            alignParentLeft()

                        }

                        imageView {
                            var flag = true
                            imageResource = R.mipmap.icon_go_position
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        height = dip(55)
                        width = matchParent
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                        topMargin = dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(178)

                }
            }

        }
        return ViewHolder(
            view,
            id,
            companyName,
            companyLogourl,
            companyLogo,
            cityName,
            countyName,
            streetName,
            financing,
            companySize,
            companyType,
            video,
            positionNum
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //id
        holder.id = mData[position].id
        //公司名
        holder.companyName.text = mData[position].name


        //公司logo
        if (mData[position].logo != null && !mData[position].logo.equals("")) {
            var imageUri = mData[position].logo
            holder.companyLogourl.text = imageUri
        } else {
            println("图片路径不存在!!!")
            println(mData[position].logo)
        }


        var imageUri = mData[position].logo

        if (imageUri != null) {
            var logoUrl = imageUri.split(";")[0]
            var option=ImageOptions.Builder()
                .setCrossFade(false)
                .setPriority(ImageOptions.LoadPriority.IMMEDIATE)
                .setDiskCacheStrategy(ImageOptions.DiskCache.ALL)
                .setSkipMemoryCache(true)
                .build()

            loadImage(logoUrl, holder.companyLogo, object : OnImageListener {
                /**
                 * 图片加载失败
                 * @param msg 加载失败的原因
                 */
                override fun onFail(msg: String?) {
                    println(msg)
                    println("图片加载失败")
                }

                /**
                 * 图片加载成功
                 * @param bitmap 加载成功生成的bitmap对象
                 */
                override fun onSuccess(bitmap: Bitmap?) {
                    println("图片加载成功")
                }
            }, R.mipmap.ico_company_default_logo, R.mipmap.ico_company_default_logo, option)


        }


        //城市名
        holder.cityName.text = mData[position].cityName
        //区县名
        holder.countyName.text = mData[position].countyName
        //街道名
        holder.streetName.text = mData[position].streetName
        //是否融资
        if (mData[position].financingStage != null) {
            holder.financing.text = FinancingStage.dataMap.get(mData[position].financingStage)
        } else {
            holder.financing.visibility = View.GONE
        }
        //公司人员数量
        if (mData[position].size != null) {
            holder.companySize.text = CompanySize.dataMap.get(mData[position].size)
        } else {
            holder.companySize.visibility = View.GONE
        }

        //公司类型
        holder.companyType.text = mData[position].type


        //是够有视频
        if (mData[position].haveVideo) {
            holder.video.visibility = View.VISIBLE
        } else {
            holder.video.visibility = View.GONE
        }

        holder.positionNum.text = "役職" + mData[position].positionNum.toString()


        holder.bindItem(mData[position], position, listener, context)
        holder.setIsRecyclable(false);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onViewRecycled(holder: ViewHolder)//这个方法是Adapter里面的
    {

        super.onViewRecycled(holder);
    }


    override fun getItemCount(): Int = mData.size

    class ViewHolder(
        view: View,
        var id: String,
        val companyName: TextView,
        val companyLogourl: TextView,
        val companyLogo: ImageView,
        val cityName: TextView,
        val countyName: TextView,
        val streetName: TextView,
        val financing: TextView,
        val companySize: TextView,
        val companyType: TextView,
        val video: ImageView,
        val positionNum: TextView
    ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(
            company: CompanyBriefInfo,
            position: Int,
            listener: (CompanyBriefInfo) -> Unit,
            context: RecyclerView
        ) {


//            var logourl=companyLogourl.text.toString()
//            if(logourl!=null && !"".equals(logourl) && (companyLogo.getTag()==null || companyLogo.getTag().toString().equals(logourl+position.toString()+companyName.text.toString()))){
//                loadCircle(logourl, companyLogo, 0, 0, 0, 0, object : OnImageListener {
//                    /**
//                     * 图片加载失败
//                     * @param msg 加载失败的原因
//                     */
//                    override fun onFail(msg: String?) {
//                        println(msg)
//                        println("图片加载失败")
//                    }
//
//                    /**
//                     * 图片加载成功
//                     * @param bitmap 加载成功生成的bitmap对象
//                     */
//                    override fun onSuccess(bitmap: Bitmap?) {
//                        println("图片加载成功")
//                    }
//                })
//            }else{
//            }


            itemView.withTrigger().click {

                listener(company)
            }
        }


    }


}