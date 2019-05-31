package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class PsMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var tool: BaseTool


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
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            textView {
                                textResource = R.string.contactNumber
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
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            textView {
                                textResource = R.string.interViewNumber
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
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            textView {
                                textResource = R.string.submittedNumber
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
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.whiteFF
                            orientation = LinearLayout.VERTICAL
                            textView {
                                textResource = R.string.favoriteNumber
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

                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                                onClick {
                                    jobwanted.jobItem()
                                }
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
}

