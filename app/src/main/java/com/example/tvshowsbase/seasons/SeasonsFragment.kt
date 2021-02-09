package com.example.tvshowsbase.seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.domain.model.WorkState
import com.example.tvshowsbase.databinding.SeasonsFragmentBinding
import org.koin.android.ext.android.inject

class SeasonsFragment : Fragment() {

    private val viewModel: SeasonsViewModel by inject()
    private lateinit var binding: SeasonsFragmentBinding
    private val args: SeasonsFragmentArgs by navArgs()
    private lateinit var adapter: SeasonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

        viewModel.seasonList.observe(viewLifecycleOwner, { response ->
            when (response) {
                WorkState.Loading -> {
                    binding.seasonProgress.visibility = View.VISIBLE
                }
                is WorkState.Success -> {
                    binding.seasonProgress.visibility = View.GONE
                    adapter.submitList(response.value)
                }
                is WorkState.Failure -> {
                    binding.seasonProgress.visibility = View.GONE
                }
            }
        })
    }

}