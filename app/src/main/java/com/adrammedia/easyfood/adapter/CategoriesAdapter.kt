package com.adrammedia.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adrammedia.easyfood.database.models.mealscategories.Category
import com.adrammedia.easyfood.databinding.CategoryItemBinding
import com.bumptech.glide.Glide

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    lateinit var onCategoryClick:((Category)->Unit)
    inner class CategoryViewHolder(val binding:CategoryItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.binding.tvCategoryName.text = category.strCategory
        Glide.with(holder.itemView)
            .load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.itemView.setOnClickListener {
            onCategoryClick.invoke(category)
        }
    }
}