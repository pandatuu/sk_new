package com.example.sk_android.mvp.model.company

import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordActionBarFragment


class CompanySize {

    enum class Key{
        TINY,
        SMALL,
        MEDIUM,
        BIG,
        HUGE,
        SUPER
    }

    companion object {
        var dataMap: MutableMap<String, String> = mutableMapOf()
        init {

            dataMap.put(CompanySize.Key.TINY.toString(),"0-20人")
            dataMap.put(CompanySize.Key.SMALL.toString(),"20-99人")
            dataMap.put(CompanySize.Key.MEDIUM.toString(),"100-499人")
            dataMap.put(CompanySize.Key.BIG.toString(),"500-999人")
            dataMap.put(CompanySize.Key.HUGE.toString(),"1000-9999人")
            dataMap.put(CompanySize.Key.SUPER.toString(),"10000人")

        }
    }

}