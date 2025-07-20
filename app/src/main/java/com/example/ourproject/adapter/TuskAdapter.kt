package com.example.ourproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ourproject.databinding.ItemTuskBinding //itemtusk сделаете заработает
import com.example.ourproject.model.TuskModel
//import com.example.ourproject.util.CatGenerator  ???? obsudit вроде мы не генерим

class CatAdapter(
    private val tusks: List<TuskModel>,
    private val onItemClicked: (TuskModel) -> Unit,
    private val onImageClicked: (TuskModel) -> Unit
) : RecyclerView.Adapter<TuskAdapter.TuskViewHolder>() {

    inner class TuskViewHolder(val binding: ItemTuskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tusk: TuskModel) {
            binding.titleText.text = tusk.title
            binding.descText.text = tusk.description
            binding.catImage.setImageResource(cat.imageResId)
            binding.root.setOnClickListener { onItemClicked(cat) }
            binding.catImage.setOnClickListener {
                cat.imageResId = CatGenerator.getRandomImage()
                notifyItemChanged(adapterPosition)
                onImageClicked(cat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun getItemCount(): Int = tusks.size

    override fun onBindViewHolder(holder: TuskViewHolder, position: Int) {
        holder.bind(tusks[position])
    }
}