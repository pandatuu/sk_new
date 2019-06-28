package com.example.sk_android.mvp.model.jobselect

import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment
import org.json.JSONObject


class EducationalBackground {



    companion object {
        //教育背景
        fun getEducationalBackground(educationalBackground: String): String? {

            var result: String? = null
            if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MIDDLE_SCHOOL.toString())) {
                result = EducationalBackground.Value.中学.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
                result = EducationalBackground.Value.高中.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
                result = EducationalBackground.Value.专科.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
                result = EducationalBackground.Value.本科.toString()
            } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
                result = EducationalBackground.Value.硕士.toString()
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
        中学,
        高中,
        专科,
        本科,
        硕士,
        博士,
    }
}

