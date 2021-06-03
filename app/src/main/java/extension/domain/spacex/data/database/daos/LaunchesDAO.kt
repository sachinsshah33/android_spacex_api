package extension.domain.spacex.data.database.daos

import androidx.paging.PagingSource
import androidx.room.*
import extension.domain.spacex.data.models.Launch

@Dao
interface LaunchesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(models: List<Launch>): List<Long>

    @Query("SELECT * FROM launches ORDER BY date_utc DESC")
    fun getLaunchesFromCacheStream(): PagingSource<Int, Launch>
}
