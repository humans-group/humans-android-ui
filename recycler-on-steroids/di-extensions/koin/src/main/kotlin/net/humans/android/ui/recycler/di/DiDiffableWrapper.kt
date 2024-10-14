package net.humans.android.ui.recycler.di

import net.humans.android.ui.recycler.diffable.Diffable
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import kotlin.reflect.KClass

interface DiDiffableWrapper {
    val kClass: KClass<out Diffable>
    val qualifier: Qualifier
}

data class SimpleDiDiffableWrapper(
    override val kClass: KClass<out Diffable>,
    override val qualifier: Qualifier
) : DiDiffableWrapper {
    constructor(kClass: KClass<out Diffable>) : this(
        kClass = kClass,
        qualifier = TypeQualifier(kClass)
    )
}

inline fun <reified T : Diffable> diDiffableWrapper(qualifier: Qualifier? = null): DiDiffableWrapper =
    qualifier?.let { safeQualifier ->
        SimpleDiDiffableWrapper(
            kClass = T::class,
            qualifier = safeQualifier
        )
    } ?: SimpleDiDiffableWrapper(kClass = T::class)
