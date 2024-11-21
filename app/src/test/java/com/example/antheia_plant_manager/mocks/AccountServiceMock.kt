package com.example.antheia_plant_manager.mocks

import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AccountServiceMock: AccountService {
    override val currentUserId: String = "user123"
    override fun currentUser(): Flow<FirebaseUser?> {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(idToken: String) {
    }

    override suspend fun signIn(email: String, password: String) {
        if(email != "a@gmail.com" || password != "123456") {
            throw FirebaseAuthInvalidCredentialsException("01", "Wrong usernameValue or password")
        }
    }

    override suspend fun signOutOfApp() {
    }

    override suspend fun anonymousSignIn() {

    }

    override suspend fun createAccount(email: String, password: String) {
    }

    override suspend fun deleteAccount() {
    }

    override suspend fun sendEmailVerification() {
        TODO("Not yet implemented")
    }

    override suspend fun sendPasswordReset() {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmail(newEmail: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUsername(newUsername: String) {
        TODO("Not yet implemented")
    }

    override fun isSignedIn(): Boolean {
        return true
    }
}