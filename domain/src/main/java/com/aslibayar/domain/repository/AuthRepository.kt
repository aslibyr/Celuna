package com.aslibayar.domain.repository

import com.aslibayar.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String, displayName: String): Result<User>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun updateProfile(displayName: String? = null, photoUrl: String? = null): Result<Unit>
} 