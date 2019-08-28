package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.graphics.Color
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
import com.example.sk_android.mvp.view.activity.person.PersonInformation
import com.example.sk_android.mvp.view.activity.register.MemberRegistActivity
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.roundImageView
import kotlinx.android.synthetic.main.row_list.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import withTrigger

class PsActionBarFragment:Fragment() {
    var toolbar: Toolbar?=null
    lateinit var nameText:TextView
    lateinit var headImage:ImageView
    lateinit var tool:BaseTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        var imageUrl=""
        var userName=""


        fun newInstance():PsActionBarFragment{
            return PsActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView

    }

    private fun createView(): View? {
        return UI {

            verticalLayout {

                relativeLayout() {
                    textView() {
                        backgroundColorResource = R.color.yellowFED95A
                    }.lparams() {
                        width = matchParent
                        height = dip(175)

                    }

                    relativeLayout() {
                        toolbar = toolbar {
                            backgroundResource = R.color.yellowFED95A
                            isEnabled = true
                            title = ""
                        }.lparams() {
                            width = matchParent
                            height =matchParent
                        }
                    }.lparams(width = matchParent,height = dip(10)){

                    }



                    linearLayout{

                        headImage = roundImageView {

                            imageResource = R.mipmap.ico_head
                            this.withTrigger().click {
                                submit()
                            }
                        }.lparams(width = dip(70),height = dip(70)){
                            leftMargin = dip(15)
                        }


                        if(imageUrl!=""){
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

                            linearLayout{
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    imageResource = R.mipmap.personedit
                                }
                                textView {
                                    textResource = R.string.myBaseInformation
                                    textSize = 14f
                                    textColorResource = R.color.black20
                                }.lparams{
                                    leftMargin = dip(7)
                                }
                                this.withTrigger().click {
                                    submit()
                                }
                            }.lparams{
                                topMargin = dip(6)
                            }

                        }.lparams(width = matchParent,height = matchParent){
                            leftMargin = dip(20)
                        }

                    }.lparams {
                        width= matchParent
                        height=dip(70)
                        alignParentBottom()
                        bottomMargin=dip(36)
                    }


                }.lparams {
                    width = matchParent
                    height = dip(175)
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

    fun changePage(url:String,name:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.ico_head)
            .into(headImage)

        nameText.text = name


         imageUrl=url
         userName=name

    }

    private fun submit(){
        var tool = BaseTool()
        var name = tool.getText(nameText)
        // 1:创建  0:更新
        if(name.equals(this.getString(R.string.personName))){
            startActivity<PersonInformation>("condition" to 1)
        }else{
            startActivity<PersonInformation>("condition" to 0)
        }
    }

}