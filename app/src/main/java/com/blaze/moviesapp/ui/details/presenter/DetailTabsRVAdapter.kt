package com.blaze.moviesapp.ui.details.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.blaze.moviesapp.databinding.DetailInfoTabItemBinding
import com.blaze.moviesapp.ui.home.presenter.StringDiffUtilCallback

class DetailTabsRVAdapter: RecyclerView.Adapter<DetailTabsRVAdapter.DetailTabViewHolder>() {
    private var selectedItem = 0

    private val differ = AsyncListDiffer(this, StringDiffUtilCallback())

    var tabs: List<String>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTabViewHolder {
        val binding = DetailInfoTabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailTabViewHolder, position: Int) {
        val tab = tabs[position]
        holder.bind(tab)
    }

    override fun getItemCount(): Int {
        return tabs.size
    }

    inner class DetailTabViewHolder(
        private val binding: DetailInfoTabItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvTabName.text = item
            binding.viewSelected.isVisible = selectedItem == bindingAdapterPosition

            itemView.setOnClickListener {
                if(selectedItem != bindingAdapterPosition) {
                    val lastSelectedItem = selectedItem
                    selectedItem = bindingAdapterPosition
                    notifyItemChanged(lastSelectedItem)
                    notifyItemChanged(selectedItem)

                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }
}