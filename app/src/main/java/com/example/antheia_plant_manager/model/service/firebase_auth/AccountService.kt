package com.example.antheia_plant_manager.model.service.firebase_auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    fun currentUser(): Flow<FirebaseUser?>
    suspend fun googleSignIn(idToken: String)
    suspend fun googleLinkAccount(googleIdToken: String)
    suspend fun emailLinkAccount(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOutOfApp()
    suspend fun anonymousSignIn()
    suspend fun createAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun sendEmailVerification()
    suspend fun sendPasswordReset()
    suspend fun updateEmail(newEmail: String)
    suspend fun updateUsername(newUsername: String)
    fun isSignedIn(): Boolean
}