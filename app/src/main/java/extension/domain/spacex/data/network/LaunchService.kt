package extension.domain.spacex.data.network

import extension.domain.spacex.data.models.Result
import extension.domain.spacex.data.network.response.LaunchBaseListResponse
import extension.domain.spacex.utils.Constants
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class LaunchService(private val api: Api) : BaseService() {

    suspend fun fetchLaunches(page: Int): Result<LaunchBaseListResponse> {
        return createCall { api.getLaunches(generateRequestBody(page)) }
    }


    fun generateRequestBody(page: Int, limit: Int = Constants.DEFAULT_LIMIT): RequestBody {
        val requestBody = JSONObject().apply {
            val query = JSONObject()

            val andArray = JSONArray()
            val and = JSONObject()

            val rocket = JSONObject()
            rocket.putOpt("\$eq", Constants.FALCON_9_ID)
            and.putOpt("rocket", rocket)
            /**
            I think this ID is correct for Falcon 9 rockets, if not, I tried searching name & description like:

            "query": {
            "$or": [
            {
            "name": {
            "$regex": ".*Falcon 9.*"
            },
            "details": {
            "$regex": ".*Falcon 9.*"
            }
            }
            ]
            }

            But this didn't seem to work? It only worked with 1 of either name or description in, but not both??
             */


            val upcoming = JSONObject()
            upcoming.putOpt("\$eq", false)
            and.putOpt("upcoming", upcoming)

            andArray.put(and)

            query.putOpt("\$and", andArray)
            putOpt("query", query)


            val options = JSONObject().apply {
                val sort = JSONObject().apply {
                    putOpt("date_unix", Constants.DESC.toString())
                    /**
                     * Sorted by date_unix as it is Long, I assume its a tiny bit faster than sorting by String?
                     */
                }
                putOpt("sort", sort)
                putOpt("page", page)
                putOpt("limit", limit)
            }
            putOpt("options", options)
        }.toString()
        return requestBody.toRequestBody("application/json; charset=utf-8".toMediaType())
    }
}



