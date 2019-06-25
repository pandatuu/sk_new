package com.example.sk_android.mvp.model.onlineresume.jobWanted

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class JobWantedModel(
    var areaIds: MutableList<String>,
    var attributes: Map<String, Serializable>,
    var createdAt: Long,
    var currencyType: String,
    var deletedAt: Long,
    var evaluation: String,
    var id: UUID,
    var industryIds: MutableList<String>,
    var recruitMethod: String,
    var resumeId: UUID,
    var salaryDailyMax: Long,
    var salaryDailyMin: Long,
    var salaryHourlyMax: Long,
    var salaryHourlyMin: Long,
    var salaryMax: Long,
    var salaryMin: Long,
    var salaryMonthlyMax: Long,
    var salaryMonthlyMin: Long,
    var salaryType: String,
    var salaryYearlyMax: Long,
    var salaryYearlyMin: Long,
    var updatedAt: Long,
    var userId: UUID,
    var workingExperience: Int,
    var workingTypes: MutableList<String>
): Parcelable

enum class SalaryType{
    HOURLY, DAILY, MONTHLY, YEARLY
}
enum class WorkingType{
    REGULAR, CONTRACT, DISPATCH, SHORT_TERM, OTHER
}
enum class RecruitMethod{
    FULL_TIME, PART_TIME
}
enum class CurrencyType{
    CNY, JPY, USD
}
enum class JobState{
    OFF, // 離職中(离职随时到岗)
    ON_NEXT_MONTH, // １か月以内には退職予定（在职-下月到岗）
    ON_CONSIDERING, // 良い条件が見つかり次第（在职-考虑机会）
    OTHER // その他（其他）
}