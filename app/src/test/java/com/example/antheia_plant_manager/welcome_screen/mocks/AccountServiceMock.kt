package com.example.antheia_plant_manager.welcome_screen.mocks

import com.example.antheia_plant_manager.model.service.AccountService

class AccountServiceMock: AccountService {
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