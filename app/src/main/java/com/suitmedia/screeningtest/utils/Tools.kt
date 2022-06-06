package com.suitmedia.screeningtest.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.suitmedia.screeningtest.R

object Tools {
    // extension function to hide soft keyboard
    // https://stackoverflow.com/a/45857155/14795594
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setSystemBarColor(act: Activity, @ColorRes color: Int, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(context, color)
        }
    }

    fun setSystemBarLight(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val window = act.window
            window.setDecorFitsSystemWindows(false)
        }
    }

    fun clearSystemBarLight(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = act.window
            window.statusBarColor = ContextCompat.getColor(act, R.color.colorPrimaryDark)
        }
    }

    fun dpToPx(c: Context, dp: Int): Int {
        val r = c.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }

    fun displayImageOriginal(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
        Glide.with(ctx).load(drawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img)
    }

}
