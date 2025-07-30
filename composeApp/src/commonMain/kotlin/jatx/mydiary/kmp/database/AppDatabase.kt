package jatx.mydiary.kmp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.EntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        EntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}