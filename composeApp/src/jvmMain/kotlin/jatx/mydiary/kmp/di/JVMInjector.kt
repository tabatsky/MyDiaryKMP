package jatx.mydiary.kmp.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import jatx.mydiary.kmp.database.getDatabaseBuilder
import jatx.mydiary.kmp.database.getRoomDatabase
import jatx.mydiary.kmp.domain.usecase.DeleteAllUseCase
import jatx.mydiary.kmp.domain.usecase.DeleteByTypeUseCase
import jatx.mydiary.kmp.domain.usecase.DeleteUseCase
import jatx.mydiary.kmp.domain.usecase.GetAllSuspendUseCase
import jatx.mydiary.kmp.domain.usecase.GetAllUseCase
import jatx.mydiary.kmp.domain.usecase.InsertReplaceListUseCase
import jatx.mydiary.kmp.domain.usecase.InsertUseCase
import jatx.mydiary.kmp.presentation.main.MainViewModel
import jatx.mydiary.kmp.presentation.main.MainViewModelProviderFactory

class JVMInjector: Injector {
    private val appDatabase = getRoomDatabase(getDatabaseBuilder())
    private val entryDao = appDatabase.entryDao()

    override val deleteAllUseCase = DeleteAllUseCase(entryDao)
    override val deleteByTypeUseCase = DeleteByTypeUseCase(entryDao)
    override val deleteUseCase = DeleteUseCase(entryDao)
    override val getAllSuspendUseCase = GetAllSuspendUseCase(entryDao)
    override val getAllUseCase = GetAllUseCase(entryDao)
    override val insertReplaceListUseCase = InsertReplaceListUseCase(entryDao)
    override val insertUseCase = InsertUseCase(entryDao)

    private val factory = MainViewModelProviderFactory(
        getAllUseCase = getAllUseCase,
        getAllSuspendUseCase = getAllSuspendUseCase,
        insertUseCase = insertUseCase,
        deleteUseCase = deleteUseCase,
        deleteByTypeUseCase = deleteByTypeUseCase,
        deleteAllUseCase = deleteAllUseCase,
        insertReplaceListUseCase = insertReplaceListUseCase
    )

    @Composable
    override fun mainViewModel() = viewModel<MainViewModel>(factory = factory)
}