package com.example.tvshowsbase.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.model.WorkState
import com.example.tvshowsbase.databinding.ProfileFragmentBinding
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: ProfileFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        adapter = FavoritesAdapter()
        binding.favoritesRecycler.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val session = sharedPreferences.getString(SESSION_KEY, "").toString()
        viewModel.getAccount(session)

        viewModel.account.observe(viewLifecycleOwner, { profile ->
            when (profile) {
                WorkState.Loading -> {

                }
                is WorkState.Success -> {
                    binding.profile = profile.value
                }
                is WorkState.Failure -> {

                }
            }
        })

        viewModel.favorites.observe(viewLifecycleOwner, {
            Log.d("Account", it.toString())
            adapter.submitList(it)
        })
    }

}