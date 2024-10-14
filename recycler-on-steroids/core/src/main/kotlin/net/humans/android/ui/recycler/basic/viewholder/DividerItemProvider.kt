package net.humans.android.ui.recycler.basic.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.humans.android.ui.recycler.basic.applyContentMargins
import net.humans.android.ui.recycler.basic.model.Divider
import net.humans.android.ui.recycler.steroid.ListItemProvider
import net.humans.android.ui.recycler.steroid.SteroidAdapter
import net.humans.android.ui.wrappers.setBackgroundColor

class DividerItemProvider(
    private val impl: Impl
) : ListItemProvider<Divider, SteroidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): SteroidAdapter.ViewHolder {
        return SteroidAdapter.ViewHolder(
            View(parent.context).apply {
                layoutParams = impl.createParams(parent as RecyclerView)
            }
        )
    }

    override fun onBindViewHolder(
        holder: SteroidAdapter.ViewHolder,
        item: Divider,
        payloads: List<Any>?
    ) {
        holder.itemView.apply {
            setBackgroundColor(item.color)
            (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                impl.setSize(this, item.size.getInt(context))
            }
            applyContentMargins(item.contentMargins)
        }
    }

    interface Impl {
        fun createParams(parent: RecyclerView): RecyclerView.LayoutParams
        fun setSize(params: ViewGroup.MarginLayoutParams, size: Int)
    }

    object Vertical : Impl {
        override fun createParams(parent: RecyclerView): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        }

        override fun setSize(params: ViewGroup.MarginLayoutParams, size: Int) {
            params.height = size
        }
    }

    object Horizontal : Impl {
        override fun createParams(parent: RecyclerView): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        override fun setSize(params: ViewGroup.MarginLayoutParams, size: Int) {
            params.width = size
        }
    }
}
