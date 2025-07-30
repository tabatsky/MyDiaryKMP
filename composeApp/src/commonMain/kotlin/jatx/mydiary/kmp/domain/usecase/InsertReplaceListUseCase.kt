package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.toEntryEntity
import jatx.mydiary.kmp.domain.models.Entry

class InsertReplaceListUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(list: List<Entry>) = entryDao
        .insertReplaceList(list.map { it.toEntryEntity() })
}