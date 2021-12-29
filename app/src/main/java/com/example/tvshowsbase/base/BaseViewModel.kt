package com.example.tvshowsbase.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val isLoading = ObservableBoolean(false)
    private var actualWorks = 0

    fun addWork() {
        actualWorks += 1
        checkWorks()
    }

    fun removeWork() {
        if (actualWorks > 0) actualWorks -= 1
        checkWorks()
    }

    fun checkWorks() {
        isLoading.set(actualWorks > 0)
    }
}
