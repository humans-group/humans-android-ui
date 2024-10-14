package net.humans.android.ui.recycler.di

fun interface DiDiffableWrapperProvider {
    fun providerWrappers(): List<DiDiffableWrapper>
}

fun <T : DiDiffableWrapper> Iterable<T>.toDiDiffableWrapperProvider(): DiDiffableWrapperProvider =
    DiDiffableWrapperProvider { this.toList() }
