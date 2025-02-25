package com.dunglv.appmusic.ui.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel()

fun <T> MutableLiveData<List<T>>.toMutableList(transform: (T) -> T): MutableList<T> {
    return this.value?.map { transform(it) }?.toMutableList() ?: mutableListOf()
}

fun <T> LiveData<T>.observeWithCatch(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner) { value ->
        value?.let {
            kotlin.runCatching {
                observer.invoke(it)
            }.onFailure { it.printStackTrace() }
        }
    }
}