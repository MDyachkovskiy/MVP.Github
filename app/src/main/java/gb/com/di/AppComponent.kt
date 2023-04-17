package gb.com.di

import dagger.Component
import gb.com.di.modules.*
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.presenter.main.MainPresenter
import gb.com.mvp.presenter.repositoryDetails.RepositoryDetailsPresenter
import gb.com.mvp.presenter.userRepositories.UserRepositoryListPresenter
import gb.com.mvp.presenter.users.UsersListPresenter
import gb.com.mvp.view.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        CiceroneModule::class,
        RepositoryModule::class,
        AvatarCacheModule::class
    ]
)


interface AppComponent {
    fun inject (mainActivity: MainActivity)
    fun inject (mainPresenter: MainPresenter)
    fun inject (usersPresenter: UsersListPresenter)
    fun inject (repositoryDetailsPresenter: RepositoryDetailsPresenter)
    fun inject (userRepositoryListPresenter: UserRepositoryListPresenter)
    fun inject (glideImageLoader: GlideImageLoader)
}