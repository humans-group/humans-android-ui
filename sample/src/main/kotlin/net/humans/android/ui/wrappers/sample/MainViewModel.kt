package net.humans.android.ui.wrappers.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    private val factory: MainViewStateFactory = MainViewStateFactory.DEFAULT
) : ViewModel() {

    private var clickCount = 0
    private val _viewState = MutableStateFlow(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState

    init {
        updateState()
    }

    fun minus() {
        clickCount--
        updateState()
    }

    fun plus() {
        clickCount++
        updateState()
    }

    private fun updateState() {
        _viewState.value = factory.create(clickCount)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MainViewModel() as T
            }
        }
    }
}