package jatx.mydiary.kmp.domain.usecase

import jatx.mydiary.kmp.database.dao.EntryDao
import jatx.mydiary.kmp.database.entity.toEntry
import kotlinx.coroutines.flow.map

class GetAllUseCase(
    private val entryDao: EntryDao
) {
    fun execute() = entryDao
        .getAll()
        .map { list ->
            list.map { it.toEntry() }
        }
}