package gb.com.mvp.model.repository.userRepository

import gb.com.mvp.model.entity.GithubUserRepositories
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepositories {
    fun getUserRepositories(url: String): Single<List<GithubUserRepositories>>
}