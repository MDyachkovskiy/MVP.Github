package gb.com.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import gb.com.ui.fragments.UserFragment
import gb.com.ui.fragments.UsersFragment

class Screens {
    class UsersScreen(): FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return UsersFragment.newInstance()
        }
    }

    class UserScreen(private val login:String): FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return UserFragment.newInstance(login)
        }
    }
}