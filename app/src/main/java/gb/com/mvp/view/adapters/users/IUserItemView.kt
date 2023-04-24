package gb.com.mvp.view.adapters.users

import gb.com.mvp.view.adapters.common.IItemView

interface IUserItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}