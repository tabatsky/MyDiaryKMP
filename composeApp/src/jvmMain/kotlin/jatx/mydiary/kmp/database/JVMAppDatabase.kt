package jatx.mydiary.kmp.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual object AppDatabaseCtor: RoomDatabaseConstructor<AppDatabase> {
    actual override fun initialize(): AppDatabase = getRoomDatabase(getDatabaseBuilder())
}

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(".", "MyDiary.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
}