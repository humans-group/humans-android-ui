package net.humans.android.ui.wrappers

import android.content.Context
import android.os.Parcelable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize
import net.humans.android.ui.wrappers.internal.getColorByAttrRes
import net.humans.android.ui.wrappers.internal.getColorCompat

sealed class ColorWrapper : Parcelable {

    @ColorInt
    abstract fun get(context: Context): Int

    @Parcelize
    data class Raw(@ColorInt val color: Int) : ColorWrapper() {
        override fun get(context: Context): Int {
            return color
        }
    }

    @Parcelize
    data class Id(@ColorRes val id: Int) : ColorWrapper() {
        override fun get(context: Context): Int {
            return context.getColorCompat(id)
        }
    }

    @Parcelize
    data class Attr(@AttrRes val attr: Int) : ColorWrapper() {
        override fun get(context: Context): Int {
            return context.getColorByAttrRes(attr)
        }
    }

    companion object {
        val TRANSPARENT = Raw(0)
    }
}
