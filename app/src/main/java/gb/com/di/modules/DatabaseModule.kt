package gb.com.di.modules

import androidx.room.Room
import dagger.Module
import dagger.Provides
import gb.com.App
import gb.com.mvp.model.room.Database
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(app: App): Database =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()


}