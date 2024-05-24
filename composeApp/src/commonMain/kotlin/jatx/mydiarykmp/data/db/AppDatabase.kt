package jatx.mydiarykmp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jatx.mydiarykmp.data.db.dao.EntryDao
import jatx.mydiarykmp.data.db.entity.EntryEntity

@Database(
    entities = [
        EntryEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}

expect fun getDatabase(): AppDatabase