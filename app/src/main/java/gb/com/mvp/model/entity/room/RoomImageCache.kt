package gb.com.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomImageCache(
    @PrimaryKey var url: String,
    var localPath: String
)