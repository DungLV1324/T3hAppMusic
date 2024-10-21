package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemFavouriteAlbumBinding

class PlayListAdapter : BaseBindingAdapter<ItemPlayList, ItemFavouriteAlbumBinding>(

    object : DiffUtil.ItemCallback<ItemPlayList>() {
        override fun areItemsTheSame(oldItem: ItemPlayList, newItem: ItemPlayList) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemPlayList, newItem: ItemPlayList): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickSongPlay: (result: Int, itemPlayList: ItemPlayList) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int, music: ItemPlayList) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(
        holder: BaseHolder<ItemFavouriteAlbumBinding>,
        position: Int
    ) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvName.text = name
                    imAvatar.setImageResource(R.drawable.ic_mi_music)
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_favourite_album
}