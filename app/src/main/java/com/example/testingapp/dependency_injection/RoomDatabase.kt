package com.example.testingapp.dependency_injection

import androidx.room.Database
import com.example.testingapp.database.Result
import com.example.testingapp.database.ResultDao

@Database(entities = [Result::class], version = 1, exportSchema = false)

 abstract class RoomDatabase : androidx.room.RoomDatabase() {

 abstract fun resultDao(): ResultDao

}