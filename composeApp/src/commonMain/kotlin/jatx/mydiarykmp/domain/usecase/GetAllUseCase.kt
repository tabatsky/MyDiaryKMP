package jatx.mydiarykmp.domain.usecase

import jatx.mydiarykmp.data.db.dao.EntryDao
import jatx.mydiarykmp.data.db.entity.toEntry
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