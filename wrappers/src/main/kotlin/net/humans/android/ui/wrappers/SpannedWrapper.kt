package net.humans.android.ui.wrappers

import android.text.Spannable
import android.text.TextPaint
import android.text.style.URLSpan

data class SpannedWrapper(
    val spannedText: Collection<SpannedText>
) {
    constructor(vararg args: SpannedText) : this(spannedText = args.toList())
}

data class SpannedText(
    val text: TextWrapper,
    val spans: List<Span>
) {
    sealed class Span {
        object StrikeThrough : Span()

        object Underline : Span()

        data class Style(
            val type: StyleType
        ) : Span() {
            enum class StyleType {
                BOLD,
                ITALIC,
                BOLD_ITALIC;
            }
        }

        data class ForegroundColor(
            val color: ColorWrapper
        ) : Span()
    }
}

fun removeUnderlines(spannable: Spannable): Spannable {
    val spans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
    for (span in spans) {
        val start = spannable.getSpanStart(span)
        val end = spannable.getSpanEnd(span)
        spannable.removeSpan(span)
        val newSpan = object : URLSpan(span.url) {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        spannable.setSpan(newSpan, start, end, 0)
    }
    return spannable
}
