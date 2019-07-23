package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import withTrigger

class RecruitInfoSelectbarFragment : Fragment() {


    private var mContext: Context? = null
    private lateinit var selectBar: SelectBar

    var textShow1: String = ""
    var textShow2: String = ""
    var textShow3: String = ""
    var textShow4: String = ""


    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView

    private lateinit var text1Circle: TextView
    private lateinit var text3Circle: TextView
    private lateinit var text4Circle: TextView


    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var image3: ImageView
    private lateinit var image4: ImageView


    private var selectIndex: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(text1: String, text2: String, text3: String, text4: String): RecruitInfoSelectbarFragment {
            val fragment = RecruitInfoSelectbarFragment()
            if (!text1.equals(""))
                fragment.textShow1 = text1
            else
                fragment.textShow1 = ""

            if (!text2.equals(""))
                fragment.textShow2 = text2
            else
                fragment.textShow2 = "地点"

            if (!text3.equals(""))
                fragment.textShow3 = text3
            else
                fragment.textShow3 = ""

            if (!text4.equals(""))
                fragment.textShow4 = text4
            else
                fragment.textShow4 = ""

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        selectBar = activity as SelectBar
        return fragmentView
    }

    fun createView(): View {

        return UI {
            verticalLayout {
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout {
                        linearLayout {
                            this.withTrigger(200).click {
                                selectBar.getSelectBarItem(0)

                                if (selectIndex != 0) {
                                    selectIndex = 0
                                    //未选中时,改为选中
                                    text1.textColorResource = R.color.themeColor
                                    text2.textColorResource = R.color.gray89
                                    text3.textColorResource = R.color.gray89
                                    text4.textColorResource = R.color.gray89

                                    text1Circle.textColorResource = R.color.themeColor
                                    text1Circle.backgroundResource = R.drawable.circle_border_theme_color


                                    text3Circle.textColorResource = R.color.gray89
                                    text3Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text4Circle.textColorResource = R.color.gray89
                                    text4Circle.backgroundResource = R.drawable.circle_border_gray89


                                    image1.setImageResource(R.mipmap.icon_up_home)
                                    image2.setImageResource(R.mipmap.icon_down_home)
                                    image3.setImageResource(R.mipmap.icon_down_home)
                                    image4.setImageResource(R.mipmap.icon_down_home)


                                } else {
                                    text1.textColorResource = R.color.gray89
                                    image1.setImageResource(R.mipmap.icon_down_home)

                                    text1Circle.textColorResource = R.color.gray89
                                    text1Circle.backgroundResource = R.drawable.circle_border_gray89

                                    selectIndex = -1

                                }


                            }



                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            text1 = textView {
                                textColorResource = R.color.gray89
                                text = "仕事タイプ"
                                textSize = 12f
                                gravity = Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height = matchParent
                            }


                            text1Circle = textView {
                                backgroundResource = R.drawable.circle_border_gray89
                                textColorResource = R.color.gray89
                                text = textShow1
                                if (text.equals("")) {
                                    visibility = View.GONE
                                } else {
                                    visibility = View.VISIBLE
                                }
                                textSize = 10f
                                gravity = Gravity.CENTER
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                leftMargin = dip(2)
                                width = dip(15)
                                height = dip(15)
                            }


                            image1 = imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height = dip(5)
                                leftMargin = dip(7)
                            }
                            rightPadding = dip(5)
                            leftPadding = dip(5)
                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout {

                            this.withTrigger(200).click {
                                selectBar.getSelectBarItem(1)


                                if (selectIndex != 1) {
                                    selectIndex = 1
                                    //未选中时,改为选中
                                    text1.textColorResource = R.color.gray89
                                    text2.textColorResource = R.color.themeColor
                                    text3.textColorResource = R.color.gray89
                                    text4.textColorResource = R.color.gray89

                                    text1Circle.textColorResource = R.color.gray89
                                    text1Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text3Circle.textColorResource = R.color.gray89
                                    text3Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text4Circle.textColorResource = R.color.gray89
                                    text4Circle.backgroundResource = R.drawable.circle_border_gray89

                                    image1.setImageResource(R.mipmap.icon_down_home)
                                    image2.setImageResource(R.mipmap.icon_up_home)
                                    image3.setImageResource(R.mipmap.icon_down_home)
                                    image4.setImageResource(R.mipmap.icon_down_home)


                                } else {
                                    text2.textColorResource = R.color.gray89
                                    image2.setImageResource(R.mipmap.icon_down_home)

                                    selectIndex = -1

                                }


                            }



                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            text2 = textView {
                                textColorResource = R.color.gray89
                                text = textShow2
                                textSize = 12f
                                gravity = Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height = matchParent
                            }

                            image2 = imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height = dip(5)
                                leftMargin = dip(7)
                            }
                            rightPadding = dip(5)
                            leftPadding = dip(5)
                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout {

                            this.withTrigger(200).click {
                                selectBar.getSelectBarItem(2)

                                if (selectIndex != 2) {
                                    selectIndex = 2
                                    //未选中时,改为选中
                                    text1.textColorResource = R.color.gray89
                                    text2.textColorResource = R.color.gray89
                                    text3.textColorResource = R.color.themeColor
                                    text4.textColorResource = R.color.gray89

                                    text1Circle.textColorResource = R.color.gray89
                                    text1Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text3Circle.textColorResource = R.color.themeColor
                                    text3Circle.backgroundResource = R.drawable.circle_border_theme_color

                                    text4Circle.textColorResource = R.color.gray89
                                    text4Circle.backgroundResource = R.drawable.circle_border_gray89

                                    image1.setImageResource(R.mipmap.icon_down_home)
                                    image2.setImageResource(R.mipmap.icon_down_home)
                                    image3.setImageResource(R.mipmap.icon_up_home)
                                    image4.setImageResource(R.mipmap.icon_down_home)


                                } else {
                                    text3.textColorResource = R.color.gray89
                                    image3.setImageResource(R.mipmap.icon_down_home)

                                    text3Circle.textColorResource = R.color.gray89
                                    text3Circle.backgroundResource = R.drawable.circle_border_gray89

                                    selectIndex = -1

                                }


                            }



                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            text3 = textView {
                                textColorResource = R.color.gray89
                                text = "会社"
                                textSize = 12f
                                gravity = Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height = matchParent
                            }
                            text3Circle = textView {
                                backgroundResource = R.drawable.circle_border_gray89
                                textColorResource = R.color.gray89
                                text = textShow3
                                if (text.equals("")) {
                                    visibility = View.GONE
                                } else {
                                    visibility = View.VISIBLE
                                }
                                textSize = 10f
                                gravity = Gravity.CENTER
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                leftMargin = dip(2)
                                width = dip(15)
                                height = dip(15)
                            }

                            image3 = imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height = dip(5)
                                leftMargin = dip(7)
                            }
                            rightPadding = dip(5)
                            leftPadding = dip(5)
                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout {


                            this.withTrigger(200).click {
                                selectBar.getSelectBarItem(3)
                                if (selectIndex != 3) {
                                    selectIndex = 3
                                    //未选中时,改为选中
                                    text1.textColorResource = R.color.gray89
                                    text2.textColorResource = R.color.gray89
                                    text3.textColorResource = R.color.gray89
                                    text4.textColorResource = R.color.themeColor

                                    text1Circle.textColorResource = R.color.gray89
                                    text1Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text3Circle.textColorResource = R.color.gray89
                                    text3Circle.backgroundResource = R.drawable.circle_border_gray89

                                    text4Circle.textColorResource = R.color.themeColor
                                    text4Circle.backgroundResource = R.drawable.circle_border_theme_color

                                    image1.setImageResource(R.mipmap.icon_down_home)
                                    image2.setImageResource(R.mipmap.icon_down_home)
                                    image3.setImageResource(R.mipmap.icon_down_home)
                                    image4.setImageResource(R.mipmap.icon_up_home)


                                } else {
                                    text4.textColorResource = R.color.gray89
                                    image4.setImageResource(R.mipmap.icon_down_home)

                                    text4Circle.textColorResource = R.color.gray89
                                    text4Circle.backgroundResource = R.drawable.circle_border_gray89

                                    selectIndex = -1
                                }


                            }



                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            text4 = textView {
                                textColorResource = R.color.gray89
                                text = "希望"
                                textSize = 12f
                                gravity = Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height = matchParent
                            }

                            text4Circle = textView {
                                backgroundResource = R.drawable.circle_border_gray89
                                textColorResource = R.color.gray89
                                text = textShow4
                                if (text.equals("")) {
                                    visibility = View.GONE
                                } else {
                                    visibility = View.VISIBLE
                                }
                                textSize = 10f
                                gravity = Gravity.CENTER
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                leftMargin = dip(2)
                                width = dip(15)
                                height = dip(15)
                            }
                            image4 = imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height = dip(5)
                                leftMargin = dip(7)
                            }
                            rightPadding = dip(5)
                            leftPadding = dip(5)
                        }.lparams {
                            height = matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }
                }.lparams {
                    width = matchParent
                    height = dip(51)
                }

                view {
                    backgroundColorResource = R.color.grayba
                }.lparams {
                    width = matchParent
                    height = dip(1)
                }

            }
        }.view
    }

     interface SelectBar {

        fun getSelectBarItem(index: Int)
    }


}

