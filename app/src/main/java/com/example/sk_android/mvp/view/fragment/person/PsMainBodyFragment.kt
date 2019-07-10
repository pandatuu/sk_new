package com.example.sk_android.mvp.view.fragment.person

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.myhelpfeedback.HelpFeedbackActivity
import com.example.sk_android.mvp.view.activity.mysystemsetup.SystemSetupActivity
import com.example.sk_android.mvp.view.activity.onlineresume.ResumeEdit
import com.example.sk_android.mvp.view.activity.person.InterviewListActivity
import com.example.sk_android.mvp.view.activity.person.MyRecruitListActivity
import com.example.sk_android.mvp.view.activity.privacyset.PrivacySetActivity
import com.example.sk_android.mvp.view.activity.resume.ResumeListActivity
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.io.Serializable

class PsMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var statuText:TextView
    lateinit var oneTextView: TextView
    lateinit var twoTextView: TextView
    lateinit var threeTextView: TextView
    lateinit var fourTextView: TextView
    var workAttributes = mapOf<String, Serializable>()
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")


    companion object {
        fun newInstance(): PsMainBodyFragment {
            val fragment = PsMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        jobwanted = activity as JobWanted
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
        return UI {
            scrollView {
                verticalLayout {
                    backgroundColorResource = R.color.grayF6
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        linearLayout {
                            //挑战到已沟通的职位  已经收藏
                            this.withTrigger().click {
                                var intent = Intent(mContext, MyRecruitListActivity::class.java)
                                intent.putExtra("type",1)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }

                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            oneTextView = textView {
                                text = "0"
                                textColor = R.color.black20
                                textSize = 16f
                            }.lparams(width = wrapContent,height = dip(23)){}

                            textView {
                                textResource = R.string.contact
                                textColor = R.color.black33
                                textSize = 12f
                            }.lparams(width = wrapContent,height = dip(17)){}


                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        linearLayout {
                            //跳转到面试信息
                            this.withTrigger().click {
                                var intent = Intent(mContext, InterviewListActivity::class.java)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }




                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            twoTextView = textView {
                                text = "0"
                                textColor = R.color.black20
                                textSize = 16f
                            }.lparams(width = wrapContent,height = dip(23)){}

                            textView {
                                textResource = R.string.interView
                                textColor = R.color.black33
                                textSize = 12f
                            }.lparams(width = wrapContent,height = dip(17)){}


                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        linearLayout {

                            //挑战到已沟通的职位  已经投递
                            this.withTrigger().click {
                                var intent = Intent(mContext, MyRecruitListActivity::class.java)
                                intent.putExtra("type",3)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }

                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            threeTextView = textView {
                                text = "0"
                                textColor = R.color.black20
                                textSize = 16f
                            }.lparams(width = wrapContent,height = dip(23)){}

                            textView {
                                textResource = R.string.submitted
                                textColor = R.color.black33
                                textSize = 12f
                            }.lparams(width = wrapContent,height = dip(17)){}


                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        linearLayout {
                            //挑战到已沟通的职位  已经收藏
                            this.withTrigger().click {
                                var intent = Intent(mContext, MyRecruitListActivity::class.java)
                                intent.putExtra("type",2)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }

                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            fourTextView = textView {
                                text = "0"
                                textColor = R.color.black20
                                textSize = 16f
                            }.lparams(width = wrapContent,height = dip(23)){}

                            textView {
                                textResource = R.string.favorite
                                textColor = R.color.black33
                                textSize = 12f
                            }.lparams(width = wrapContent,height = dip(17)){}
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(60))

                    linearLayout {
                        backgroundColorResource = R.color.whiteFF
                        orientation = LinearLayout.VERTICAL
                        leftPadding = dip(15)
                        rightPadding = dip(15)

                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.web_resume
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.webResume
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }

                            this.withTrigger().click {
                                startActivity<ResumeEdit>()
                                activity!!.overridePendingTransition(R.anim.left_in, R.anim.right_out)
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }

                        view {
                            backgroundColorResource = R.color.grayEBEAEB
                        }.lparams(width = matchParent, height = dip(1)) {}


                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.file_resume
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.fileResume
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }

                            this.withTrigger().click {
                                startActivity<ResumeListActivity>()
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }

                        view {
                            backgroundColorResource = R.color.grayEBEAEB
                        }.lparams(width = matchParent, height = dip(1)) {}

                        linearLayout {
                            gravity = Gravity.CENTER

                            this.withTrigger().click {
                                jobwanted.jobItem()
                            }
                            imageView {
                                imageResource = R.mipmap.hope_industry
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.hopeIndustry
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            statuText = textView {
                                addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(
                                        s: CharSequence?,
                                        start: Int,
                                        count: Int,
                                        after: Int
                                    ) {

                                    }

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                                    }

                                    override fun afterTextChanged(s: Editable?) {
                                        changeStatu()
                                    }
                                })

                            }.lparams(width = wrapContent,height = matchParent){
                                rightMargin = dip(15)
                            }


                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }

                        view {
                            backgroundColorResource = R.color.grayEBEAEB
                        }.lparams(width = matchParent, height = dip(1)) {}


                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.customs_company
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.customsCompany
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }
                    }.lparams(width = matchParent,height = dip(222)){
                        topMargin = dip(8)
                    }

                    linearLayout {
                        backgroundColorResource = R.color.whiteFF
                        orientation = LinearLayout.VERTICAL
                        leftPadding = dip(15)
                        rightPadding = dip(15)

                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.privacy_settings
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.privacySettings
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }

                            this.withTrigger().click {
                                startActivity<PrivacySetActivity>()
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }

                        view {
                            backgroundColorResource = R.color.grayEBEAEB
                        }.lparams(width = matchParent, height = dip(1)) {}

                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.reisuke_sukesuke
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.reisukeSukesuke
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }

                            this.withTrigger().click {
                                startActivity<HelpFeedbackActivity>()
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }

                        view {
                            backgroundColorResource = R.color.grayEBEAEB
                        }.lparams(width = matchParent, height = dip(1)) {}


                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.settings
                            }.lparams(width = dip(19),height = dip(18))

                            textView {
                                textResource = R.string.settings
                                textSize = 13f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                leftMargin = dip(10)
                                rightMargin = dip(15)
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = dip(6),height = dip(11)){
                            }
                            this.withTrigger().click {
                                startActivity<SystemSetupActivity>()
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(18)
                            bottomMargin = dip(18)
                        }
                    }.lparams(width = matchParent,height = wrapContent){
                        topMargin = dip(8)
                    }
                }.lparams(width = matchParent){}
            }
        }.view
    }

    lateinit var jobwanted : JobWanted
    interface JobWanted{
        fun jobItem()
    }

    fun setData(statu:String){
        statuText.text = statu
    }

    fun initStatu(work:JsonObject){
        var statu = this.getString(R.string.IiStatusOne)
        var workStatu = work.get("state").toString().replace("\"","")

        println(workStatu)
        when(workStatu){
            "OTHER" -> statu = this.getString(R.string.IiStatusOne)
            "ON_NEXT_MONTH"-> statu = this.getString(R.string.IiStatusTwo)
            "ON_CONSIDERING" -> statu = this.getString(R.string.IiStatusThree)
            "OFF"-> statu = this.getString(R.string.IiStatusFour)
        }
        statuText.setText(statu)
    }

    fun initOne(one:String){
        oneTextView.text = one
    }

    fun initTwo(two:String){
        twoTextView.text = two
    }

    fun initThree(three:String){
        threeTextView.text = three
    }

    fun initFour(four:String){
        fourTextView.text = four
    }

    @SuppressLint("CheckResult")
    fun changeStatu(){
        var myStatu = tool.getText(statuText)

        var workStatu = ""
        when(myStatu){
            this.getString(R.string.IiStatusOne) -> workStatu = "OTHER"
            this.getString(R.string.IiStatusTwo) -> workStatu = "ON_NEXT_MONTH"
            this.getString(R.string.IiStatusThree) -> workStatu = "ON_CONSIDERING"
            this.getString(R.string.IiStatusFour) -> workStatu = "OFF"
        }

        if(workStatu != ""){
            val statuParams = mapOf(
                "attributes" to workAttributes,
                "state" to workStatu
            )

            val statuJson = JSON.toJSONString(statuParams)
            var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
            val statuBody = RequestBody.create(json,statuJson)

            retrofitUils.create(RegisterApi::class.java)
                .UpdateWorkStatu(statuBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("成功了")
                },{})
        }
    }

}


