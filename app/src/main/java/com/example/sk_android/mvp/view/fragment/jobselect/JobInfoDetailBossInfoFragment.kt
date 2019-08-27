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
import android.text.TextUtils
import android.widget.ImageView
import android.widget.LinearLayout
import click
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.pingerx.imagego.core.strategy.loadCircle
import withTrigger

class JobInfoDetailBossInfoFragment : Fragment() {

    private var mContext: Context? = null

    var thePositionNum=0


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
        val fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {



        val intent=activity!!.intent
        val companyName=intent.getStringExtra("companyName")
        val userName=intent.getStringExtra("userName")
        val userPositionName=intent.getStringExtra("userPositionName")
        var avatarURL=intent.getStringExtra("avatarURL")
        if(avatarURL.indexOf(";")!=-1){
            avatarURL = avatarURL.split(";")[0]
        }

        var userId=intent.getStringExtra("userId")
        val organizationId=intent.getStringExtra("organizationId")

        lateinit var logoIamge:ImageView

        val view= UI {
            linearLayout {
                verticalLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity=Gravity.CENTER_VERTICAL
                    relativeLayout {

                        this.withTrigger().click {



                            val intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
                                intent.putExtra("companyId",organizationId)
                                intent.putExtra("positionNum",thePositionNum)

                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            

                        }




                        backgroundResource=R.drawable.box_shadow_weak
                        val iamgeId=31
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
                                ellipsize = TextUtils.TruncateAt.END
                                maxLines = 1
                                textColorResource=R.color.themeBule
                                textSize=13f

                            }.lparams {
                                width= wrapContent
                            }

                            textView {
                                val name = "$userName·$userPositionName"
                                text = name
                                ellipsize = TextUtils.TruncateAt.END
                                maxLines = 1
//                                text=
//                                    if(name.length>13) name.substring(0,13)+"..." else name
                                textColorResource=R.color.themeBule
                                textSize=15f
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)

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
                            width= wrapContent
                            rightOf(logoIamge)
                            leftMargin=dip(17)
                            rightMargin = dip(25)
                            centerVertically()
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

    fun setPositionNum(i :Int){
        thePositionNum=i
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            val scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

}




