package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.company.CompanySize
import com.example.sk_android.mvp.model.company.FinancingStage
import com.example.sk_android.mvp.model.jobselect.Benifits
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSearchWithHistoryActivity
import com.example.sk_android.utils.RetrofitUtils
import com.pingerx.imagego.core.strategy.loadCircle
import com.pingerx.imagego.core.strategy.loadImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

class JobInfoDetailCompanyInfoFragment : Fragment() {

    private var mContext: Context? = null


    lateinit  var companyName:TextView
    lateinit  var companyBriefInfo:TextView
    lateinit  var companyLogo:ImageView

    var thePositionNum=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailCompanyInfoFragment {
            return JobInfoDetailCompanyInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {

        var intent=activity!!.intent
        var organizationId=intent.getStringExtra("organizationId")
        if(organizationId!=null){
            getCompanyInfo(organizationId)

        }

        return UI {
            linearLayout {
                verticalLayout {
                    gravity=Gravity.CENTER_VERTICAL
                    relativeLayout {


                        this.withTrigger().click {
                           

                                var intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
                                intent.putExtra("organizationId",organizationId)
                                intent.putExtra("companyId",organizationId)
                                intent.putExtra("positionNum",thePositionNum)

                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }

                      



                        backgroundResource=R.drawable.box_shadow_weak
                        var iamgeId=31
                        companyLogo=imageView {
                            id=iamgeId
                            scaleType = ImageView.ScaleType.CENTER_CROP
//                            setImageResource(R.mipmap.icon_tx_home)

                        }.lparams() {
                            width = dip(48)
                            height =dip(48)
                            leftMargin=dip(18)
                            topMargin=dip(30)
                        }


                        verticalLayout {



                            companyName=  textView {
                                text=""
                                textColorResource=R.color.normalTextColor
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                textSize=15f
                            }.lparams {
                                topMargin=dip(3)
                                width= wrapContent
                            }

                          companyBriefInfo=  textView {
                                text="上場会社·500-999人·IT"
                                textColorResource=R.color.gray99
                                textSize=13f

                            }.lparams {
                                topMargin=dip(3)
                            }

                        }.lparams {
                            rightOf(companyLogo)
                            leftMargin=dip(17)
                            centerVertically()
                            width= wrapContent
                        }


                        imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_go_zwxq_gray)

                        }.lparams() {

                            rightMargin=dip(18)
                            centerVertically()
                            alignParentRight()
                        }

                    }.lparams {
                        leftMargin=dip(15)
                        rightMargin=dip(15)
                        height=dip(127)
                        width= matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(177)
                }
            }
        }.view

    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }


    fun setPositionNum(i :Int){
        thePositionNum=i
    }

    fun getCompanyInfo(id:String){
        var requestCompany = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
        requestCompany.create(RecruitInfoApi::class.java)
            .getCompanyInfo(
                id
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("公司信息请求成功")
                println(it)
                var json = org.json.JSONObject(it.toString())
                var name = json.getString("name")
                val logo = json.getString("logo")
                val financingStage = json.getString("financingStage")
                val size = json.getString("size")

                if(logo!=null && !logo.equals("")){
                    loadCircle(
                        logo,
                        companyLogo
                    )
                }
                companyName.text=name



                var industryIds= json.getJSONArray("industryIds")
                if(industryIds!=null){
                    for(i in 0..industryIds.length()-1){
                        var requestIndustry = RetrofitUtils(mContext!!, "https://industry.sk.cgland.top/")
                        requestIndustry.create(CompanyInfoApi::class.java)
                            .getCompanyIndustryInfo(
                                industryIds.getString(i)
                            )
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println("行业信息请求成功")
                                println(it)
                                var json = org.json.JSONObject(it.toString())
                                var name = json.getString("name")

                                companyBriefInfo.text= FinancingStage.dataMap.get(financingStage)+"."+ CompanySize.dataMap.get(size)+"."+name

                            }, {
                                //失败
                                println("行业信息请求失败")
                                println(it)
                            })
                    }
                }


            }, {
                //失败
                println("公司信息请求失败")
                println(it)
            })
    }
}




