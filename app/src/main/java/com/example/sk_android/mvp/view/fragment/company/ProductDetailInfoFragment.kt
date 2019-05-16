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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toolbar

class ProductDetailInfoFragment : Fragment() {

    private var mContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): ProductDetailInfoFragment {
            return ProductDetailInfoFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                verticalLayout{

                    textView {
                        text="製品の詳細な説明"
                        textColorResource=R.color.black20
                        textSize=16f
                    }

                    textView {
                        text="アニメ谷はデジタル映像制作に携わっており、CG技术 作品で世界を繋ぐことに力を注いでいる。！私たちは世界 市场に向けてより広范なグローバル市场に进むことができ るように、制作の実力の向上とチーム管理のレベ ルの向上を知っている! そのため、私达はら進んで、要求の高い、サービスレ ベルの高い日本市场"
                        textColorResource=R.color.black33
                        textSize=13f
                    }.lparams {
                        topMargin=dip(8)
                    }

                }.lparams {
                    width= matchParent
                }
            }
        }.view

    }







}




