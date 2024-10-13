package net.humans.android.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Lambda wrapper of [RecyclerView.addOnScrollListener]
 * */
fun RecyclerView.addOnScrollListener(
    onScrolled: ((recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit) = { _, _, _ -> },
    onScrollStateChanged: ((recyclerView: RecyclerView, newState: Int) -> Unit) = { _, _ -> }
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            onScrollStateChanged(recyclerView, newState)
        }
    })
}

fun RecyclerView.getReliableAdapterPosition(view: View): Int {
    var position = getChildAdapterPosition(view)
    if (position == RecyclerView.NO_POSITION) {
        val holder = getChildViewHolder(view)
        if (holder != null) {
            position = holder.oldPosition
        }
    }

    return position
}

/**
 * Adds [RecyclerView.addOnScrollListener] to detect scrolled to top state changes
 * */
fun RecyclerView.addOnScrollToTopChangeListener(onChanged: (isScrolledToTop: Boolean) -> Unit) {
    var previousIsScrolledToTop: Boolean? = null
    val scrollDirectionTop = -1

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val currentIsScrolledToTop = !recyclerView.canScrollVertically(scrollDirectionTop)
            if (previousIsScrolledToTop != currentIsScrolledToTop) {
                onChanged(currentIsScrolledToTop)
                previousIsScrolledToTop = currentIsScrolledToTop
            }
        }
    })
}
