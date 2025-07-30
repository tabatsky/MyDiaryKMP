package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao

class DeleteAllUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute() = entryDao
        .deleteAll()
}