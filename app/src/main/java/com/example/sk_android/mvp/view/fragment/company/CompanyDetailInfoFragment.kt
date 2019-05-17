package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.adapter.company.LabelShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyCityAddressAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyPicShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustrySelectAdapter
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.px2dip

class CompanyDetailInfoFragment : Fragment() {



    var toolbar1: Toolbar? = null
    private var mContext: Context? = null
    var contentText: String = ""
    lateinit var desContent: TextView

    lateinit var swipeLayout: LinearLayout

    lateinit var scrollViewSon:LinearLayout

    var productDetailInfoBottomPartFragment: ProductDetailInfoBottomPartFragment?=null
    var productDetailInfoTopPartFragment:ProductDetailInfoTopPartFragment?=null
    lateinit var fuliContaner: FlowLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(contentText: String): CompanyDetailInfoFragment {
            var f = CompanyDetailInfoFragment()
            f.contentText = contentText
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {

        var dm = getResources().getDisplayMetrics();
        var w_screen = dm.widthPixels;
        var h_screen = dm.heightPixels;

        return UI {
            linearLayout {
                linearLayout() {



                    backgroundColor = Color.TRANSPARENT
                    swipeLayout = verticalLayout {

                        scrollView{

                            verticalLayout {
                                backgroundResource = R.drawable.radius_top_white

                                var topId = 11
                                frameLayout {
                                    id = topId
                                    productDetailInfoTopPartFragment = ProductDetailInfoTopPartFragment.newInstance("")
                                    getChildFragmentManager().beginTransaction()
                                        .replace(id, productDetailInfoTopPartFragment!!)
                                        .commit()

                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }


                                verticalLayout {

                                    textView {
                                        text = "人気職位(200)"
                                        textSize = 16f
                                        textColorResource = R.color.black20
                                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                        gravity = Gravity.CENTER_VERTICAL

                                        setOnClickListener(object : View.OnClickListener {

                                            override fun onClick(v: View?) {
                                                if (productDetailInfoBottomPartFragment != null) {
                                                    getChildFragmentManager().beginTransaction()
                                                        .remove(productDetailInfoBottomPartFragment!!).commit()

                                                    getChildFragmentManager().beginTransaction()
                                                        .remove(productDetailInfoTopPartFragment!!).commit()


                                                }
                                                if (productDetailInfoBottomPartFragment == null) {
                                                    getChildFragmentManager().beginTransaction()
                                                        .add(id, productDetailInfoBottomPartFragment!!).commit()
                                                }
                                            }

                                        })

                                    }.lparams {
                                        height = dip(61)
                                        width = matchParent
                                        leftMargin = dip(15)
                                        rightMargin = dip(15)

                                    }

                                    scrollView {


                                        var scrollViewSonId = 1
                                        scrollViewSon = verticalLayout {
                                            id = scrollViewSonId


                                            productDetailInfoBottomPartFragment =
                                                ProductDetailInfoBottomPartFragment.newInstance("アニメ谷はデジタル映像制作に携わっており、CG技 术作品で世界を繋ぐことに力を注いでいる。！私たち は、世界市场に向けてより広范なグローバル市场に进 むことができるように、制作の実力の向上とチーー…")
                                            getChildFragmentManager().beginTransaction()
                                                .replace(id, productDetailInfoBottomPartFragment!!).commit()


                                        }
                                    }.lparams {
                                        height = wrapContent
                                        width = matchParent
                                    }

                                    var selectBarId = 21
                                    frameLayout {
                                        id = selectBarId
                                        var companyInfoSelectbarFragment =
                                            CompanyInfoSelectbarFragment.newInstance("", "", "", "")
                                        getChildFragmentManager().beginTransaction()
                                            .replace(id, companyInfoSelectbarFragment!!).commit()


                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                    }

                                    var listId = 22
                                    frameLayout {
                                        id = listId
                                        var recruitInfoListFragment = RecruitInfoListFragment.newInstance()
                                        getChildFragmentManager().beginTransaction()
                                            .replace(id, recruitInfoListFragment!!)
                                            .commit()

                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                    }


                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }
                        }
                    }.lparams() {
                        width = matchParent
                        topMargin = dip(65)
                      // topMargin = dip(343)
                        height = dip(px2dip(context, h_screen * 1.0f) - 65)
                    }


                }.lparams() {
                    width = matchParent
                    height = matchParent
                }


            }
        }.view


    }

    fun px2dip(context: Context, pxValue: Float): Int {
        var scale = context.getResources().getDisplayMetrics().density;
        return ((pxValue / scale + 0.5f).toInt())
    }


}




