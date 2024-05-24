package jatx.mydiarykmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jatx.mydiarykmp.data.db.AppDatabase
import jatx.mydiarykmp.data.db.getDatabase
import jatx.mydiarykmp.domain.models.Entry
import jatx.mydiarykmp.domain.usecase.DeleteByTypeUseCase
import jatx.mydiarykmp.domain.usecase.DeleteUseCase
import jatx.mydiarykmp.domain.usecase.GetAllUseCase
import jatx.mydiarykmp.domain.usecase.InsertUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

private val INSTANCE by lazy {
    MainViewModel()
}

fun viewModel() = INSTANCE

class MainViewModel(
    appDatabase: AppDatabase = getDatabase()
): ViewModel() {
    private val entryDao = appDatabase.entryDao()

    private val getAllUseCase = GetAllUseCase(entryDao)
    private val insertUseCase = InsertUseCase(entryDao)
    private val deleteUseCase = DeleteUseCase(entryDao)
    private val deleteByTypeUseCase = DeleteByTypeUseCase(entryDao)

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

    private var onLoadPermissionRequest: () -> Unit = {}
    private var onSavePermissionRequest: () -> Unit = {}
    private var onShowDateTimePicker: () -> Unit = {}

    private var getAllJob: Job? = null

    val loadFlow: Flow<Any?> = callbackFlow {
        onLoadPermissionRequest = {
            trySend(null)
        }
        awaitClose {
            onLoadPermissionRequest = {}
        }
    }

    val saveFlow: Flow<Any?> = callbackFlow {
        onSavePermissionRequest = {
            trySend(null)
        }
        awaitClose {
            onSavePermissionRequest = {}
        }
    }

    val dateTimePickerFlow: Flow<Any?> = callbackFlow {
        onShowDateTimePicker = {
            trySend(null)
        }
        awaitClose {
            onShowDateTimePicker = {}
        }
    }

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
        println("set current type: $type")
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


//    fun onLoadFromUri(uri: Uri) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    val sc = Scanner(appContext.contentResolver.openInputStream(uri))
//                    val backupDataStr = sc.nextLine()
//                    sc.close()
//                    val backupData = Gson().fromJson(backupDataStr, BackupData::class.java)
//                    Log.e("backup", backupData.toString())
//                    deleteAllUseCase.execute()
//                    insertReplaceListUseCase.execute(backupData.list)
//                    withContext(Dispatchers.Main) {
//                        toasts.showToast(R.string.toast_load_data_success)
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    withContext(Dispatchers.Main) {
//                        toasts.showToast(R.string.toast_some_error)
//                    }
//                }
//            }
//        }
//    }

//    fun onSavePermissionGranted() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val list = getAllSuspendUseCase.execute()
//                val backupData = BackupData(list)
//                val backupDataStr = Gson().toJson(backupData)
//                Log.e("backup", backupDataStr)
//                try {
//                    val dir = Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//                    dir.mkdirs()
//                    val uid = android.os.Process.myUid()
//                    val outFile = File(dir, "MyDiary_$uid.json.txt")
//                    val pw = PrintWriter(outFile)
//                    pw.println(backupDataStr)
//                    pw.flush()
//                    pw.close()
//                    Log.e("success", "saving")
//                    withContext(Dispatchers.Main) {
//                        toasts.showToast(R.string.toast_save_data_success)
//                    }
//                } catch (e: Exception) {
//                    Log.e("error", "saving", e)
//                    withContext(Dispatchers.Main) {
//                        toasts.showToast(R.string.toast_some_error)
//                    }
//                }
//            }
//        }
//    }

    fun loadData() {
        onLoadPermissionRequest()
    }

    fun saveData() {
        onSavePermissionRequest()
    }

    fun showDateTimePicker() {
        onShowDateTimePicker()
    }
}