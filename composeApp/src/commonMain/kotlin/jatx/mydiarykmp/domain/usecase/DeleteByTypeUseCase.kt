package jatx.mydiarykmp.domain.usecase

import jatx.mydiarykmp.data.db.dao.EntryDao

class DeleteByTypeUseCase(
    private val entryDao: EntryDao
) {
    suspend fun execute(type: Int) = entryDao
        .deleteByType(type)
}