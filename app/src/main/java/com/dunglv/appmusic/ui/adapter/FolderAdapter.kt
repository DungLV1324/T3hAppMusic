package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dunglv.appmusic.data.model.Folder
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemFolderBinding

class FolderAdapter : BaseBindingAdapter<Folder, ItemFolderBinding>(

    object : DiffUtil.ItemCallback<Folder>() {
        override fun areItemsTheSame(oldItem: Folder, newItem: Folder)= oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem==newItem
        }
    }) {

    var iClickSongPlay: (result: Int,folder: Folder) -> Unit = { _, _->}

    override fun onBindViewHolderBase(holder: BaseHolder<ItemFolderBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    tvFolderName.text = nameFolder
                }
                itemView.setOnSafeClick {
                    iClickSongPlay(position, this@with)
                }
            }
        }
    }

    override val layoutIdItem = R.layout.item_folder
}