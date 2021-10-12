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
import kotlin.math.*


class BottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewList: MutableList<ViewItem> = mutableListOf();
    private var bodyPaint: Paint = Paint().apply { color = Color.WHITE };
    private var clickListener: IItemClickListener? = null

    /**
     * View距离上边距，比实际的View小
     */
    private val BODYMARGIN_TOP: Float = (20).dp2Px(context).toFloat();

    /**
     * 圆大小
     */
    private val CIRCLE_SIZE: Float = (23).dp2Px(context).toFloat();

    /**
     * 图标最大高度
     */
    private val ICON_MAX_TOP = BODYMARGIN_TOP - (5).dp2Px(context).toFloat();

    /**
     * 图标默认高度
     */
    private val ICON_DEFAULT_TOP = BODYMARGIN_TOP + (2).dp2Px(context).toFloat();

    /**
     * 贝塞尔曲线最大高度
     */
    private var bezierMaxHeight: Float = -(4).dp2Px(context).toFloat();

    var defaultTextColor = Color.BLACK;
    var selectTextColor = Color.parseColor("#03a9f4");
    var textSize:Float=(10).dp2Px(context).toFloat();


    private var bezierHeightMap: MutableMap<Int, Float> = mutableMapOf();
    private var iconHeightMap: MutableMap<Int, Float> = mutableMapOf();
    private var currentIndex: Int = -1;

    private var itemWidth = 0


    fun Int.dp2Px(context: Context): Int {
        return (this * (context.resources.displayMetrics.density) + 0.5f).toInt();
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setItemWidth();
    }

    fun setItemWidth() {
        itemWidth = measuredWidth / viewList.size;

        for (i in viewList.indices) {
            bezierHeightMap[i] = BODYMARGIN_TOP;
            iconHeightMap[i] = ICON_DEFAULT_TOP;
        }
    }


    fun loadBitmap(id: Int): Bitmap {
        var options = BitmapFactory.Options()
        val dm = resources.displayMetrics
        options.inDensity = dm.densityDpi
        return BitmapFactory.decodeResource(resources, id, options)
    }

    fun isNightMode(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (viewList.size == 0) {
            return
        }
        if (!isNightMode()) {
            bodyPaint.setShadowLayer(25f, -10f, 2f, Color.parseColor("#BDBFBEBE"));
        }
        canvas.drawRect(
            0f,
            BODYMARGIN_TOP,
            width.toFloat(),
            measuredHeight.toFloat(),
            bodyPaint
        );

        for (i in viewList.indices) {

            var itemRect = getItemRect(i)
            var path = Path()
            path.moveTo(itemRect.mid - CIRCLE_SIZE, BODYMARGIN_TOP)
            path.quadTo(
                itemRect.mid.toFloat(),
                bezierHeightMap[i]!!.toFloat(),
                itemRect.mid + CIRCLE_SIZE,
                BODYMARGIN_TOP
            )
            canvas.drawPath(path, Paint().apply { color = Color.WHITE })
            drawIcon(viewList[i].icon, i, canvas);
            drawText(viewList[i].title, i, canvas)
        }

    }

    fun drawText(text: String, index: Int, canvas: Canvas) {
        var itemRect = getItemRect(index)
        var paint = Paint()
        paint.textSize = textSize
        paint.color = if (index==currentIndex) selectTextColor else defaultTextColor;
        var measureText = paint.measureText(text);
        canvas.drawText(text, itemRect.mid - measureText / 2, measuredHeight - 25f, paint)
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            var index = floor(event.x / itemWidth);
            showTabForIndex(index.toInt());
            clickListener?.click(index.toInt());
        }
        return true
    }

    fun getItemRect(index: Int): ItemRect {
        var start: Int = itemWidth * index;
        var end: Int = (itemWidth * index) + itemWidth;
        var mid = start + (end - start) / 2
        return ItemRect(start, end, mid)
    }

    fun showTabForIndex(index: Int) {
        var start = bezierHeightMap[index]!!.toFloat()
        var end = bezierMaxHeight;
        var valueAnimator = ValueAnimator.ofFloat(start, end)

        valueAnimator.addUpdateListener {
            var fl = it.animatedValue as Float
            bezierHeightMap[currentIndex] = (bezierMaxHeight + BODYMARGIN_TOP - fl)
            bezierHeightMap[index] = fl
            var progress = 1 - (fl + ((abs(min(start, end))))) / (abs(start) + abs(end));

            iconHeightMap[currentIndex] =
                ICON_MAX_TOP + progress * (ICON_DEFAULT_TOP - ICON_MAX_TOP)
            iconHeightMap[index] = ICON_DEFAULT_TOP - progress * (ICON_DEFAULT_TOP - ICON_MAX_TOP)
            invalidate()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentIndex = index
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
        valueAnimator.start()
    }

    fun refresh() {
        setItemWidth();
        invalidate();
    }

    fun addTab(title: String, icon: Bitmap): BottomNavigationView {
        viewList.add(ViewItem(title, icon))
        return this;
    }

    fun addTab(title: String, resId: Int): BottomNavigationView {
        viewList.add(ViewItem(title, loadBitmap(resId)))
        return this;
    }

    fun setClickListener(callback: IItemClickListener): BottomNavigationView {
        this.clickListener = callback;
        return this;
    }

    fun init(select: Int) {
        currentIndex = select;
        refresh();
        showTabForIndex(select);

    }

    inner class ItemRect(var start: Int, var end: Int, var mid: Int)

}