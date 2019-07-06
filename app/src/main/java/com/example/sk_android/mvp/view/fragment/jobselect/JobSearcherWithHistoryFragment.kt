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
import android.content.Intent
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import android.view.KeyEvent.KEYCODE_ENTER
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray


class JobSearcherWithHistoryFragment : Fragment() {

    public lateinit var editText: EditText
    lateinit var delete: ImageView
    var imageId=1
    var editTextId=2
    private var mContext: Context? = null
    private lateinit var sendMessage:SendSearcherText

    private lateinit var cityName:TextView

    //是手动输入的(查询出中间列表)  还是  直接赋值的(直接显示职位列表)
    private var  inputFlag=true

    //是根据内容改变事件(查询出中间列表)    还是  点击了搜索按钮(直接显示职位列表)
    private var  byInputChange=true


    private var type_job_or_company_search=2  //1:职位 2,公司

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobSearcherWithHistoryFragment {
            val fragment = JobSearcherWithHistoryFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        sendMessage =  activity as SendSearcherText
        return fragmentView
    }

    fun createView(): View {
        getIntentData()
        return UI {
            linearLayout {
                linearLayout  {
                    linearLayout {
                        isFocusable=true
                        gravity=Gravity.CENTER_VERTICAL
                        backgroundResource=R.drawable.radius_border_searcher_theme_border
                        linearLayout{
                            gravity=Gravity.CENTER_VERTICAL


                            setOnClickListener(object :View.OnClickListener{

                                override fun onClick(v: View?) {

                                    var intent = Intent(mContext, CitySelectActivity::class.java).also {
                                        it.putExtra("mostChooseNum",1)
                                        startActivityForResult(it,4)
                                    }
                                    activity!!.overridePendingTransition(R.anim.right_in,R.anim.left_out)
                                }

                            })


                            cityName= textView {
                                text="请选择"
                                textColorResource=R.color.normalTextColor
                                textSize=13f
                            }.lparams {
                                leftMargin=dip(15)
                            }

                            imageView {
                                imageResource=R.mipmap.icon_down_search

                            }.lparams {
                                rightMargin=dip(8)
                                leftMargin=dip(15)
                            }
                        }.lparams(
                            height= matchParent
                        )

                       var hide= editText{
                           isFocusable=true
                           visibility=View.GONE
                        }
                        editText=editText  {
                            showSoftInputOnFocus
                            id=editTextId
                            backgroundColor=Color.TRANSPARENT
                            gravity=Gravity.CENTER_VERTICAL
                            textSize=13f
                            isFocusable=true
                            singleLine = true
                            hint="肩書き名を入力する"
                            imeOptions=EditorInfo.IME_ACTION_SEARCH
                            setOnFocusChangeListener(object : View.OnFocusChangeListener {
                                override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                                    if(!hasFocus){
//                                        delete.visibility=View.INVISIBLE
//                                    }else if(!text.trim().isEmpty()){
//                                        toast(text)
//                                        delete.visibility=View.VISIBLE
//                                    }
                                }
                            })
                            addTextChangedListener(object:TextWatcher{
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun afterTextChanged(s: Editable?) {
                                    if(inputFlag){
                                        //手动输入
                                        byInputChange=true
                                        if(!s!!.toString().trim().equals("")){
                                            delete.visibility=View.VISIBLE
                                            //请求过度列表
                                            if(type_job_or_company_search==1){
                                                getMiddleList_position(s!!.toString().trim())
                                            }else if(type_job_or_company_search==2){
                                                getMiddleList_company(s!!.toString().trim())
                                            }
                                        }else{
                                            delete.visibility=View.INVISIBLE
                                            sendMessage.sendMessage(""  ,     JSONArray() )
                                        }
                                    }else{

                                        //直接赋值的方式
                                        if(!s!!.toString().trim().equals("")){
                                            delete.visibility=View.VISIBLE

                                        }else{
                                            delete.visibility=View.INVISIBLE
                                        }
                                        inputFlag=true
                                        clearFocus()
                                        hide.requestFocus()
                                    }

                                }

                            })
                            setOnEditorActionListener(object: TextView.OnEditorActionListener{
                                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                                    //以下方法防止两次发送请求
                                    if (actionId === EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                                        if(event!=null){
                                            println(event)
                                            //直接请求终极列表

                                            sendMessage.sendInputText(text.toString())
                                            EmoticonsKeyboardUtils.closeSoftKeyboard(editText)
                                            byInputChange=false
                                        }
//                                        when (event!!.getAction()) {
//                                            KeyEvent.ACTION_UP -> {
//                                                //发送请求
//                                                toast("5555")
//                                                EmoticonsKeyboardUtils.closeSoftKeyboard(editText)
//                                                return true
//                                            }
//                                            else -> return true
//                                        }
                                    }
                                    return false
                                }

                            })
                        }.lparams(width = 0, height = matchParent,weight = 1.toFloat()) {
                            leftMargin=dip(12)

                        }



                        linearLayout {
                            backgroundColor=Color.TRANSPARENT
                            gravity=Gravity.CENTER
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    editText.setText("")
                                }
                            })
                            delete=imageView {
                                id=imageId
                                imageResource=R.mipmap.icon_delete_circle
                                visibility=View.INVISIBLE

                            }.lparams {
                                rightMargin=dip(11)
                                leftMargin=dip(11)
                            }
                        }.lparams {
                            height= matchParent
                            width= wrapContent
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
                                activity!!.finish()
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

        fun sendMessage(msg:String,list:JSONArray )

        fun cancle()

        fun sendInputText(text:String)
    }


    fun setCityName(name:String){
        cityName.text=name
    }

    fun setEditeTextShow(str:String){


        inputFlag=false
        editText.setText(str)
        editText.clearFocus()
    }

    //查询中间过度列表
    fun  getMiddleList_position(name:String){

        //请求搜藏
        var requestAddress = RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
        requestAddress.create(RecruitInfoApi::class.java)
            .getRecruitInfoMiddleList(
                name
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("中间过度列表请求成功(职位)")
                println(it)
                var list=JSONArray(it.toString())


                if(byInputChange){
                    sendMessage.sendMessage(name,list)
                }else{
                    //不显示中间列表
                    byInputChange=true
                }

            }, {
                //失败
                println("中间过度列表请求失败(职位)")
                println(it)
            })

    }



    //直接查询终极列表
    fun getMiddleList_company(name:String){


        //请求搜藏
        var requestAddress = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
        requestAddress.create(CompanyInfoApi::class.java)
            .getCompanyInfoMiddleList(
                name
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("中间过度列表请求成功(公司)")
                println(it)
                var list=JSONArray(it.toString())


                if(byInputChange){
                    sendMessage.sendMessage(name,list)
                }else{
                    //不显示中间列表
                    byInputChange=true
                }

            }, {
                //失败
                println("中间过度列表请求失败(公司)")
                println(it)
            })



    }




    //得到传递的数据
    fun getIntentData(){
        var intent= activity!!.intent
        type_job_or_company_search=intent.getIntExtra("searchType",1)
    }


}

