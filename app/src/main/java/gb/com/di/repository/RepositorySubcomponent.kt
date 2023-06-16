package gb.com.di.repository

import dagger.Subcomponent
import gb.com.di.repository.module.RepositoryModule
import gb.com.mvp.presenter.repositoryDetails.RepositoryDetailsPresenter
import gb.com.mvp.presenter.userRepositories.UserRepositoryListPresenter

@RepositoryScope
@Subcomponent(
    modules = [
        RepositoryModule::class
    ]
)
interface RepositorySubcomponent {
    fun inject(repositoryDetailsPresenter: RepositoryDetailsPresenter)
    fun inject(repositoryListPresenter: UserRepositoryListPresenter)
}