package com.adrammedia.easyfood.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adrammedia.easyfood.database.models.popularmeals.PopularMeal
import com.adrammedia.easyfood.databinding.PopularItemsBinding
import com.adrammedia.easyfood.ui.activities.MealDetailsActivity
import com.bumptech.glide.Glide

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    private val diffUtil = object :DiffUtil.ItemCallback<PopularMeal>(){
        override fun areItemsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    lateinit var onItemClick: ((PopularMeal)->Unit)
    lateinit var onItemLongClick: ((PopularMeal)->Unit)

    inner class PopularViewHolder(val binding:PopularItemsBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
       return PopularViewHolder(PopularItemsBinding.inflate(
           LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
       val popularMeal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(popularMeal.strMealThumb)
            .into(holder.binding.imgPopularMeal)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(popularMeal)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick.invoke(popularMeal)
            true
        }
    }
}