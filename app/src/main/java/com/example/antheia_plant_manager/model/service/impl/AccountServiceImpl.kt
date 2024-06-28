package com.example.antheia_plant_manager.model.service.impl

import com.example.antheia_plant_manager.model.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOutOfApp() {
        Firebase.auth.signOut()
    }

    override suspend fun anonymousSignIn() {
        Firebase.auth.signInAnonymously().await()
    }

    override suspend fun createAccount(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}