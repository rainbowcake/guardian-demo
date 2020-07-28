package co.zsmb.rainbowcake.guardiandemo.data.disk

import co.zsmb.rainbowcake.guardiandemo.data.model.News
import co.zsmb.rainbowcake.guardiandemo.local.entities.RoomNewsItem

fun News.toRoomNewsItem(): RoomNewsItem {
    return RoomNewsItem(
        id = id,
        headline = headline,
        trailText = trail,
        thumbnailUrl = thumbnailUrl,
        content = content
    )
}

fun RoomNewsItem.toNews(): News {
    return News(
        id = id,
        headline = headline,
        trail = trailText,
        thumbnailUrl = thumbnailUrl,
        content = content
    )
}
