package com.example.sk_android.mvp.view.fragment.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context

class ShadowFragment : Fragment() {


    private var mContext: Context? = null
    private lateinit var shadowClick:ShadowClick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): ShadowFragment {
            val fragment = ShadowFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        shadowClick =  activity as ShadowClick
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                verticalLayout()  {
                    setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            shadowClick.shadowClicked()
                        }
                    })
                    backgroundColorResource=R.color.shadowColor
                }.lparams(width = matchParent, height =matchParent){

                }
            }
        }.view
    }

    public interface ShadowClick {

        fun shadowClicked( )
    }


}

