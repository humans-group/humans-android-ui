@file:Suppress("DeprecatedCallableAddReplaceWith", "TooManyFunctions")

package net.humans.android.ui.recycler.basic

import net.humans.android.ui.recycler.basic.model.Divider
import net.humans.android.ui.recycler.diffable.Diffable
import net.humans.android.ui.wrappers.ColorWrapper
import net.humans.android.ui.wrappers.DimensWrapper

typealias DecorationRule = (prev: Diffable?, next: Diffable?) -> Diffable?

fun List<Diffable>.decorate(rules: List<DecorationRule>): List<Diffable> {
    val initial = this
    return mutableListOf<Diffable>().apply {
        repeat(initial.size + 1) { index ->
            val prev = initial.getOrNull(index - 1)
            val next = initial.getOrNull(index)
            rules.forEach { rule ->
                rule.invoke(prev, next)?.let { add(it) }
            }
            next?.let { add(it) }
        }
    }
}

@Suppress("FunctionNaming")
fun DecorationRules(block: DecorationRulesBuilder.() -> Unit): List<DecorationRule> {
    return DecorationRulesBuilder().apply(block).build()
}

fun id(prev: Diffable?, next: Diffable?): String {
    return when {
        prev != null && next != null -> "${prev.id}-${next.id}"
        prev != null -> "after:${prev.id}"
        next != null -> "before:${next.id}"
        else -> ""
    }
}

class DecorationRulesBuilder(
    val rules: MutableList<DecorationRule> = mutableListOf()
) {
    inline fun between(
        crossinline predicate: (prev: Diffable?, next: Diffable?) -> Boolean,
        crossinline factory: (Diffable?, Diffable?) -> Diffable
    ) {
        rules.add { prev, next ->
            when (predicate.invoke(prev, next)) {
                true -> factory.invoke(prev, next)
                false -> null
            }
        }
    }

    inline fun between(
        prevClass: Class<out Diffable>,
        nextClass: Class<out Diffable>,
        crossinline factory: (Diffable?, Diffable?) -> Diffable
    ) {
        between({ prev, next -> prevClass.isInstance(prev) && nextClass.isInstance(next) }, factory)
    }

    inline fun <reified Prev : Diffable, reified Next : Diffable> between(
        crossinline factory: (Diffable?, Diffable?) -> Diffable
    ) {
        between(Prev::class.java, Next::class.java, factory)
    }

    fun rule(rule: DecorationRule) {
        rules.add(rule)
    }

    inline fun between(
        crossinline predicate: (prev: Diffable?, next: Diffable?) -> Boolean,
        offset: DimensWrapper,
        color: ColorWrapper = ColorWrapper.TRANSPARENT
    ) {
        between(predicate) { prev, next ->
            Divider(
                id = id(prev, next),
                size = offset,
                color = color
            )
        }
    }

    inline fun <reified Prev : Diffable, reified Next : Diffable> between(
        offset: DimensWrapper,
        color: ColorWrapper = ColorWrapper.TRANSPARENT
    ) {
        between(Prev::class.java, Next::class.java, offset, color)
    }

    fun between(
        prevClass: Class<out Diffable>,
        nextClass: Class<out Diffable>,
        offset: DimensWrapper,
        color: ColorWrapper = ColorWrapper.TRANSPARENT
    ) {
        between(
            { prev, next -> prevClass.isInstance(prev) && nextClass.isInstance(next) },
            offset,
            color
        )
    }

    fun before(
        cls: Class<out Diffable>,
        offset: DimensWrapper,
        color: ColorWrapper
    ) {
        between({ _, next -> cls.isInstance(next) }, offset, color)
    }

    inline fun <reified T : Diffable> before(
        offset: DimensWrapper,
        color: ColorWrapper = ColorWrapper.TRANSPARENT
    ) {
        before(T::class.java, offset, color)
    }

    inline fun <reified T : Diffable> after(
        offset: DimensWrapper,
        color: ColorWrapper = ColorWrapper.TRANSPARENT
    ) {
        between({ prev, _ -> T::class.java.isInstance(prev) }, offset, color)
    }

    fun build(): List<DecorationRule> {
        return rules
    }
}
