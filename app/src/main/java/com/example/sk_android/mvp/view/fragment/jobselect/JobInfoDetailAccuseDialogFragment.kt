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
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.PictrueScroll
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.ArrayList


class JobInfoDetailAccuseDialogFragment : Fragment() {

    interface AddPictrue{
        fun addPic()
        fun clickItem(urlItem: String)
    }

    private var mContext: Context? = null
    private lateinit var addPic:AddPictrue
    var typeAccuse=""

    lateinit var editeArea:EditText
    var list = ArrayList<String>()

    companion object {
        fun newInstance(): JobInfoDetailAccuseDialogFragment {
            val fragment = JobInfoDetailAccuseDialogFragment()

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addPic = activity as AddPictrue
        var fragmentView=createView()
        return fragmentView
    }

    fun getContent(): String{
        return editeArea.text.toString().trim()
    }
    fun getReportType(): String{
        return typeAccuse
    }

    fun createView(): View {

        var intent=activity!!.intent
        if(intent.getStringExtra("type")!=null)
            typeAccuse=intent.getStringExtra("type")

        return UI {
            linearLayout {
                relativeLayout()  {
                    verticalLayout {
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                editeArea.clearFocus()
                            }
                        })
                        backgroundColor = Color.WHITE
                        verticalLayout {
                            textView{
                                gravity=Gravity.CENTER_VERTICAL
                                textSize=14f
                                textColorResource=R.color.gray5c
                                text="通報カテゴリ："+typeAccuse
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
                            text="通報内容"
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
                            hint="ここに通報内容を入力してください。"
                            setOnFocusChangeListener(object : View.OnFocusChangeListener {
                                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                                    if(!hasFocus){
                                        var imm: InputMethodManager =activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                        imm.hideSoftInputFromWindow(getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                }
                            })
                            leftPadding=dip(10)
                            rightPadding=dip(10)
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
                            onClick {
                                addPic.addPic()
                            }
                        }.lparams {
                            topMargin=dip(7)
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(35)
                            width=dip(150)
                        }

                    }.lparams(width = matchParent, height = wrapContent){
                        centerInParent()
                    }
                }.lparams(width = matchParent, height = wrapContent){
                }
            }
        }.view
    }


}

