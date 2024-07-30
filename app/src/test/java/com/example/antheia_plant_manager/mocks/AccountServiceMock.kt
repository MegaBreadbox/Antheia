package com.example.antheia_plant_manager.mocks

import com.example.antheia_plant_manager.model.service.AccountService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AccountServiceMock: AccountService {
    override suspend fun googleSignIn(idToken: String) {
    }

    override suspend fun signIn(email: String, password: String) {
        if(email != "a@gmail.com" || password != "123456") {
            throw FirebaseAuthInvalidCredentialsException("01", "Wrong username or password")
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

    override fun isSignedIn(): Boolean {
        return true
    }
}