package com.gxsales.client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gxsales.client.R
import com.gxsales.client.databinding.RvItemleadLayoutBinding
import com.gxsales.client.model.Constants.LEAD_PROBABILITY.Companion.CANCEL
import com.gxsales.client.model.Constants.LEAD_PROBABILITY.Companion.CONVERTED
import com.gxsales.client.model.Constants.LEAD_PROBABILITY.Companion.PENDING
import com.gxsales.client.model.Constants.LEAD_STATUS.Companion.CONSIDERATION
import com.gxsales.client.model.Constants.LEAD_STATUS.Companion.JUNK
import com.gxsales.client.model.Constants.LEAD_STATUS.Companion.SCHEDULED
import com.gxsales.client.model.Extensions.Companion.formatDate
import com.gxsales.client.model.dataclass.response.Lead.DataLeadResponse

class ItemLeadAdapter(
    var data: MutableList<DataLeadResponse>
): RecyclerView.Adapter<ItemLeadAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: DataLeadResponse) = with(itemView){
            val binding = RvItemleadLayoutBinding.bind(itemView)
            binding.apply {
                tvLeadName.text = item.fullName
                tvLeadNumber.text = item.number
                tvLeadAddress.text = item.address
                tvLeadStatus.apply {
                    text = item.status!!.name
                    background = when(item.status.name){
                        SCHEDULED ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_bubble_bobble)
                        }
                        CONSIDERATION ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_sonic_blue)
                        }
                        JUNK ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_splendor_gold)
                        }
                        else -> null
                    }
                }
                tvleadPossibility.apply {
                    text = item.probability!!.name
                    background = when(item.probability.name){
                        PENDING ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_himawari_yellow)
                        }
                        CONVERTED ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_aztec_jade)
                        }
                        CANCEL ->{
                            ContextCompat.getDrawable(itemView.context, R.drawable.bg_rectangle_ginshu)
                        }
                        else -> null
                    }
                }
                tvLeadCreatedAt.text = item.createdAt!!.formatDate()
                tvLeadBranchOffice.text = item.branchOffice!!.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_itemlead_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position))
    }


}