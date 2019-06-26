package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
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
import android.widget.Toolbar
import com.example.sk_android.mvp.view.activity.jobselect.JobSearchWithHistoryActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity

class CompanyInfoActionBarFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): CompanyInfoActionBarFragment {
            return CompanyInfoActionBarFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                relativeLayout() {

                    imageView {

                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.pic_top)

                    }.lparams() {
                        width = matchParent
                        height =dip(65)

                    }


                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                        }.lparams() {
                            width = matchParent
                            height =dip(65)
                            alignParentBottom()

                        }

                        var textViewLeftId=1
                        var textViewLeft=textView {
                            id=textViewLeftId
                            text = "会社"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams() {
                            width = wrapContent
                            height =dip(65-getStatusBarHeight(this@CompanyInfoActionBarFragment.context!!))
                            alignParentBottom()
                            centerInParent()
                            leftMargin=dip(15)
                        }





                        relativeLayout {




                            linearLayout {
                                backgroundColor=Color.RED
                                gravity=Gravity.CENTER
                                setOnClickListener(object :View.OnClickListener{

                                    override fun onClick(v: View?) {

                                        var intent = Intent(mContext, JobSearchWithHistoryActivity::class.java)
                                        intent.putExtra("searchType",2)
                                        startActivity(intent)

                                    }

                                })
                                var addImageId=2
                                imageView {
                                    id=addImageId
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.icon_search_home)

                                }.lparams() {
                                    width = dip(17)
                                    height =dip(17)
                                    leftMargin=dip(10)
                                    rightMargin=dip(10)

                                }
                            }.lparams {
                                width = wrapContent
                                alignParentRight()
                                centerVertically()
                                height= matchParent
                            }

                        }.lparams() {
                            width = wrapContent
                            height =dip(65-getStatusBarHeight(this@CompanyInfoActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin=dip(10)
                        }




                    }.lparams() {
                        width = matchParent
                        height =dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height =dip(65)
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

}




