@file:OptIn(ExperimentalTime::class)

package jatx.mydiary.kmp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import jatx.mydiary.kmp.domain.models.Entry
import jatx.mydiary.kmp.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Suppress("UNCHECKED_CAST")
class MainViewModelProviderFactory(
    private val getAllUseCase: GetAllUseCase,
    private val getAllSuspendUseCase: GetAllSuspendUseCase,
    private val insertUseCase: InsertUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val deleteByTypeUseCase: DeleteByTypeUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val insertReplaceListUseCase: InsertReplaceListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return MainViewModel(
            getAllUseCase = getAllUseCase,
            getAllSuspendUseCase = getAllSuspendUseCase,
            insertUseCase = insertUseCase,
            deleteUseCase = deleteUseCase,
            deleteByTypeUseCase = deleteByTypeUseCase,
            deleteAllUseCase = deleteAllUseCase,
            insertReplaceListUseCase = insertReplaceListUseCase
        ) as? T ?: throw IllegalStateException("cannot create view model")
    }
}

class MainViewModel(
    private val getAllUseCase: GetAllUseCase,
    private val getAllSuspendUseCase: GetAllSuspendUseCase,
    private val insertUseCase: InsertUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val deleteByTypeUseCase: DeleteByTypeUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val insertReplaceListUseCase: InsertReplaceListUseCase
): ViewModel() {

    private val _entries = MutableStateFlow(listOf<Entry>())
    val entries = _entries.asStateFlow()

    private val _currentType = MutableStateFlow(-1)
    val currentType = _currentType.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private val _entryToDelete = MutableStateFlow<Entry?>(null)
    val entryToDelete = _entryToDelete.asStateFlow()

    private val _showDeleteByTypeDialog = MutableStateFlow(false)
    val showDeleteByTypeDialog = _showDeleteByTypeDialog.asStateFlow()

    private val _typeToDelete = MutableStateFlow(-1)
    val typeToDelete = _typeToDelete.asStateFlow()

    private var _typeForEntry = MutableStateFlow(-1)
    private val typeForEntry = _typeForEntry.asStateFlow()

    private var getAllJob: Job? = null

    private val _loadChannel = Channel<Unit>()
    val loadChannel: ReceiveChannel<Unit> = _loadChannel

    private val _saveChannel = Channel<Unit>()
    val saveChannel: ReceiveChannel<Unit> = _saveChannel

    private val _showDateTimePickerChannel = Channel<Unit>()
    val showDateTimePickerChannel: ReceiveChannel<Unit> = _showDateTimePickerChannel

    fun init() {
        getAllJob?.let {
            if (!it.isCancelled) it.cancel()
        }
        getAllJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAllUseCase
                    .execute()
                    .combine(currentType) { list, type ->
                        if (type == -1) {
                            list
                        } else {
                            list.filter { it.type == type }
                        }
                    }
                    .collect {
                        withContext(Dispatchers.Main) {
                            _entries.value = it
                        }
                    }
            }
        }
    }

    fun setCurrentType(type: Int) {
        if (currentType.value != type) {
            _currentType.value = type
        } else {
            _currentType.value = -1
        }
    }

    fun setTypeForEntry(type: Int) {
        _typeForEntry.value = type
    }

    fun createEntry() {
        val entry = Entry(
            type = typeForEntry.value,
            time = Clock.System.now().toEpochMilliseconds()
        )
        viewModelScope.launch {
            insertUseCase.execute(entry)
        }
    }

    fun createEntry(time: Long) {
        val entry = Entry(
            type = typeForEntry.value,
            time = time
        )
        viewModelScope.launch {
            insertUseCase.execute(entry)
        }
    }

    fun deleteEntry() {
        entryToDelete.value?.let {
            viewModelScope.launch {
                deleteUseCase.execute(it)
            }
        }
    }

    fun deleteByType() {
        viewModelScope.launch {
            deleteByTypeUseCase.execute(typeToDelete.value)
        }
    }

    fun setEntryToDelete(entry: Entry?) {
        _entryToDelete.value = entry
    }

    fun setShowDeleteDialog(value: Boolean) {
        _showDeleteDialog.value = value
    }

    fun setTypeToDelete(type: Int) {
        _typeToDelete.value = type
    }

    fun setShowDeleteByTypeDialog(value: Boolean) {
        _showDeleteByTypeDialog.value = value
    }

    fun loadData() {
        _loadChannel.trySend(Unit)
    }

    fun saveData() {
        _saveChannel.trySend(Unit)
    }

    fun showDateTimePicker() {
        _showDateTimePickerChannel.trySend(Unit)
    }
}