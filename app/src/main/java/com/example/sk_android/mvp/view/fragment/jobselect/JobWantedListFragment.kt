package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import java.util.ArrayList

class JobWantedListFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var deleteButton: DeleteButton
    var  operateType:Int=1

    private lateinit var wantJob:TextView
    private lateinit var city:TextView
    private lateinit var jobType:TextView
    private lateinit var salary:TextView
    private lateinit var recruitWay:TextView
    private lateinit var overseasRecruit:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(type:Int): JobWantedListFragment {
            val fragment = JobWantedListFragment()
            fragment.operateType=type
            return fragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        deleteButton = activity as DeleteButton


        return fragmentView
    }

    fun createView(): View {
        return UI {
            linearLayout {
                relativeLayout {
                    verticalLayout {
                        scrollView {
                            isVerticalScrollBarEnabled = false
                            verticalLayout() {
                                verticalLayout() {

                                    setOnClickListener(object :View.OnClickListener{

                                        override fun onClick(v: View?) {


                                            var intent = Intent(mContext, JobSelectActivity::class.java)
                                            startActivityForResult(intent,3)
                                            activity!!.overridePendingTransition(R.anim.right_in,R.anim.left_out)

                                        }

                                    })


                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView() {
                                        text = "期望职位"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        wantJob=textView() {
                                            text = "PHP"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(10)
                                    rightMargin = 50
                                    leftMargin = 50
                                }

                                verticalLayout() {

                                    setOnClickListener(object :View.OnClickListener{

                                        override fun onClick(v: View?) {

                                            var intent = Intent(mContext, CitySelectActivity::class.java).also {
                                                startActivityForResult(it,4)
                                            }
                                            activity!!.overridePendingTransition(R.anim.right_in,R.anim.left_out)

                                        }

                                    })


                                    backgroundResource = R.drawable.text_view_bottom_border
                                   textView() {
                                        text = "工作城市"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        city=textView() {
                                            text = "成都"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }

                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = 50
                                    leftMargin = 50
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.oneDialogCLick("工作类别")
                                    }
                                    textView() {
                                        text = "工作类别"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        jobType = textView() {
                                            text = "小时工"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.oneDialogCLick("工作类别")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent

                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = 50
                                    leftMargin = 50
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.twoDialogCLick("薪资要求")
                                    }
                                    textView() {
                                        text = "薪资要求"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        salary = textView() {
                                            text = "10k-15K"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.twoDialogCLick("薪资要求")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent

                                    rightMargin = 50
                                    leftMargin = 50
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.oneDialogCLick("招聘方式")
                                    }
                                    textView() {
                                        text = "招聘方式"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        recruitWay = textView {
                                            text = "企业直聘"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.oneDialogCLick("招聘方式")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = 50
                                    leftMargin = 50
                                }



                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.oneDialogCLick("海外招聘")
                                    }
                                    textView() {
                                        text = "海外採用"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        overseasRecruit = textView {
                                            text = "受け入れない"
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.oneDialogCLick("海外招聘")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = 50
                                    leftMargin = 50
                                }

                                verticalLayout() {
                                }.lparams() {
                                    width = matchParent
                                    height = dip(80)
                                    rightMargin = 50
                                    leftMargin = 50
                                }
                            }
                        }
                    }.lparams() {
                        alignParentTop()
                        width = matchParent
                        height = matchParent
                    }
                    textView() {
                        text = "删除本条"
                        //  backgroundColorResource = R.color.buttonColor
                        backgroundResource = R.drawable.job_intention_radius_button
                        textColorResource = R.color.white
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        gravity = Gravity.CENTER
                        if(operateType==2){
                            visibility=View.GONE
                        }
                        setOnClickListener(object:View.OnClickListener{
                            override fun onClick(v: View?) {
                                deleteButton.delete()
                            }
                        })
                    }.lparams() {
                        width = matchParent
                        height = dip(47)
                        leftMargin = 50
                        rightMargin = 50
                        bottomMargin = dip(15)
                        alignParentBottom()
                    }
                }.lparams(width = matchParent, height = matchParent) {

                }
            }
        }.view
    }

    interface DeleteButton {
        fun delete()
        fun oneDialogCLick(s: String)
        fun twoDialogCLick(s: String)
    }



    fun setWantJobText(str:String){
        wantJob.text=str
    }

    fun setCity(str:String){
        city.text=str
    }

    fun setJobtype(text: String){
        jobType.text = text
    }

    fun setSalary(text: String){
        salary.text = text
    }

    fun setRecruitWay(text: String){
        recruitWay.text = text
    }

    fun setOverseasRecruit(text: String){
        overseasRecruit.text = text
    }
}

