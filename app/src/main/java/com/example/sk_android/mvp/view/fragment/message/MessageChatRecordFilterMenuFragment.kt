package com.example.sk_android.mvp.view.fragment.message

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar

class MessageChatRecordFilterMenuFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null

    lateinit var textView1:TextView
    lateinit var textView2:TextView
    lateinit var textView3:TextView
    lateinit var textView4:TextView


    private lateinit var filterMenu: FilterMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): MessageChatRecordFilterMenuFragment {
            return MessageChatRecordFilterMenuFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        filterMenu=activity as FilterMenu
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {

                linearLayout  {
                    backgroundResource=R.drawable.radius_border_searcher_theme_border
                    gravity=Gravity.CENTER_VERTICAL
                   textView1= textView {
                        text="全部"
                        gravity=Gravity.CENTER
                        textSize=14f
                        textColorResource=R.color.black33
                        backgroundResource=R.color.transparent
                        setOnClickListener(object:View.OnClickListener{
                            override fun onClick(v: View?) {
                                resetBackground()
                                textColor=Color.WHITE
                                backgroundResource=R.drawable.radius_left_theme
                                filterMenu.getFilterMenuselect(0)
                            }

                        })
                    }.lparams {
                        height= matchParent
                        width=0
                        weight=1f
                    }


                    textView {
                        backgroundResource=R.drawable.left_border_theme
                    }.lparams {
                        height= matchParent
                        width=dip(1)
                    }


                    textView2=  textView {
                        text="気が合う"
                        gravity=Gravity.CENTER
                        textSize=14f
                        textColorResource=R.color.black33
                        backgroundResource=R.color.transparent
                        setOnClickListener(object:View.OnClickListener{
                            override fun onClick(v: View?) {
                                resetBackground()
                                textColor=Color.WHITE
                                backgroundColorResource=R.color.themeColor
                                filterMenu.getFilterMenuselect(1)

                            }

                        })
                    }.lparams {
                        height= matchParent
                        width=0
                        weight=1f
                    }



                    textView {
                        backgroundResource=R.drawable.left_border_theme
                    }.lparams {
                        height= matchParent
                        width=dip(1)
                    }


                    textView3=  textView {
                        text="まあまあ"
                        gravity=Gravity.CENTER
                        textSize=14f
                        textColorResource=R.color.black33
                        backgroundResource=R.color.transparent
                        setOnClickListener(object:View.OnClickListener{
                            override fun onClick(v: View?) {
                                resetBackground()
                                textColor=Color.WHITE
                                backgroundColorResource=R.color.themeColor
                                filterMenu.getFilterMenuselect(2)

                            }

                        })

                    }.lparams {
                        height= matchParent
                        width=0
                        weight=1f
                    }

                    textView {
                        backgroundResource=R.drawable.left_border_theme
                    }.lparams {
                        height= matchParent
                        width=dip(1)
                    }


                    textView4=  textView {
                        text="考えない"
                        gravity=Gravity.CENTER
                        textSize=14f
                        textColorResource=R.color.black33
                        backgroundResource=R.color.transparent
                        setOnClickListener(object:View.OnClickListener{
                            override fun onClick(v: View?) {
                                resetBackground()
                                textColor=Color.WHITE
                                backgroundResource=R.drawable.radius_right_theme
                                filterMenu.getFilterMenuselect(3)

                            }

                        })

                    }.lparams {
                        height= matchParent
                        width=0
                        weight=1f
                    }





                }.lparams() {
                    width = matchParent
                    height=dip(32)
                    bottomMargin=dip(18)
                    topMargin=dip(18)
                    leftMargin=dip(15)
                    rightMargin=dip(15)
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


    fun resetBackground(){
        textView1.backgroundResource=R.color.transparent
        textView2.backgroundResource=R.color.transparent
        textView3.backgroundResource=R.color.transparent
        textView4.backgroundResource=R.color.transparent
        textView1.textColorResource=R.color.black33
        textView2.textColorResource=R.color.black33
        textView3.textColorResource=R.color.black33
        textView4.textColorResource=R.color.black33
    }


    interface FilterMenu{
       fun getFilterMenuselect(index:Int)
    }

}




