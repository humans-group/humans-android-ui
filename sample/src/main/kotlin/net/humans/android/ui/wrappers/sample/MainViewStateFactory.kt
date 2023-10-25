package net.humans.android.ui.wrappers.sample

import net.humans.android.ui.wrappers.TextWrapper

internal interface MainViewStateFactory {
    fun create(clickCount: Int): MainViewState

    companion object {
        private val CLICK_RANGE = 0..10
        val DEFAULT: MainViewStateFactory = object : MainViewStateFactory {
            override fun create(clickCount: Int): MainViewState = MainViewState(
                value1 = TextWrapper.Raw("Raw value"),
                value2 = TextWrapper.Id(R.string.app_name),
                value3 = TextWrapper.Plural(
                    R.plurals.click_quantity,
                    clickCount,
                    arrayOf(clickCount)
                ),
                counter = TextWrapper.Raw(clickCount.toString()),
                plusAvailable = clickCount < CLICK_RANGE.last,
                minusAvailable = clickCount > CLICK_RANGE.first,
            )
        }
    }
}
