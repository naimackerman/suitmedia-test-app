package com.suitmedia.naim.suitmediatestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suitmedia.naim.suitmediatestapp.databinding.ItemUserBinding
import com.suitmedia.naim.suitmediatestapp.network.UserResponseItem

class UserListAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<UserResponseItem, UserListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener {
                onClick("${data.firstName} ${data.lastName}")
            }
        }
    }

    class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserResponseItem) {
            Glide.with(binding.itemIvPhoto.context)
                .load(data.avatar)
                .circleCrop()
                .into(binding.itemIvPhoto)
            binding.itemTvName.text = String.format("%s %s", data.firstName, data.lastName)
            binding.itemTvEmail.text = data.email
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}