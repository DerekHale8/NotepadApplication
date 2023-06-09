package com.example.notepad2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(@PrimaryKey(autoGenerate = true) val id: Int,
                @ColumnInfo(name = "title")val title:String,
                @ColumnInfo(name = "text") val text:String)