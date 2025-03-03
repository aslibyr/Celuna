package com.aslibayar.data.repository

import com.aslibayar.domain.model.User
import com.aslibayar.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                firestore.collection("users")
                    .document(auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        trySend(document.toObject(User::class.java))
                    }
            } else {
                trySend(null)
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signIn(email: String, password: String): Result<User> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("Kullanıcı bulunamadı")
        val userData = firestore.collection("users")
            .document(user.uid)
            .get()
            .await()
            .toObject(User::class.java) ?: throw Exception("Kullanıcı verisi bulunamadı")
        Result.success(userData)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String
    ): Result<User> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("Kullanıcı oluşturulamadı")

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        user.updateProfile(profileUpdates).await()

        val newUser = User(
            id = user.uid,
            email = email,
            displayName = displayName
        )

        firestore.collection("users")
            .document(user.uid)
            .set(newUser)
            .await()

        Result.success(newUser)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun resetPassword(email: String): Result<Unit> = try {
        auth.sendPasswordResetEmail(email).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signOut(): Result<Unit> = try {
        auth.signOut()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateProfile(displayName: String?, photoUrl: String?): Result<Unit> =
        try {
            val user = auth.currentUser ?: throw Exception("Kullanıcı bulunamadı")
            val profileUpdates = UserProfileChangeRequest.Builder().apply {
                displayName?.let { setDisplayName(it) }
                photoUrl?.let { setPhotoUri(android.net.Uri.parse(it)) }
            }.build()

            user.updateProfile(profileUpdates).await()

            val updates = mutableMapOf<String, Any>()
            displayName?.let { updates["displayName"] = it }
            photoUrl?.let { updates["photoUrl"] = it }

            if (updates.isNotEmpty()) {
                firestore.collection("users")
                    .document(user.uid)
                    .update(updates)
                    .await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
} 