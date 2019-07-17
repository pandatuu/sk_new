package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import kotlinx.android.synthetic.main.activity_chat.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


//CoordinatorLayoutExample
class CompanyDetailInfoFragment : Fragment() {


    var toolbar1: Toolbar? = null
    private var mContext: Context? = null
    var id: String = ""
    lateinit var desContent: TextView

    var swipeLayout: LinearLayout? = null

    lateinit var scrollViewSon: LinearLayout

    var productDetailInfoBottomPartFragment: ProductDetailInfoBottomPartFragment? = null
    var productDetailInfoTopPartFragment: ProductDetailInfoTopPartFragment? = null
    var listFragment: RecruitInfoListFragment? = null
    lateinit var viewPager: ViewPager
    lateinit var baseAdapter: BaseFragmentAdapter

    lateinit var fuliContaner: FlowLayout
    var company: CompanyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(userId: String): CompanyDetailInfoFragment {
            var f = CompanyDetailInfoFragment()
            f.id = userId
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    fun setDetailInfo(com: CompanyInfo) {

        productDetailInfoTopPartFragment = ProductDetailInfoTopPartFragment.newInstance(com)
        childFragmentManager.beginTransaction()
            .replace(R.id.fold_nav_layout, productDetailInfoTopPartFragment!!)
            .commit()



        productDetailInfoBottomPartFragment?.setInformation(com)
    }

    private fun createView(): View {

        var dm = resources.displayMetrics
        var w_screen = dm.widthPixels;
        var h_screen = dm.heightPixels;

        var organizationId = activity!!.intent.getStringExtra("companyId");
        var positionNum = activity!!.intent.getIntExtra("positionNum", 0);

        var cotainer =
            LayoutInflater.from(context).inflate(R.layout.jike_topic_detail_layout, null);

        val fold_nav_layout = cotainer.findViewById(R.id.fold_nav_layout) as FrameLayout

        var productDetailInfoTopPartFragment = ProductDetailInfoTopPartFragment.newInstance(null)
        getChildFragmentManager().beginTransaction()
            .replace(fold_nav_layout.id, productDetailInfoTopPartFragment!!)
            .commit()


        var mFragments: MutableList<Fragment> = mutableListOf()
        var mTitles = arrayOf("详细信息", "人気職位(" + positionNum.toString() + ")")

        // 详细信息
        productDetailInfoBottomPartFragment = ProductDetailInfoBottomPartFragment.newInstance(company)
        mFragments.add(productDetailInfoBottomPartFragment!!)
        //职位列表
        val listFragment = RecruitInfoListFragment.newInstance(false,null, organizationId, null)
        mFragments.add(listFragment)

        baseAdapter = BaseFragmentAdapter(fragmentManager, mFragments, mTitles)
        viewPager = cotainer.findViewById(R.id.fold_content_layout) as ViewPager
        viewPager.adapter = baseAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {


            }

            override fun onPageSelected(i: Int) {
                viewPager.setCurrentItem(i, true)
            }

        })

        viewPager.setOffscreenPageLimit(2)


        val tabLayout = cotainer.findViewById(R.id.fold_tab_layout) as TabLayout
        tabLayout.setTabRippleColor(ColorStateList.valueOf(getContext()!!.getResources().getColor(R.color.white)));

        tabLayout.setupWithViewPager(viewPager)

        return UI {
            linearLayout {
                linearLayout() {
                    backgroundColor = Color.TRANSPARENT
                    swipeLayout = verticalLayout {

                        backgroundResource = R.drawable.radius_top_white

                        addView(cotainer)

                    }.lparams() {
                        width = matchParent
                         topMargin = dip(65)
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
        var scale = context.resources.displayMetrics.density;
        return ((pxValue / scale + 0.5f).toInt())
    }

}




