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
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
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
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import com.example.sk_android.mvp.view.adapter.company.LabelShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyCityAddressAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyPicShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustrySelectAdapter
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.px2dip


//CoordinatorLayoutExample
class CompanyDetailInfoFragment1111 : Fragment() {



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
        fun newInstance(contentText: String): CompanyDetailInfoFragment1111 {
            var f = CompanyDetailInfoFragment1111()
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

                        backgroundColor=Color.RED


                        var cotainer=  LayoutInflater.from(context).inflate(R.layout.jike_topic_detail_layout, null);



                        var mFragments: MutableList<Fragment> = mutableListOf()



                        var mTitles = arrayOf("精选", "广场")


                        for (i in mTitles.indices) {
                            val listFragment = RecruitInfoListFragment.newInstance()
                            mFragments.add(listFragment)
                        }

                        val adapter = BaseFragmentAdapter(getFragmentManager(), mFragments, mTitles)

                        val viewPager = cotainer.findViewById(R.id.view_pager) as ViewPager
                        viewPager.setAdapter(adapter)

                        val tabLayout = cotainer.findViewById(R.id.tabs) as TabLayout
                        tabLayout.setupWithViewPager(viewPager)


                        addView(cotainer)


                    }.lparams() {
                        width = matchParent
                      // topMargin = dip(65)
                      topMargin = dip(343)
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




