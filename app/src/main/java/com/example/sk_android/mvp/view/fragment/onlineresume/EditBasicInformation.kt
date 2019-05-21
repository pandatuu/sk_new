package com.example.sk_android.mvp.view.fragment.onlineresume

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.codbking.widget.DatePickDialog
import com.codbking.widget.OnSureLisener
import com.codbking.widget.bean.DateType
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.util.*
import java.lang.String.format
import java.text.SimpleDateFormat


class EditBasicInformation : Fragment() {

    val sexList = listOf("男","女")
    val chooseList = listOf("撮影","アルバムから選ぶ","黙認")
    lateinit var mContext : Context
    lateinit var middleware : Middleware
    companion object {
        fun newInstance(context : Context): EditBasicInformation {
            val fragment = EditBasicInformation()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        middleware =  activity as Middleware
        val dialog = DatePickDialog(mContext)
        //设置上下年分限制
        dialog.setYearLimt(5)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_ALL)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm")

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    verticalLayout {
                        // Head frame
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "アバター"
                                textSize = 17f
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.sk
                                setOnClickListener(object : View.OnClickListener{
                                    override fun onClick(v: View?) {
                                        middleware.addListFragment()
                                    }
                                })
                            }.lparams {
                                width = dip(60)
                                height = dip(60)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(100)
                        }
                        // Name
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "お名前"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "張魏"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                        }
                        // Sex
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "性别"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "男"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
                                    tool.navigationIconResource = R.mipmap.icon_down
                                    selector("请选择", sexList.toList()) { _, i ->
                                        toast("你的选择是：${sexList[i]}")
                                        textv.text = sexList[i]
                                        tool.navigationIconResource = R.mipmap.icon_go_position
                                    }
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Phone
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "電話番号"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "1388888888"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Email
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "メールアドレス"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "devil@gmail.com"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Line
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "Line番号"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "cgland"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Birth Date
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "出生年月"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = ""
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
//                                setOnClickListener(object : View.OnClickListener{
//                                    override fun onClick(v: View?) {
//                                        val dialog = DatePickDialog(mContext)
//                                        //设置上下年分限制
//                                        dialog.setYearLimt(5)
//                                        //设置标题
//                                        dialog.setTitle("选择时间")
//                                        //设置类型
//                                        dialog.setType(DateType.TYPE_YMD)
//                                        //设置消息体的显示格式，日期格式
//                                        dialog.setMessageFormat("yyyy-MM")
//                                        dialog.setOnSureLisener(object : OnSureLisener{
//                                            override fun onSure(date: Date?) {
//                                                textv.text = SimpleDateFormat("yyyy-MM").format(date)
//                                            }
//                                        })
//                                        dialog.show()
//                                    }
//                                })
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Job Date
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "就職期日"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "スキルを選択してください"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
                                    var mOptionsPickerView: OptionsPickerView<String> =
                                        OptionsPickerView<String>(mContext)
                                    var list: ArrayList<String> = ArrayList<String>()
                                    list.add("应届生")
                                    val aa = 2019
                                    for (listadd in 0..39)
                                    {
                                        list.add((aa-listadd).toString())
                                    }
                                    list.add("1990以前")
                                    // 设置数据
                                    mOptionsPickerView.setPicker(list);
                                    mOptionsPickerView.setTitle("就職期日")
                                    // 设置选项单位
                                    mOptionsPickerView.setOnOptionsSelectListener(object :
                                        OptionsPickerView.OnOptionsSelectListener {
                                        override fun onOptionsSelect(option1: Int, option2: Int, option3: Int) {
                                            var sex: String = list.get(option1)
                                            textv.text = sex
                                        }
                                    })
                                    mOptionsPickerView.show()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Job Skill
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "仕事スキル"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "スキルを選択してください"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // My Skill
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "個人スキル"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "スキルを選択してください"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // EditView
                        relativeLayout {
                            textView {
                                text = "私ができること"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            editText {
                                backgroundResource = R.drawable.area_text
                                gravity = top
                            }.lparams {
                                width = matchParent
                                height = dip(170)
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(220)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
    interface Middleware {

        fun addListFragment()
    }
}