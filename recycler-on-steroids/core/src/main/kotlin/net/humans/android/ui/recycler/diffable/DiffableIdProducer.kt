package net.humans.android.ui.recycler.diffable

import java.util.UUID

interface DiffableIdProducer {
    fun newDiffableId(): Any
}

class SimpleDiffableIdProducer : DiffableIdProducer {
    override fun newDiffableId(): Any = UUID.randomUUID()
}
