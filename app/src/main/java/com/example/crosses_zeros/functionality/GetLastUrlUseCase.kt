package com.example.crosses_zeros.functionality

import com.example.crosses_zeros.functionality.abstractions.Repository
import javax.inject.Inject

class GetLastUrlUseCase
@Inject
constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke() {

    }
//        repository.getLastUrl()
}
