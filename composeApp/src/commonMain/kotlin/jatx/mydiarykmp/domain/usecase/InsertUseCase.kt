package jatx.mydiarykmp.domain.usecase

import jatx.mydiarykmp.data.db.dao.EntryDao
import jatx.mydiarykmp.data.db.entity.toEntryEntity
import jatx.mydiarykmp.domain.models.Entry

class InsertUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(entry: Entry) = entryDao
        .insert(entry.toEntryEntity())
}