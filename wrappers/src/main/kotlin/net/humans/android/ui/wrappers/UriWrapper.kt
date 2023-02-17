package net.humans.android.ui.wrappers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Parcelable
import androidx.annotation.AnyRes
import kotlinx.parcelize.Parcelize

sealed class UriWrapper : Parcelable {
    abstract fun get(context: Context): Uri

    @Parcelize
    data class Id(@AnyRes val id: Int) : UriWrapper() {
        override fun get(context: Context): Uri {
            return Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(context.resources.getResourcePackageName(id))
                .appendPath(context.resources.getResourceTypeName(id))
                .appendPath(context.resources.getResourceEntryName(id))
                .build()
        }
    }

    @Parcelize
    data class Raw(val uri: Uri) : UriWrapper() {
        constructor(value: String) : this(uri = Uri.parse(value))

        override fun get(context: Context): Uri {
            return uri
        }
    }

    companion object {
        val EMPTY = Raw(uri = Uri.EMPTY)
    }
}

fun Uri.toUriWrapper(): UriWrapper = this.let(UriWrapper::Raw)
