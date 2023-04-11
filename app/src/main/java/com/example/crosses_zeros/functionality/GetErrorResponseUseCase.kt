package com.example.crosses_zeros.functionality

import com.example.crosses_zeros.functionality.abstractions.Repository
import javax.inject.Inject

class GetErrorResponseUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke() =
        repository.getErrorResponse()
}