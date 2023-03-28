package gb.com.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gb.com.R
import gb.com.mvp.presenter.list.IUserListPresenter
import gb.com.mvp.view.list.IUserItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class UsersRVAdapter(
    val presenter: IUserListPresenter
): RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
    LayoutContainer, IUserItemView{

        override fun setLogin(text: String) = with(containerView) {
           tv_login.text = text
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener{
            presenter.itemClickListener?.invoke(holder)
        }
        presenter.bindView(holder)
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }
}