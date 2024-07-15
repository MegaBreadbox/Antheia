package com.example.antheia_plant_manager.model.service.impl

import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.WEB_CLIENT_ID
import com.example.antheia_plant_manager.model.service.AccountService
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.oAuthCredential
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class AccountServiceImpl @Inject constructor(): AccountService {
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

    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}