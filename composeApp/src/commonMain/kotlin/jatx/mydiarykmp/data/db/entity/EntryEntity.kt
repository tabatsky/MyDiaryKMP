package jatx.mydiarykmp.data.db.entity

import jatx.mydiarykmp.domain.models.Entry
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey
    val id: Long? = null,
    val type: Int,
    val time: Long
)

fun Entry.toEntryEntity() = EntryEntity(
    id = id,
    type = type,
    time = time
)

fun EntryEntity.toEntry() = Entry(
    id = id,
    type = type,
    time = time
)