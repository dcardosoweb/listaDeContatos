package com.picpay.desafio.android.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.presentation.model.UserContactModel
import com.picpay.desafio.android.presentation.viewHolder.UserListItemViewHolder

class UserListAdapter: ListAdapter<UserContactModel, UserListItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        return UserListItemViewHolder.createInstance(parent)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UserContactModel>() {
            override fun areItemsTheSame(
                oldItem: UserContactModel,
                newItem: UserContactModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserContactModel,
                newItem: UserContactModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}