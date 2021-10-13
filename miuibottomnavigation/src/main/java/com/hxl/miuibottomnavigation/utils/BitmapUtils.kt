package com.hxl.miuibottomnavigation.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class BitmapUtils {
    companion object {
        fun loadBitmap(resId: Int, resource: Resources): Bitmap {
            var options = BitmapFactory.Options()
            val dm = resource.displayMetrics
            options.inDensity = dm.densityDpi
            return BitmapFactory.decodeResource(resource, resId, options)
        }

    }


}