package com.ingridentify.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ingridentify.R
import com.ingridentify.databinding.FragmentDetailBinding
import com.ingridentify.ui.ViewModelFactory
import com.ingridentify.utils.hideBottomBar
import com.ingridentify.utils.showBottomBar

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        hideBottomBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FIXME: the recipe should not be nullable
        viewModel.getDetail(args.recipeId).observe(viewLifecycleOwner) { it?.let { recipe ->
            binding.tvRecipeName.text = recipe.name
            Glide
                .with(requireContext())
                .load(recipe.imageUrl)
                .into(binding.ivPreview)
            binding.tvRecipeDetail.text = getString(R.string.recipe_detail, recipe.cuisine, recipe.recipes)
        }
    }}

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomBar()
    }
}