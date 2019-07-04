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
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.pingerx.imagego.core.strategy.loadCircle

class JobInfoDetailBossInfoFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailBossInfoFragment {
            return JobInfoDetailBossInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {



        var intent=activity!!.intent
        var companyName=intent.getStringExtra("companyName")
        var userName=intent.getStringExtra("userName")
        var userPositionName=intent.getStringExtra("userPositionName")
        var avatarURL=intent.getStringExtra("avatarURL")
        var userId=intent.getStringExtra("userId")
        var organizationId=intent.getStringExtra("organizationId")

        lateinit var logoIamge:ImageView

        var view= UI {
            linearLayout {
                verticalLayout {
                    gravity=Gravity.CENTER_VERTICAL
                    relativeLayout {

                        setOnClickListener(object :View.OnClickListener{

                            override fun onClick(v: View?) {

                                var intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
                                intent.putExtra("companyId",organizationId)

                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }

                        })




                        backgroundResource=R.drawable.box_shadow_weak
                        var iamgeId=31
                        logoIamge=imageView {
                            id=iamgeId
                            scaleType = ImageView.ScaleType.CENTER_CROP
//                            setImageResource(R.mipmap.icon_tx_home)

                        }.lparams() {
                            width = dip(50)
                            height =dip(50)
                            leftMargin=dip(18)
                            topMargin=dip(33)
                        }


                        verticalLayout {

                            textView {
                                text=companyName
                                textColorResource=R.color.themeBule
                                textSize=13f

                            }.lparams {
                                width= wrapContent
                            }

                            textView {
                                text=userName+"·"+userPositionName
                                textColorResource=R.color.themeBule
                                textSize=15f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                            }.lparams {
                                topMargin=dip(3)
                                width= wrapContent
                            }

                            textView {
                                backgroundResource=R.drawable.radius_border_label_gray89
                                text="活躍中"
                                visibility=View.GONE
                                textColorResource=R.color.gray89
                                textSize=11f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                topPadding=dip(2)
                                bottomPadding=dip(2)
                                rightPadding=dip(4)
                                leftPadding=dip(4)
                            }.lparams {
                                topMargin=dip(5)
                            }

                        }.lparams {
                            rightOf(logoIamge)
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
                        height=dip(122)
                        width= matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(150)
                }
            }
        }.view

        loadCircle(
            avatarURL,
            logoIamge
        )
        return view


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

}




