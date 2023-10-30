package net.humans.android.ui.wrappers.compose

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import net.humans.android.ui.wrappers.ColorWrapper
import net.humans.android.ui.wrappers.DrawableWrapper
import net.humans.android.ui.wrappers.TextWrapper
import net.humans.android.ui.wrappers.UriWrapper

@Composable
fun TextWrapper.get(): String = this.get(LocalContext.current).toString()

@Composable
fun ColorWrapper.get(): Color = Color(this.get(LocalContext.current))

@Composable
fun DrawableWrapper.get(): Drawable = this.get(LocalContext.current)

@Composable
fun UriWrapper.get(): Uri = this.get(LocalContext.current)
