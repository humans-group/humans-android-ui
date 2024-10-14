package net.humans.android.ui.recycler.diffable

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Implementation of [DiffUtil.ItemCallback] that use [Diffable.id] in [DiffUtil.ItemCallback.areItemsTheSame] and
 * [Diffable.isContentTheSame] in [DiffUtil.ItemCallback.areContentsTheSame].
 */
@SuppressLint("DiffUtilEquals")
object DiffableEqualityItemCallback : DiffUtil.ItemCallback<Diffable>() {

    override fun areItemsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return newItem.isContentTheSame(oldItem)
    }

    override fun getChangePayload(oldItem: Diffable, newItem: Diffable): Any? {
        return newItem.getChangePayload(oldItem)
    }
}
