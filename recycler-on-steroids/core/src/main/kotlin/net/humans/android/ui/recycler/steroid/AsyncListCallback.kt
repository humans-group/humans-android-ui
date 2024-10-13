package net.humans.android.ui.recycler.steroid

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil

/**
 * Wrap [DiffUtil.ItemCallback] to [AsyncDifferConfig]. It use [AsyncDifferConfig.Builder] under the hood.
 */
fun <T> DiffUtil.ItemCallback<T>.asAsyncDifferConfig(): AsyncDifferConfig<T> {
    return AsyncDifferConfig.Builder(this).build()
}
