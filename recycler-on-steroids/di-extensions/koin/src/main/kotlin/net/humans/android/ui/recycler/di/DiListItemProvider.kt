package net.humans.android.ui.recycler.di

import net.humans.android.ui.recycler.steroid.ListItemProvider
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

class DiListItemProvider<out LIP : ListItemProvider<*, *>>(
    private val scope: Scope,
    private val klass: KClass<LIP>,
    private val qualifier: Qualifier? = null,
    private val parameters: ParametersDefinition? = null
) {
    fun get(q: Qualifier? = qualifier, parameters1: ParametersDefinition? = null): LIP =
        scope.get(klass, q) {
            ParametersHolder(
                listOfNotNull(
                    parameters?.invoke()?.values,
                    parameters1?.invoke()?.values
                ).flatten().toMutableList()
            )
        }

    fun getOrNull(q: Qualifier? = qualifier): LIP? = scope.getOrNull(klass, q, parameters)
}

inline fun <reified LIP : ListItemProvider<*, *>> Scope.listItemProvider(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): DiListItemProvider<LIP> {
    return DiListItemProvider(this, LIP::class, qualifier, parameters)
}
