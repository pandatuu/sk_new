package com.example.sk_android.mvp.view.activity.privacyset

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.umeng.message.PushAgent
import com.example.sk_android.mvp.view.fragment.common.EditAlertDialog
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CauseChooseActivity : AppCompatActivity(),ShadowFragment.ShadowClick,EditAlertDialog.EditDialogSelect {
    override fun shadowClicked() {
        closeAlertDialog()
    }

    override fun getEditDialogSelect() {
        closeAlertDialog()
    }

    var shadowFragment: ShadowFragment?=null
    var editAlertDialog:EditAlertDialog?=null
    lateinit var mainBody: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        val mainId = 1
        mainBody = frameLayout {
            id = mainId
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            finish()
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "原因の選択"
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

                relativeLayout {
                    var group = radioGroup {
                        //仕事が見つかりました
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "仕事が見つかりました"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //しばらくは仕事を探したくないです
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "しばらくは仕事を探したくないです"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //資料を暴露するのは嫌いです。
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "資料を暴露するのは嫌いです。"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //いつも挨拶をする人がいます。面倒くさいです。
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "いつも挨拶をする人がいます。面倒くさいです。"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
                        //その他
                        radioButton {
                            backgroundResource = R.drawable.text_view_bottom_border
                            text = "その他"
                            textSize = 13f
                            textColor = Color.parseColor("#202020")
                            backgroundResource = R.drawable.text_view_bottom_border
                            buttonDrawable = null
                            setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.hook,0)
                                        showAlertDialog()
                                    } else {
                                        setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.oval,0)
                                    }
                                }
                            })
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams{
                            width = matchParent
                            height = dip(62)
                            setMargins(dip(15),0,dip(15),0)
                        }
//                        check(ids)
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    textView {
                        backgroundResource = R.drawable.button_shape_orange
                        text = "完了"
                        textSize = 16f
                        textColor = Color.parseColor("#FFFFFFFF")
                        gravity = Gravity.CENTER
                        onClick {
//                            group.clearCheck()
                            val intent = Intent(this@CauseChooseActivity, PrivacySetActivity::class.java)
                            startActivity(intent)
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(55)
                        alignParentBottom()
                        setMargins(dip(15),0,dip(15),0)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomPadding = dip(20)
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

        editAlertDialog= EditAlertDialog.newInstance("理由を入力してください",null,"キャンセル","確定", 14f)
        mTransaction.add(mainBody.id, editAlertDialog!!)
        mTransaction.commit()
    }

    //关闭弹窗
    fun closeAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(editAlertDialog!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog=null
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