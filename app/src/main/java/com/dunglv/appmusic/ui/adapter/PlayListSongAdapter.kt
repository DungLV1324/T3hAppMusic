package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemPlayListSongBinding

class PlayListSongAdapter : BaseBindingAdapter<ItemPlayList, ItemPlayListSongBinding>(

    object : DiffUtil.ItemCallback<ItemPlayList>() {
        override fun areItemsTheSame(oldItem: ItemPlayList, newItem: ItemPlayList) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemPlayList, newItem: ItemPlayList): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickSongPlay: (result: Int, itemPlayList: ItemPlayList) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int) -> Unit = {}

    override fun onBindViewHolderBase(
        holder: BaseHolder<ItemPlayListSongBinding>,
        position: Int
    ) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvName.text = name
                }

                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_play_list_song
}