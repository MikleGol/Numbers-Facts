package com.miklegol.numbersfacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miklegol.numbersfacts.databinding.ItemHistoryBinding
import com.miklegol.numbersfacts.models.Fact

class HistoryAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyList = ArrayList<Fact>()
    lateinit var onItemClick: OnItemHistoryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val fact = historyList[position]
        holder.binding.apply {
            tvNumberItemHistory.text = fact.number
            tvFactItemHistory.text = fact.text
        }
        holder.itemView.setOnClickListener{
            onItemClick.onClickListener(historyList[position])
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun addFact(fact: Fact) {
        historyList.add(fact)
        notifyItemInserted(historyList.size - 1)
        recyclerView.smoothScrollToPosition(historyList.size - 1)
    }

    fun setHistory(historyList : List<Fact>){
        this.historyList = historyList as ArrayList<Fact>
        notifyDataSetChanged()
    }

    fun onItemClicked(onItemClick: OnItemHistoryClicked){
        this.onItemClick = onItemClick
    }


    interface OnItemHistoryClicked{
        fun onClickListener(fact:Fact)
    }

    class HistoryViewHolder(val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)
}
