package com.gxsales.client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gxsales.client.R
import com.gxsales.client.databinding.RvItemshopLayoutBinding
import com.gxsales.client.model.dataclass.response.ItemShopResponse

class ItemShopAdapter(
    var data: MutableList<ItemShopResponse>
): RecyclerView.Adapter<ItemShopAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: ItemShopResponse) = with(itemView){
            val binding = RvItemshopLayoutBinding.bind(itemView)
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.productImage)
                    .into(ivProduct)

                tvProductName.text = item.productName
                tvProductPrice.text = String.format(itemView.context.getString(R.string.tvFormat_ProductPrice), item.productPrice)
                tvProductStockDesc.text = String.format(itemView.context.getString(R.string.tvFormat_ProductStock, item.productStock))
                tvTypeProductDesc.text = item.productType
                tvTaxProductDesc.text = String.format(itemView.context.getString(R.string.tvFormat_ProductTax), item.productTax)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_itemshop_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position))
    }

}