package extension.domain.spacex.utils.helpers

import extension.domain.spacex.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    fun toDateStamp(date: Date) = SimpleDateFormat(Constants.SHORT_DATE_FORMAT).format(date.time)
}