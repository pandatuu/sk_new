package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity

class MainActivity : AppCompatActivity() {
    lateinit var stateSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sp.getString("token", "")
        var mEditor: SharedPreferences.Editor = stateSharedPreferences.edit()
        mEditor.putInt("condition", 0)
        mEditor.commit()
        if (token == "") {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            i.putExtra("condition",0)
            startActivity(i)
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        } else {
            var intent  = Intent(this@MainActivity,RecruitInfoShowActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }
}
