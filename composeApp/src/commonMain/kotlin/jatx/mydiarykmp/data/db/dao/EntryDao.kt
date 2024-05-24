package jatx.mydiarykmp.data.db.dao

import androidx.room.*
import jatx.mydiarykmp.data.db.entity.EntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY time DESC")
    fun getAll(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries ORDER BY time DESC")
    suspend fun getAllSuspend(): List<EntryEntity>

    @Insert
    suspend fun insert(entryEntity: EntryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplaceList(list: List<EntryEntity>)

    @Delete
    suspend fun delete(entryEntity: EntryEntity)

    @Query("DELETE FROM entries WHERE type=:type")
    suspend fun deleteByType(type: Int)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}