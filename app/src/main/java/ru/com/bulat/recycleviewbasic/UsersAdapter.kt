package ru.com.bulat.recycleviewbasic

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.com.bulat.recycleviewbasic.databinding.ItemUserBinding
import ru.com.bulat.recycleviewbasic.model.User

interface UserActionListener {
    fun onUserMove (user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
}

class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter <UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()

        }

    override fun onClick(v: View) {
        val user = v.tag as User

        when (v.id) {
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu (view.context, view)
        val context = view.context
        val user:User = view.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }

        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < users.size-1
        }
        popupMenu.menu.add(0, ID_MOVE_REMOVE, Menu.NONE, context.getString(R.string.move_rempve))

        popupMenu.setOnMenuItemClickListener {item->
            when (item.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
            holder.itemView.tag = user
            moreImageViewButton.tag = user

            userNameTextView.text = user.name
            userCompanyTextView.text=user.company
            if (user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else {
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater,parent,false)

        binding.root.setOnClickListener (this)
        binding.moreImageViewButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    class UsersViewHolder (
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder (binding.root)

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_MOVE_REMOVE = 3
    }
}