package gb.com.di.user

import dagger.Subcomponent
import gb.com.di.repository.RepositorySubcomponent
import gb.com.di.user.module.UserModule
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.presenter.users.UsersListPresenter

@UserScope
@Subcomponent(
    modules = [
        UserModule::class
    ]
)
interface UserSubcomponent {
    fun repositorySubcomponent(): RepositorySubcomponent

    fun inject (usersPresenter: UsersListPresenter)
    fun inject (glideImageLoader: GlideImageLoader)
}