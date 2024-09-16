package com.example.antheia_plant_manager.model.service.mock

import com.example.antheia_plant_manager.model.service.AccountService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AccountServiceMock: AccountService {
    override val currentUserId: String
        get() = TODO("Not yet implemented")

    override suspend fun googleSignIn(idToken: String) {
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

    override fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }
}