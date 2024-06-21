package com.example.antheia_plant_manager.model.service.impl

import com.example.antheia_plant_manager.model.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    override fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override fun anonymousSignIn() {
        Firebase.auth.signInAnonymously()
    }

    override fun createAccount(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
    }

    override fun deleteAccount() {
        Firebase.auth.currentUser!!.delete()
    }

    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}