package com.adrammedia.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.databinding.FavoriteItemBinding
import com.bumptech.glide.Glide

class FavoriteAdapter:RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(val binding: FavoriteItemBinding):ViewHolder(binding.root)
    private val diffUtil = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)
    lateinit var onItemClick:((Meal)->Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
       return FavoriteViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivFavoriteItemImg)
        holder.binding.tvFavMealName.text = meal.strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}