package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.toEntryEntity
import jatx.mydiary.kmp.domain.models.Entry

class InsertUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(entry: Entry) = entryDao
        .insert(entry.toEntryEntity())
}