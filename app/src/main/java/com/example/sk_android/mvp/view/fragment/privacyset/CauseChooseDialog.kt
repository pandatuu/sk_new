package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class CauseChooseDialog : Fragment() {

    private lateinit var choose: CauseChoose
    private lateinit var group: RadioGroup
    var name = ""

    companion object {
        fun newInstance(): CauseChooseDialog {

            return CauseChooseDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        choose = activity as CauseChoose
        return createView()
    }

    private fun createView(): View? {
        val list = mutableListOf<String>()
        list.add("仕事が見つかりました")
        list.add("しばらくは仕事を探したくないです。")
        list.add("資料を暴露するのは嫌いです。")
        list.add("いつも挨拶をする人がいます。面倒くさいです。")
        list.add("その他")
        return UI {
            linearLayout {
                gravity = Gravity.CENTER
                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    relativeLayout {
                        textView {
                            text = "原因の選択"
                            textSize = 16f
                            textColor = Color.parseColor("#FF202020")
                        }.lparams(wrapContent, wrapContent) {
                            centerInParent()
                        }
                        toolbar {
                            navigationIconResource = R.mipmap.popup_x
                            this.withTrigger().click {
                                choose.cancleClick()
                            }
                        }.lparams(dip(20), dip(20)) {
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams(matchParent, dip(50)) {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    group = radioGroup {
                        for (causeName in list) {
                            radioButton {
                                leftPadding = dip(10)
                                rightPadding = dip(10)
                                backgroundResource = R.drawable.text_view_bottom_border

                                if (!isChecked) {
                                    buttonDrawableResource = R.mipmap.oval
                                }
                                buttonDrawable = null
                                setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                onCheckedChange { _, isChecked ->
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                        name = causeName
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                                text = causeName
                                textSize = 12f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(matchParent, dip(50))
                        }
                    }.lparams(matchParent, wrapContent){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                    relativeLayout {
                        button {
                            text = "完了"
                            textSize = 16f
                            textColor = Color.parseColor("#FFFFFFFF")
                            backgroundResource = R.drawable.button_shape_orange
                            onClick {
                                if(name != ""){
                                    choose.chooseClick(name)
                                }else {
                                    val toast = Toast.makeText(activity, "理由を選択してください。", Toast.LENGTH_SHORT)
                                    toast.setGravity(Gravity.CENTER,0,0)
                                    toast.show()
                                }
                            }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip(20), dip(30), dip(20), dip(30))
                        }
                    }.lparams(matchParent, dip(100))
                }.lparams(matchParent, wrapContent)
            }
        }.view
    }

    interface CauseChoose {
        fun cancleClick()
        suspend fun chooseClick(name: String)
    }
}