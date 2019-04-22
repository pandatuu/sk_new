package com.example.sk_android.mvp.view.activity

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.ListAdapter
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class JobWantedManageActivity : AppCompatActivity() {

    private lateinit var toolbar1: Toolbar
    lateinit var imageView: ImageView

    var list = LinkedList<String>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {

            relativeLayout() {
                backgroundColor = Color.BLUE
                imageView = imageView {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setImageResource(R.mipmap.pic_top)
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }
                relativeLayout() {
                    toolbar1 = toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back_white
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                        alignParentBottom()

                    }
                    textView {
                        text = "求職意思を管理する"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.WHITE
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height = dip(65 - getStatusBarHeight(this@JobWantedManageActivity))
                        alignParentBottom()
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }

            relativeLayout {
                backgroundResource = R.color.underToolBar
                textView {
                    text = "求職状態"
                    textSize = 13f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.underToolBarTextColorLeft
                }.lparams() {
                    alignParentLeft()
                    leftMargin = 50
                    width = wrapContent
                    height = matchParent

                }

                lateinit var textView: TextView
                var verticalCornerId = 1
                var verticalCorner = relativeLayout {
                    id = verticalCornerId
                    onClick {
                        toast("xx")
                        var mOptionsPickerView: OptionsPickerView<String> =
                            OptionsPickerView<String>(this@JobWantedManageActivity)
                        var list: ArrayList<String> = ArrayList<String>()
                        list.add("离职-随时到岗")
                        list.add("在职-月内到岗")
                        list.add("在职-考虑机会")
                        list.add("在职-暂不考虑")
                        // 设置数据
                        mOptionsPickerView.setPicker(list);
                        mOptionsPickerView.setTitle("求职意向")
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
                    imageView() {
                        setImageResource(R.mipmap.icon_go_position)


                    }.lparams() {
                        width = wrapContent
                        height = wrapContent
                        alignParentRight()
                        centerVertically()
                        rightMargin = 50
                    }
                }.lparams() {
                    width = dip(35)
                    height = matchParent
                    alignParentRight()

                }

                textView = textView {
                    text = "在职-しばらくは考えない"
                    textSize = 13f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.underToolBarTextColorRight
                    backgroundColor = Color.TRANSPARENT
                }.lparams() {
                    leftOf(verticalCorner.id)
                    width = wrapContent
                    height = matchParent

                }

            }.lparams() {
                width = matchParent
                height = dip(53)

            }

            relativeLayout {
                backgroundColor = Color.WHITE
                val listView = listView() {
                    setVerticalScrollBarEnabled(false)
                    dividerHeight = 0
                    setOnItemClickListener { parent, view, position, id ->

                    }
                }.lparams() {
                    width = matchParent
                    height = matchParent
                    rightMargin = 50
                    leftMargin = 50
                    topMargin = 10
                }
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                list.add("11111")
                listView.adapter = ListAdapter(list)

                relativeLayout() {
                    onClick {
                        toast("xxxx")
                    }
                    backgroundResource = R.drawable.radius
                    relativeLayout {
                        imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_add_position)
                        }.lparams() {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView() {
                            text = "希望職種"
                            textColor = Color.WHITE
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            gravity = Gravity.CENTER
                        }.lparams() {
                            alignParentRight()
                            centerVertically()
                            width = wrapContent
                            height = matchParent
                        }
                    }.lparams() {
                        centerInParent()
                        width = dip(85)
                        height = matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(47)
                    leftMargin = 50
                    rightMargin = 50
                    bottomMargin = dip(15)
                    alignParentBottom()
                }
            }.lparams() {
                width = matchParent
                height = matchParent
            }
        }
        setActionBar(toolbar1)
        getActionBar()!!.setDisplayHomeAsUpEnabled(true);
        StatusBarUtil.setTranslucentForImageView(this@JobWantedManageActivity, 0, toolbar1)

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
