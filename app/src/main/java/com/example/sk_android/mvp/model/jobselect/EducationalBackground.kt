package com.example.sk_android.mvp.model.jobselect

import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment
import org.json.JSONObject


class EducationalBackground {



    companion object {
        //教育背景
        fun getEducationalBackground(educationalBackground: String): String? {

            var result: String? = null
            if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MIDDLE_SCHOOL.toString())) {
                result = EducationalBackground.Value.MIDDLE_SCHOOL.text
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
                result = EducationalBackground.Value.HIGH_SCHOOL.text
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
                result = EducationalBackground.Value.SHORT_TERM_COLLEGE.text
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
                result = EducationalBackground.Value.BACHELOR.text
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
                result = EducationalBackground.Value.MASTER.text
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.DOCTOR.toString())) {
                result = EducationalBackground.Value.DOCTOR.text
            }
            return result
        }
    }


    enum class Key{
        MIDDLE_SCHOOL,
        HIGH_SCHOOL,
        SHORT_TERM_COLLEGE,
        BACHELOR,
        MASTER,
        DOCTOR,
    }

    enum class Value(val text: String) {
        MIDDLE_SCHOOL("中卒"),
        HIGH_SCHOOL("高卒"),
        SHORT_TERM_COLLEGE("専門卒・短大卒"),
        BACHELOR("大卒"),
        MASTER("修士"),
        DOCTOR("博士")
    }

//    enum class Value{
//        中卒,
//        高卒,
//        短大卒,//専門卒・短大卒
//        大卒,
//        修士,
//        博士,
//    }
}

