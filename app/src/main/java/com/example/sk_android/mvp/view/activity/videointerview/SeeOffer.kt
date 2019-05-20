package com.example.sk_android.mvp.view.activity.videointerview

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SeeOffer : AppCompatActivity(),ShadowFragment.ShadowClick , TipDialogFragment.TipDialogSelect {
    override fun getTipDialogSelect(b: Boolean) {
        closeAlertDialog()
    }

    override fun shadowClicked() {
        closeAlertDialog()
    }

    var shadowFragment: ShadowFragment?=null
    var tipDialogFragment:TipDialogFragment?=null
    lateinit var mainBody: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id1 = 1
        mainBody = frameLayout {
            id = id1
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "offer詳細を見る"
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
                    backgroundResource = R.mipmap.shading
                    scrollView {
                        isVerticalScrollBarEnabled = false
                        verticalLayout {
                            textView {
                                text = "聘用通知书/offer"
                                textSize = 16f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                            textView {
                                text = "拝啓"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text =
                                    "このたびは、弊社の求人にご応募いただきましてありがとうございました。また、先日はお忙しい中をご足労いただきましたこと、重ねてお礼申し上げます。厳正なる選考の結果、貴殿を採用することに決定いたしましたのでご通知申し上げます。"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text =
                                    "つきましては、同封の書類を良くお読みいただき、必要事項をご記入の上、入社日にご持参下さいますようお願い申し上げます。なお、応募書類は当社人事部にてお預かりさせていただきますのでご了承ください。今後とも宜しくお願い申し上げます。"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "敬具"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "1．提出書類入社承諾書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "誓約書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "身元保証書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "2．入社日令和○○年○○月○○日（○曜日） 朝○時○分までに○Ｆ人事部におこしください"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams{
                        setMargins(dip(32), dip(110), dip(32), 0)
                        width = matchParent
                        height = dip(410)
                    }

                    relativeLayout {
                        button {
                            backgroundResource = R.drawable.button_shape_grey
                            text = "このofferを拒否する"
                            textSize = 13f
                            textColor = Color.WHITE
                            onClick {
                                showAlertDialog()
                            }
                        }.lparams{
                            width = dip(150)
                            height = dip(50)
                            alignParentLeft()
                        }
                        button {
                            backgroundResource = R.drawable.button_shape_orange
                            text = "このofferを承認する"
                            textSize = 13f
                            textColor = Color.WHITE
                            onClick {
                                toast("このofferを承認する")
                            }
                        }.lparams{
                            width = dip(150)
                            height = dip(50)
                            alignParentRight()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        setMargins(dip(25),dip(40),dip(25),0)
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

    //打开弹窗
    fun showAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance()
            mTransaction.add(mainBody.id,shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)


        tipDialogFragment= TipDialogFragment.newInstance(1,"拒否すると企業にお知らせし ますが、確かに拒否しますか？")
        mTransaction.add(mainBody.id, tipDialogFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    fun closeAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(tipDialogFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(tipDialogFragment!!)
            tipDialogFragment=null
        }


        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()
    }
}