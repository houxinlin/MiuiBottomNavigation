package com.hxl.miuibottomnavigation.mode

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import com.hxl.miuibottomnavigation.BottomNavigationView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ScrollMode(bottomNavigationView: BottomNavigationView) : BaseMode(bottomNavigationView) {

    var currentStart = 0f;
    var currentEnd = 0f;

    override fun draw(canvas: Canvas) {
        var itemRect = getItemRect(currentIndex)
        var path = Path()
        path.moveTo(currentStart, bodyMarginTop)
        currentEnd = currentStart + circleSize * 2
        path.quadTo(
            currentStart + circleSize,
            bezierMaxHeight,
            currentEnd,
            bodyMarginTop
        )
        canvas.drawPath(path, Paint().apply { color = Color.WHITE })

        for (i in bottomNavigationView.navigationBuild.itemList.indices) {
            drawIcon(bottomNavigationView.navigationBuild.itemList[i].icon, i, canvas);
            drawText(bottomNavigationView.navigationBuild.itemList[i].title, i, canvas)

        }
    }

    override fun handlerClick
                (index: Int) {
        if (isPlay) {
            return
        }
        if (currentIndex == index) {
            iconHeightMap[index] = iconDefaultTop - 1 * (iconDefaultTop - iconMaxTop)
        }
        var itemRect = getItemRect(currentIndex)
        var start = itemRect.mid - circleSize
        var end = getItemRect(index).mid - (circleSize)
        var valueAnimator = ValueAnimator.ofFloat(start, end)
        valueAnimator.addUpdateListener {
            var fl = it.animatedValue as Float

            var maxValue = max(start, end) - min(start, end);
            var currentValue = fl - min(start, end);
            var progress = 1 - currentValue / maxValue
            currentStart = fl;

//            if (start)
            if (currentIndex != index) {
                var va = iconMaxTop + progress * (iconDefaultTop - iconMaxTop)
                var vb = iconDefaultTop - progress * (iconDefaultTop - iconMaxTop)
                iconHeightMap[index] = if (start < end) va else vb
                iconHeightMap[currentIndex] = if (start > end) va else vb
            }


            bottomNavigationView.invalidate()
        }
        startValueAnimator(valueAnimator, index);


    }


}