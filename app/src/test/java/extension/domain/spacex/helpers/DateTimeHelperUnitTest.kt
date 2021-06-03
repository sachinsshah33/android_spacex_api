package extension.domain.spacex.helpers

import extension.domain.spacex.utils.helpers.DateTimeHelper
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


class DateTimeHelperUnitTest {

    lateinit var dateTimeHelper: DateTimeHelper

    @Before
    fun before() {
    }

    @Test
    fun date_to_dateStamp_isCorrect() {
        val date = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 30)
            set(Calendar.HOUR, 10)
            set(Calendar.AM_PM, Calendar.PM)
            set(Calendar.MONTH, Calendar.MARCH)
            set(Calendar.DAY_OF_MONTH, 24)
            set(Calendar.YEAR, 2006)
        }.time
        val dateStamp = dateTimeHelper.toDateStamp(date)
        assertEquals("24-03-2006", dateStamp)
    }

    @After
    fun after() {

    }
}