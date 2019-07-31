package com.example.sk_android.mvp.model.jobselect

import org.json.JSONObject


class SalaryType {

    companion object {
        val HOURLY="时"
        val DAILY="日"
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
        日,
        月,
        年
    }
}

