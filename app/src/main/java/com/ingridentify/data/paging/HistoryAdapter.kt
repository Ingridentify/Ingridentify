package com.ingridentify.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.databinding.CardHistoryBinding

class HistoryAdapter(private val onClick: (RecipeModel) -> Unit) :
    PagingDataAdapter<RecipeModel, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding: CardHistoryBinding = CardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null) {
            holder.bind(currentItem, onClick)
        }
    }

    class HistoryViewHolder(private val binding: CardHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: RecipeModel, onClick: (RecipeModel) -> Unit) {
            Glide.with(itemView.context)
                .load(history.imageUrl)
                .into(binding.ivHistory)

            binding.tvHistory.text = history.name

            itemView.setOnClickListener {
                onClick(history)
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
                        oldItem.recipes == newItem.recipes &&
                        oldItem.imageUrl == newItem.imageUrl
            }
        }
    }
}
