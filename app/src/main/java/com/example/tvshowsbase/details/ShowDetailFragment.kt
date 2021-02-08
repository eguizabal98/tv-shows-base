package com.example.tvshowsbase.details

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.domain.model.NetworkResult
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.ShowDetailFragmentBinding
import com.example.tvshowsbase.login.LoginFragment.Companion.ACCOUNT_KEY
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShowDetailFragment : Fragment() {

    private val viewModel: ShowDetailViewModel by viewModel()
    private lateinit var binding: ShowDetailFragmentBinding
    private var castListAdapter: CastListAdapter? = null

    private val args: ShowDetailFragmentArgs by navArgs()

    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowDetailFragmentBinding.inflate(inflater)
        castListAdapter = CastListAdapter()

        binding.castRecycler.adapter = castListAdapter

        viewModel.getDetails(args.showId)
        viewModel.getCast(args.showId)

        viewModel.details.observe(viewLifecycleOwner, {
            it?.let {
                binding.tvShowDetails = it
            }
        })

        viewModel.cast.observe(viewLifecycleOwner, {
            it?.let {
                castListAdapter?.submitList(it)
            }
        })

        binding.favIconImage.setOnClickListener {
            val sessionId = sharedPreferences.getString(SESSION_KEY, "").toString()
            val accountId = sharedPreferences.getInt(ACCOUNT_KEY, 0)
            viewModel.putFavorite(sessionId, accountId)
        }

        viewModel.favoriteResult.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Failure -> {
                    Log.e("Error", "Manage Error")
                }
                is NetworkResult.Success -> {
                    viewModel.favoriteState = it.value
                    if (it.value) {
                        binding.favIconImage.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        binding.favIconImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }
        })

        return binding.root
    }

}