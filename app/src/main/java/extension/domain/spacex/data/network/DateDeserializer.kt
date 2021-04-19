package extension.domain.spacex.data.network


import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import extension.domain.spacex.utils.Constants
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateDeserializer : JsonDeserializer<Date?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement?,
        typeOF: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        if (jsonElement?.asString.isNullOrEmpty()) return null
        try {
            val dateStr = jsonElement!!.asString
            return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).parse(dateStr)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return null
    }
}