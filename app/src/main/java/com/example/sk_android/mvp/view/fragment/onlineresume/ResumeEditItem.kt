package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduBack
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.view.adapter.onlineresume.ResumeEditEduAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.text.SimpleDateFormat
import java.util.*


class ResumeEditItem : Fragment() {

    interface UserResume {
        fun jumpToBasic()
    }

    companion object {
        fun newInstance(): ResumeEditItem {
            val fragment = ResumeEditItem()
            return fragment
        }
    }

    private lateinit var user: UserResume

    //用户基本信息
    private lateinit var basic: UserBasicInformation
    private lateinit var uri: String
    private lateinit var image: ImageView
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var age: TextView
    private lateinit var eduBack: TextView
    private lateinit var iCanDo: TextView

    //用户求职意向

    //用户工作经历

    //用户项目经历

    //用户教育经历
    private lateinit var eduExprience: RelativeLayout
    var mlist = mutableListOf<EduExperienceModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        user = activity as UserResume
        return createView()
    }

    fun setUserBasicInfo(info: UserBasicInformation) {
        basic = info
        uri = info.avatarURL

        //加载网络图片
        interPic(uri)
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val userAge = year - longToString(info.birthday).substring(0, 4).toInt()
        firstName.text = info.firstName
        lastName.text = info.lastName
        age.text = "${userAge}歳"
        eduBack.text = enumToString(info.educationalBackground)
        iCanDo.text = info.attributes.iCanDo
    }

    fun setEduExprience(list: MutableList<EduExperienceModel>){
//        eduExprience.setList(list)
    }

    private fun createView(): View? {
        return UI {
            relativeLayout {
                verticalLayout {
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            firstName = textView {
                                textSize = 24f
                                textColor = Color.BLACK
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                            }
                            lastName = textView {
                                textSize = 24f
                                textColor = Color.BLACK
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftMargin = dip(5)
                            }
                            imageView {
                                imageResource = R.mipmap.edit_icon
                                this.withTrigger().click {
                                    user.jumpToBasic()
                                }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftMargin = dip(10)
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            topMargin = dip(18)
                        }
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            age = textView {
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
                                leftMargin = dip(5)
                            }
                            eduBack = textView {
                                textSize = 13f
                                textColor = Color.parseColor("#FF666666")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftMargin = dip(5)
                            }
                            view {
                                backgroundColor = Color.parseColor("#FF000000")
                            }.lparams {
                                width = dip(1)
                                height = dip(20)
                                leftMargin = dip(5)
                            }
                            textView {
                                text = "10年以上"
                                textSize = 13f
                                textColor = Color.parseColor("#FF666666")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftMargin = dip(5)
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            topMargin = dip(65)
                        }
                        image = imageView {
                            imageResource = R.mipmap.sk
                        }.lparams {
                            width = dip(70)
                            height = dip(70)
                            centerVertically()
                            alignParentRight()
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(100)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        iCanDo = textView {
                            textSize = 12f
                            textColor = Color.parseColor("#FF333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            topMargin = dip(15)
                            bottomMargin = dip(15)
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    //就職状況
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
                    //希望の業種
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
                    //就職経験
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
                    //プロジェクト経験
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
                    //教育経験
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
                                height = dip(50)
                            }
                            relativeLayout {
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    eduExprience = relativeLayout{
                                        var list: MutableList<EduExperienceModel>? = null
                                        fun setList(clist: MutableList<EduExperienceModel>){
                                            list = clist
                                        }
                                        if(list!=null){
                                            for (item in list!!){
                                                relativeLayout {
                                                    textView {
                                                        text = item.schoolName
                                                        textSize = 14f
                                                        textColor = Color.parseColor("#FF202020")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        centerVertically()
                                                    }
                                                    textView {
                                                        text = "2017.03-2017.06"
                                                        textSize = 12f
                                                        textColor = Color.parseColor("#FF999999")
                                                    }.lparams {
                                                        width = wrapContent
                                                        height = wrapContent
                                                        alignParentRight()
                                                        rightMargin = dip(25)
                                                        centerVertically()
                                                    }
                                                    toolbar {
                                                        navigationIconResource = R.mipmap.icon_go_position
                                                    }.lparams {
                                                        width = dip(20)
                                                        height = dip(20)
                                                        alignParentRight()
                                                        centerVertically()
                                                    }
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    topMargin = dip(20)
                                                }
                                                textView {
                                                    text = item.educationalBackground.toString()
                                                    textSize = 10f
                                                    textColor = Color.parseColor("#FF999999")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    topMargin = dip(40)
                                                }
                                            }
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
                    height = wrapContent
                }
            }
        }.view
    }

    // 类型转换
    private fun longToString(long: Long): String {
        val str = SimpleDateFormat("yyyy-MM-dd").format(Date(long))
        return str
    }

    // 类型转换
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }

    //获取网络图片
    private fun interPic(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.default_avatar)
            .into(image)
    }

    private fun stringToEnum(edu: String): String?{
        when(edu){
            "中卒" -> return EduBack.MIDDLE_SCHOOL.toString()
            "高卒" -> return EduBack.HIGH_SCHOOL.toString()
            "専門卒・短大卒" -> return EduBack.SHORT_TERM_COLLEGE.toString()
            "大卒" -> return EduBack.BACHELOR.toString()
            "修士" -> return EduBack.MASTER.toString()
            "博士" -> return EduBack.DOCTOR.toString()
        }
        return null
    }
    private fun enumToString(edu: EduBack): String?{
        when(edu){
            EduBack.MIDDLE_SCHOOL -> return "中卒"
            EduBack.HIGH_SCHOOL -> return "高卒"
            EduBack.SHORT_TERM_COLLEGE -> return "専門卒・短大卒"
            EduBack.BACHELOR -> return "大卒"
            EduBack.MASTER -> return "修士"
            EduBack.DOCTOR -> return "博士"
        }
        return null
    }
}