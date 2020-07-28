package co.zsmb.rainbowcake.guardiandemo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.zsmb.rainbowcake.guardiandemo.local.entities.RoomNewsItem

@Database(
    entities = [RoomNewsItem::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
