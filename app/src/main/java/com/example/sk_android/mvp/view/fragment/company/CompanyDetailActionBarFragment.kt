package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.company.VideoShowActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.http.Url

class CompanyDetailActionBarFragment : Fragment() {

    var toolbar1: Toolbar? = null
    private var mContext: Context? = null

    lateinit var mainLayout: RelativeLayout
    lateinit var select: CompanyDetailActionBarSelect
    var videoRela: RelativeLayout? = null
    lateinit var video: VideoView
    lateinit var image: ImageView
    lateinit var rela: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): CompanyDetailActionBarFragment {
            return CompanyDetailActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        select = activity as CompanyDetailActionBarSelect
        return fragmentView
    }


    fun setUrl(url: String) {
        if (url != "") {
            val view = UI {
                videoRela = relativeLayout {
                    linearLayout(){
                        gravity=Gravity.CENTER
                        image = imageView {
                            imageResource = R.mipmap.player
                            onClick {
                                var intent =Intent(activity!!, VideoShowActivity::class.java)
                                intent.putExtra("url", url)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }
                        }.lparams(dip(70), dip(70)) {
                        }
                    }.lparams {
                        width= matchParent
                        height= matchParent
                    }

                }
            }.view
            rela.addView(view)
        }
    }

    private fun createView(): View {
        return UI {
            relativeLayout {
                mainLayout = relativeLayout() {
                    backgroundResource = R.mipmap.company_bg


                    rela = relativeLayout {

                    }.lparams(matchParent, wrapContent) {
                        centerInParent()
                    }

                    relativeLayout() {
                        gravity = Gravity.CENTER_VERTICAL
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back_white

                        }.lparams() {
                            width = matchParent
                            height = dip(65)

                        }

                        var textViewLeftId = 1
                        var textViewLeft = textView {
                            id = textViewLeftId
                            text = ""
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams() {
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentBottom()
                            centerInParent()
                            leftMargin = dip(15)
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL

                            var soucangId = 1
                            var soucang = toolbar {
                                id = soucangId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.soucang_no
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        navigationIconResource = R.mipmap.icon_zan_h_home
                                    }

                                })
                            }.lparams(dip(25), dip(25)) {
                                rightMargin = dip(10)

                            }

                            var jubaoId = 2
                            var juba = toolbar {
                                id = jubaoId
                                navigationIconResource = R.mipmap.jubao
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        navigationIconResource = R.mipmap.jubao_light
                                        select.jubaoSelect()
                                    }

                                })
                            }.lparams(dip(25), dip(25)) {

                                rightMargin = dip(10)

                            }

                            var pingbiId = 3
                            var pingbi = toolbar {
                                id = pingbiId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.pingbi

                            }.lparams(dip(25), dip(25)) {

                            }

                        }.lparams() {
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin = dip(15)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(383)
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


    interface CompanyDetailActionBarSelect {
        fun jubaoSelect()
    }

}




