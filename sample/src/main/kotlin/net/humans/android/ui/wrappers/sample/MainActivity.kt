package net.humans.android.ui.wrappers.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.humans.android.ui.wrappers.sample.compose.ComposeSampleActivity
import net.humans.android.ui.wrappers.sample.databinding.ActivityMainBinding
import net.humans.android.ui.wrappers.setText

internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.minus.setOnClickListener { viewModel.minus() }
        binding.plus.setOnClickListener { viewModel.plus() }
        binding.compose.setOnClickListener {
            startActivity(Intent(this,ComposeSampleActivity::class.java))
        }

        viewModel.viewState
            .onEach(::updateView)
            .launchIn(lifecycleScope)

    }

    private fun updateView(viewState: MainViewState) {
        with(binding) {
            text1.setText(viewState.value1)
            text2.setText(viewState.value2)
            text3.setText(viewState.value3)
            count.setText(viewState.counter)
            minus.isEnabled = viewState.minusAvailable
            plus.isEnabled = viewState.plusAvailable
        }
    }
}