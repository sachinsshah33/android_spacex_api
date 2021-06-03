package extension.domain.spacex.data.repositories.launch.paged

import android.util.Size
import androidx.paging.PagingSource
import androidx.paging.PagingState
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.data.repositories.launch.LaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchPagingSource(private val launchRepository: LaunchRepository) : PagingSource<Int, Launch>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Launch> {
        return withContext(Dispatchers.IO) {
            try {
                val nextPage = params.key ?: 1
                val launchListResponse = launchRepository.getLaunches(nextPage)

                if(launchListResponse != null){
                    LoadResult.Page(
                        data = launchListResponse.docs,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (nextPage < launchListResponse.totalPages) launchListResponse.page.plus(1) else null
                    )
                }
                else{
                    LoadResult.Error(Throwable())
                    //todo maybe throw an error with more meaningful error message
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Launch>): Int? {
        return state.anchorPosition
    }
}
