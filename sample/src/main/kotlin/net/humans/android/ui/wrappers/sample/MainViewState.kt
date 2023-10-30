package net.humans.android.ui.wrappers.sample

import net.humans.android.ui.wrappers.TextWrapper

internal data class MainViewState(
    val value1: TextWrapper = TextWrapper.EMPTY,
    val value2: TextWrapper = TextWrapper.EMPTY,
    val value3: TextWrapper = TextWrapper.EMPTY,
    val counter: TextWrapper = TextWrapper.EMPTY,
    val plusAvailable: Boolean = true,
    val minusAvailable: Boolean = false,
)
