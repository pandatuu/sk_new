package com.example.sk_android.mvp.model.company

import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordActionBarFragment


class FinancingStage {

    enum class Key{
        TSE_1,
        TSE_2,
        TSE_MOTHERS,
        OTHER,
        NONE
    }

    companion object {
        var dataMap: MutableMap<String, String> = mutableMapOf()
        init {

            dataMap.put(FinancingStage.Key.TSE_1.toString(),"東証第一部")
            dataMap.put(FinancingStage.Key.TSE_2.toString(),"東証第二部")
            dataMap.put(FinancingStage.Key.TSE_MOTHERS.toString(),"東証マザーズ")
            dataMap.put(FinancingStage.Key.OTHER.toString(),"その他")
            dataMap.put(FinancingStage.Key.NONE.toString(),"なし")

        }
    }

}