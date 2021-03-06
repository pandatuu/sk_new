package com.example.sk_android.mvp.view.fragment.message

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
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils

class MessageChatRecordSearchActionBarFragment : Fragment() {

    public lateinit var editText: EditText
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
        fun newInstance(): MessageChatRecordSearchActionBarFragment {
            val fragment = MessageChatRecordSearchActionBarFragment()
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
                linearLayout  {
                    linearLayout {
                        gravity=Gravity.CENTER_VERTICAL
                        backgroundResource=R.drawable.radius_border_cd

                        delete=imageView {
                            id=imageId
                            imageResource=R.mipmap.icon_search
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    editText.setText("")
                                }
                            })
                        }.lparams {
                            leftMargin=dip(18)
                        }



                        editText=editText  {
                            showSoftInputOnFocus
                            id=editTextId
                            backgroundColor=Color.TRANSPARENT
                            gravity=Gravity.CENTER_VERTICAL
                            textSize=13f
                            singleLine = true
                            hint="肩書き名を入力する"
                            imeOptions=EditorInfo.IME_ACTION_SEARCH
                            setOnFocusChangeListener(object : View.OnFocusChangeListener {
                                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                                    if(!hasFocus){
                                        delete.visibility=View.INVISIBLE
                                    }else if(!text.trim().isEmpty()){
                                        delete.visibility=View.VISIBLE
                                    }
                                }
                            })
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

                                    //以下方法防止两次发送请求
                                    if (actionId === EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                                        if(event!=null){
                                            println(event)
                                            //直接请求终极列表
                                            EmoticonsKeyboardUtils.closeSoftKeyboard(editText)
                                        }

                                    }
                                    return false
                                }

                            })

                        }.lparams(width = 0, height = matchParent,weight = 1.toFloat()) {
                            leftMargin=dip(14)

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
                        width= 0
                        weight=1f
                        height=dip(38)
                        topMargin=dip(getStatusBarHeight(mContext!!)+11)
                    }

                    textView {
                        text="キャンセル"
                        gravity=Gravity.CENTER
                        textSize=12f
                        textColorResource=R.color.black33
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                sendMessage.cancle()
                            }
                        })
                    }.lparams {
                        height=dip(38)
                        topMargin=dip(getStatusBarHeight(mContext!!)+11)
                        leftMargin=dip(9)
                    }


                }.lparams(width = matchParent, height = dip(60+getStatusBarHeight(mContext!!))){
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

    interface SendSearcherText {

        fun sendMessage(msg:String )

        fun cancle()

    }

}

