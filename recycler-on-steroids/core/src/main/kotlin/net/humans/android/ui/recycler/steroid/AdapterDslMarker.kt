package net.humans.android.ui.recycler.steroid

/**
 * Kotlin DSL marker for [SteroidAdapter] builder
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
annotation class AdapterDslMarker
