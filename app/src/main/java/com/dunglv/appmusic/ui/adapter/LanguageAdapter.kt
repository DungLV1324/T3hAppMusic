package com.dunglv.appmusic.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.dunglv.appmusic.data.model.Language
import com.dunglv.appmusic.ui.base.BaseBindingAdapter
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ItemLanguageBinding

class LanguageAdapter : BaseBindingAdapter<Language, ItemLanguageBinding>(
    object : DiffUtil.ItemCallback<Language>() {
        override fun areItemsTheSame(oldItem: Language, newItem: Language) =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem == newItem
        }
    }) {

    var iClickItem: (language :Language, res: Int) -> Unit = { _, _ -> }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemLanguageBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.apply {
                with(binding) {
                    itemView.context
                    tvLanguage.text = name
                    Glide.with(imAvatar.context)
                        .load(image)
                        .into(imAvatar)
                }

                itemView.setOnSafeClick {
                    iClickItem(this@with, position)
                }

                holder.binding.imSelect.setImageResource(if (isSelect) R.drawable.ic_tronlam else R.drawable.ic_tronx)
            }
        }
    }

    override val layoutIdItem = R.layout.item_language
}