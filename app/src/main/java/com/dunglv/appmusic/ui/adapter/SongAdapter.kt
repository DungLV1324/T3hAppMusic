package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemSongBinding

class SongAdapter : BaseBindingAdapter<Music, ItemSongBinding>(
    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music) = oldItem.uri == newItem.uri
        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem && oldItem.imageBitmap.hashCode() == newItem.imageBitmap.hashCode()
        }
    }) {

    var iClickSongPlay: (result: Int, music: Music) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int, music: Music) -> Unit = { _, _ -> }
    var iClickSearch: (result: Int, music: Music) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemSongBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                binding.apply {
                    songPosition = position
                    tvSong.text = nameSong
                    tvAlbum.text = album
                    tvSinger.text = nameSing

                    Glide.with(imAvatar.context)
                        .load(imageBitmap)
                        .into(imAvatar)

                    imAdd.setOnSafeClick {
                        iClickMenu(position, this@with)
                    }
                    imShare.setOnSafeClick {
                        iClickSearch(position, this@with)
                    }
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_song
}