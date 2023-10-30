package net.humans.android.ui.wrappers.sample.compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class ComposeSampleViewModel(
    private val factory: ComposeSampleViewStateFactory = ComposeSampleViewStateFactory.DEFAULT,
) : ViewModel() {
    private var counterValue = 0
    private val _viewState = MutableStateFlow(factory.create(counterValue))
    val viewState: StateFlow<ComposeSampleViewState> = _viewState

    fun minus() {
        counterValue--
        updateViewState()
    }

    fun plus() {
        counterValue++
        updateViewState()
    }

    private fun updateViewState() {
        _viewState.value = factory.create(counterValue)
    }
}