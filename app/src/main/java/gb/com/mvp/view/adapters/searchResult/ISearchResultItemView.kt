package gb.com.mvp.view.adapters.searchResult

import gb.com.mvp.view.adapters.common.IItemView

interface ISearchResultItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}