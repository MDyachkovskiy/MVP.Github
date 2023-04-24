package gb.com.mvp.model.repository.userRepository

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.GithubUserRepositories
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUserRepository (
    private val api: IDataSource
    ): IGithubUserRepositories {
    override fun getUserRepositories(url: String): Single<List<GithubUserRepositories>> =
        api.getUserRepositories(url).subscribeOn(Schedulers.io())

}