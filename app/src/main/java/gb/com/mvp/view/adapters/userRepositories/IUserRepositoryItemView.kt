package gb.com.mvp.view.adapters.userRepositories

import gb.com.mvp.view.adapters.common.IItemView

interface IUserRepositoryItemView: IItemView {
    fun setId(id: String)
    fun setName(name: String)
}