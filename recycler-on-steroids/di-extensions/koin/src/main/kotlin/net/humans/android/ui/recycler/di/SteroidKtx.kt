package net.humans.android.ui.recycler.di

import net.humans.android.ui.recycler.steroid.Adapter
import net.humans.android.ui.recycler.steroid.ListItemProvider
import org.koin.core.parameter.parametersOf

fun <T : Any> Adapter<T>.addItemProviders(
    diDiffableProvider: DiDiffableWrapperProvider,
    diListItemProvider: DiListItemProvider<ListItemProvider<T, *>>,
    parameter: Any,
) {
    diDiffableProvider.providerWrappers()
        .forEach { wrapper ->
            val itemProvider = diListItemProvider.get(wrapper.qualifier) { parametersOf(parameter) }
            addItemProvider(wrapper.kClass.java, itemProvider)
        }
}
