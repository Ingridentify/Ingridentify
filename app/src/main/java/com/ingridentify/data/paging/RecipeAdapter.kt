package com.ingridentify.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.databinding.CardRecipeBinding

class RecipeAdapter(private val onClick: (RecipeModel) -> Unit) :
    PagingDataAdapter<RecipeModel, RecipeAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding: CardRecipeBinding = CardRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null) {
            holder.bind(currentItem, onClick)
        }
    }

    class HistoryViewHolder(private val binding: CardRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel, onClick: (RecipeModel) -> Unit) {
            Glide.with(itemView.context)
                .load(recipe.urlImage)
                .into(binding.ivHistory)

            binding.tvHistory.text = recipe.cuisine

            itemView.setOnClickListener {
                onClick(recipe)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipeModel>() {
            override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.cuisine == newItem.cuisine &&
                        oldItem.ingridient == newItem.ingridient &&
                        oldItem.recipes == newItem.recipes &&
                        oldItem.urlImage == newItem.urlImage
            }
        }
    }
}
