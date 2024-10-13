package net.humans.android.ui.recycler.steroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import net.humans.android.ui.recycler.inflateViewBinding

/**
 * Delegate creation and bind [RecyclerView.ViewHolder]
 */
@Suppress("UnnecessaryAbstractClass")
abstract class ListItemProvider<T : Any, VH : SteroidAdapter.ViewHolder> {

    abstract fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): VH

    @Suppress("UNCHECKED_CAST")
    internal fun bindViewHolder(
        holder: SteroidAdapter.ViewHolder,
        item: T,
        payloads: List<Any>? = null
    ) {
        holder.item = item
        onBindViewHolder(holder as VH, item, payloads)
    }

    protected open fun onBindViewHolder(holder: VH, item: T, payloads: List<Any>?) {
    }

    @Suppress("UNCHECKED_CAST")
    internal fun recycleView(holder: SteroidAdapter.ViewHolder) {
        onViewRecycled(holder as VH)
    }

    protected open fun onViewRecycled(holder: VH) {
    }
}

/**
 * [ListItemProvider] that wrap parse a [View] from [itemViewLayoutId]
 */
open class ViewListItemProvider<T : Any>(
    @LayoutRes private val itemViewLayoutId: Int = 0
) : ListItemProvider<T, SteroidAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): SteroidAdapter.ViewHolder {
        return SteroidAdapter.ViewHolder(layoutInflater.inflate(itemViewLayoutId, parent, false))
    }
}

/**
 * [ListItemProvider] that based on View Binding
 */
open class ViewBindingListItemProvider<T : Any, VB : ViewBinding>(
    private val vbClass: Class<VB>? = null
) : ListItemProvider<T, ViewBindingListItemProvider.ViewHolder<VB>>() {

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB? {
        return if (vbClass == null) null else inflateViewBinding(
            vbClass,
            layoutInflater,
            parent,
            attachToRoot = false
        )
    }

    override fun onCreateViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder<VB> {
        val viewBinding = requireNotNull(createViewBinding(layoutInflater, parent)) {
            "ViewBinding hasn't been created. Override createViewBinding or set vbClass in the constructor: $vbClass"
        }
        return ViewHolder(viewBinding)
    }

    @Suppress("UndocumentedPublicClass")
    class ViewHolder<VB : ViewBinding> internal constructor(
        val viewBinding: VB
    ) : SteroidAdapter.ViewHolder(viewBinding.root)
}

/**
 * Create [ViewBindingListItemProvider]
 */
@Suppress("FunctionNaming")
inline fun <T : Any, VB : ViewBinding> ViewBindingListItemProvider(
    crossinline createViewBinding: (LayoutInflater, ViewGroup?) -> VB,
    crossinline bindViewHolder: (ViewBindingListItemProvider.ViewHolder<VB>, T, List<Any>?) -> Unit
): ViewBindingListItemProvider<T, VB> {
    return object : ViewBindingListItemProvider<T, VB>() {

        override fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB {
            return createViewBinding(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: ViewHolder<VB>, item: T, payloads: List<Any>?) {
            bindViewHolder(holder, item, payloads)
        }
    }
}
