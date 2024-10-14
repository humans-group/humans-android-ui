package net.humans.android.ui.recycler.diffable

interface SimpleDiffable : Diffable {
    override val id: Any
        get() = javaClass
}
