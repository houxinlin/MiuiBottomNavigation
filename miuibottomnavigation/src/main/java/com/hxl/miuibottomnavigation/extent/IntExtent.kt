package com.hxl.miuibottomnavigation.extent

import android.content.Context

class IntExtent {

}
public inline fun Int.dp2Px(context: Context): Int {
    return (this * (context.resources.displayMetrics.density) + 0.5f).toInt();
}