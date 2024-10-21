package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemFavouriteSongBinding

class FavouriteSongAdapter : BaseBindingAdapter<Music, ItemFavouriteSongBinding>(

    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickSongPlay: (result: Int, heart: Music) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int, heart: Music) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemFavouriteSongBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvSong.text = nameSong
                    tvAlbum.text = album
                    tvSinger.text = nameSing

                }
                binding.imMenuSong.setOnSafeClick {
                    iClickMenu(position, this@with)
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_favourite_song
}