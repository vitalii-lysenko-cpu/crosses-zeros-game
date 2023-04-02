package com.example.crosses_zeros.functionality

import com.example.crosses_zeros.functionality.abstractions.Repository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
   private val repository: Repository
){
    operator fun invoke() =
        repository.checkConnection()
}