package net.humans.android.ui.wrappers.sample.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.humans.android.ui.wrappers.compose.get
import net.humans.android.ui.wrappers.sample.R
import net.humans.android.ui.wrappers.sample.compose.ui.theme.HumansandroiduiTheme
import net.humans.android.ui.wrappers.toTextWrapper

@Composable
internal fun SampleScreen(
    viewState: ComposeSampleViewState,
    onBackClick: () -> Unit = {},
    onMinusClick: () -> Unit = {},
    onPlusClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        IconButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    onClick = { },
                ),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = viewState.value1.get())
            Text(text = viewState.value2.get())
            Text(text = viewState.value3.get())
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    enabled = viewState.minusAvailable,
                    onClick = onMinusClick,
                ) { Text(text = "-") }
                Text(text = viewState.counter.get())
                Button(
                    enabled = viewState.plusAvailable,
                    onClick = onPlusClick,
                ) { Text(text = "+") }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSampleScreen() {
    HumansandroiduiTheme {
        SampleScreen(
            viewState = ComposeSampleViewState(
                value1 = "value1".toTextWrapper(),
                value2 = "value2".toTextWrapper(),
                value3 = "value3".toTextWrapper(),
                counter = "counter".toTextWrapper(),
                plusAvailable = true,
                minusAvailable = false,
            )
        )
    }
}