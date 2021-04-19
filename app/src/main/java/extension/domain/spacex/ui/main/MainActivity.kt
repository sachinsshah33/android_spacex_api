package extension.domain.spacex.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.databinding.ActivityMainBinding
import extension.domain.spacex.ui.adapters.LaunchAdapter
import extension.domain.spacex.ui.adapters.LaunchLoadStateAdapter
import extension.domain.spacex.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var launchAdapter: LaunchAdapter
    private lateinit var mainBinding: ActivityMainBinding

    val onClickItem: (item: Launch?) -> Unit = {
        if (it == null) {
            //Scroll launchRecycler to top
            val size = launchAdapter.snapshot().size
            if (size > Constants.DEFAULT_LIMIT * 3) {
                //If its large then quickly scroll
                mainBinding.launchRecycler.scrollToPosition(0)
            } else {
                //Otherwise smooth scroll looks nicer
                mainBinding.launchRecycler.smoothScrollToPosition(0)
            }
            //Probably should move this to an interface if more functionality is to be added
        } else {
            Toast.makeText(this, it.details, Toast.LENGTH_LONG).show()
            //todo: send parcelable object into new activity when create details screen
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        launchAdapter = LaunchAdapter(onClickItem)

        mainBinding.launchRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = launchAdapter.withLoadStateFooter(
                footer = LaunchLoadStateAdapter { launchAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.launches.collectLatest {
                launchAdapter.submitData(it)
            }
        }

        mainBinding.retry.setOnClickListener {
            launchAdapter.retry()
        }

        launchAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                mainBinding.retry.isVisible = false
                mainBinding.spinner.isVisible = true
            } else {
                mainBinding.spinner.isVisible = false

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        mainBinding.retry.isVisible = true
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
}