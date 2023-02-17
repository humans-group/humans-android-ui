package net.humans.android.ui.wrappers

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

sealed class ImageWrapper : Parcelable {

    @Parcelize
    data class Id(@DrawableRes val id: Int) : ImageWrapper()

    @Parcelize
    data class URI(val uri: Uri) : ImageWrapper() {
        constructor(value: String) : this(uri = Uri.parse(value))
    }
}
