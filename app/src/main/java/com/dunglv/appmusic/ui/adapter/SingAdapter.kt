package com.dunglv.appmusic.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Singer
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemSingerBinding

class SingAdapter : BaseBindingAdapter<Singer, ItemSingerBinding>(

    object : DiffUtil.ItemCallback<Singer>() {
        override fun areItemsTheSame(oldItem: Singer, newItem: Singer)= oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Singer, newItem: Singer): Boolean {
            return oldItem==newItem
        }
    }) {
    var iClickSongPlay: (result: Int,singer : Singer) -> Unit = { _, _->}

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolderBase(
        holder: BaseHolder<ItemSingerBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvNameSing.text = nameSinger
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_singer
}