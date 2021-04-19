package extension.domain.spacex.data.network.response

import com.google.gson.annotations.SerializedName

open class BaseListResponse<Item> {
    @SerializedName("totalPages")
    val totalPages: Int? = null
    @SerializedName("totalDocs")
    val totalDocs: Int? = null
    @SerializedName("docs")
    val docs: List<Item>? = null
    @SerializedName("page")
    val page: Int? = null
}