package net.humans.android.ui.recycler.basic.model

import android.graphics.Color
import net.humans.android.ui.recycler.diffable.Diffable
import net.humans.android.ui.recycler.diffable.SimpleDiffable
import net.humans.android.ui.wrappers.ColorWrapper
import net.humans.android.ui.wrappers.DimensWrapper

data class Divider(
    override val id: Any,
    val size: DimensWrapper,
    val color: ColorWrapper = ColorWrapper.TRANSPARENT,
    val backgroundColor: ColorWrapper = ColorWrapper.Raw(Color.GRAY),
    val contentMargins: ContentMargins = ContentMargins.NO_MARGINS,
) : Diffable {
    object Vertical : SimpleDiffable
    object Horizontal : SimpleDiffable
}
