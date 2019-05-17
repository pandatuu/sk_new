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
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.px2dip
import org.jetbrains.anko.support.v4.viewPager

class CompanyDetailInfo1111Fragment : Fragment() {



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
        fun newInstance(contentText: String): CompanyDetailInfo1111Fragment {
            var f = CompanyDetailInfo1111Fragment()
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
                verticalLayout {
                    appBarLayout{
                        relativeLayout {

                        }.lparams {
                            height=dip(100)
                            width= matchParent
                        }

                        tabLayout{



                        }.lparams {
                            height= wrapContent
                            width= matchParent
                        }
                    }

                    viewPager{
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




