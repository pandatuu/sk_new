package com.example.sk_android.mvp.view.activity.privacySet

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.ArrayList

class PrivacySetActivity : AppCompatActivity() {

    lateinit var texView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "プライバシー設定"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                verticalLayout {
                    //履歴書の表示
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.vc
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "履歴書の表示"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        texView = textView {
                            text = "ブラックリストは有効"
                            textSize = 12f
                            textColor = Color.parseColor("#FFB3B3B3")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            rightMargin = dip(20)
                            alignParentRight()
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_go_position
                        }.lparams{
                            width = dip(15)
                            height = wrapContent
                            alignParentRight()
                            centerVertically()
                        }
                        onClick {
                            var mOptionsPickerView: OptionsPickerView<String> =
                                OptionsPickerView<String>(this@PrivacySetActivity)
                            var list: ArrayList<String> = ArrayList<String>()
                            list.add("完全に公開")
                            list.add("ホワイトリスト有効")
                            list.add("ブラックリストは有効")
                            list.add("1")
                            list.add("2")
                            list.add("3")
                            list.add("4")

                            // 设置数据
                            mOptionsPickerView.setPicker(list);
                            mOptionsPickerView.setTitle("履歴書の表示")
                            // 设置选项单位
                            mOptionsPickerView.setOnOptionsSelectListener(object :
                                OptionsPickerView.OnOptionsSelectListener {
                                override fun onOptionsSelect(option1: Int, option2: Int, option3: Int) {
                                    var sex: String = list.get(option1)
                                    texView.text = sex
                                }
                            })
                            mOptionsPickerView.show()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                    //ブラックリスト
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.black_list
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ブラックリスト"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_go_position
                        }.lparams{
                            width = dip(15)
                            height = wrapContent
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                    //ホワイトリスト
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.white_list
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ホワイトリスト"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.icon_go_position
                        }.lparams{
                            width = dip(15)
                            height = wrapContent
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                    view {
                        backgroundColor = Color.parseColor("#FFF6F6F6")
                    }.lparams{
                        width = matchParent
                        height = dip(8)
                    }
                    //ビデオ履歴書有効
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.open_video_cv
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ビデオ履歴書有効"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                            isChecked = true
                        }.lparams{
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                    //ビデオ履歴書有効
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.show_work_experience
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "ビデオ履歴書有効"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                            isChecked = true
                        }.lparams{
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                    //猟師は私に連絡する
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        imageView {
                            imageResource = R.mipmap.allow_liaison
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            centerVertically()
                        }
                        textView {
                            text = "猟師は私に連絡する"
                            textSize = 13f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(25)
                            centerVertically()
                        }
                        switch {
                            setThumbResource(R.drawable.thumb)
                            setTrackResource(R.drawable.track)
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        leftMargin = dip(15)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }

            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}