package extension.domain.spacex.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import extension.domain.spacex.data.models.Launch
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaunchBaseListResponse(
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalDocs")
    val totalDocs: Int,
    @SerializedName("docs")
    val docs: List<Launch>,
    @SerializedName("page")
    val page: Int
) : Parcelable
