package jatx.mydiary.kmp.navigation

sealed interface ScreenVariant {
    object MainScreenVariant: ScreenVariant
    object AuthScreenVariant: ScreenVariant
}