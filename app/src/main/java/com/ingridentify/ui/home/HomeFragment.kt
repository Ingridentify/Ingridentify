package com.ingridentify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ingridentify.R
import com.ingridentify.data.HistoryItem
import com.ingridentify.data.pagging.HistoryAdapter

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_history)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val historyItemList = generateDummyData()
        adapter = HistoryAdapter(historyItemList)
        recyclerView.adapter = adapter

        return view
    }

    //test
    private fun generateDummyData(): List<HistoryItem> {
        val drawableList = listOf(
            R.drawable.fruit,
            R.drawable.fruit,
            )
        val dummyText = "Dummy Text"
        return drawableList.map { HistoryItem(it, dummyText) }
    }
}
