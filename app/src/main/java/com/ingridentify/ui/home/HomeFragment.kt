package com.ingridentify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridentify.data.paging.HistoryAdapter
import com.ingridentify.databinding.FragmentHomeBinding
import com.ingridentify.ui.ViewModelFactory

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> { ViewModelFactory.getInstance(requireContext()) }
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter { history ->
            //TODO: Handle click
            Toast.makeText(requireContext(), history.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        viewModel.checkSession().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email

                viewModel.histories.observe(viewLifecycleOwner) { histories ->
                    Log.d("HomeFragment", "onViewCreated: $histories")
                    adapter.submitData(lifecycle, histories)
                    //TODO: Handle empty state
                }
            }
        }
    }
}
