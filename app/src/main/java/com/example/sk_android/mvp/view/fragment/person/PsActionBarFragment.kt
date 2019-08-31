package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.view.activity.person.PersonInformation
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.roundImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import withTrigger
import java.util.*

class PsActionBarFragment : Fragment() {
    var toolbar: Toolbar? = null
    private lateinit var nameText: TextView
    private lateinit var headImage: ImageView
    lateinit var tool: BaseTool

    companion object {


        var myResult: ArrayList<UserBasicInformation> = arrayListOf()

        var imageUrl = ""
        var userName = ""


        fun newInstance(): PsActionBarFragment {
            return PsActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()

    }

    private fun createView(): View? {
        val view =  UI {

            verticalLayout {

                relativeLayout {
                    textView {
                        backgroundColorResource = R.color.yellowFED95A
                    }.lparams {
                        width = matchParent
                        height = dip(175)

                    }

                    relativeLayout {
                        toolbar = toolbar {
                            backgroundResource = R.color.yellowFED95A
                            isEnabled = true
                            title = ""
                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams(width = matchParent, height = dip(10)) {

                    }



                    linearLayout {

                        headImage = roundImageView {

                            imageResource = R.mipmap.ico_head
                            this.withTrigger().click {
                                submit()
                            }
                        }.lparams(width = dip(70), height = dip(70)) {
                            leftMargin = dip(15)
                        }


                        if (imageUrl != "") {
                            Glide.with(this)
                                .asBitmap()
                                .load(imageUrl)
                                .placeholder(R.mipmap.ico_head)
                                .into(headImage)
                        }

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL
                            orientation = LinearLayout.VERTICAL

                            nameText = textView {
                                textResource = R.string.personName
                                textSize = 24f
                                singleLine = true
                                maxEms = 5
                                ellipsize = TextUtils.TruncateAt.END
                                textColorResource = R.color.black33
                            }

                            nameText.text = userName

                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    imageResource = R.mipmap.personedit
                                }
                                textView {
                                    textResource = R.string.myBaseInformation
                                    textSize = 14f
                                    textColorResource = R.color.black20
                                }.lparams {
                                    leftMargin = dip(7)
                                }
                                this.withTrigger().click {
                                    submit()
                                }
                            }.lparams {
                                topMargin = dip(6)
                            }

                        }.lparams(width = matchParent, height = matchParent) {
                            leftMargin = dip(20)
                        }

                    }.lparams {
                        width = matchParent
                        height = dip(70)
                        alignParentBottom()
                        bottomMargin = dip(36)
                    }


                }.lparams {
                    width = matchParent
                    height = dip(175)
                }
            }

        }.view
        initView(1)
        val application: App? = App.getInstance()
        application?.setPsActionBarFragment(this)
        return view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
            val scale = context.resources.displayMetrics.density
            result = ((result / scale + 0.5f).toInt())
        }
        return result
    }

    fun changePage(url: String, name: String) {
        if(url != imageUrl && name!= userName ) {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .placeholder(R.mipmap.ico_head)
                .into(headImage)

            nameText.text = name

            imageUrl = url
            userName = name
        }
    }

    private fun submit() {
        val tool = BaseTool()
        val name = tool.getText(nameText)
        // 1:创建  0:更新
        if (name == this.getString(R.string.personName)) {
            startActivity<PersonInformation>("condition" to 1)
        } else {
            startActivity<PersonInformation>("condition" to 0)
        }
    }

    fun initView(from: Int) {


        if ((myResult.size == 0) && from == 1) {
            //第一次进入


        } else {

            val image: String
            val name: String

            val statu = myResult[0].auditState.toString().replace("\"","")
                if(statu == "PENDING"){
                    val url = myResult[0].changedContent!!.avatarURL
                    image = if(url.indexOf(";")!=-1) url.replace("\"","").split(";")[0] else url.replace("\"","")
                    name = myResult[0].changedContent!!.displayName.replace("\"","")
                }else{
                    val url = myResult[0].avatarURL
                    image = if(url.indexOf(";") !=-1) url.replace("\"","").split(";")[0] else url.replace("\"","")
                    name = myResult[0].displayName.replace("\"", "")
                }
            Glide.with(this)
                .asBitmap()
                .load(image)
                .placeholder(R.mipmap.ico_head)
                .into(headImage)

            nameText.text = name
            imageUrl = image
            userName = name
        }
    }
    override fun onDestroy() {
        super.onDestroy()


    }
}