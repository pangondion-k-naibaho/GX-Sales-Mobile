package com.gxsales.client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.gxsales.client.R
import com.gxsales.client.databinding.RvItemsummaryLayoutBinding
import com.gxsales.client.model.Extensions.Companion.dpToPx
import com.gxsales.client.model.dataclass.response.ItemSummaryResponse

class ItemSummaryAdapter(
    var data: MutableList<ItemSummaryResponse>
): RecyclerView.Adapter<ItemSummaryAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: ItemSummaryResponse) = with(itemView){
            val binding = RvItemsummaryLayoutBinding.bind(itemView)
            binding.apply {
                if(adapterPosition%2==0 || adapterPosition == 0){
                    val layoutParams = root.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.marginEnd = 16.dpToPx(itemView.context)
                    root.layoutParams = layoutParams
                }else{
                    val layoutParams = root.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.marginStart = 16.dpToPx(itemView.context)
                    root.layoutParams = layoutParams
                }
                tvCount.text = when{
                    item.count < 10 -> "0${item.count}"
                    else -> item.count.toString()
                }
                tvSummaryName.text = item.summaryName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_itemsummary_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position))
    }
}