package com.aslibayar.domain.usecase.auth

import com.aslibayar.domain.model.User
import com.aslibayar.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email boş olamaz"))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException("Şifre boş olamaz"))
        }
        return repository.signIn(email, password)
    }
} 