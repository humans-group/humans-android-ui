package net.humans.android.ui.wrappers.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.humans.android.ui.wrappers.sample.compose.ui.theme.HumansandroiduiTheme

internal class ComposeSampleActivity : ComponentActivity() {

    private val viewModel: ComposeSampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HumansandroiduiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewState: ComposeSampleViewState by viewModel.viewState.collectAsState()
                    SampleScreen(
                        viewState = viewState,
                        onBackClick = onBackPressedDispatcher::onBackPressed,
                        onMinusClick = viewModel::minus,
                        onPlusClick = viewModel::plus,
                    )
                }
            }
        }
    }
}
