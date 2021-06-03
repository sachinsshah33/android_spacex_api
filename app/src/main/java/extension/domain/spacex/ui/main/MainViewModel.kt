package extension.domain.spacex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.data.repositories.launch.LaunchRepository
import extension.domain.spacex.data.repositories.launch.paged.LaunchPagingSource
import extension.domain.spacex.utils.Constants

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val launchRepository: LaunchRepository
) : ViewModel() {

    val launchesFromCloud: LiveData<PagingData<LaunchUIModel>> = getLaunchesFromCloudStream()
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
        }.asLiveData(viewModelScope.coroutineContext)

    private fun getLaunchesFromCloudStream(): Flow<PagingData<Launch>> {
        return Pager(PagingConfig(Constants.DEFAULT_LIMIT)) {
            LaunchPagingSource(launchRepository)
        }.flow.cachedIn(viewModelScope).distinctUntilChanged()
    }



    val launchesFromCache: LiveData<PagingData<LaunchUIModel>> = getLaunchesFromCacheStream()
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
        }.asLiveData(viewModelScope.coroutineContext)

    private fun getLaunchesFromCacheStream(): Flow<PagingData<Launch>> {
        return launchRepository.getLaunchesFromCacheStream().cachedIn(viewModelScope).distinctUntilChanged()
    }
}

sealed class LaunchUIModel {
    data class LaunchItem(val launch: Launch) : LaunchUIModel()
    object EndFooterItem : LaunchUIModel()
}