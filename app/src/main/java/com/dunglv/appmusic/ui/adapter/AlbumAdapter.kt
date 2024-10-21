package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.dunglv.appmusic.data.model.Album
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemAlbumBinding

class AlbumAdapter : BaseBindingAdapter<Album, ItemAlbumBinding>(
    object : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickSongPlay: (result: Int, album: Album) -> Unit = { _, _ -> }
    var iClickNext: (result: Int, album: Album) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemAlbumBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    itemView.context
                    tvAlbum.text = name
                    tvSinger.text = nameSing
                    Glide.with(imAvatar.context)
                        .load(imageBitmap)
                        .into(imAvatar)
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
                binding.imNext.setOnSafeClick {
                    iClickNext(position, this@with)

                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_album
}