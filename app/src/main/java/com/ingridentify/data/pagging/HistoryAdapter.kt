package com.ingridentify.data.pagging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ingridentify.R
import com.ingridentify.data.HistoryItem

class HistoryAdapter(private val historyItemList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = historyItemList[position]

        holder.ivHistory.setImageResource(currentItem.imageResId)
        holder.tvHistory.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return historyItemList.size
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivHistory: ImageView = itemView.findViewById(R.id.iv_history)
        val tvHistory: TextView = itemView.findViewById(R.id.tv_history)
    }
}
