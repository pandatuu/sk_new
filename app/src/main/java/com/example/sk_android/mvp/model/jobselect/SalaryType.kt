package com.example.sk_android.mvp.model.jobselect

import org.json.JSONObject


class SalaryType {

    companion object {
        val HOURLY="时"
        val DAILY="天"
        val MONTHLY="月"
        val YEARLY="年"
    }

    enum class Key{
        HOURLY,
        DAILY,
        MONTHLY,
        YEARLY
    }

    enum class Value{
        时,
        天,
        月,
        年
    }
}

