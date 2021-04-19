package extension.domain.spacex.data.repositories.launch.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import extension.domain.spacex.data.models.Launch
import extension.domain.spacex.data.repositories.launch.LaunchRepository

class LaunchPagingSource(
    private val launchRepository: LaunchRepository
) : PagingSource<Int, Launch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Launch> {

        return try {
            val nextPage = params.key ?: 1
            val launchListResponse = launchRepository.getLaunches(nextPage)

            LoadResult.Page(
                data = launchListResponse.docs!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < launchListResponse.totalPages!!)
                    launchListResponse.page?.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Launch>): Int? {
        return state.anchorPosition
    }
}
