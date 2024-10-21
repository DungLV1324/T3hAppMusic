package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemRecentlySongBinding

class RecentlyAdapter : BaseBindingAdapter<Music, ItemRecentlySongBinding>(

    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music)= oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem==newItem
        }
    }) {
    var iClickSongPlay: (result: Int,music: Music) ->Unit = { _, _->}
    var iClickDelete: (result: Int, music : Music) -> Unit = { _, _->}

    override fun onBindViewHolderBase(holder: BaseHolder<ItemRecentlySongBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                binding.apply {
                    tvSong.text = nameSong
                    tvAlbum.text = album
                    tvSinger.text = nameSing
                }

                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }

                binding.imMenuSong.setOnSafeClick {
                    iClickDelete(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_recently_song
}