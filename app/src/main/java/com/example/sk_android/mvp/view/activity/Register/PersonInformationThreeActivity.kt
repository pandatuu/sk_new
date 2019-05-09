package com.example.sk_android.mvp.view.activity.Register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.example.sk_android.mvp.view.fragment.Register.WsBackgroundFragment
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

class PersonInformationThreeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainScreenId=1
        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar


            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }

}