package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemAddAllBinding

class TypeHeartAddAdapter : BaseBindingAdapter<Music, ItemAddAllBinding>(

    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickAdd: (result: Int, music: Music) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemAddAllBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvName.text = nameSong
                    tvAlbum.text = album
                    tvSinger.text = nameSing
                }
                binding.imAdd.setOnSafeClick {
                    iClickAdd(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_add_all
}