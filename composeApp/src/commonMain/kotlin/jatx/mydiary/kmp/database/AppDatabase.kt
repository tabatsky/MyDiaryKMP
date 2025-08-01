package jatx.mydiary.kmp.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.EntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@ConstructedBy(AppDatabaseCtor::class)
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

expect object AppDatabaseCtor: RoomDatabaseConstructor<AppDatabase>

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}