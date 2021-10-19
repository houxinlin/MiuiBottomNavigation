package com.hxl.miuibottomnavigation.build

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import com.hxl.miuibottomnavigation.BottomNavigationView
import com.hxl.miuibottomnavigation.Mode
import com.hxl.miuibottomnavigation.ViewItem
import com.hxl.miuibottomnavigation.extent.dp2Px
import com.hxl.miuibottomnavigation.mode.BaseMode
import com.hxl.miuibottomnavigation.mode.FixedMode
import com.hxl.miuibottomnavigation.mode.MIUIMode
import com.hxl.miuibottomnavigation.mode.ScrollMode
import com.hxl.miuibottomnavigation.utils.BitmapUtils

class NavigationBuild {

    private constructor()

    var mode: BaseMode? = null;

    var modeValue: Int = Mode.MODE_MIUI.value;

    var selectTextColor: Int = Color.BLACK;

    var defaultTextColor: Int = Color.BLACK;

    var itemList: MutableList<ViewItem> = mutableListOf();

    var defaultSelectIndex = 0;
    var textSize = 0;

    var fixedItems = mutableSetOf<Int>()

    var viewPager: ViewPager? = null;

    class Builder(var context: Context) {
        private var instance = NavigationBuild();

        private fun setDefaultValues() {
            if (instance.textSize == 0) {
                instance.textSize = (11).dp2Px(context);
            }
        }

        fun build(): NavigationBuild {
            setDefaultValues()
            return instance;
        }

        fun setMode(mode: Mode): Builder {
            instance.modeValue = mode.value;
            return this;
        }

        fun setSelectTextColor(value: Int): Builder {
            instance.selectTextColor = value
            return this;
        }

        fun setDefaultTextColor(value: Int): Builder {
            instance.defaultTextColor = value
            return this;
        }

        fun addItem(title: String, resId: Int): Builder {
            instance.itemList.add(
                ViewItem(
                    title,
                    BitmapUtils.loadBitmap(resId, context.resources!!)
                )
            )
            return this;
        }

        fun addItem(title: String, bitmap: Bitmap): Builder {
            instance.itemList.add(ViewItem(title, bitmap));
            return this;
        }

        fun setDefaultSelectIndex(index: Int): Builder {
            instance.defaultSelectIndex = index;
            return this;
        }

        fun setTextSize(int: Int): Builder {
            instance.textSize = int;
            return this;
        }

        fun setFixedItems(id: Set<Int>): Builder {
            instance.fixedItems.clear()
            instance.fixedItems.addAll(id)
            return this;
        }

        fun setupWithViewPager(viewPager: ViewPager): Builder {
            instance.viewPager = viewPager;
            return this;
        }


    }
}