package com.example.tvshowsbase.showslist

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.util.Connectivity
import com.example.domain.model.FilterType
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.TvShowsFragmentBinding
import com.example.tvshowsbase.login.LoginFragment.Companion.ACCOUNT_KEY
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsFragment :
    Fragment(),
    TvShowsAdapter.ItemClickListener {

    private val viewModel: TvShowsViewModel by viewModels()
    @Inject lateinit var sharedPreferences: SharedPreferences
    @Inject lateinit var connectivityManager: Connectivity

    private lateinit var binding: TvShowsFragmentBinding
    private var tvShowsAdapter: TvShowsAdapter? = null
    private var noConnection = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvShowsFragmentBinding.inflate(inflater)

        val toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.title = getString(R.string.TV_Shows_Fragment_Title)

        tvShowsAdapter = TvShowsAdapter(this)
        binding.tvShowsRecycler.adapter = tvShowsAdapter

        setLastFilterPosition()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModelObservers()
        createBindingObservers()
        setNetworkStateCallbacks()
        val accountId = sharedPreferences.getInt(ACCOUNT_KEY, 0)
        val sessionId = sharedPreferences.getString(SESSION_KEY, "").toString()
        viewModel.getFavorites(accountId, sessionId, 1)
    }

    private fun setLastFilterPosition() {
        val lastFilterPosition = sharedPreferences.getInt("filter", 0)

        when (lastFilterPosition) {
            0 -> {
                viewModel.setInitialFilter(FilterType.POPULAR)
            }
            1 -> {
                viewModel.setInitialFilter(FilterType.TOP_RATE)
            }
            2 -> {
                viewModel.setInitialFilter(FilterType.ON_AIR)
            }
            3 -> {
                viewModel.setInitialFilter(FilterType.AIRING_TODAY)
            }
        }

        binding.filterSpinner.setSelection(lastFilterPosition)
    }

    private fun createBindingObservers() {
        binding.filterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    changeFilter(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile_menu -> {
                    findNavController().navigate(TvShowsFragmentDirections.actionTvShowsFragmentToProfileFragment())
                    true
                }
                R.id.logOut_menu -> {
                    val sessionId = sharedPreferences.getString(SESSION_KEY, "")
                    sessionId?.let {
                        viewModel.logOut(sessionId)
                    }
                    true
                }
                else -> true
            }
        }
    }

    private fun createViewModelObservers() {
        viewModel.showsList.observe(
            viewLifecycleOwner,
            {
                it?.let { tvShowsAdapter?.submitList(it) }
            }
        )

        viewModel.logOutState.observe(
            viewLifecycleOwner,
            {
                handleResponse(
                    response = it,
                    successAction = {
                        sharedPreferences.edit().clear().apply()
                        findNavController().navigate(
                            TvShowsFragmentDirections.actionTvShowsFragmentToLoginFragment(
                                true
                            )
                        )
                    }
                )
            }
        )
    }

    private fun setNetworkStateCallbacks() {
        val request = NetworkRequest.Builder().build()
        connectivityManager.getConnectivityManager().registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    showSnackBar(getString(R.string.no_internet_access))
                    noConnection = true
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (noConnection) {
                        noConnection = true
                        if (viewModel.showsList.value?.isEmpty() == true) {
                            changeFilter(binding.filterSpinner.selectedItemPosition)
                        }
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

    private fun changeFilter(position: Int) {
        sharedPreferences.edit { this.putInt("filter", position).apply() }
        when (position) {
            0 -> {
                viewModel.changeFilter(FilterType.POPULAR)
            }
            1 -> {
                viewModel.changeFilter(FilterType.TOP_RATE)
            }
            2 -> {
                viewModel.changeFilter(FilterType.ON_AIR)
            }
            3 -> {
                viewModel.changeFilter(FilterType.AIRING_TODAY)
            }
        }
    }

    override fun onItemClicked(tvShow: TvShow) {
        showSnackBar(tvShow.name)
        findNavController().navigate(
            TvShowsFragmentDirections.actionTvShowsFragmentToShowDetailFragment(
                tvShow.showId
            )
        )
    }

    private fun showErrorMessage(response: InternalErrorCodes) {
        when (response) {
            InternalErrorCodes.NO_INTERNET_ACCESS -> showSnackBar(getString(R.string.no_internet_access))
            InternalErrorCodes.BAD_CREDENTIALS -> showSnackBar(getString(R.string.error_provider))
            InternalErrorCodes.NOT_FOUND -> showSnackBar(getString(R.string.error_provider))
            InternalErrorCodes.NOT_SPECIFIC -> showSnackBar(getString(R.string.try_again))
            InternalErrorCodes.NOT_DB_ENTRY -> showSnackBar(getString(R.string.no_local_available))
        }
    }

    private fun startProgress() {
        binding.listProgressBar.visibility = View.VISIBLE
    }

    private fun stopProgress() {
        binding.listProgressBar.visibility = View.GONE
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.tvShowConstrain, message, Snackbar.LENGTH_SHORT).show()
    }
}
