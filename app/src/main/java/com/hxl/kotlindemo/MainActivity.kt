package com.hxl.kotlindemo

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import okhttp3.*


import android.os.Build


import android.widget.*
import androidx.annotation.RequiresApi
import com.hxl.miuibottomnavigation.BottomNavigationView
import com.hxl.miuibottomnavigation.IItemClickListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom)
            .addTab("首页", R.drawable.ic_home)
            .addTab("娱乐", R.drawable.ic_game)
            .addTab("我的",R.drawable.ic_me)
            .setClickListener(object : IItemClickListener {
                override fun click(index: Int) {
                    Toast.makeText(this@MainActivity, "${index}", Toast.LENGTH_SHORT).show()

                }
            })
            .init(0);


    }


}

