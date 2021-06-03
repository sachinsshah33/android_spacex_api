package extension.domain.spacex.data.repositories.launch

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Query
import extension.domain.spacex.data.database.daos.LaunchesDAO
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.data.network.LaunchService
import extension.domain.spacex.data.network.response.LaunchBaseListResponse
import extension.domain.spacex.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LaunchRepository @Inject constructor(val service: LaunchService, val launchesDAO: LaunchesDAO) {

    suspend fun getLaunches(page: Int): LaunchBaseListResponse? {
        val result = service.fetchLaunches(page)
        if(result.isSuccessful && result.body() != null){
            result.body()?.docs?.let {
                cache(it)
            }
            return result.body()!!
        }
        return null
        //todo maybe map this into a more meaningful error, using sealed class
    }

    private fun cache(models: List<Launch>){
        launchesDAO.insertLaunches(models)
    }



    fun getLaunchesFromCacheStream(): Flow<PagingData<Launch>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_LIMIT,
                enablePlaceholders = true
            ), pagingSourceFactory = {
                launchesDAO.getLaunchesFromCacheStream()
            }).flow
    }
}