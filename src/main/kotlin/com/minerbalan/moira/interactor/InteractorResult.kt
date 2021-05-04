package com.minerbalan.moira.interactor

import com.minerbalan.moira.usecase.UseCaseResult

class InteractorResult private constructor(private val success: Boolean, private val errorMessage: String) :
    UseCaseResult {

    override fun isSuccess(): Boolean {
        return success
    }

    override fun getErrorMessage(): String {
        return errorMessage
    }

    companion object {
        fun createSuccessResult(): InteractorResult {
            return InteractorResult(true, "")
        }

        fun createErrorResult(errorMessage: String): InteractorResult {
            return InteractorResult(false, errorMessage)
        }
    }

}
