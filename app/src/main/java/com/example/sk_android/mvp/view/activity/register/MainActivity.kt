package com.example.sk_android.mvp.view.activity.register


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.MainBodyFragment
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainScreenId=1

        frameLayout {
                val actionBarId = 2
                backgroundColorResource = R.color.whiteFF
                frameLayout{

                    id=actionBarId
                    val mainBodyFragment= MainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(id,mainBodyFragment).commit()

                }.lparams {
                    height= matchParent
                    width= matchParent
                }

        }
    }
}
