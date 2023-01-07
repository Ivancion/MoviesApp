package com.blaze.moviesapp.ui.home.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blaze.moviesapp.databinding.CategoryItemBinding

class CategoriesRVAdapter: RecyclerView.Adapter<CategoriesRVAdapter.CategoryViewHolder>() {
    private var selectedIndex = 0

    val differ = AsyncListDiffer(this, StringDiffUtilCallback())

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CategoryViewHolder(
        private val binding: CategoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.tvName.text = category
            binding.viewSelected.isVisible = bindingAdapterPosition == selectedIndex
            itemView.setOnClickListener {
                if(bindingAdapterPosition != selectedIndex) {
                    val lastSelected = selectedIndex
                    selectedIndex = bindingAdapterPosition
                    notifyItemChanged(lastSelected)
                    notifyItemChanged(selectedIndex)

                    onItemClickListener?.let {
                        it(category)
                    }
                }
            }
        }
    }
}

class StringDiffUtilCallback(): DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}