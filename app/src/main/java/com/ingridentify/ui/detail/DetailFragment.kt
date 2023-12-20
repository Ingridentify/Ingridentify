package com.ingridentify.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ingridentify.data.Result
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

        viewModel.getDetail(args.recipeId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    setLoading(true)
                }

                is Result.Success -> {
                    val recipe = result.data
                    binding.tvRecipeName.text = recipe.cuisine
                    Glide
                        .with(requireContext())
                        .load(recipe.urlImage)
                        .into(binding.ivPreview)
                    //numbered list
                    binding.tvIngridients.text = recipe.ingridient.mapIndexed { index, s ->
                        "${index + 1}. $s"
                    }.joinToString("\n")
                    binding.tvDirections.text = recipe.recipes.joinToString("\n")

                    setLoading(false)
                }

                is Result.Error -> {
                    setLoading(false)
                }
            }
        }
    }

    private fun setLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomBar()
    }
}