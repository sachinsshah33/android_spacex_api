package extension.domain.spacex.utils.extensions

import extension.domain.spacex.utils.helpers.DateTimeHelper
import java.util.*

fun Date.toDateStamp() = DateTimeHelper.toDateStamp(this)
