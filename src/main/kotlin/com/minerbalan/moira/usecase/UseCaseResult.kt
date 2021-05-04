package com.minerbalan.moira.usecase

/**
 * 処理が正常に終了したかどうかを格納する.
 * 実行時例外以外の検査例外を表したい際に使用する.
 */
interface UseCaseResult {
    fun isSuccess(): Boolean

    fun getErrorMessage(): String
}
