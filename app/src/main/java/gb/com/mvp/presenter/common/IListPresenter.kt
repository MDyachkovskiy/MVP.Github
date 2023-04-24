package gb.com.mvp.presenter.common

import gb.com.mvp.view.adapters.common.IItemView

interface IListPresenter<V: IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}