package com.example.tvshowsbase.details

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.util.Connectivity
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.ShowDetailFragmentBinding
import com.example.tvshowsbase.databindingutils.visible
import com.example.tvshowsbase.login.LoginFragment.Companion.ACCOUNT_KEY
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import javax.inject.Inject

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    private val viewModel: ShowDetailViewModel by viewModels()
    private lateinit var binding: ShowDetailFragmentBinding
    private var castListAdapter: CastListAdapter? = null

    @Inject
    lateinit var connectivityManager: Connectivity
    private var noConnection = false

    private val args: ShowDetailFragmentArgs by navArgs()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowDetailFragmentBinding.inflate(inflater)
        castListAdapter = CastListAdapter()

        binding.castRecycler.adapter = castListAdapter

        OverScrollDecoratorHelper.setUpOverScroll(binding.scrollView2)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getDetails(args.showId)
        viewModel.fetchDetails(args.showId)
        viewModel.getCast(args.showId)
        viewModel.fetchCast(args.showId)

        createViewObservers()
        createViewModelObservers()
        setNetworkStateCallbacks()
    }

    private fun createViewObservers() {
        binding.favIconImage.setOnClickListener {
            val sessionId = sharedPreferences.getString(SESSION_KEY, "").toString()
            val accountId = sharedPreferences.getInt(ACCOUNT_KEY, 0)
            viewModel.putFavorite(sessionId, accountId)
        }

        binding.allSeasonButton.setOnClickListener {
            val showId: Int? = viewModel.details.value?.showId
            val seasons: Int? = viewModel.details.value?.lastSeason?.seasonNumber
            if (showId != null && seasons != null) {
                findNavController().navigate(
                    ShowDetailFragmentDirections.actionShowDetailFragmentToSeasonsFragment(
                        showId,
                        seasons
                    )
                )
            } else {
                showErrorMessage(InternalErrorCodes.NO_INTERNET_ACCESS)
            }
        }
    }

    private fun createViewModelObservers() {
        viewModel.details.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding.tvShowDetails = it
                    favCheckObserver()
                }
            }
        )

        viewModel.cast.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    castListAdapter?.submitList(it)
                    if (it.isEmpty()) {
                        binding.emptyCast.visibility = View.VISIBLE
                    }
                }
            }
        )

        viewModel.detailsRequest.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(
                    response = response, successAction = { favCheckObserver() }
                )
            }
        )

        viewModel.castRequest.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(
                    response = response,
                    successAction = {
                    }
                )
            }
        )

        viewModel.favoriteRequest.observe(
            viewLifecycleOwner,
            { response ->
                handleResponse(response = response, successAction = {})
            }
        )

        viewModel.favoriteResult.observe(
            viewLifecycleOwner,
            {
                favCheckObserver()
            }
        )
    }

    private fun favCheckObserver() {
        val listShow = viewModel.favoriteResult.value
        var condition = false
        listShow?.forEach {
            if (it.showId == viewModel.details.value?.showId) {
                condition = true
            }
        }
        if (condition) {
            viewModel.favoriteState = true
            binding.favIconImage.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            viewModel.favoriteState = false
            binding.favIconImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun setNetworkStateCallbacks() {
        if (!connectivityManager.hasNetworkAccess()) {
            noConnection = true
            showErrorMessage(InternalErrorCodes.NO_INTERNET_ACCESS)
        }

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
                        viewModel.fetchDetails(args.showId)
                        viewModel.fetchCast(args.showId)
                    }
                }
            }
        )
    }

    private fun <T : Any> handleResponse(response: WorkState<T>, successAction: (T) -> Unit) {
        when (response) {
            WorkState.Loading -> {
                starProgressBar()
            }
            is WorkState.Success -> {
                stopProgressBar()
                successAction(response.value)
            }
            is WorkState.Failure -> {
                stopProgressBar()
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

    private fun starProgressBar() {
        binding.progressBarDetails.visible(true)
    }

    private fun stopProgressBar() {
        binding.progressBarDetails.visible(false)
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.detailsConstrain, message, Snackbar.LENGTH_SHORT).show()
    }
}
