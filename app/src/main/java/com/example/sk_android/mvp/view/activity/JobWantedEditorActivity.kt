package com.example.sk_android.mvp.view.activity


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.widget.*
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil


class JobWantedEditActivity : AppCompatActivity() {

    private lateinit var relativeLayout: RelativeLayout
    private lateinit var listView: ListView


    lateinit var imageView: ImageView

    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag


        // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        // getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light))
//        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.holo_red_light))


        relativeLayout {
            backgroundColor=Color.WHITE
            verticalLayout {
                  relativeLayout() {

                    imageView = imageView {

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
                            navigationIconResource=R.mipmap.icon_back_white


                        }.lparams() {
                            width = matchParent
                            height =dip(65)
                            alignParentBottom()

                        }


//                        imageView() {
//
//                            setImageResource(R.mipmap.icon_back_white)
//
//
//                        }.lparams() {
//                            width = wrapContent
//                            height = wrapContent
//                            leftMargin = 50
//                            height =dip(50)
//                            alignParentLeft()
//                            alignParentBottom()
//                        }

                        textView {
                            text = "编辑求职意向"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height =dip(65-getStatusBarHeight(this@JobWantedEditActivity))
                            alignParentBottom()
                        }

                        textView {
                            text = "保存"
                            textColor = Color.WHITE
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER_VERTICAL
                            textSize = 13f
                            onClick {
                                toast("bbb")
                            }
                        }.lparams() {
                            width = dip(52)
                            height =dip(65-getStatusBarHeight(this@JobWantedEditActivity))
                            alignParentRight()
                            alignParentBottom()
                        }
                    }.lparams() {
                        width = matchParent
                        height =dip(65)
                    }
                }.lparams() {
                    width = matchParent
                      height =dip(65)
                  }



                scrollView {
                    isVerticalScrollBarEnabled=false
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }


                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                            topMargin=dip(10)
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }
                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }

                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                                    OptionsPickerView<String>(this@JobWantedEditActivity)
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }

                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                                    OptionsPickerView<String>(this@JobWantedEditActivity)
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }

                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                                    OptionsPickerView<String>(this@JobWantedEditActivity)
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
                                    gravity=Gravity.CENTER
                                }.lparams() {
                                    width = wrapContent
                                    height = matchParent
                                    alignParentLeft()
                                }

                                verticalLayout() {
                                    gravity=Gravity.CENTER_VERTICAL
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
                backgroundResource =R.drawable.job_intention_radius_button
                textColorResource = R.color.buttonTextColor
                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                gravity=Gravity.CENTER
                onClick {
                    toast("xxxx")
                }
            }.lparams() {
                width = matchParent
                height = dip(47)
                leftMargin = 50
                rightMargin = 50
                bottomMargin = dip(15)
                alignParentBottom()
            }

        }

        setActionBar(toolbar1)
//        getActionBar()!!.setDisplayHomeAsUpEnabled(true);
//        StatusBarUtil.setTranslucentForDrawerLayout(this, , 0)
        // StatusBarUtil.setColor(this, R.color.transparent);
        // StatusBarUtil.setColorForDrawerLayout(this, layout, 0)
        StatusBarUtil.setTranslucentForImageView(this@JobWantedEditActivity, 0, toolbar1)

    }

    protected fun setStatusBar() {


        StatusBarUtil.setTranslucentForImageView(this, imageView)
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale  = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }



}
