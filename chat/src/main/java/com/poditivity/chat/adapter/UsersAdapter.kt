package com.poditivity.chat.adapter

import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.poditivity.chat.UsersFragmentDirections
import com.poditivity.chat.databinding.UserRowLayoutBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private val client = ChatClient.instance()
    private var userList = emptyList<User>()

    class MyViewHolder(val binding: UserRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.binding.avatarImageView.setAvatar(currentUser.image)
        holder.binding.usernameTextView.text = currentUser.id
        holder.binding.lastActiveTextView.text = convertDate(currentUser.lastActive!!.time)
        holder.binding.rootLayout.setOnClickListener {
            createNewChannel(currentUser.id, holder)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }

    private fun convertDate(milliseconds: Long): String {
        return DateFormat.format("dd/MM/yyyy hh:mm a", milliseconds).toString()
    }

    private fun createNewChannel(selectedUser: String, holder: MyViewHolder) {
        client.createChannel(
            channelType = "messaging",
            channelId = selectedUser,
            memberIds = listOf(client.getCurrentUser()!!.id, selectedUser),
            extraData = emptyMap()
        ).enqueue { result ->
            if (result.isSuccess) {
                navigateToChatFragment(holder, result.getOrNull()!!.cid)
            } else {
                Log.e("UsersAdapter", result.errorOrNull()!!.message.toString())
            }
        }
    }

    private fun navigateToChatFragment(holder: MyViewHolder, cid: String) {
        val action = UsersFragmentDirections.actionUsersFragmentToChatFragment(cid)
        holder.itemView.findNavController().navigate(action)
    }

}











