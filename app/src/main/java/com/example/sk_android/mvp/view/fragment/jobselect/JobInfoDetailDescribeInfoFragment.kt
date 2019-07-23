package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray

class JobInfoDetailDescribeInfoFragment : Fragment() {

    private var mContext: Context? = null
    var contentText: String = ""

    lateinit var desContent: TextView
    lateinit var getmore: TextView

    lateinit var plusView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(contentText: String): JobInfoDetailDescribeInfoFragment {
            var fragment = JobInfoDetailDescribeInfoFragment()
            fragment.contentText = contentText
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity

        return fragmentView
    }

    private fun createView(): View {


        var intent = activity!!.intent
        var content = intent.getStringExtra("content")
        var plus = intent.getStringExtra("plus")
        //content="1、是是是是是是是是是;\n2、少时诵诗书所所"

        var plusStr = ""
        if(plus!=null){
            var array = JSONArray(plus)
            for (i in 0..array.length() - 1) {
                plusStr = plusStr+(i + 1).toString() + "、" + array.get(i) + "\n"
            }
        }


        var view = UI {
            linearLayout {
                verticalLayout {

                    textView {
                        text = "業務内容"
                        textSize = 14f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        textColorResource = R.color.gray5c
                    }.lparams {
                        topMargin = dip(10)
                    }

                    desContent = textView {
                        text = contentText
                        textSize = 14f
                        textColorResource = R.color.gray5c
                    }.lparams {
                        topMargin = dip(10)
                    }




                    plusView = verticalLayout {
                        visibility = View.GONE
                        textView {
                            text = "特技"
                            textSize = 14f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            textColorResource = R.color.gray5c
                        }.lparams {
                            topMargin = dip(10)
                        }

                        textView {
                            text = plusStr
                            textSize = 14f
                            textColorResource = R.color.gray5c
                        }.lparams {
                            topMargin = dip(10)
                        }

                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }



                    linearLayout {
                        gravity = Gravity.RIGHT
                        getmore = textView {
                            text = "全部を調べる"
                            textSize = 13f
                            textColorResource = R.color.normalTextColor
                            setTypeface(Typeface.DEFAULT_BOLD)
                            setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    getmore.visibility = View.INVISIBLE
                                    plusView.visibility = View.VISIBLE
                                    desContent.text = content
                                }
                            })
                        }

                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(15)
                    }


                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(5)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view

        var length = 10;
        if (content != null) {
            if (content.length <= length) {
                length = content.length
                getmore.visibility = View.INVISIBLE
                plusView.visibility = View.VISIBLE
            }
            var briefContent = content.substring(0, length)
            desContent.text = briefContent
        }




        return view

    }


}




