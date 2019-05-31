package com.example.sk_android.mvp.view.fragment.resume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Button
import android.widget.ListView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.adapter.resume.ResumeAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*
import android.view.*


class RlMainBodyFragment:Fragment(){
    private lateinit var myDialog : MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var myList:ListView
    var mId = 2
    lateinit var mData:LinkedList<Resume>
    lateinit var resumeAdapter:ResumeAdapter
    lateinit var myTool:Tool


    companion object {
        fun newInstance(): RlMainBodyFragment {
            val fragment = RlMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        myTool = activity as Tool
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        super.onActivityCreated(savedInstanceState)
    }

    fun createView():View{
        tool= BaseTool()
        return UI {
            verticalLayout {



                        myList = listView {
                            id = mId
                        }.lparams(width = matchParent,height = wrapContent){
                            weight = 1f
                        }


                linearLayout {
                    gravity = Gravity.CENTER
                    backgroundColorResource = R.color.yellowFFB706
                    imageView {
                        imageResource = R.mipmap.add
                    }.lparams(width = dip(20),height = dip(20))
                    textView {
                        textResource = R.string.rlButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                    }.lparams(width = wrapContent,height = wrapContent){
                        leftMargin = dip(10)
                    }

                    setOnClickListener { myTool.addVideo() }
                }.lparams(width = matchParent,height = dip(47)){
                    topMargin = dip(10)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(30)
                }
            }
        }.view
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.cancel_interview, null)
        val mmLoading2 = MyDialog(this.mContext!!, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }

    private fun initView() {
        mContext = activity
        myList = this.find(mId)
        mData = LinkedList()
        mData.add(Resume("2.5m","a","a.word","2019-01-01"))
        mData.add(Resume("2.6m","b","b.pdf","2019-01-02"))
        mData.add(Resume("2.7m","c","c.jpg","2019-01-03"))
        mData.add(Resume("2.8m","d","d.pdf","2019-01-04"))
        mData.add(Resume("2.9m","d","d.pdf","2019-01-04"))
        mData.add(Resume("2.8m","d","d.pdf","2019-01-04"))
        mData.add(Resume("2.8m","d","d.pdf","2019-01-04"))
        mData.add(Resume("2.8m","d","d.pdf","2019-01-04"))
        mData.add(Resume("2.8m","d","d.pdf","2019-01-04"))

        resumeAdapter = ResumeAdapter(mData, mContext,myTool)
        myList.setAdapter(resumeAdapter)

    }

    interface Tool {
        fun addList()
        fun addVideo()
    }


}

