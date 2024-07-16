package com.example.antheia_plant_manager.welcome_screen.mocks

import com.example.antheia_plant_manager.model.service.AccountService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AccountServiceMock: AccountService {
    override suspend fun googleSignIn(idToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) {
        if(email != "a@gmail.com" || password != "123456") {
            throw FirebaseAuthInvalidCredentialsException("01", "Wrong username or password")
        }
    }

    override suspend fun signOutOfApp() {
        TODO("Not yet implemented")
    }

    override suspend fun anonymousSignIn() {

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