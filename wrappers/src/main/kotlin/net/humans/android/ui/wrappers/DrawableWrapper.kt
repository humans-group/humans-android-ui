package net.humans.android.ui.wrappers

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import net.humans.android.ui.wrappers.internal.getDrawableCompat

sealed class DrawableWrapper {
    abstract fun get(context: Context): Drawable

    data class Id(@DrawableRes val id: Int) : DrawableWrapper() {
        override fun get(context: Context): Drawable {
            return requireNotNull(context.getDrawableCompat(id))
        }
    }

    data class Raw(val drawable: Drawable) : DrawableWrapper() {
        override fun get(context: Context): Drawable {
            return drawable
        }
    }
}
