package net.humans.android.ui.recycler.basic

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.view.updatePadding
import net.humans.android.ui.recycler.basic.model.ContentMargins
import net.humans.android.ui.recycler.basic.model.ContentPaddings
import net.humans.android.ui.wrappers.DimensWrapper
import net.humans.android.ui.wrappers.updateMargins

fun View.applyContentPaddings(paddings: ContentPaddings) {
    with(paddings) {
        updatePadding(
            start?.getInt(context) ?: paddingLeft,
            top?.getInt(context) ?: paddingTop,
            end?.getInt(context) ?: paddingRight,
            bottom?.getInt(context) ?: paddingBottom
        )
    }
}

fun View.applyContentMargins(margins: ContentMargins) {
    this.updateMargins(
        start = margins.start,
        top = margins.top,
        end = margins.end,
        bottom = margins.bottom
    )
}

fun View.setBackgroundCornerRadius(dimensWrapper: DimensWrapper) {
    (this.background as? GradientDrawable)?.cornerRadius = dimensWrapper.getFloat(this.context)
}
