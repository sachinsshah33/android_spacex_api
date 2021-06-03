package extension.domain.spacex.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
@Entity(tableName = "launches")
data class Launch(
    @PrimaryKey
    val id: String,
    val name: String?,
    val details: String?,
    val date_utc: Date?,
    val date_unix: Long?,
    val links: Links?,
    val success: Boolean?
): Parcelable

@Parcelize
data class Links(
    val patch: Patch?
): Parcelable

@Parcelize
data class Patch(
    val small: String?
): Parcelable