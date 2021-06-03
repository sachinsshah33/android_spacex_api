package extension.domain.spacex.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.databinding.ActivityMainBinding
import extension.domain.spacex.ui.adapters.LaunchAdapter
import extension.domain.spacex.ui.adapters.LaunchClickListener
import extension.domain.spacex.ui.adapters.LaunchLoadStateAdapter
import extension.domain.spacex.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LaunchClickListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var launchAdapter: LaunchAdapter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setupView()
        fetchLaunchesFromCloud()
    }

    fun setupView(){
        launchAdapter = LaunchAdapter(this)
        binding.launchRecycler.apply {
            adapter = launchAdapter.withLoadStateFooter(
                footer = LaunchLoadStateAdapter { launchAdapter.retry() }
            )
        }


        binding.retry.setOnClickListener {
            launchAdapter.retry()
        }
        binding.cache.setOnClickListener {
            fetchLaunchesFromCache()
        }

        launchAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.retry.isVisible = false
                binding.cache.isVisible = false
                binding.spinner.isVisible = true
            } else {
                binding.spinner.isVisible = false

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.retry.isVisible = true
                        binding.cache.isVisible = true
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    var cloudCoroutine: Job? = null
    private fun fetchLaunchesFromCloud(){
        cacheCoroutine?.cancel()

        cloudCoroutine?.cancel()
        cloudCoroutine = CoroutineScope(Dispatchers.Main).launch {
            viewModel.launchesFromCloud.observe(this@MainActivity, {
                lifecycleScope.launch {
                    launchAdapter.submitData(it)
                }
            })
        }
    }

    var cacheCoroutine: Job? = null
    private fun fetchLaunchesFromCache(){
        cloudCoroutine?.cancel()

        cacheCoroutine?.cancel()
        cacheCoroutine = CoroutineScope(Dispatchers.Main).launch {
            viewModel.launchesFromCache.observe(this@MainActivity, {
                lifecycleScope.launch {
                    launchAdapter.submitData(it)
                }
            })
        }
    }

    override fun itemClicked(launch: Launch?) {
        if (launch == null) {
            //Scroll launchRecycler to top
            val size = launchAdapter.snapshot().size
            if (size > Constants.DEFAULT_LIMIT * 3) {
                //If its large then quickly scroll
                binding.launchRecycler.scrollToPosition(0)
            } else {
                //Otherwise smooth scroll looks nicer
                binding.launchRecycler.smoothScrollToPosition(0)
            }
        } else {
            Toast.makeText(this, launch.details, Toast.LENGTH_LONG).show()
            //todo: send parcelable object into new activity when create details screen
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cloudCoroutine?.cancel()
        cacheCoroutine?.cancel()
    }
}