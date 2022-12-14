package com.example.mvvm_example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_example.repository.MemoRepository

class MemoViewModelFactory(private val memoRepository: MemoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MemoRepository::class.java).newInstance(memoRepository)
    }
}
