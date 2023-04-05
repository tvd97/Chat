package com.example.chatfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatfirebase.databinding.ItemUserBinding
import com.example.chatfirebase.extentions.ViewDiffUtil
import com.example.chatfirebase.model.User
import javax.inject.Inject

class UserAdapter @Inject constructor() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var onClickItem: ((User) -> Unit)? = null
    private var users = mutableListOf<User>()
    fun submitData(data: List<User>) {
        val diffUtil = ViewDiffUtil(users, data)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        users.clear()
        users.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        users[position].let {
            holder.onBind(it)
            holder.itemView.setOnClickListener { _ ->
                onClickItem?.invoke(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }

    }
}