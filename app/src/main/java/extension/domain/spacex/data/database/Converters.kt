package extension.domain.spacex.data.database

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import extension.domain.spacex.data.models.Links
import extension.domain.spacex.data.models.Patch
import extension.domain.spacex.utils.Constants
import java.util.*


class Converters {
    @TypeConverter
    fun fromDate(state: Date) = Constants.gson.toJson(state, TypeToken.getParameterized(Date::class.java, Date::class.java).type)
    @TypeConverter
    fun toDate(value: String) = Constants.gson.fromJson<Date>(value, TypeToken.getParameterized(Date::class.java, Date::class.java).type)


    @TypeConverter
    fun fromLinks(state: Links) = Constants.gson.toJson(state, TypeToken.getParameterized(Links::class.java, Links::class.java).type)
    @TypeConverter
    fun toLinks(value: String) = Constants.gson.fromJson<Links>(value, TypeToken.getParameterized(Links::class.java, Links::class.java).type)



    @TypeConverter
    fun fromPatch(state: Patch) = Constants.gson.toJson(state, TypeToken.getParameterized(Patch::class.java, Patch::class.java).type)
    @TypeConverter
    fun toPatch(value: String) = Constants.gson.fromJson<Patch>(value, TypeToken.getParameterized(Patch::class.java, Patch::class.java).type)

}