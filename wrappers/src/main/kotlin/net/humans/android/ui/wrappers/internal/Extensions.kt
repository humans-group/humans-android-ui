@file:Suppress("TooManyFunctions", "WildcardImport")

package net.humans.android.ui.wrappers.internal

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

@ColorInt
internal fun Context.getColorCompat(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

@ColorInt
internal fun Context.getColorByAttrRes(@AttrRes attrRes: Int): Int {
    val colorResId = this.getAttributeValue(attrRes)
    require(colorResId != 0) { "Invalid attribute value by id: $attrRes" }
    return getColorCompat(colorResId)
}

internal fun Context.getAttributeValue(@AttrRes attribute: Int) = theme.getAttributeValue(attribute)

internal fun Resources.Theme.getAttributeValue(@AttrRes attribute: Int): Int {
    val outValue = TypedValue().apply { resolveAttribute(attribute, this, true) }
    return outValue.resourceId
}

internal fun Context.pxi(dp: Float): Int {
    return resources.pxi(dp)
}

@Suppress("MagicNumber")
internal fun Resources.pxi(dp: Float): Int {
    return (0.5f + displayMetrics.density * dp).toInt()
}

internal fun Context.pxf(dp: Float): Float {
    return resources.pxf(dp)
}

internal fun Resources.pxf(dp: Float): Float {
    return displayMetrics.density * dp
}

@DimenRes
internal fun Context.getDimenResByAttrRes(@AttrRes attrRes: Int): Int {
    val dimenResId = this.getAttributeValue(attrRes)
    requireNotNull(dimenResId != 0) { "Invalid attribute value by id: $attrRes" }
    return dimenResId
}

internal fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? {
    return if (id != 0) ContextCompat.getDrawable(this, id) else null
}

internal fun Context.getPlural(@PluralsRes id: Int, quantity: Int): String {
    return resources.getQuantityString(id, quantity, quantity)
}

internal fun SpannableStringBuilder.set(substring: String, span: CharacterStyle) {
    val startIndex = indexOf(substring)
    setSpan(
        span,
        startIndex,
        startIndex + substring.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

internal fun View.updateMargins(
    start: Int = marginStart,
    top: Int = marginTop,
    end: Int = marginEnd,
    bottom: Int = marginBottom
) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        var changed = false
        if (marginStart != start) {
            marginStart = start
            changed = true
        }
        if (topMargin != top) {
            topMargin = top
            changed = true
        }
        if (marginEnd != end) {
            marginEnd = end
            changed = true
        }
        if (bottomMargin != bottom) {
            bottomMargin = bottom
            changed = true
        }
        if (changed) {
            requestLayout()
        }
    }
}

@Suppress("DEPRECATION")
internal fun String.fromHtmlCompat(
    flags: Int = FROM_HTML_MODE_LEGACY,
    imageGetter: Html.ImageGetter? = null,
    tagHandler: Html.TagHandler? = null
): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, flags, imageGetter, tagHandler)
    } else {
        Html.fromHtml(this)
    }
}
