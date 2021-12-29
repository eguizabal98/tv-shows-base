package com.example.tvshowsbase.seasons

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.data.util.Connectivity
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.SeasonsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeasonsFragment : Fragment() {

    private val viewModel: SeasonsViewModel by viewModels()
    private lateinit var binding: SeasonsFragmentBinding
    private val args: SeasonsFragmentArgs by navArgs()
    private lateinit var adapter: SeasonAdapter

    @Inject
    lateinit var connectivityManager: Connectivity
    private var noConnection = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SeasonsFragmentBinding.inflate(inflater)
        adapter = SeasonAdapter()
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSeasons(args.showId, args.seasons)

        createViewModelObservers()
        setNetworkStateCallbacks()
    }

    private fun createViewModelObservers() {
        viewModel.seasonRequest.observe(
            viewLifecycleOwner,
            { seasonRequest ->
                handleResponse(response = seasonRequest, successAction = {})
            }
        )

        viewModel.seasonList.observe(
            viewLifecycleOwner,
            {
                adapter.submitList(it)
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
                        viewModel.getSeasons(args.showId, args.seasons)
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
        binding.seasonProgress.visibility = View.VISIBLE
    }

    private fun stopProgress() {
        binding.seasonProgress.visibility = View.GONE
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.constrainSeasons, message, Snackbar.LENGTH_SHORT).show()
    }
}
