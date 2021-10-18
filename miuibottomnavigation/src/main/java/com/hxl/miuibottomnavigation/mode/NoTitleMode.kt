package com.hxl.miuibottomnavigation.mode

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.hxl.miuibottomnavigation.BottomNavigationView

class NoTitleMode(bottomNavigationView: BottomNavigationView) : MIUIMode(bottomNavigationView) {
    init {
        drawTitle = false;
    }


}