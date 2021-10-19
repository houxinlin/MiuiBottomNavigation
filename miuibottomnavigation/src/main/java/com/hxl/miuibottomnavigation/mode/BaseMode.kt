package com.hxl.miuibottomnavigation.mode

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.hxl.miuibottomnavigation.BottomNavigationView
import com.hxl.miuibottomnavigation.extent.dp2Px
import kotlin.math.log

abstract class BaseMode(var bottomNavigationView: BottomNavigationView) {
    var currentIndex: Int = 0;
    var isPlay: Boolean = false;


    /**
     * 绘制View的Paint
     */
    protected var bodyPaint: Paint = Paint().apply { color = Color.WHITE };

    /**
     * Item宽度
     */
    var itemWidth = 0

    /**
     * View距离上边距，比实际的View小
     */
    val bodyMarginTop: Float = (20).dp2Px(bottomNavigationView.context).toFloat();

    /**
     * 圆大小
     */
    val circleSize: Float = (23).dp2Px(bottomNavigationView.context).toFloat();

    /**
     * 图标最大高度
     */
    val iconMaxTop = bodyMarginTop - (5).dp2Px(bottomNavigationView.context).toFloat();

    /**
     * 图标默认高度
     */
    val iconDefaultTop = bodyMarginTop + (2).dp2Px(bottomNavigationView.context).toFloat();

    /**
     * 贝塞尔曲线最大高度
     */
    var bezierMaxHeight: Float = -(4).dp2Px(bottomNavigationView.context).toFloat();

    /**
     * 每个图片高度
     */
    var iconHeightMap: MutableMap<Int, Float> = mutableMapOf();

    /**
     * 每个贝塞尔曲线高度
     */
    var bezierHeightMap: MutableMap<Int, Float> = mutableMapOf();


    /**
     * 是否绘制标题
     */
    var drawTitle: Boolean = true;
    fun isNightMode(resources: Resources): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES
    }

    fun drawText(text: String, index: Int, canvas: Canvas) {
        if (!drawTitle) {
            return
        }
        var itemRect = getItemRect(index)
        var paint = Paint()
        paint.textSize = bottomNavigationView.navigationBuild.textSize.toFloat()
        paint.color =
            if (index == currentIndex) bottomNavigationView.navigationBuild.selectTextColor
            else bottomNavigationView.navigationBuild.defaultTextColor;
        var measureText = paint.measureText(text);
        canvas.drawText(
            text, itemRect.mid - measureText / 2, bottomNavigationView.measuredHeight - 25f,
            paint
        )
    }

    fun drawIcon(bitmap: Bitmap, index: Int, canvas: Canvas) {
        var width = bitmap.width
        var itemRect = getItemRect(index)
        canvas.drawBitmap(
            bitmap,
            (itemRect.mid - width / 2).toFloat(),
            iconHeightMap[index]!!,
            Paint()
        )
    }

    fun getItemRect(index: Int): BottomNavigationView.ItemRect {
        var start: Int = itemWidth * index;
        var end: Int = (itemWidth * index) + itemWidth;
        var mid = start + (end - start) / 2
        return BottomNavigationView.ItemRect(start, end, mid)
    }

    fun setItemWidth() {
        itemWidth = bottomNavigationView.measuredWidth /
                bottomNavigationView.navigationBuild.itemList.size

    }

    abstract fun draw(canvas: Canvas);

    fun onDraw(canvas: Canvas) {
        if (!isNightMode(bottomNavigationView.resources)) {
            bodyPaint.setShadowLayer(25f, -10f, 2f, Color.parseColor("#BDBFBEBE"));
        }
        canvas.drawRect(
            0f,
            bodyMarginTop,
            bottomNavigationView.measuredWidth.toFloat(),
            bottomNavigationView.measuredHeight.toFloat(),
            bodyPaint
        );
        draw(canvas);
    }

    fun startValueAnimator(valueAnimator: ValueAnimator, index: Int) {
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                isPlay = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentIndex = index
                isPlay = false
            }

            override fun onAnimationCancel(animation: Animator?) {
                isPlay = false
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
        valueAnimator.start()
    }

    abstract fun handlerClick(index: Int);

    fun clickItem(index: Int) {
        if (isPlay) {
            return
        }
        bottomNavigationView.navigationBuild.viewPager?.currentItem = index
        handlerClick(index)
    }


    fun init() {
        for (i in bottomNavigationView.navigationBuild.itemList.indices) {
            bezierHeightMap[i] = bodyMarginTop;
            iconHeightMap[i] = iconDefaultTop;
        }
    }
}