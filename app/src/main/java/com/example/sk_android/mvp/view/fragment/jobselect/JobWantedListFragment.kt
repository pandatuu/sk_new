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
import android.graphics.Typeface
import com.airsaid.pickerviewlibrary.OptionsPickerView
import java.util.ArrayList

class JobWantedListFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var deleteButton: DeleteButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): JobWantedListFragment {
            val fragment = JobWantedListFragment()
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
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView() {
                                        text = "期望职位"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView() {
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
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView() {
                                        text = "期望行业"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView() {
                                            text = "移动互联网 | 计算机软件"
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
                                    textView() {
                                        text = "工作城市"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView() {
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
                                    lateinit var textView: TextView
                                    onClick {
                                        var mOptionsPickerView: OptionsPickerView<String> =
                                            OptionsPickerView<String>(this@JobWantedListFragment.context)
                                        var list: ArrayList<String> = ArrayList<String>()
                                        list.add("小时工")
                                        list.add("临时工")
                                        list.add("正式工")
                                        // 设置数据
                                        mOptionsPickerView.setPicker(list);
                                        mOptionsPickerView.setTitle("工作类别")
                                        // 设置选项单位
                                        mOptionsPickerView.setOnOptionsSelectListener(object :
                                            OptionsPickerView.OnOptionsSelectListener {
                                            override fun onOptionsSelect(option1: Int, option2: Int, option3: Int) {
                                                var sex: String = list.get(option1)
                                                textView.text = sex.toString()
                                            }
                                        });
                                        mOptionsPickerView.show();
                                    }
                                    textView() {
                                        text = "工作类别"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView = textView() {
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
                                    lateinit var textView: TextView
                                    onClick {
                                        var mOptionsPickerView: OptionsPickerView<String> =
                                            OptionsPickerView<String>(this@JobWantedListFragment.context)
                                        var list: ArrayList<String> = ArrayList<String>()
                                        list.add("面议")
                                        list.add("5k-10k")
                                        list.add("10k-15k")
                                        list.add("15k-20k")
                                        // 设置数据
                                        mOptionsPickerView.setPicker(list);
                                        mOptionsPickerView.setTitle("薪资要求(单位:千元)")
                                        // 设置选项单位
                                        mOptionsPickerView.setOnOptionsSelectListener(object :
                                            OptionsPickerView.OnOptionsSelectListener {
                                            override fun onOptionsSelect(option1: Int, option2: Int, option3: Int) {
                                                var sex: String = list.get(option1)
                                                textView.text = sex.toString()
                                            }
                                        });
                                        mOptionsPickerView.show();
                                    }
                                    textView() {
                                        text = "薪资要求"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView = textView() {
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
                                    lateinit var textView: TextView
                                    onClick {
                                        var mOptionsPickerView: OptionsPickerView<String> =
                                            OptionsPickerView<String>(this@JobWantedListFragment.context)
                                        var list: ArrayList<String> = ArrayList<String>()
                                        list.add("企业直聘")
                                        list.add("校招")
                                        list.add("社招")
                                        list.add("熟人推荐")
                                        // 设置数据
                                        mOptionsPickerView.setPicker(list);
                                        mOptionsPickerView.setTitle("招聘方式")
                                        // 设置选项单位
                                        mOptionsPickerView.setOnOptionsSelectListener(object :
                                            OptionsPickerView.OnOptionsSelectListener {
                                            override fun onOptionsSelect(option1: Int, option2: Int, option3: Int) {
                                                var sex: String = list.get(option1)
                                                textView.text = sex.toString()
                                            }
                                        });
                                        mOptionsPickerView.show();
                                    }
                                    textView() {
                                        text = "招聘方式"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        textView = textView() {
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
                        textColorResource = R.color.buttonTextColor
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        gravity = Gravity.CENTER
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
    }
}

