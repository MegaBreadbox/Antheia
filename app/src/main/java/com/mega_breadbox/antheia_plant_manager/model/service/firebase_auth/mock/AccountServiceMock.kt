package com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.mock

import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AccountServiceMock: AccountService {
    override val currentUserId: String
        get() = TODO("Not yet implemented")

    override fun currentUser(): Flow<FirebaseUser?> {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(idToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun googleLinkAccount(googleIdToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun emailLinkAccount(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signOutOfApp() {
        TODO("Not yet implemented")
    }

    override suspend fun anonymousSignIn() {
        TODO("Not yet implemented")
    }

    override suspend fun createAccount(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
}