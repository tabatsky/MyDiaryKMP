package jatx.mydiary.kmp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import jatx.mydiary.kmp.MyApp

actual object AppDatabaseCtor: RoomDatabaseConstructor<AppDatabase> {
    actual override fun initialize(): AppDatabase = getRoomDatabase(getDatabaseBuilder(MyApp.appContext))
}

fun getDatabaseBuilder(appContext: Context): RoomDatabase.Builder<AppDatabase> {
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}