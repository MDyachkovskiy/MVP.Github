package gb.com.navigation

import com.github.terrakok.cicerone.Screen
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepositories

interface IScreens {
    fun usersScreen(): Screen
    fun userScreen(user: GithubUser): Screen
    fun repositoryDetailsScreen(user: GithubUser, userRepo: GithubUserRepositories): Screen
}