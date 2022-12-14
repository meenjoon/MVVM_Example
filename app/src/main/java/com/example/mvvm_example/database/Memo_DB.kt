package com.example.mvvm_example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1)
abstract class Memo_DB: RoomDatabase() {
    abstract fun memoDao(): Memo_Dao


}
