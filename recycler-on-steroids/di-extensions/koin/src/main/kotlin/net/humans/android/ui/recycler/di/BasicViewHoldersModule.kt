package net.humans.android.ui.recycler.di

import net.humans.android.ui.recycler.basic.model.Divider
import net.humans.android.ui.recycler.basic.viewholder.DividerItemProvider
import net.humans.android.ui.recycler.diffable.Diffable
import net.humans.android.ui.recycler.steroid.ListItemProvider
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.module

fun basicViewHoldersModule() = module {

    factory<ListItemProvider<out Diffable, *>>(TypeQualifier(Divider.Vertical::class)) {
        DividerItemProvider(impl = DividerItemProvider.Vertical)
    }
    factory<ListItemProvider<out Diffable, *>>(TypeQualifier(Divider.Horizontal::class)) {
        DividerItemProvider(impl = DividerItemProvider.Horizontal)
    }
}
