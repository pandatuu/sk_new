package com.example.sk_android.mvp.view.fragment.resume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ListView
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.view.*
import android.widget.LinearLayout
import com.example.sk_android.mvp.model.resume.Resume


class SrMainBodyFragment:Fragment(){
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var myList:ListView
    var mId = 2
    var resume = Resume(R.mipmap.word,"","","","","","")


    companion object {
        fun newInstance(resume: Resume): SrMainBodyFragment {
            val fragment = SrMainBodyFragment()
            fragment.resume = resume
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }


    fun createView():View{
        return UI {
            verticalLayout {
                leftPadding = dip(15)
                rightPadding = dip(15)
                backgroundColorResource = R.color.whiteFF


                linearLayout {
                    backgroundColorResource = R.color.whiteFF
                    editText {
                        backgroundColorResource = R.color.whiteFF
                        gravity = Gravity.LEFT
                        textResource = R.string.srHint
                        textSize = 14f
                        textColorResource = R.color.gray89
                        singleLine = true
                    }.lparams(width = wrapContent,height = wrapContent)
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(15)
                }

                view {
                    backgroundColorResource = R.color.grayE6
                }.lparams(width = matchParent, height = dip(1)) {}

                linearLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView{
                        imageResource = resume.imageUrl
                    }.lparams(width = dip(36),height = dip(46)){
                        leftMargin = dip(23)
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_VERTICAL
                        textView {
                            text = resume.name
                            textSize = 16f
                            textColorResource = R.color.black20
                        }.lparams(width = wrapContent,height = wrapContent)
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            textView {
                                text = resume.size
                                textSize = 13f
                                textColorResource = R.color.gray89
                            }.lparams(width= wrapContent,height = wrapContent){
                                rightMargin = dip(5)
                            }
                            textView {
                                text = resume.updateData
                                textSize = 13f
                                textColorResource = R.color.gray89
                            }.lparams(width = wrapContent,height = wrapContent){}
                        }.lparams(width = wrapContent,height = wrapContent){
                            topMargin = dip(3)
                        }
                    }.lparams(width = wrapContent,height = matchParent){
                        weight = 1f
                        leftMargin = dip(12)
                    }
                }.lparams(width = matchParent,height = dip(90)){
                    topMargin = dip(15)
                }

            }
        }.view
    }


}

