package net.humans.android.ui.recycler.basic.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.humans.android.ui.wrappers.DimensWrapper

@Parcelize
data class ContentMargins(
    val start: DimensWrapper? = null,
    val top: DimensWrapper? = null,
    val end: DimensWrapper? = null,
    val bottom: DimensWrapper? = null
) : Parcelable {

    constructor(all: DimensWrapper? = null) : this(start = all, top = all, end = all, bottom = all)

    companion object {
        val NO_MARGINS = ContentMargins()
    }
}

fun ContentMargins.toPaddings(): ContentPaddings = ContentPaddings(
    start = start,
    end = end,
    top = top,
    bottom = bottom
)
