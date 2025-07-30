package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.toEntry

class GetAllSuspendUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute() = entryDao
        .getAllSuspend()
        .map { it.toEntry() }
}