package gb.com.navigation

import com.github.terrakok.cicerone.Screen
import gb.com.mvp.model.entity.GithubUser

interface IScreens {
    fun usersScreen(): Screen
    fun userScreen(user: GithubUser): Screen
}