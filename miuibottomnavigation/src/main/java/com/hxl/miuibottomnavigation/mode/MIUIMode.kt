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
import kotlin.math.min

class MIUIMode(bottomNavigationView: BottomNavigationView) : BaseMode(bottomNavigationView) {

    override fun draw(canvas: Canvas) {
        for (i in bottomNavigationView.navigationBuild.itemList.indices) {
            var itemRect = getItemRect(i)
            var path = Path()
            path.moveTo(itemRect.mid - circleSize, bodyMarginTop)
            path.quadTo(
                itemRect.mid.toFloat(),
                bezierHeightMap[i]!!.toFloat(),
                itemRect.mid + circleSize,
                bodyMarginTop
            )
            canvas.drawPath(path, Paint().apply { color = Color.WHITE })
            drawIcon(bottomNavigationView.navigationBuild.itemList[i].icon, i, canvas);
            drawText(bottomNavigationView.navigationBuild.itemList[i].title, i, canvas)
        }
    }


    override fun handlerClick(index: Int) {
        var start = bezierHeightMap[index]!!.toFloat()
        var end = bezierMaxHeight;
        var valueAnimator = ValueAnimator.ofFloat(start, end)

        valueAnimator.addUpdateListener {
            var fl = it.animatedValue as Float
            bezierHeightMap[currentIndex] = (bezierMaxHeight + bodyMarginTop - fl)
            bezierHeightMap[index] = fl
            var progress = 1 - (fl + ((abs(min(start, end))))) / (abs(start) + abs(end));

            iconHeightMap[currentIndex] =
                iconMaxTop + progress * (iconDefaultTop - iconMaxTop)
            iconHeightMap[index] = iconDefaultTop - progress * (iconDefaultTop - iconMaxTop)
            bottomNavigationView.invalidate()
        }
        startValueAnimator(valueAnimator,index)
    }


}