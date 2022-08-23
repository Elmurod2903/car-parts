package uz.elmurod.carparts.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import java.lang.Exception
import kotlin.math.ceil
import android.R
import android.util.TypedValue
import android.view.Display

enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

fun Context.dp(value: Float): Float {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0f
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
}

fun Context.sp(value: Float): Float {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0f
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics)
}

fun View.dp(value: Float): Float {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0f
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
}

fun View.sp(value: Float): Float {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0f
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics)
}


fun View.dpFloat(value: Float): Float {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0f
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
}

fun View.dpInt(value: Float): Int {
    val metrics = this.resources.displayMetrics
    return if (value == 0f) {
        0
    } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics).toInt()
}


fun Context.getRealScreenSize(): Point {
    val size = Point()
    try {
        val windowManager =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getRealSize(size)
    } catch (e: Exception) {

    }
    return size
}

fun Context.test(): Point {
    val display: Display =
        (applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).getDefaultDisplay()
    val size = Point()
    display.getRealSize(size)
    val width = size.x
    val height = size.y
    return Point(width, height)
}

fun Activity.computeWindowSizeClasses() {

}

fun Context.hideKeyboard(view: View?) {
    if (view == null) {
        return
    }
    try {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
    }
}

fun Activity.setStatusBarColor(color: Int) {
    val window = this.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}



