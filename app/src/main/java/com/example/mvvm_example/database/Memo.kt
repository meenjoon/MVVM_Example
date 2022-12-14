package com.example.mvvm_example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Memo (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var memo: String,
    var editMode: Boolean
)
