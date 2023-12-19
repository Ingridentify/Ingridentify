package com.ingridentify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.paging.RecipeAdapter
import com.ingridentify.databinding.FragmentHomeBinding
import com.ingridentify.ui.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { ViewModelFactory.getInstance(requireContext()) }
    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter { recipe ->
            val toDetailFragment = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
                recipeId = recipe.id
            )
            requireView().findNavController().navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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

                viewModel.histories.observe(viewLifecycleOwner) { histories: PagingData<RecipeModel> ->
                    adapter.submitData(lifecycle, histories)

                    if (adapter.itemCount == 0) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }
}
