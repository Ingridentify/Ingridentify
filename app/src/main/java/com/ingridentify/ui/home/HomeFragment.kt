package com.ingridentify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridentify.R
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.paging.RecipeAdapter
import com.ingridentify.databinding.FragmentHomeBinding
import com.ingridentify.ui.ViewModelFactory
import com.ingridentify.ui.auth.AuthActivity

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

        setupRecyclerView()

        binding.cardUser.setOnClickListener {
            val toProfileFragment = HomeFragmentDirections.actionNavigationHomeToProfileFragment()
            requireView().findNavController().navigate(toProfileFragment)
        }

        viewModel.checkSession().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email

                //FIXME: load image from url using Glide
                binding.ivProfile.setImageResource(R.drawable.profile)

                viewModel.histories.observe(viewLifecycleOwner) { histories: PagingData<RecipeModel> ->
                    adapter.submitData(lifecycle, histories)
                }
            } else {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            showLoading(loadState.refresh is LoadState.Loading)

            if (loadState.append.endOfPaginationReached)
                showEmpty(adapter.itemCount < 1)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.tvEmpty.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }
}
