package com.example.tvshowsbase.profile

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.data.util.Connectivity
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.ProfileFragmentBinding
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: ProfileFragmentBinding
    @Inject lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: FavoritesAdapter
    @Inject lateinit var connectivityManager: Connectivity
    private var noConnection = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        createViewModelObservers()
        setNetworkStateCallbacks()
    }

    private fun createViewModelObservers() {
        viewModel.account.observe(
            viewLifecycleOwner,
            { profile ->
                handleResponse(
                    response = profile,
                    successAction = {
                        binding.profile = it
                    }
                )
            }
        )

        viewModel.favoriteResult.observe(
            viewLifecycleOwner,
            { listShow ->
                adapter.submitList(listShow)
            }
        )
    }

    private fun setNetworkStateCallbacks() {
        if (!connectivityManager.hasNetworkAccess()) noConnection = true

        val request = NetworkRequest.Builder().build()
        connectivityManager.getConnectivityManager().registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {

                override fun onLost(network: Network) {
                    super.onLost(network)
                    noConnection = true
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (noConnection) {
                        noConnection = true
                        val session = sharedPreferences.getString(SESSION_KEY, "").toString()
                        viewModel.getAccount(session)
                    }
                }
            }
        )
    }

    private fun <T : Any> handleResponse(response: WorkState<T>, successAction: (T) -> Unit) {
        when (response) {
            WorkState.Loading -> {
                startProgress()
            }
            is WorkState.Success -> {
                stopProgress()
                successAction(response.value)
            }
            is WorkState.Failure -> {
                stopProgress()
                showErrorMessage(response.error)
            }
            else -> {
            }
        }
    }

    private fun showErrorMessage(response: InternalErrorCodes) {
        when (response) {
            InternalErrorCodes.NO_INTERNET_ACCESS -> showMessage(getString(R.string.no_internet_access))
            InternalErrorCodes.BAD_CREDENTIALS -> showMessage(getString(R.string.error_provider))
            InternalErrorCodes.NOT_FOUND -> showMessage(getString(R.string.error_provider))
            InternalErrorCodes.NOT_SPECIFIC -> showMessage(getString(R.string.try_again))
            InternalErrorCodes.NOT_DB_ENTRY -> showMessage(getString(R.string.no_local_available))
        }
    }

    private fun startProgress() {
        binding.progressBarProfile.visibility = View.VISIBLE
    }

    private fun stopProgress() {
        binding.progressBarProfile.visibility = View.GONE
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.scrollViewProfile, message, Snackbar.LENGTH_SHORT).show()
    }
}
