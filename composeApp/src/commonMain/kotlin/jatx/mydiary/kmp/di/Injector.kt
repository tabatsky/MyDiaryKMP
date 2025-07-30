package jatx.mydiary.kmp.di

import androidx.compose.runtime.Composable
import jatx.mydiary.kmp.domain.usecase.DeleteAllUseCase
import jatx.mydiary.kmp.domain.usecase.DeleteByTypeUseCase
import jatx.mydiary.kmp.domain.usecase.DeleteUseCase
import jatx.mydiary.kmp.domain.usecase.GetAllSuspendUseCase
import jatx.mydiary.kmp.domain.usecase.GetAllUseCase
import jatx.mydiary.kmp.domain.usecase.InsertReplaceListUseCase
import jatx.mydiary.kmp.domain.usecase.InsertUseCase
import jatx.mydiary.kmp.presentation.main.MainViewModel

interface Injector {
    val deleteAllUseCase: DeleteAllUseCase
    val deleteByTypeUseCase: DeleteByTypeUseCase
    val deleteUseCase: DeleteUseCase
    val getAllSuspendUseCase: GetAllSuspendUseCase
    val getAllUseCase: GetAllUseCase
    val insertReplaceListUseCase: InsertReplaceListUseCase
    val insertUseCase: InsertUseCase
    @Composable
    fun mainViewModel(): MainViewModel
}