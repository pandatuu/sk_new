package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context

class JobSearcherFragment : Fragment() {

    lateinit var editText: EditText
    lateinit var delete: ImageView
    var imageId=1
    var editTextId=2
    private var mContext: Context? = null
    private lateinit var sendMessage:SendSearcherText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobSearcherFragment {
            val fragment = JobSearcherFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        sendMessage =  activity as SendSearcherText
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                relativeLayout  {
                    linearLayout {
                        gravity=Gravity.CENTER_VERTICAL
                        backgroundResource=R.drawable.radius_border_searcher

                       imageView {
                            imageResource=R.mipmap.icon_search

                        }.lparams {

                            leftMargin=dip(15)
                        }
                        editText=editText  {
                            id=editTextId
                            backgroundColor=Color.TRANSPARENT
                            gravity=Gravity.CENTER_VERTICAL
                            textSize=14f
                            singleLine = true
                            hint="肩書き名を入力する"
                            imeOptions=EditorInfo.IME_ACTION_SEARCH
                            addTextChangedListener(object:TextWatcher{
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun afterTextChanged(s: Editable?) {
                                    if(!s!!.toString().trim().equals("")){
                                        delete.visibility=View.VISIBLE
                                    }else{
                                        delete.visibility=View.INVISIBLE
                                    }
                                    sendMessage.sendMessage(s!!.toString())
                                }

                            })
                            setOnEditorActionListener(object: TextView.OnEditorActionListener{
                                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                                    return false
                                }

                            })
                        }.lparams(width = 0, height = matchParent,weight = 1.toFloat()) {
                            leftMargin=dip(12)

                        }

                        delete=imageView {
                            id=imageId
                            imageResource=R.mipmap.icon_delete_circle
                            visibility=View.INVISIBLE
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    editText.setText("")
                                }
                            })
                        }.lparams {
                            rightMargin=dip(11)
                            leftMargin=dip(11)
                        }


                    }.lparams {
                        width= matchParent
                        height=dip(38)
                        topMargin=dip(11)
                    }


                }.lparams(width = matchParent, height = dip(60)){
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view
    }

    interface SendSearcherText {

        fun sendMessage(msg:String )
    }

}

