package com.hxl.miuibottomnavigation.mode

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import com.hxl.miuibottomnavigation.BottomNavigationView

class FixedMode(bottomNavigationView: BottomNavigationView) : BaseMode(bottomNavigationView) {
    override fun draw(canvas: Canvas) {
        for (i in bottomNavigationView.navigationBuild.itemList.indices) {
            if (bottomNavigationView.navigationBuild.fixedItems.contains(i)) {
                var itemRect = getItemRect(i)
                var path = Path()
                path.moveTo(itemRect.mid - circleSize, bodyMarginTop)
                path.quadTo(
                    itemRect.mid.toFloat(),
                    bezierMaxHeight,
                    itemRect.mid + circleSize,
                    bodyMarginTop
                )
                canvas.drawPath(path, Paint().apply { color = Color.WHITE })
            }
            drawIcon(bottomNavigationView.navigationBuild.itemList[i].icon, i, canvas);
            drawText(bottomNavigationView.navigationBuild.itemList[i].title, i, canvas)
        }
    }

    override fun handlerClick(index: Int) {
        currentIndex = index;
        bottomNavigationView.invalidate()
    }

}