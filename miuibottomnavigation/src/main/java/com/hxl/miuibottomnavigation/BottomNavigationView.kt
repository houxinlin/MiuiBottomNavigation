package com.hxl.miuibottomnavigation

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

import android.graphics.Paint

import android.graphics.Color

import android.graphics.Canvas

import android.graphics.Bitmap
import android.view.MotionEvent
import android.content.res.Configuration
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.hxl.miuibottomnavigation.build.NavigationBuild
import com.hxl.miuibottomnavigation.mode.FixedMode
import com.hxl.miuibottomnavigation.mode.MIUIMode
import com.hxl.miuibottomnavigation.mode.NoTitleMode
import com.hxl.miuibottomnavigation.mode.ScrollMode
import kotlin.math.*


class BottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var navigationBuild: NavigationBuild = NavigationBuild.Builder(context).build();

    private var clickListener: IItemClickListener? = null


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        navigationBuild.mode?.setItemWidth()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (navigationBuild.itemList.size == 0) {
            return
        }
        navigationBuild.mode?.onDraw(canvas);

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            var index = floor(event.x / navigationBuild.mode!!.itemWidth);
            navigationBuild.mode?.clickItem(index.toInt());
            clickListener?.click(index.toInt());
        }
        return true
    }


    fun refresh() {
        invalidate();

    }


    fun setClickListener(callback: IItemClickListener): BottomNavigationView {
        this.clickListener = callback;
        return this;
    }

    fun setCurrentItem(index: Int) {
        if ((index >= navigationBuild.itemList.size) || index < 0) {
            return
        }
        navigationBuild.mode?.clickItem(index)
    }

    fun init(builder: NavigationBuild) {
        this.navigationBuild = builder;
        when (builder.modeValue) {
            Mode.MODE_MIUI.value -> {
                navigationBuild.mode = MIUIMode(this)
            }
            Mode.MODE_SCROLL.value -> {
                navigationBuild.mode = ScrollMode(this)
            }
            Mode.MODE_FIXED.value -> {
                navigationBuild.mode = FixedMode(this)
            }
            Mode.MODE_NO_TITLE.value -> {
                navigationBuild.mode = NoTitleMode(this)
            }
        }
        navigationBuild.mode?.init()
        post {
            navigationBuild.mode?.clickItem(builder.defaultSelectIndex)
            refresh()
        }
        navigationBuild.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                setCurrentItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    class ItemRect(var start: Int, var end: Int, var mid: Int)

}