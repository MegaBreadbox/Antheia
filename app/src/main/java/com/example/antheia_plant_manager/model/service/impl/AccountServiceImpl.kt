package com.example.antheia_plant_manager.model.service.impl

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.model.service.AccountService
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.oAuthCredential
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class AccountServiceImpl @Inject constructor(): AccountService {

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override suspend fun googleSignIn(idToken: String) {
        Firebase.auth.signInWithCredential(
            GoogleAuthProvider.getCredential(idToken, null)
        ).await()

    }

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

    override suspend fun sendEmailVerification() {
        //TODO("Not yet implemented")
    }

    override suspend fun sendPasswordReset() {
        Firebase.auth.sendPasswordResetEmail(Firebase.auth.currentUser!!.email!!).await()
    }

    override suspend fun updateEmail(newEmail: String) {
        Firebase.auth.currentUser!!.verifyBeforeUpdateEmail(newEmail).await()
    }


    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}