@file:Suppress("TooManyFunctions", "WildcardImport")

package net.humans.android.ui.wrappers

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import net.humans.android.ui.wrappers.internal.fromHtmlCompat
import net.humans.android.ui.wrappers.internal.set
import net.humans.android.ui.wrappers.internal.updateMargins

fun ImageView.setImage(drawa: DrawableWrapper?) {
    setImageDrawable(drawa?.get(context))
}

fun View.setBackground(drawa: DrawableWrapper?) {
    background = drawa?.get(context)
}

fun View.setBackgroundColor(color: ColorWrapper) {
    setBackgroundColor(color.get(context))
}

fun ImageView.setColorFilter(color: ColorWrapper, mode: PorterDuff.Mode) {
    setColorFilter(color.get(context), mode)
}

fun ImageView.setTint(color: ColorWrapper?) {
    imageTintList = color?.get(context)?.let(ColorStateList::valueOf)
}

fun TextView.setTextColor(color: ColorWrapper) {
    setTextColor(color.get(context))
}

fun TextView.setText(
    text: TextWrapper?,
    context: Context = this.context
) {
    setText(text?.get(context))
}

fun TextView.setTextFromHtml(
    text: TextWrapper?,
    spannedModifier: (Spanned) -> Spanned = { it }
) {
    setText(text?.get(context)?.toString()?.fromHtmlCompat()?.let { spannedModifier(it) })
}

fun TextView.setDrawable(
    start: DrawableWrapper? = null,
    top: DrawableWrapper? = null,
    end: DrawableWrapper? = null,
    bottom: DrawableWrapper? = null
) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        start?.get(context),
        top?.get(context),
        end?.get(context),
        bottom?.get(context)
    )
}

fun View.updatePadding(
    start: DimensWrapper? = null,
    top: DimensWrapper? = null,
    end: DimensWrapper? = null,
    bottom: DimensWrapper? = null
) {
    updatePadding(
        start?.getInt(context) ?: paddingStart,
        top?.getInt(context) ?: paddingTop,
        end?.getInt(context) ?: paddingEnd,
        bottom?.getInt(context) ?: paddingBottom,
    )
}

fun View.updateMargins(
    start: DimensWrapper? = null,
    top: DimensWrapper? = null,
    end: DimensWrapper? = null,
    bottom: DimensWrapper? = null
) {
    updateMargins(
        start?.getInt(context) ?: marginStart,
        top?.getInt(context) ?: marginTop,
        end?.getInt(context) ?: marginEnd,
        bottom?.getInt(context) ?: marginBottom,
    )
}

fun TextView.setText(spannedWrapper: SpannedWrapper) {
    text = buildSpannedString {
        spannedWrapper.spannedText.forEach { spannedText ->
            val text = spannedText.text.get(context).toString()
            append(text)
            spannedText.spans.forEach { span ->
                set(
                    substring = text,
                    span = when (span) {
                        SpannedText.Span.StrikeThrough -> StrikethroughSpan()
                        SpannedText.Span.Underline -> UnderlineSpan()
                        is SpannedText.Span.ForegroundColor ->
                            ForegroundColorSpan(span.color.get(context))
                        is SpannedText.Span.Style -> when (span.type) {
                            SpannedText.Span.Style.StyleType.BOLD -> Typeface.BOLD
                            SpannedText.Span.Style.StyleType.ITALIC -> Typeface.ITALIC
                            SpannedText.Span.Style.StyleType.BOLD_ITALIC -> Typeface.BOLD_ITALIC
                        }.let(::StyleSpan)
                    }
                )
            }
        }
    }
}
