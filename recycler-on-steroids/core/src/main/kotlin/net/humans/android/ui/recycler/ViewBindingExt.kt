package net.humans.android.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Inflate ViewBinding using [layoutInflater]
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateViewBinding(
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToRoot: Boolean = false
): VB {
    return inflateViewBinding(VB::class.java, layoutInflater, parent, attachToRoot)
}

/**
 * Inflate ViewBinding using [layoutInflater]
 */
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> inflateViewBinding(
    vbClass: Class<VB>,
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToRoot: Boolean = false
): VB {
    val inflateViewMethod = vbClass.getMethod(
        "inflate",
        LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    )
    return inflateViewMethod(null, layoutInflater, parent, attachToRoot) as VB
}
