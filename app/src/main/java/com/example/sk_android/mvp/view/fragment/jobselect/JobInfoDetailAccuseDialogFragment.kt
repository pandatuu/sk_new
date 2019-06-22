package com.example.sk_android.mvp.view.fragment.jobselect

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView


class JobInfoDetailAccuseDialogFragment : Fragment() {

    private var mContext: Context? = null

    lateinit var editeArea:EditText
    lateinit var edite:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {


        fun newInstance(): JobInfoDetailAccuseDialogFragment {
            val fragment = JobInfoDetailAccuseDialogFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {



        var intent=activity!!.intent
        var typeAccuse=intent.getStringExtra("type")

        return UI {
            linearLayout {
                relativeLayout()  {
                    verticalLayout {
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                editeArea.clearFocus()
                                edite.clearFocus()

                            }
                        })
                        backgroundResource=R.drawable.radius_dialog_white
                        verticalLayout {
                            textView{
                                gravity=Gravity.CENTER_VERTICAL
                                textSize=14f
                                textColorResource=R.color.gray5c
                                text="告発カテゴリ："+typeAccuse
                            }.lparams {

                                width= matchParent
                                height=dip(50)
                            }

                            textView{
                               backgroundColorResource=R.color.grayE6
                            }.lparams {
                                width= matchParent
                                height=dip(1)
                            }

                        }.lparams {
                            width= matchParent
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height= wrapContent
                        }


                        textView {
                            text="告発内容"
                            textSize=13f
                            visibility=View.GONE
                            textColorResource=R.color.gray5c
                        }.lparams {
                            topMargin=dip(15)
                            leftMargin=dip(15)
                        }

                        edite=editText {
                            backgroundResource=R.drawable.border_graycd
                            textSize=14f
                            visibility=View.GONE
                            setOnFocusChangeListener(object : View.OnFocusChangeListener {
                                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                                    if(!hasFocus){
                                        var imm: InputMethodManager =activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                        imm.hideSoftInputFromWindow(getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                }
                            })
                        }.lparams {
                            width= matchParent
                            height=dip(37)
                            topMargin=dip(7)
                            leftMargin=dip(15)
                            rightMargin=dip(15)

                        }

                        textView {
                            text="告発内容"
                            textSize=13f
                            textColorResource=R.color.gray5c
                        }.lparams {
                            topMargin=dip(15)
                            leftMargin=dip(15)
                            topMargin=dip(13)
                        }

                        editeArea=editText {
                            backgroundResource=R.drawable.border_graycd
                            singleLine=false
                            gravity=Gravity.TOP
                            textSize=14f
                            hint="ここに告発内容を入力してください。"
                            setOnFocusChangeListener(object : View.OnFocusChangeListener {
                                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                                    if(!hasFocus){
                                        var imm: InputMethodManager =activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                        imm.hideSoftInputFromWindow(getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                }
                            })
                        }.lparams {
                            width= matchParent
                            height=dip(100)
                            topMargin=dip(7)
                            leftMargin=dip(15)
                            rightMargin=dip(15)

                        }

                        textView {
                            text="添付ファイル"
                            textSize=13f
                            textColorResource=R.color.gray5c
                        }.lparams {
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            topMargin=dip(17)
                        }

                        linearLayout {
                            backgroundColorResource=R.color.originColor
                            gravity=Gravity.CENTER_VERTICAL


                            imageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.ico_add)

                            }.lparams() {

                                leftMargin=dip(10)

                            }

                            textView {
                                textSize=12f
                                text="写真を添付します"
                                textColorResource=R.color.gray63
                            }.lparams {
                                leftMargin=dip(5)
                            }
                        }.lparams {
                            topMargin=dip(7)
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(35)
                            width=dip(150)
                        }







                    }.lparams(width = matchParent, height =matchParent){
                        centerInParent()
                    }
                }.lparams(width = matchParent, height =matchParent){
                }
            }
        }.view
    }



}

