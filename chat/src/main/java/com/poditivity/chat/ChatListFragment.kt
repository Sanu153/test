package com.poditivity.chat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.poditivity.chat.databinding.FragmentChatListBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.channels.bindView


class ChatListFragment : Fragment() {
    private val args: ChatListFragmentArgs by navArgs()

    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    private lateinit var client:ChatClient
    private lateinit var user: User

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatListBinding.inflate(inflater, container, false)


        binding.channelsView.setChannelDeleteClickListener { channel ->

        }

        binding.btn.setOnClickListener {
            findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToUsersFragment())
        }

        binding.channelsView.setChannelItemClickListener { channel ->
            val action = ChatListFragmentDirections.actionChatListFragmentToChatFragment(channel.cid)
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            client = ChatClient.instance()
            Log.v("Doxx",client.toString())
        }catch (e:Exception){
            Log.v("Doxx","error")
        }
       
        setupUser()
    }

    private fun setupUser() {
        Log.d("ChannelFragment", "Hii")
        if (client.getCurrentUser() == null) {
            user = if (args.chatUser.firstName.contains("Stefan")) {
                User(
                    id = args.chatUser.username,
                    extraData = mutableMapOf(
                        "name" to args.chatUser.firstName,
                        "county" to "Serbia",
                        "image" to "https://yt3.ggpht.com/ytc/AAUvwniNg3lwIeJ-ybvA1xuWBEzLoYA5KPxnKrojub0zhg=s900-c-k-c0x00ffffff-no-rj"
                    )
                )
            } else {
                User(
                    id = args.chatUser.username,
                    extraData = mutableMapOf(
                        "name" to args.chatUser.firstName
                    )
                )
            }
            val token = client.devToken(user.id)
            Log.d("ChannelFragment", token)
            client.connectUser(
                user = user,
                token = token
            ).enqueue { result ->
                if (result.isSuccess) {
                    Log.d("ChannelFragment", "Success Connecting the User")
                    setupChannels()
                } else {
                    Log.d("ChannelFragment", result.errorOrNull()!!.message.toString())
                }
            }
        }
    }

    private fun setupChannels() {
        val filters = Filters.and(
            Filters.eq("type", "messaging"),
            Filters.`in`("members", listOf(client.getCurrentUser()!!.id))
        )
        val viewModelFactory = ChannelListViewModelFactory(
            filters,
            ChannelListViewModel.DEFAULT_SORT
        )
        val listViewModel: ChannelListViewModel by viewModels { viewModelFactory }
        val listHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        listViewModel.bindView(binding.channelsView, viewLifecycleOwner)
    }





    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}