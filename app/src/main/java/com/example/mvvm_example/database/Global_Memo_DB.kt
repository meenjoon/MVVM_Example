package com.example.mvvm_example.database

import android.app.Application
import androidx.room.Room

class Global_Memo_DB: Application() {
    companion object{
        lateinit var appInstance: Global_Memo_DB
            private set

        lateinit var appDataBaseInstance: Memo_DB
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        appDataBaseInstance = Room.databaseBuilder(
            appInstance.applicationContext,
            Memo_DB::class.java, "memo.db"
        )
            .fallbackToDestructiveMigration() // DB version 달라졌을 경우 데이터베이스 초기화
            .allowMainThreadQueries() // 메인 스레드에서 접근 허용
            .build()
    }
}
