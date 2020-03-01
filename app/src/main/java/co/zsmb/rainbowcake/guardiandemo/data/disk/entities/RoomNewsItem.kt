package co.zsmb.rainbowcake.guardiandemo.data.disk.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsitems")
class RoomNewsItem(
    @PrimaryKey
    val id: String,
    val headline: String,
    val trailText: String,
    val thumbnailUrl: String?,
    val content: String
)
