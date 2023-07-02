package ru.com.bulat.recycleviewbasic.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import ru.com.bulat.recycleviewbasic.R
import ru.com.bulat.recycleviewbasic.databinding.FragmentUsersDetailsBinding

class UserDetailsFragment : Fragment() {
    private lateinit var binding: FragmentUsersDetailsBinding
    private val viewModel : UserDetailsViewModel by viewModels { factory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersDetailsBinding.inflate(layoutInflater,container,false)

        viewModel.userDetail.observe(viewLifecycleOwner, Observer {item->
            binding.userNameTextView.text = item.user.name
            if (item.user.photo.isNotBlank()){
                Glide.with(this)
                    .load(item.user.photo)
                    .circleCrop()
                    .into(binding.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_user_avatar)
                    .into(binding.photoImageView)
            }
            binding.userDetaisTextView.text = item.details
        })

        binding.deleteButton.setOnClickListener {
            viewModel.deleteUser()
            navigator().toast(R.string.user_has_been_deleted)
            navigator().goBack()
        }

        return binding.root
    }
    companion object {

        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance (userId: Long) : UserDetailsFragment {
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to userId)
            return fragment
        }
    }
}