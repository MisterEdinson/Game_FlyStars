package com.example.game_flystars.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.game_flystars.R
import com.example.game_flystars.databinding.ItemBetHeaderBinding

class GameAdapter : RecyclerView.Adapter<GameAdapter.ItemHolder>() {
    class ItemHolder(view : View) : RecyclerView.ViewHolder(view)

    private val callback = object : DiffUtil.ItemCallback<Double>(){
        override fun areItemsTheSame(oldItem: Double, newItem: Double): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Double, newItem: Double): Boolean {
            return oldItem == newItem
        }
    }

    val list = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bet_header, parent , false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.currentList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = list.currentList[position]
        val binding = ItemBetHeaderBinding.bind(holder.itemView)
        binding.tvItemBet.text = item.toString()
    }
}