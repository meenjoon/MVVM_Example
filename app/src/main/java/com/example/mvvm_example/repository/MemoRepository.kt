package com.example.mvvm_example.repository

import com.example.mvvm_example.database.Global_Memo_DB
import com.example.mvvm_example.database.Memo

class MemoRepository {
    private val appDBInstance = Global_Memo_DB.appDataBaseInstance.memoDao()

    suspend fun insertMemo(memo: Memo) = appDBInstance.insertMemo(memo)
    suspend fun deleteMemo(memo: Memo) = appDBInstance.deleteMemo(memo)
    suspend fun deleteMemoById(id: Long) = appDBInstance.deleteMemoById(id)
    suspend fun getAllMemo() = appDBInstance.getAllMemo()
    suspend fun modifyMemo(id: Long, memo: String) = appDBInstance.modifyMemo(id, memo)
}
