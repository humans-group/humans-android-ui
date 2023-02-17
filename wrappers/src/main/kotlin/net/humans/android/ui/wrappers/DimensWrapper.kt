package net.humans.android.ui.wrappers

import android.content.Context
import android.os.Parcelable
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import kotlinx.parcelize.Parcelize
import net.humans.android.ui.wrappers.internal.getDimenResByAttrRes
import net.humans.android.ui.wrappers.internal.pxf
import net.humans.android.ui.wrappers.internal.pxi

sealed class DimensWrapper : Parcelable {
    abstract fun getFloat(context: Context): Float

    abstract fun getInt(context: Context): Int

    @Parcelize
    data class Raw(@Px val pixels: Int) : DimensWrapper() {

        override fun getInt(context: Context): Int {
            return pixels
        }

        override fun getFloat(context: Context): Float {
            return getInt(context).toFloat()
        }
    }

    @Parcelize
    data class RawDp(val dp: Float) : DimensWrapper() {

        override fun getInt(context: Context): Int {
            return context.pxi(dp)
        }

        override fun getFloat(context: Context): Float {
            return context.pxf(dp)
        }
    }

    @Parcelize
    data class Id(@DimenRes val id: Int) : DimensWrapper() {

        override fun getInt(context: Context): Int {
            return context.resources.getDimensionPixelSize(id)
        }

        override fun getFloat(context: Context): Float {
            return context.resources.getDimension(id)
        }
    }

    @Parcelize
    data class Attr(@AttrRes val attr: Int) : DimensWrapper() {

        override fun getInt(context: Context): Int {
            return context.resources.getDimensionPixelSize(context.getDimenResByAttrRes(attr))
        }

        override fun getFloat(context: Context): Float {
            return context.resources.getDimension(context.getDimenResByAttrRes(attr))
        }
    }
}
