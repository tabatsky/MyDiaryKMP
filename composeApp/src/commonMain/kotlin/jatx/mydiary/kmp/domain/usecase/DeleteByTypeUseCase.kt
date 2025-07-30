package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao

class DeleteByTypeUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(type: Int) = entryDao
        .deleteByType(type)
}