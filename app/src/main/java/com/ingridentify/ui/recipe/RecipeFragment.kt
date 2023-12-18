package com.ingridentify.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridentify.data.paging.RecipeAdapter
import com.ingridentify.databinding.FragmentRecipeBinding
import com.ingridentify.ui.ViewModelFactory

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val viewModel by viewModels<RecipeViewModel> { ViewModelFactory.getInstance(requireContext()) }
    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter { recipe ->
            val toDetailFragment = RecipeFragmentDirections.actionNavigationRecipeToDetailFragment(
                recipeId = recipe.id
            )
            requireView().findNavController().navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            adapter.submitData(lifecycle, recipes)

            if (adapter.itemCount == 0) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.GONE
            }
        }
    }
}