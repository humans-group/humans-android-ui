package net.humans.android.ui.recycler.diffable

interface Diffable {
    val id: Any

    fun isContentTheSame(old: Diffable): Boolean {
        return equals(old)
    }

    fun getChangePayload(old: Diffable): Any? {
        return null
    }
}

inline fun <reified T> simpleNameOf(): String {
    return T::class.java.simpleName
}

fun join(separator: String, vararg args: Any): String {
    return args.joinToString(separator = separator) { it.toString() }
}
