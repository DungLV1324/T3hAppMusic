package com.dunglv.appmusic.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemDetailPlayListBinding

class DetailPlayListAdapter : BaseBindingAdapter<Music, ItemDetailPlayListBinding>(
    object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickSongPlay: (result: Int, music: Music) -> Unit = { _, _ -> }
    var iClickMenu: (result: Int, music: Music) -> Unit = { _, _ -> }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolderBase(holder: BaseHolder<ItemDetailPlayListBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
           holder.apply {
               with(binding) {
                   tvSong.text = nameSong
                   tvAlbum.text = album
                   tvSinger.text = nameSing
                   tvNumber.text = (position + 1).toString()
                   imAvatar.setImageBitmap(imageBitmap)
               }
               itemView.setOnSafeClick {
                   iClickSongPlay(position, this@with)
               }
               binding.imShare.setOnSafeClick {
                   iClickMenu(position, this@with)
               }
           }
        }
    }

    override val layoutIdItem = R.layout.item_detail_play_list
}