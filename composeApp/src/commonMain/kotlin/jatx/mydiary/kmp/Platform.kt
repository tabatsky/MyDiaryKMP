package jatx.mydiary.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform