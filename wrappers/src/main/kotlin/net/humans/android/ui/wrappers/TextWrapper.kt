package net.humans.android.ui.wrappers

import android.content.Context
import android.os.Parcelable
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import net.humans.android.ui.wrappers.internal.getPlural

sealed class TextWrapper : Parcelable {
    abstract fun get(context: Context): CharSequence

    @Parcelize
    data class Id(@StringRes val id: Int, val args: @RawValue Array<Any>) : TextWrapper() {

        @IgnoredOnParcel
        private var isProcessed: Boolean = args.none { it is TextWrapper }

        @IgnoredOnParcel
        private val _args: Array<Any> = args.copyOf()

        constructor(@StringRes id: Int) : this(id, emptyArray())

        @SuppressWarnings("SpreadOperator", "NestedBlockDepth")
        override fun get(context: Context): CharSequence {
            return if (_args.isNotEmpty()) {
                if (!isProcessed) {
                    for ((index, arg) in _args.withIndex()) {
                        if (arg is TextWrapper) {
                            _args[index] = arg.get(context)
                        }
                    }
                    isProcessed = true
                }
                context.getString(id, *_args)
            } else {
                context.getString(id)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Id

            if (id != other.id) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Parcelize
    data class Plural(
        @PluralsRes val id: Int,
        val quantity: Int,
        val args: @RawValue Array<Any>
    ) : TextWrapper() {

        constructor(@PluralsRes id: Int, quantity: Int) : this(id, quantity, emptyArray())

        @IgnoredOnParcel
        private var isProcessed: Boolean = args.none { it is TextWrapper }

        @IgnoredOnParcel
        private val _args: Array<Any> = args.copyOf()

        @SuppressWarnings("SpreadOperator", "NestedBlockDepth")
        override fun get(context: Context): CharSequence {
            return if (_args.isNotEmpty()) {
                if (!isProcessed) {
                    for ((index, arg) in _args.withIndex()) {
                        if (arg is TextWrapper) {
                            _args[index] = arg.get(context)
                        }
                    }
                    isProcessed = true
                }
                context.resources.getQuantityString(id, quantity, *_args)
            } else {
                return context.getPlural(id, quantity)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Plural

            if (id != other.id) return false
            if (quantity != other.quantity) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + quantity
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Parcelize
    data class Raw(val text: CharSequence) : TextWrapper() {
        override fun get(context: Context): CharSequence {
            return text
        }
    }

    companion object {
        val EMPTY = Raw("")
    }
}

fun CharSequence.toTextWrapper(): TextWrapper = TextWrapper.Raw(this)
