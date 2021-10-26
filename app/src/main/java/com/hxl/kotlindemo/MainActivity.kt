package com.hxl.kotlindemo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewGroup


import android.widget.*
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.hxl.miuibottomnavigation.BottomNavigationView
import com.hxl.miuibottomnavigation.IItemClickListener
import com.hxl.miuibottomnavigation.Mode
import com.hxl.miuibottomnavigation.build.NavigationBuild


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<ViewPager>(R.id.viewpager).adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 3
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`;
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                var arr = arrayListOf<Int>(Color.RED, Color.BLUE, Color.YELLOW)
                var relativeLayout = RelativeLayout(this@MainActivity)
                relativeLayout.setBackgroundColor(arr[position])
                container.addView(relativeLayout)
                return relativeLayout;
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

        }
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
                    .setupWithViewPager(findViewById(R.id.viewpager))
                    .setFixedItems(mutableSetOf(1))
                    .build()
            )


    }


}

