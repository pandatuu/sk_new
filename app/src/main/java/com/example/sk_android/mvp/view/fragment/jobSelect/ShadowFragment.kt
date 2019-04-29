package com.example.sk_android.mvp.view.fragment.jobSelect

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import android.content.Context
import com.example.sk_android.mvp.view.activity.JobSelectActivity


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

