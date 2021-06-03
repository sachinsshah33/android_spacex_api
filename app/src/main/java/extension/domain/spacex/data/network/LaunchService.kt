package extension.domain.spacex.data.network

import extension.domain.spacex.data.network.response.LaunchBaseListResponse
import extension.domain.spacex.utils.Constants
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.nio.charset.Charset

class LaunchService(private val api: Api) {

    suspend fun fetchLaunches(page: Int): Response<LaunchBaseListResponse> {
        return api.getLaunches(generateRequestBody(page))
    }


    private fun generateRequestBody(page: Int, limit: Int = Constants.DEFAULT_LIMIT): RequestBody {
        val requestBody = JSONObject().apply {
            val query = JSONObject()

            val andArray = JSONArray()
            val and = JSONObject()

            val rocket = JSONObject()
            rocket.putOpt("\$eq", Constants.FALCON_9_ID)
            and.putOpt("rocket", rocket)

            val upcoming = JSONObject()
            upcoming.putOpt("\$eq", false)
            and.putOpt("upcoming", upcoming)

            andArray.put(and)

            query.putOpt("\$and", andArray)
            putOpt("query", query)

            val options = JSONObject().apply {
                val sort = JSONObject().apply {
                    putOpt("date_unix", Constants.DESC.toString())
                }
                putOpt("sort", sort)
                putOpt("page", page)
                putOpt("limit", limit)
            }
            putOpt("options", options)
        }.toString()

        return requestBody.toRequestBody(Constants.APPLICATION_JSON_UTF8.toMediaType())
    }
}



