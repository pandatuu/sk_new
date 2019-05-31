package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class ResumeShareFragment : Fragment() {

    var TrpToolbar: Toolbar? = null
    private var mContext: Context? = null
    lateinit var cancelTool: CancelTool
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): ResumeShareFragment {
            return ResumeShareFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        cancelTool = activity as CancelTool
        return fragmentView
    }

    private fun createView(): View {
        return UI {
            linearLayout {
                gravity = Gravity.BOTTOM
                verticalLayout {
                    backgroundResource = R.drawable.top_radius
                    relativeLayout {
                        textView {
                            text = "分け合う"
                            gravity = Gravity.CENTER
                            textSize = 14f
                            textColor = Color.parseColor("#FF202020")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerInParent()
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(20)
                        bottomMargin = dip(20)
                    }
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        relativeLayout {
                            relativeLayout {
                                imageView {
                                    imageResource = R.mipmap.skype
                                }.lparams {
                                    width = matchParent
                                    height = dip(60)
                                }
                                textView {
                                    text = "Skype"
                                    textSize = 14f
                                    textColorResource = R.color.blue007AFF
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(70)
                                    centerHorizontally()
                                }
                            }.lparams {
                                width = dip(60)
                                height = dip(90)
                            }
                            relativeLayout {
                                imageView {
                                    imageResource = R.mipmap.line
                                }.lparams {
                                    width = matchParent
                                    height = dip(60)
                                }
                                textView {
                                    text = "Line"
                                    textSize = 14f
                                    textColorResource = R.color.blue007AFF
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(70)
                                    centerHorizontally()
                                }
                            }.lparams {
                                width = dip(60)
                                height = dip(90)
                                alignParentRight()
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                            leftPadding = dip(55)
                            rightPadding = dip(55)
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(120)
                        leftPadding = dip(15)
                        rightPadding = dip(15)
                    }

                    relativeLayout {
                        textView {
                            text = "キャンセル"
                            gravity = Gravity.CENTER
                            textSize = 15f
                            textColor = Color.parseColor("#FF5C5C5C")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerInParent()
                            setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    cancelTool.cancelList()
                                }
                            })
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(45)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(230)
                }
            }
        }.view
    }

    public interface CancelTool {

        fun cancelList()
    }

}