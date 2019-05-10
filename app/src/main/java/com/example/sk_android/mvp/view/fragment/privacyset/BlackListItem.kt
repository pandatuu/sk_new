//package com.example.sk_android.mvp.view.fragment.privacyset
//
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import com.example.sk_android.R
//import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
//import com.example.sk_android.mvp.view.activity.privacySet.BlackListActivity
//import java.util.*
//
//class BlackListItem : Fragment() {
//
//    companion object {
//        fun newInstance(blackListActivity: BlackListActivity): BlackListItem {
//            val fragment = BlackListItem()
//            return fragment
//        }
//    }
//
//    var blackListItemList = LinkedList<BlackListItemModel>()
//
//
//
////    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//////        var fragmentView = createView()
////
////        return fragmentView
////    }
//
////    private fun createView(): View? {
////
////
////
////        return UI {
////            linearLayout {
////                linearLayout {
////                    verticalLayout {
////
////                        recyclerView {
////                            setLayoutManager(LinearLayoutManager(this.getContext()))
////                            adapter = BlackListAdapter(this.context,blackListItemList)
////                        }
////                        relativeLayout {
////                            imageView {
////                                imageResource = R.mipmap.sk
////                            }.lparams {
////                                width = dip(60)
////                                height = dip(60)
////                                alignParentLeft()
////                                centerVertically()
////                            }
////                            verticalLayout {
////                                textView {
////                                    text = "ソニー株式会社"
////                                    textSize = 16f
////                                    textColor = Color.parseColor("#FF202020")
////                                }.lparams {
////
////                                }
////                                textView {
////                                    text = "東京都品川區南大井3-27-14 "
////                                    textSize = 13f
////                                    textColor = Color.parseColor("#FF999999")
////                                }.lparams {
////
////                                }
////                            }.lparams {
////                                width = wrapContent
////                                height = wrapContent
////                                leftMargin = dip(70)
////                                centerVertically()
////                            }
////                        }.lparams {
////                            width = matchParent
////                            height = dip(90)
////                            leftPadding = dip(15)
////                            rightPadding = dip(15)
////                        }
////                        relativeLayout {
////                            imageView {
////                                imageResource = R.mipmap.sk
////                            }.lparams {
////                                width = dip(60)
////                                height = dip(60)
////                                alignParentLeft()
////                                centerVertically()
////                            }
////                            verticalLayout {
////                                textView {
////                                    text = "123诛仙会社"
////                                    textSize = 16f
////                                    textColor = Color.parseColor("#FF202020")
////                                }.lparams {
////
////                                }
////                                textView {
////                                    text = "東京都品川區南大井3-27-14 "
////                                    textSize = 13f
////                                    textColor = Color.parseColor("#FF999999")
////                                }.lparams {
////
////                                }
////                            }.lparams {
////                                width = wrapContent
////                                height = wrapContent
////                                leftMargin = dip(70)
////                                centerVertically()
////                            }
////                        }.lparams {
////                            width = matchParent
////                            height = dip(90)
////                            leftPadding = dip(15)
////                            rightPadding = dip(15)
////                        }
////                        relativeLayout {
////                            imageView {
////                                imageResource = R.mipmap.sk
////                            }.lparams {
////                                width = dip(60)
////                                height = dip(60)
////                                centerVertically()
////                                alignParentLeft()
////                            }
////                            verticalLayout {
////                                textView {
////                                    text = "しん友教育"
////                                    textSize = 16f
////                                    textColor = Color.parseColor("#FF202020")
////                                }.lparams {
////
////                                }
////                                textView {
////                                    text = "東京都品川區南大井3-27-14  "
////                                    textSize = 13f
////                                    textColor = Color.parseColor("#FF999999")
////                                }.lparams {
////
////                                }
////                            }.lparams {
////                                width = wrapContent
////                                height = wrapContent
////                                leftMargin = dip(70)
////                                centerVertically()
////                            }
////                        }.lparams {
////                            width = matchParent
////                            height = dip(90)
////                            leftPadding = dip(15)
////                            rightPadding = dip(15)
////                        }
////
////                    }.lparams {
////                        width = matchParent
////                        height = wrapContent
////                        bottomMargin = dip(60)
////                    }
////                }.lparams {
////                    width = matchParent
////                    height = matchParent
////                }
////            }
////        }.view
////    }
//}