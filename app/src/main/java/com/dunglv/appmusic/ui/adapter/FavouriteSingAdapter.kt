package com.dunglv.appmusic.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemFavouriteSingBinding

class FavouriteSingAdapter : BaseBindingAdapter<Music, ItemFavouriteSingBinding>(
    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }) {
    var iClickSongPlay: (result: Int, music: Music) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int, music: Music) -> Unit = { _, _ -> }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolderBase(holder: BaseHolder<ItemFavouriteSingBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            val context = holder.itemView.context
            holder.apply {
                with(binding) {
                    tvSinger.text = nameSing
                    tvSong.text = currentList.size.toString() + context.getString(R.string.song)
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
                binding.imNext.setOnSafeClick {
                    iClickMenu(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_favourite_sing
}