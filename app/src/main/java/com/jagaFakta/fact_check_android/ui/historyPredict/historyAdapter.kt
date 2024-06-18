package com.jagaFakta.fact_check_android.ui.historyPredict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.HistoryRecycleViewBinding
import com.jagaFakta.fact_check_android.network.response.History

class historyAdapter (private val histori: List<History>): RecyclerView.Adapter<historyAdapter.HistoriHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallBack
//    private lateinit var onItemLongClickCallback: OnItemLongClickCallBack

//    interface OnItemLongClickCallBack{
//        fun onLongClick(data: History)
//    }
    interface OnItemClickCallBack {
        fun onItemClicked(data: History  )
    }
    fun setOnItemClickCallback(onItemClickCallback: historyAdapter.OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }
    inner class HistoriHolder (private val binding: HistoryRecycleViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: History){

            var tgl = data.createdAt.substring(0,10)

            binding.tvPredict.text = data.text
            binding.tvResult.text = data.result
            binding.tvCreated.text = tgl
            if (data.result == "Terindikasi hoax"){
                binding.imageView2.setImageResource(R.drawable.icon_silang)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriHolder {
        return  HistoriHolder( HistoryRecycleViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = histori.size

    override fun onBindViewHolder(holder: HistoriHolder, position: Int) {
        var story = histori[position]
        holder.bind(story)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(story) }
//        holder.itemView.setOnLongClickListener {
//            onItemLongClickCallback.onLongClick(story)
//            true
//        }
    }
}