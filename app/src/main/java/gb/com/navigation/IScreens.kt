package gb.com.navigation

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun usersScreen(): Screen
    fun userScreen(login: String): Screen
}