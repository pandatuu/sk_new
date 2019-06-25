package com.example.sk_android.mvp.model.jobselect

import com.example.sk_android.mvp.model.company.CompanySize
import org.json.JSONObject


class UserPosition {

    enum class Key{
        organization_admin,
        organization_hr
    }



    companion object {
        var dataMap: MutableMap<String, String> = mutableMapOf()
        init {

            dataMap.put(UserPosition.Key.organization_admin.toString(),"企业管理员")
            dataMap.put(UserPosition.Key.organization_hr.toString(),"企业HR")


        }
    }




}

