package com.example.sk_android.mvp.view.adapter.company

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import org.jetbrains.anko.*

class LabelShowAdapter(
    private val professions: MutableList<String>,
    private val listener: (String) -> Unit

) : RecyclerView.Adapter<LabelShowAdapter.ViewHolder>() {


    lateinit var itemShow: FlowLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {

                    itemShow = flowLayout {

                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(5)
                    leftMargin = dip(5)
                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            println("11111111111111111111111111111111111111111111111111111111111111111111")
            if (professions != null || professions.size > 0) {
                for (item in professions) {
                    itemShow.addView(getItemView(item))
                }
            }else{
                itemShow.addView(getNulView())
            }
        }

    }

    override fun getItemCount(): Int = professions.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(str: String, listener: (String) -> Unit) {
            itemView.setOnClickListener {
                listener(str)
            }
        }
    }

    private val benifits = mapOf(
        "BONUS" to "ボーナス",// 奖金,
        "SOCIAL_INSURANCE" to "社会保険",// 社会保险,
        "CHILD_ALLOWANCE" to "児童手当拠出金",//育儿补贴,
        "ORGANIZATION_TOUR" to "社内旅行",// 公司旅游,
        "RESERVE_FUNDS" to "勤労者財産形成貯蓄",//劳动者公积金,
        "HOUSE" to "住居",//住房,
        "TRAFFIC" to "通勤補助",//出勤辅助,
        "CHILD_EDUCATION" to "子育て支援",//子女教育支援金,
        "CERTIFICATE_FUNDS" to "資格取得支援",//支持员工考取资格证书的补贴,
        "BENIFIT_FACILITIES" to "福利厚生施設",//福利设施,
        "HEALTH_HOUSE" to "保養所",//企业，健康保健组织等成立建设的,
        "CANTEEN" to "社員食堂",//员工食堂,
        "DORM" to "社員寮・社員住宅",//员工宿舍,
        "SPORTS_FACILITIES" to "運動施設など",//运动设施,
        "STAFF_TOUR" to "社員旅行",//员工旅游,
        "CLUB" to "クラブ活動、実業団の補助",//俱乐部活动,
        "OTHER" to "その他"//其他
    )
    private fun getItemView(tx: String): View? {
        return with(itemShow.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = if("福利暂定" != tx) benifits[tx] else tx
                        backgroundResource = R.drawable.radius_border_unselect
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(10)
                        leftPadding = dip(10)
                        textColorResource = R.color.black33
                        textSize = 11f
                    }.lparams {
                        leftMargin = dip(10)
                        topMargin = dip(10)
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }
    private fun getNulView(): View? {
        return with(itemShow.context) {
            verticalLayout {
                relativeLayout{
                    textView {
                        text = "福利暂定"
                        textSize = 13f
                    }.lparams{
                        leftMargin = dip(10)
                        topMargin = dip(10)
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }
}