package net.humans.android.ui.recycler.steroid

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Implementation of [DiffUtil.ItemCallback] that use [Any.equals] in [DiffUtil.ItemCallback.areItemsTheSame] and
 * [DiffUtil.ItemCallback.areContentsTheSame].
 */
@SuppressLint("DiffUtilEquals")
private object EqualityItemCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}

/**
 * Get instance of [DiffUtil.ItemCallback] that work based on [Any.equals]
 */
@Suppress("UNCHECKED_CAST", "FunctionName")
fun <T> EqualityItemCallback(): DiffUtil.ItemCallback<T> {
    return EqualityItemCallback as DiffUtil.ItemCallback<T>
}
