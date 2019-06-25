package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.*
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter


//CoordinatorLayoutExample
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

                        backgroundResource=R.drawable.radius_top_white


                        var cotainer=  LayoutInflater.from(context).inflate(R.layout.jike_topic_detail_layout, null);



                        val fold_nav_layout = cotainer.findViewById(R.id.fold_nav_layout) as FrameLayout



                        var productDetailInfoTopPartFragment = ProductDetailInfoTopPartFragment.newInstance("")
                        getChildFragmentManager().beginTransaction()
                            .replace(fold_nav_layout.id, productDetailInfoTopPartFragment!!)
                            .commit()


                        var mFragments: MutableList<Fragment> = mutableListOf()



                        var mTitles = arrayOf("详细信息", "人気職位(200)")



                        val listFragment = RecruitInfoListFragment.newInstance(null)
                        mFragments.add(listFragment)

                        val productDetailInfoBottomPartFragment = ProductDetailInfoBottomPartFragment.newInstance("")
                        mFragments.add(productDetailInfoBottomPartFragment)



                        val adapter = BaseFragmentAdapter(getFragmentManager(), mFragments, mTitles)

                        val viewPager = cotainer.findViewById(R.id.fold_content_layout) as ViewPager
                        viewPager.setAdapter(adapter)

                        val tabLayout = cotainer.findViewById(R.id.fold_tab_layout) as TabLayout
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




