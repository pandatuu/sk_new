package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class AddJobExperienceFrag : Fragment() {

    lateinit var mContext : Context
    companion object {
        fun newInstance(context : Context): AddJobExperienceFrag {
            val fragment = AddJobExperienceFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    verticalLayout {
                        // 会社名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "会社名"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "会社名を入力してください"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 業種
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
                            relativeLayout {
                                var textv = textView {
                                    text = "会社の業種に入力してください"
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
                            height = dip(85)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 職種
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "職種"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "職種を選択してください。"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 職名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "職名"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "職名を入力してください"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 属する部門
                        relativeLayout {
                            textView {
                                text = "属する部門"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "必須でない"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        view{
                            backgroundColor = Color.parseColor("#FFF6F6F6")
                        }.lparams{
                            width = matchParent
                            height = dip(8)
                        }
                        // 開始時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "開始時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "開始時間を選択する"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 終了時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "終了時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                var textv = textView {
                                    text = "終了時間を選択する"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 主要役職
                        relativeLayout {
                            textView {
                                text = "主要役職"
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
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //滑动框1
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            var textv = textView {
                                text = "この会社に私の情報は表示しない"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                                centerVertically()
                            }
                            var tool = switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                                isChecked = true
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //滑动框2
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            var textv = textView {
                                text = "履歴書に会社フルネームを隠す"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                                centerVertically()
                            }
                            var tool = switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                                isChecked = true
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
}