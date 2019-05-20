package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI


class ResumeEditItem : Fragment() {


    companion object {
        fun newInstance(): ResumeEditItem {
            val fragment = ResumeEditItem()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        return fragmentView
    }


    private fun createView(): View? {
        return UI {
            relativeLayout {
                scrollView {
                    linearLayout {
                        relativeLayout {
                            verticalLayout {
                                relativeLayout {
                                    backgroundResource = R.mipmap.job_photo_upload
                                    button {
                                        backgroundResource = R.drawable.fifteen_radius_button
                                        text = "仕事の写真/ビデオをアップロド"
                                        textSize = 10f
                                        textColor = Color.WHITE
                                        onClick {
                                            toast("yeah!!!!!!!!!!!!")
                                        }
                                    }.lparams{
                                        width = dip(170)
                                        height = dip(35)
                                        centerInParent()
                                    }
                                }.lparams {
                                    width = dip(370)
                                    height = dip(370)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.twenty_three_radius_top
                                    relativeLayout {
                                        relativeLayout {
                                            backgroundResource = R.drawable.text_view_bottom_border
                                            textView {
                                                text = "張魏"
                                                textSize = 24f
                                                textColor = Color.BLACK
                                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                centerVertically()
                                            }
                                            imageView {
                                                imageResource = R.mipmap.edit_icon
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                leftMargin = dip(60)
                                                centerVertically()
                                            }
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            topMargin = dip(18)
                                        }
                                        relativeLayout {
                                            textView {
                                                text = "25歳"
                                                textSize = 13f
                                                textColor = Color.parseColor("#FF666666")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                            view {
                                                backgroundColor = Color.parseColor("#FF000000")
                                            }.lparams {
                                                width = dip(1)
                                                height = dip(20)
                                                leftMargin = dip(35)
                                            }
                                            textView {
                                                text = "修士"
                                                textSize = 13f
                                                textColor = Color.parseColor("#FF666666")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                leftMargin = dip(42)
                                            }
                                            view {
                                                backgroundColor = Color.parseColor("#FF000000")
                                            }.lparams {
                                                width = dip(1)
                                                height = dip(20)
                                                leftMargin = dip(75)
                                            }
                                            textView {
                                                text = "10年以上"
                                                textSize = 13f
                                                textColor = Color.parseColor("#FF666666")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                leftMargin = dip(82)
                                            }
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            topMargin = dip(65)
                                        }
                                        imageView {
                                            imageResource = R.mipmap.sk
                                        }.lparams {
                                            width = dip(70)
                                            height = dip(70)
                                            centerVertically()
                                            alignParentRight()
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                        leftMargin = dip(15)
                                        rightMargin = dip(15)
                                    }
                                }.lparams{
                                    width = matchParent
                                    height = dip(100)
                                    topMargin = dip(-100)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    verticalLayout {
                                        textView {
                                            text = "1.java/php/go/c#/c++をよく知っている"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF333333")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            text = "2.vscode/phpstromをよく知っている"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF333333")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            text = "3.windows/macをよく知っている"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF333333")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        centerVertically()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(80)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView {
                                        text = "就職状況"
                                        textSize = 16f
                                        textColor = Color.parseColor("#FF202020")
                                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        centerVertically()
                                        alignParentLeft()
                                    }
                                    textView {
                                        text = "職場に勤め、チャンスを考える"
                                        textSize = 13f
                                        textColor = Color.parseColor("#FF5C5C5C")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        centerVertically()
                                        alignParentRight()
                                        rightMargin = dip(15)
                                    }
                                    toolbar {
                                        navigationIconResource = R.mipmap.icon_go_position
                                    }.lparams {
                                        width = dip(20)
                                        height = dip(20)
                                        centerVertically()
                                        alignParentRight()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(80)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    verticalLayout {
                                        relativeLayout {
                                            textView {
                                                text = "希望の業種"
                                                textSize = 16f
                                                textColor = Color.parseColor("#FF202020")
                                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                centerVertically()
                                                alignParentLeft()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(65)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.HORIZONTAL
                                                textView {
                                                    text = "PHP開発エンジニア"
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                }
                                                textView {
                                                    text = "30万-60万"
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    leftMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                alignParentLeft()
                                                topMargin = dip(20)
                                                alignParentTop()
                                            }
                                            textView {
                                                text = "東京都 IT ソフトウェアエンジニア"
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(40)
                                                alignParentLeft()
                                            }
                                            toolbar {
                                                navigationIconResource = R.mipmap.icon_go_position
                                            }.lparams {
                                                width = dip(22)
                                                height = dip(22)
                                                alignParentRight()
                                                centerVertically()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(65)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.HORIZONTAL
                                                textView {
                                                    text = "視覚デザイン"
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                }
                                                textView {
                                                    text = "30万-60万"
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    leftMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                alignParentLeft()
                                                topMargin = dip(20)
                                                alignParentTop()
                                            }
                                            textView {
                                                text = "東京都 IT ソフトウェアエンジニア"
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(40)
                                                alignParentLeft()
                                            }
                                            toolbar {
                                                navigationIconResource = R.mipmap.icon_go_position
                                            }.lparams {
                                                width = dip(22)
                                                height = dip(22)
                                                alignParentRight()
                                                centerVertically()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(65)
                                        }
                                        relativeLayout {
                                            backgroundResource = R.drawable.text_view_bottom_border
                                            relativeLayout {
                                                backgroundResource = R.drawable.four_radius_grey_button
                                                textView {
                                                    text = "就職希望を追加する"
                                                    textSize = 16f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerInParent()
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = dip(50)
                                                centerInParent()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(85)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(280)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    verticalLayout {
                                        relativeLayout {
                                            textView {
                                                text = "就職経験"
                                                textSize = 16f
                                                textColor = Color.parseColor("#FF202020")
                                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                centerVertically()
                                                alignParentLeft()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(60)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.VERTICAL
                                                relativeLayout {
                                                    relativeLayout {
                                                        textView {
                                                            text = "ミラノ整形"
                                                            textSize = 14f
                                                            textColor = Color.parseColor("#FF202020")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                        }
                                                        textView {
                                                            text = "2018.03-1019.03"
                                                            textSize = 12f
                                                            textColor = Color.parseColor("#FF999999")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                            alignParentRight()
                                                            rightMargin = dip(20)
                                                        }
                                                        toolbar {
                                                            navigationIconResource = R.mipmap.icon_go_position
                                                        }.lparams {
                                                            width = dip(22)
                                                            height = dip(22)
                                                            alignParentRight()
                                                        }
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentLeft()
                                                        topMargin = dip(20)
                                                    }
                                                    textView {
                                                        text = "UIデザイナー"
                                                        textSize = 10f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        topMargin = dip(40)
                                                        alignParentLeft()
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                                linearLayout {
                                                    orientation = LinearLayout.VERTICAL
                                                    textView {
                                                        text = "1、ソフト面における美術設計、クリエイティブ作業，及び 製作業務を担当する。"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                    textView {
                                                        text = "2、さまざまな関连ソフトのユーザーグループによると…"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    topMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = matchParent
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(130)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.VERTICAL
                                                relativeLayout {
                                                    relativeLayout {
                                                        textView {
                                                            text = "ミラノ整形"
                                                            textSize = 14f
                                                            textColor = Color.parseColor("#FF202020")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                        }
                                                        textView {
                                                            text = "2018.03-1019.03"
                                                            textSize = 12f
                                                            textColor = Color.parseColor("#FF999999")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                            alignParentRight()
                                                            rightMargin = dip(20)
                                                        }
                                                        toolbar {
                                                            navigationIconResource = R.mipmap.icon_go_position
                                                        }.lparams {
                                                            width = dip(22)
                                                            height = dip(22)
                                                            alignParentRight()
                                                        }
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentLeft()
                                                        topMargin = dip(20)
                                                    }
                                                    textView {
                                                        text = "UIデザイナー"
                                                        textSize = 10f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        topMargin = dip(40)
                                                        alignParentLeft()
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                                linearLayout {
                                                    orientation = LinearLayout.VERTICAL
                                                    textView {
                                                        text = "1、ソフト面における美術設計、クリエイティブ作業，及び 製作業務を担当する。"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                    textView {
                                                        text = "2、さまざまな関连ソフトのユーザーグループによると…"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    topMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = matchParent
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(130)
                                        }
                                        relativeLayout {
                                            backgroundResource = R.drawable.text_view_bottom_border
                                            relativeLayout {
                                                backgroundResource = R.drawable.four_radius_grey_button
                                                textView {
                                                    text = "就職経験を追加する"
                                                    textSize = 16f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerInParent()
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = dip(50)
                                                centerInParent()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(80)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(400)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    verticalLayout {
                                        relativeLayout {
                                            textView {
                                                text = "プロジェクト経験"
                                                textSize = 16f
                                                textColor = Color.parseColor("#FF202020")
                                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                centerVertically()
                                                alignParentLeft()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(60)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.VERTICAL
                                                relativeLayout {
                                                    relativeLayout {
                                                        textView {
                                                            text = "ABCシステム"
                                                            textSize = 14f
                                                            textColor = Color.parseColor("#FF202020")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                        }
                                                        textView {
                                                            text = "2017.03-2017.06"
                                                            textSize = 12f
                                                            textColor = Color.parseColor("#FF999999")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                            alignParentRight()
                                                            rightMargin = dip(20)
                                                        }
                                                        toolbar {
                                                            navigationIconResource = R.mipmap.icon_go_position
                                                        }.lparams {
                                                            width = dip(22)
                                                            height = dip(22)
                                                            alignParentRight()
                                                        }
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentLeft()
                                                        topMargin = dip(20)
                                                    }
                                                    textView {
                                                        text = "開発者"
                                                        textSize = 10f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        topMargin = dip(40)
                                                        alignParentLeft()
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                                linearLayout {
                                                    orientation = LinearLayout.VERTICAL
                                                    textView {
                                                        text = "同プロジェクトはPHP+Java+C#+Goの開発に成功し、"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                    textView {
                                                        text = "HTML5+CSS3を先端に使用して。同プロジェクトはPHP…"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    topMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = matchParent
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(115)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.VERTICAL
                                                relativeLayout {
                                                    relativeLayout {
                                                        textView {
                                                            text = "CG原画"
                                                            textSize = 14f
                                                            textColor = Color.parseColor("#FF202020")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                        }
                                                        textView {
                                                            text = "2016.09-2016.12"
                                                            textSize = 12f
                                                            textColor = Color.parseColor("#FF999999")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                            alignParentRight()
                                                            rightMargin = dip(20)
                                                        }
                                                        toolbar {
                                                            navigationIconResource = R.mipmap.icon_go_position
                                                        }.lparams {
                                                            width = dip(22)
                                                            height = dip(22)
                                                            alignParentRight()
                                                        }
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentLeft()
                                                        topMargin = dip(20)
                                                    }
                                                    textView {
                                                        text = "原画師"
                                                        textSize = 10f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        topMargin = dip(40)
                                                        alignParentLeft()
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                                linearLayout {
                                                    orientation = LinearLayout.VERTICAL
                                                    textView {
                                                        text = "ゲーム原画は、ゲーム企画を抽象的にする発想を负担し、目 に见えるキャラクターやシーンに、具体的な视覚的枠组…"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF333333")
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    topMargin = dip(10)
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = matchParent
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(115)
                                        }
                                        relativeLayout {
                                            backgroundResource = R.drawable.text_view_bottom_border
                                            relativeLayout {
                                                backgroundResource = R.drawable.four_radius_grey_button
                                                textView {
                                                    text = "プロジェクト経験を追加する"
                                                    textSize = 16f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerInParent()
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = dip(50)
                                                centerInParent()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(80)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(370)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.twenty_three_radius_bottom
                                    verticalLayout {
                                        relativeLayout {
                                            textView {
                                                text = "教育経験"
                                                textSize = 16f
                                                textColor = Color.parseColor("#FF202020")
                                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                centerVertically()
                                                alignParentLeft()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(60)
                                        }
                                        relativeLayout {
                                            linearLayout {
                                                orientation = LinearLayout.VERTICAL
                                                relativeLayout {
                                                    relativeLayout {
                                                        textView {
                                                            text = "東京大学"
                                                            textSize = 14f
                                                            textColor = Color.parseColor("#FF202020")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                        }
                                                        textView {
                                                            text = "2017.03-2017.06"
                                                            textSize = 12f
                                                            textColor = Color.parseColor("#FF999999")
                                                        }.lparams {
                                                            width = wrapContent
                                                            height = wrapContent
                                                            alignParentRight()
                                                            rightMargin = dip(20)
                                                        }
                                                        toolbar {
                                                            navigationIconResource = R.mipmap.icon_go_position
                                                        }.lparams {
                                                            width = dip(22)
                                                            height = dip(22)
                                                            alignParentRight()
                                                        }
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentLeft()
                                                        topMargin = dip(20)
                                                    }
                                                    textView {
                                                        text = "修士 IT"
                                                        textSize = 10f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        topMargin = dip(40)
                                                        alignParentLeft()
                                                    }
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = matchParent
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(60)
                                        }
                                        relativeLayout {
                                            backgroundResource = R.drawable.text_view_bottom_border
                                            relativeLayout {
                                                backgroundResource = R.drawable.four_radius_grey_button
                                                textView {
                                                    text = "教育経験を追加する"
                                                    textSize = 16f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerInParent()
                                                }
                                            }.lparams {
                                                width = matchParent
                                                height = dip(50)
                                                centerInParent()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = dip(80)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(200)
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