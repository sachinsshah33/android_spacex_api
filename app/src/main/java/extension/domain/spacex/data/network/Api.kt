package extension.domain.spacex.data.network

import extension.domain.spacex.data.network.response.LaunchBaseListResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("launches/query")
    suspend fun getLaunches(
        @Body body: RequestBody
    ): Response<LaunchBaseListResponse>
}