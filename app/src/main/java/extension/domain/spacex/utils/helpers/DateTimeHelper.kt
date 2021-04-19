package extension.domain.spacex.utils.helpers

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    fun toDateStamp(date: Date) = SimpleDateFormat("dd-MM-yyyy").format(date.time)
}