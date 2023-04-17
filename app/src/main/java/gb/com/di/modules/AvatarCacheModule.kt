package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.mvp.model.entity.room.cache.IRoomGithubAvatarCache
import gb.com.mvp.model.entity.room.cache.RoomGithubAvatarCache
import gb.com.mvp.model.repository.avatar.AvatarFile
import gb.com.mvp.model.repository.avatar.IAvatarFile

@Module
class AvatarCacheModule {

    @Provides
    fun avatarCache(repositoryFile: IAvatarFile): IRoomGithubAvatarCache =
        RoomGithubAvatarCache(repositoryFile)

    @Provides
    fun avatarFile(): IAvatarFile = AvatarFile()
}

