package jatx.mydiary.kmp.database

import androidx.room.Room
import androidx.room.RoomDatabase
import jatx.mydiary.kmp.MyApp

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appContext = MyApp.appContext
    val dbFile = MyApp.appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}