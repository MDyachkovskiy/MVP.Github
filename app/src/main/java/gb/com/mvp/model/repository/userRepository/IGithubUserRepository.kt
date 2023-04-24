package gb.com.mvp.model.repository.userRepository

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepositories {
    fun getUserRepositories(user: GithubUser): Single<List<GithubUserRepository>>
}