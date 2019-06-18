package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.sk_android.R
import it.sephiroth.android.library.easing.Linear
import kotlinx.android.synthetic.main.row_list.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class PsActionBarFragment:Fragment() {
    var toolbar: Toolbar?=null
    lateinit var nameText:TextView
    lateinit var headImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
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

                        headImage = imageView {
                            imageResource = R.mipmap.portrait
                        }.lparams(width = dip(70),height = dip(70)){
                            leftMargin = dip(15)
                        }


                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL
                            orientation = LinearLayout.VERTICAL

                            nameText = textView {
                                textResource = R.string.personName
                                textSize = 24f
                                textColorResource = R.color.black33
                            }

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
//        Glide.with(this)
//            .asBitmap()
//            .load(url)
//            .placeholder(R.mipmap.portrait)
//            .into(headImage)
//
//        nameText.text = name
    }

}