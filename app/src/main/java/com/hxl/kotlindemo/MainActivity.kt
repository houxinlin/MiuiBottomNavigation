package com.hxl.kotlindemo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.os.Build
import android.os.Handler


import android.widget.*
import androidx.annotation.RequiresApi
import com.hxl.miuibottomnavigation.BottomNavigationView
import com.hxl.miuibottomnavigation.IItemClickListener
import com.hxl.miuibottomnavigation.Mode
import com.hxl.miuibottomnavigation.build.NavigationBuild


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        findViewById<BottomNavigationView>(R.id.bottom)
            .setClickListener(object : IItemClickListener {
                override fun click(index: Int) {
                }
            })
            .init(
                NavigationBuild.Builder(this)
                    .addItem("首页", R.drawable.ic_home)
                    .addItem("娱乐", R.drawable.ic_game)
                    .addItem("我的", R.drawable.ic_me)
                    .setMode(Mode.MODE_NO_TITLE)
                    .setSelectTextColor(Color.RED)
                    .setFixedItems(mutableSetOf(1))
                    .build()
            )

        /**
         * 手动设置位置
         */
        Handler().postDelayed({
            findViewById<BottomNavigationView>(R.id.bottom).setCurrentItem(3)
        },1000)
    }


}

