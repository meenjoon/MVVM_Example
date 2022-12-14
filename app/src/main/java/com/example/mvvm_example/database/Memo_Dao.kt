package com.example.mvvm_example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Memo_Dao {
    @Insert
    suspend fun insertMemo(memo: Memo): Long

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("DELETE FROM memo Where id = :id")
    suspend fun deleteMemoById(id: Long)

    @Query("SELECT * FROM Memo")
    suspend fun getAllMemo(): List<Memo>

    @Query("UPDATE Memo SET memo = :memo WHERE id = :id")
    suspend fun modifyMemo(id:Long, memo: String)
}
