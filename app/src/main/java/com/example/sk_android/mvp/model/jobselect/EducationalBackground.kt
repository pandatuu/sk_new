package com.example.sk_android.mvp.model.jobselect

import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment
import org.json.JSONObject


class EducationalBackground {



    companion object {
        //教育背景
        fun getEducationalBackground(educationalBackground: String): String? {

            var result: String? = null
            if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MIDDLE_SCHOOL.toString())) {
                result = EducationalBackground.Value.中卒.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
                result = EducationalBackground.Value.高卒.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
                result = EducationalBackground.Value.短大卒.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
                result = EducationalBackground.Value.大卒.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
                result = EducationalBackground.Value.修士.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.DOCTOR.toString())) {
                result = EducationalBackground.Value.博士.toString()
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

    enum class Value{
        中卒,
        高卒,
        短大卒,
        大卒,
        修士,
        博士,
    }
}

