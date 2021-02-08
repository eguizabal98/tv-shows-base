package com.example.tvshowsbase.showslist

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.paging.ItemBoundaryCallBack
import com.example.domain.model.FilterType
import com.example.domain.model.TvShow
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.TvShowsFragmentBinding
import com.example.tvshowsbase.login.LoginFragment.Companion.ACCOUNT_KEY
import com.example.tvshowsbase.login.LoginFragment.Companion.SESSION_KEY
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowsFragment : Fragment(),
    TvShowsAdapter.ItemClickListener {

    private val viewModel: TvShowsViewModel by viewModel()
    private val itemBoundaryCallBack: ItemBoundaryCallBack by inject()
    private val sharedPreferences: SharedPreferences by inject()

    private lateinit var binding: TvShowsFragmentBinding
    private var tvShowsAdapter: TvShowsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvShowsFragmentBinding.inflate(inflater)

        val toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.title = getString(R.string.TV_Shows_Fragment_Title)

        tvShowsAdapter = TvShowsAdapter(this)
        binding.tvShowsRecycler.adapter = tvShowsAdapter

        val lastFilterPosition = sharedPreferences.getInt("filter", 0)

        when (lastFilterPosition) {
            0 -> {
                itemBoundaryCallBack.setInitialFilter(FilterType.POPULAR)
            }
            1 -> {
                itemBoundaryCallBack.setInitialFilter(FilterType.TOP_RATE)
            }
            2 -> {
                itemBoundaryCallBack.setInitialFilter(FilterType.ON_AIR)
            }
            3 -> {
                itemBoundaryCallBack.setInitialFilter(FilterType.AIRING_TODAY)
            }
        }

        binding.filterSpinner.setSelection(lastFilterPosition)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModelObservers()
        createBindingObservers()
        val accountId = sharedPreferences.getInt(ACCOUNT_KEY, 0)
        val sessionId = sharedPreferences.getString(SESSION_KEY, "").toString()
        viewModel.getFavorites(accountId, sessionId, 1)
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
                    sharedPreferences.edit { this.putInt("filter", position).apply() }
                    when (position) {
                        0 -> {
                            itemBoundaryCallBack.onChange(FilterType.POPULAR)
                        }
                        1 -> {
                            itemBoundaryCallBack.onChange(FilterType.TOP_RATE)
                        }
                        2 -> {
                            itemBoundaryCallBack.onChange(FilterType.ON_AIR)
                        }
                        3 -> {
                            itemBoundaryCallBack.onChange(FilterType.AIRING_TODAY)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

    private fun createViewModelObservers() {
        viewModel.showsList.observe(viewLifecycleOwner, {
            tvShowsAdapter?.submitList(it)
        })
    }


    override fun onItemClicked(tvShow: TvShow) {
        showSnackBar(tvShow.name)
        findNavController().navigate(
            TvShowsFragmentDirections.actionTvShowsFragmentToShowDetailFragment(
                tvShow.showId
            )
        )
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.tvShowConstrain, message, Snackbar.LENGTH_SHORT).show()
    }

}
