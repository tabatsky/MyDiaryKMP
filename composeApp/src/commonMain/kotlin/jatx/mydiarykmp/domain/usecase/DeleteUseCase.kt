package jatx.mydiarykmp.domain.usecase

import jatx.mydiarykmp.data.db.dao.EntryDao
import jatx.mydiarykmp.data.db.entity.toEntryEntity
import jatx.mydiarykmp.domain.models.Entry

class DeleteUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(entry: Entry) = entryDao
        .delete(entry.toEntryEntity())
}