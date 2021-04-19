package extension.domain.spacex.ui.main

import androidx.lifecycle.ViewModel
import androidx.paging.*
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.data.repositories.launch.LaunchRepository
import extension.domain.spacex.data.repositories.launch.paged.LaunchPagingSource
import extension.domain.spacex.utils.Constants

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainViewModel(private val launchRepository: LaunchRepository) : ViewModel() {

    val launches: Flow<PagingData<LaunchUIModel>> = getLaunchListStream()
        .map { pagingData -> pagingData.map { LaunchUIModel.LaunchItem(it) } }
        .map {
            it.insertSeparators { before, after ->
                if (after == null) {
                    //End of the list
                    return@insertSeparators LaunchUIModel.EndFooterItem
                } else {
                    null
                }
            }
        }.distinctUntilChanged()


    private fun getLaunchListStream(): Flow<PagingData<Launch>> {
        return Pager(PagingConfig(Constants.DEFAULT_LIMIT)) {
            LaunchPagingSource(launchRepository)
        }.flow
    }
}

sealed class LaunchUIModel {
    data class LaunchItem(val launch: Launch) : LaunchUIModel()
    object EndFooterItem : LaunchUIModel()
}