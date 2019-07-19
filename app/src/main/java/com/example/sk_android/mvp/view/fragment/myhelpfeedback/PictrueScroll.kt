package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.util.ArrayList

class PictrueScroll : Fragment() {
    var list = ArrayList<String>()
    lateinit var pictrueItem: PictureItem

    companion object {
        fun newInstance(mList: ArrayList<String>): PictrueScroll {
            val fragment = PictrueScroll()
            fragment.list = mList
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        pictrueItem = activity as PictureItem
        var view = createV()
        return view
    }

    private fun createV(): View? {
        return UI {
            gridLayout {
                columnCount = 3
                if (list != null) {
                    for (urlItem in list) {
                        relativeLayout {
                            backgroundColor = Color.parseColor("#FFF6F6F6")
                            imageView {
                                imageURI = Uri.parse(urlItem)
                            }.lparams(matchParent, matchParent) {
                                centerInParent()
                            }
                            textView {
                                backgroundResource = R.mipmap.x
                                this.withTrigger().click {
                                    pictrueItem.clickItem(urlItem)
                                }
                            }.lparams {
                                width = dip(15)
                                height = dip(15)
                                alignParentRight()
                                setMargins(0, dip(5), dip(5), 0)
                            }
                        }.lparams {
                            width = dip(100)
                            height = dip(80)
                            setMargins(dip(10), 0, dip(10), dip(10))
                        }
                    }
                }
            }
        }.view
    }

    interface PictureItem {
        fun clickItem(url: String)
    }
}