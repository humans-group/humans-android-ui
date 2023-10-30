package net.humans.android.ui.wrappers.sample.compose

import net.humans.android.ui.wrappers.TextWrapper
import net.humans.android.ui.wrappers.sample.R

internal interface ComposeSampleViewStateFactory {
    fun create(counterValue: Int): ComposeSampleViewState

    companion object {
        private val CLICK_RANGE = 0..10
        val DEFAULT: ComposeSampleViewStateFactory = object : ComposeSampleViewStateFactory {
            override fun create(counterValue: Int): ComposeSampleViewState {
                return ComposeSampleViewState(
                    value1 = TextWrapper.Raw("Raw value"),
                    value2 = TextWrapper.Id(R.string.app_name),
                    value3 = TextWrapper.Plural(
                        R.plurals.click_quantity,
                        counterValue,
                        arrayOf(counterValue)
                    ),
                    counter = TextWrapper.Raw(counterValue.toString()),
                    plusAvailable = counterValue < CLICK_RANGE.last,
                    minusAvailable = counterValue > CLICK_RANGE.first,
                )
            }
        }
    }
}

