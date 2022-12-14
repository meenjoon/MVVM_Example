package com.example.mvvm_example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_example.database.Memo
import com.example.mvvm_example.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel(private val memoRepository: MemoRepository): ViewModel() {


    val isEdit = MutableLiveData<EditMemoPostData>()
    val isMemoInsertComplete = MutableLiveData<Long>()

    val isMemoDeleteComplete = MutableLiveData<Memo>()
    val isMemoDeleteByIdComplete = MutableLiveData<Memo>()
    val isMemoModifyComplete = MutableLiveData<EditMemoPostData>()

    val isGetAllMemoComplete = MutableLiveData<List<Memo>>()


    fun changeMode(memo: Memo, _isEdit: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            isEdit.postValue(EditMemoPostData(memo,memo.memo, _isEdit))
        }
    }

    fun insertMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.insertMemo(memo).let {
                id -> isMemoInsertComplete.postValue(id)
            }
        }
    }

    fun deleteMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemo(memo).let {
                isMemoDeleteComplete.postValue(memo)
            }
        }
    }

    fun deleteMemoById(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemoById(memo.id).let {
                isMemoDeleteByIdComplete.postValue(memo)
            }
        }
    }

    fun modifyMemo(memo: Memo, editMemo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            isMemoModifyComplete.postValue(EditMemoPostData(memo,editMemo,false))
        }
    }

    fun getAllMemo() {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.getAllMemo().let {
                isGetAllMemoComplete.postValue(it)
            }
        }
    }

    class EditMemoPostData(val memo: Memo, val editMemo: String, val isEdit: Boolean)

}

