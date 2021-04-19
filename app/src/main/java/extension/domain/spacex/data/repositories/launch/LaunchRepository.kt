package extension.domain.spacex.data.repositories.launch

import extension.domain.spacex.data.models.Result
import extension.domain.spacex.data.network.LaunchService
import extension.domain.spacex.data.network.response.LaunchBaseListResponse

class LaunchRepository(private val service: LaunchService) {

    suspend fun getLaunches(page: Int): LaunchBaseListResponse {
        return when (val result = service.fetchLaunches(page)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.error
        }
    }
}