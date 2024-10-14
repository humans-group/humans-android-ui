package net.humans.android.ui.recycler.steroid

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.MainThread
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView]'s Adapter to handle multiply type of view without creating new adapter
 */
@Suppress("LongParameterList", "TooManyFunctions")
class SteroidAdapter<T : Any> internal constructor(
    private val layoutInflater: LayoutInflater,
    itemProviders: Map<Any, ListItemProvider<T, *>>,
    asyncDifferConfig: AsyncDifferConfig<T>,
    private val itemId: ((item: T, position: Int) -> Long)?,
    private val keyForItemView: (item: T, position: Int) -> Any,
    private val onItemClickListener: OnItemClickListener<T>?,
    private val onItemLongClickListener: OnItemLongClickListener<T>?,
    private val interceptors: List<Interceptor<T>>,
    hasStableIds: Boolean
) : ListAdapter<T, SteroidAdapter.ViewHolder>(asyncDifferConfig) {

    /**
     * Item view type is index of the list
     * Provider for the view types is value in position
     */
    private val itemViewTypeToProviders: List<ListItemProvider<T, *>>

    /**
     * Item view type is index of the list
     * Key of item is value in position
     */
    private val itemViewTypeToKeys: List<Any>
    private val handler = Handler(Looper.getMainLooper())

    init {
        val itemViewTypeToProviders = ArrayList<ListItemProvider<T, *>>(itemProviders.size)
        val itemViewTypeToKeys = ArrayList<Any>(itemProviders.size)
        itemProviders.forEach { (key, provider) ->
            itemViewTypeToKeys += key
            itemViewTypeToProviders += provider
        }
        this.itemViewTypeToProviders = itemViewTypeToProviders
        this.itemViewTypeToKeys = itemViewTypeToKeys
        this.setHasStableIds(hasStableIds)
        require(!(hasStableIds() && itemId == null)) {
            "hasStableIds was set to true, but no itemId was defined."
        }
    }

    fun submitList(list: List<T>, commitCallback: () -> Unit = {}) {
        super.submitList(
            list.takeIf { it.isNotEmpty() },
            commitCallback
        )
    }

    private fun getItemViewForKey(key: Any): Int {
        return if (itemViewTypeToKeys.size == 1) {
            0
        } else {
            val index = itemViewTypeToKeys.indexOf(key)
            check(index >= 0) { "Key '$key' hasn't been found" }
            index
        }
    }

    private fun getKeyByItemViewType(viewType: Int): Any {
        require(viewType in itemViewTypeToKeys.indices) { "Impossible view type" }
        return itemViewTypeToKeys[viewType]
    }

    private fun getViewHolderItemProvider(itemViewType: Int): ListItemProvider<T, *> {
        return itemViewTypeToProviders[itemViewType]
    }

    override fun getItemId(position: Int): Long {
        return itemId?.invoke(currentList[position], position) ?: super.getItemId(position)
    }

    override fun getItemViewType(@IntRange(from = 0) position: Int): Int {
        val item = getItem(position) ?: throw IndexOutOfBoundsException("No item for $position")
        return getItemViewForKey(keyForItemView(item, position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val provider = getViewHolderItemProvider(viewType)
        val viewHolder = provider.onCreateViewHolder(layoutInflater, parent)
        if (onItemClickListener != null) initOnItemClickListener(viewHolder, onItemClickListener)
        if (onItemLongClickListener != null) initOnItemLongClickListener(
            viewHolder,
            onItemLongClickListener
        )

        val key = getKeyByItemViewType(viewType)
        notifyInterceptors { it.onViewHolderCreated(provider, viewHolder, key) }
        return viewHolder
    }

    private fun initOnItemClickListener(viewHolder: ViewHolder, l: OnItemClickListener<T>) {
        viewHolder.itemView.setOnClickListener {
            @Suppress("UNCHECKED_CAST")
            l.onItemClicked(viewHolder, viewHolder.item as T)
        }
    }

    private fun initOnItemLongClickListener(viewHolder: ViewHolder, l: OnItemLongClickListener<T>) {
        viewHolder.itemView.setOnLongClickListener {
            @Suppress("UNCHECKED_CAST")
            l.onLongClick(viewHolder, viewHolder.item as T)
        }
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (holder.itemViewType != RecyclerView.INVALID_TYPE) {
            val provider = getViewHolderItemProvider(holder.itemViewType)
            provider.bindViewHolder(
                holder,
                currentList[position],
                payloads.takeUnless { it.isEmpty() }
            )
            notifyInterceptors { it.onViewHolderBounded(provider, holder) }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder.itemViewType != RecyclerView.INVALID_TYPE) {
            val provider = getViewHolderItemProvider(holder.itemViewType)
            provider.recycleView(holder)
            notifyInterceptors { it.onViewRecycled(provider, holder) }
        }
    }

    private inline fun notifyInterceptors(crossinline action: (Interceptor<T>) -> Unit) {
        if (interceptors.isNotEmpty()) {
            handler.post {
                interceptors.forEach(action)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        error("Override onBindViewHolder(ViewHolder, Int, List)")
    }

    @Suppress("UndocumentedPublicClass")
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var _item: Any? = null

        private val tags = mutableMapOf<Any, Any?>()

        var item: Any
            get() = checkNotNull(_item) { "The ViewHolder hasn't item" }
            internal set(value) {
                _item = value
            }

        @Suppress("UNCHECKED_CAST")
        fun <V> getTag(key: Any): V? {
            return tags[key] as V?
        }

        @Suppress("UNCHECKED_CAST")
        fun <V> putTag(key: Any, value: V): V? {
            return tags.put(key, value) as V?
        }

        fun hasTag(key: Any): Boolean {
            return key in tags
        }

        @Suppress("UNCHECKED_CAST")
        fun <V> removeTag(key: Any): V? {
            return tags.remove(key) as V?
        }
    }

    /**
     * Interface for handle click on item of the list
     */
    interface OnItemClickListener<T : Any> {

        /**
         * Called when a view has been clicked.
         *
         * @param holder The [ViewHolder] that was clicked.
         * @param item The item associated with the [ViewHolder].
         */
        @MainThread
        fun onItemClicked(holder: ViewHolder, item: T)
    }

    /**
     * Interface for handle click on item of the list
     */
    interface OnItemLongClickListener<T : Any> {

        /**
         * Called when a view has been clicked and held.
         *
         * @param holder The [ViewHolder] that was clicked and held.
         * @param item The item associated with the [ViewHolder].
         *
         * @return true if the callback consumed the long click, false otherwise.
         */
        @MainThread
        fun onLongClick(holder: ViewHolder, item: T): Boolean
    }

    /**
     * Interceptor to catch different stages of [RecyclerView.ViewHolder] lifecycle.
     */
    interface Interceptor<T : Any> {

        /**
         * Called when a [ViewHolder] was created. Called after [RecyclerView.Adapter.onCreateViewHolder]
         */
        @MainThread
        fun onViewHolderCreated(
            provider: ListItemProvider<out T, *>,
            viewHolder: ViewHolder,
            key: Any
        ) {
        }

        /**
         * Called when a [ViewHolder] was bounded. Called after [RecyclerView.Adapter.onBindViewHolder]
         */
        @MainThread
        fun onViewHolderBounded(provider: ListItemProvider<out T, *>, viewHolder: ViewHolder) {
        }

        /**
         * Called when a [ViewHolder] was recycler. Called after [RecyclerView.Adapter.onViewRecycled]
         */
        @MainThread
        fun onViewRecycled(provider: ListItemProvider<out T, *>, viewHolder: ViewHolder) {
        }
    }
}

/**
 * DSL Builder for [SteroidAdapter]
 */
@AdapterDslMarker
class Adapter<T : Any> @PublishedApi internal constructor(context: Context) {

    private val layoutInflater by lazy(LazyThreadSafetyMode.NONE) { LayoutInflater.from(context) }
    private val itemProviders = mutableMapOf<Any, ListItemProvider<T, *>>()
    var asyncDifferConfig: AsyncDifferConfig<T> = EqualityItemCallback<T>().asAsyncDifferConfig()
    private var itemViewKey: (item: T, position: Int) -> Any = { item, _ -> item::class.java }
    private var itemId: ((item: T, position: Int) -> Long)? = null
    var hasStableIds: Boolean = false
    val interceptors = mutableListOf<SteroidAdapter.Interceptor<T>>()
    private var onItemClickListener: SteroidAdapter.OnItemClickListener<T>? = null
    private var onItemLongClickListener: SteroidAdapter.OnItemLongClickListener<T>? = null

    var itemCallback: DiffUtil.ItemCallback<T>
        get() = asyncDifferConfig.diffCallback
        set(value) {
            asyncDifferConfig = value.asAsyncDifferConfig()
        }

    @Suppress("UNCHECKED_CAST")
    fun addItemProvider(key: Any, itemProvider: ListItemProvider<out T, *>) {
        require(key !in itemProviders) { "$key has been already registered" }
        itemProviders[key] = itemProvider as ListItemProvider<T, *>
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : T> addItemProvider(itemProvider: ListItemProvider<E, *>) {
        addItemProvider(E::class.java, itemProvider)
    }

    /**
     * Provides key of the item in position of the adapter.
     */
    fun itemViewKey(body: (item: T, position: Int) -> Any) {
        this.itemViewKey = body
    }

    /**
     * Provides id of the item in position of the adapter.
     * Set [RecyclerView.Adapter.setHasStableIds] to true by default.
     */
    fun itemId(body: (item: T, position: Int) -> Long) {
        this.itemId = body
    }

    fun onItemClickListener(l: SteroidAdapter.OnItemClickListener<T>) {
        onItemClickListener = l
    }

    fun onItemLongClickListener(l: SteroidAdapter.OnItemLongClickListener<T>) {
        onItemLongClickListener = l
    }

    @PublishedApi
    internal fun build(): SteroidAdapter<T> {
        return SteroidAdapter(
            layoutInflater = layoutInflater,
            itemProviders = itemProviders.toMap(),
            asyncDifferConfig = asyncDifferConfig,
            keyForItemView = itemViewKey,
            itemId = itemId,
            onItemClickListener = onItemClickListener,
            onItemLongClickListener = onItemLongClickListener,
            interceptors = interceptors.toList(),
            hasStableIds = hasStableIds
        )
    }
}

inline fun <T : Any> Adapter<T>.onItemClickListener(
    crossinline action: (holder: SteroidAdapter.ViewHolder, item: T) -> Unit
) {
    onItemClickListener(object : SteroidAdapter.OnItemClickListener<T> {

        override fun onItemClicked(holder: SteroidAdapter.ViewHolder, item: T) {
            action(holder, item)
        }
    })
}

inline fun <T : Any> Adapter<T>.onItemLongClickListener(
    crossinline action: (holder: SteroidAdapter.ViewHolder, item: T) -> Boolean
) {
    onItemLongClickListener(object : SteroidAdapter.OnItemLongClickListener<T> {

        override fun onLongClick(holder: SteroidAdapter.ViewHolder, item: T): Boolean {
            return action(holder, item)
        }
    })
}

/**
 * Build new [SteroidAdapter]
 */
inline fun <T : Any> listAdapter(context: Context, body: Adapter<T>.() -> Unit): SteroidAdapter<T> {
    return Adapter<T>(context).apply(body).build()
}
