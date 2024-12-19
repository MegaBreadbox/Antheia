package com.example.antheia_plant_manager.model.service.firebase_auth.impl

import android.util.Log
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class AccountServiceImpl @Inject constructor(): AccountService {

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty().also { Log.d("currentUserId", "${isSignedIn()}") }

    override fun currentUser(): Flow<FirebaseUser?> = callbackFlow {
        val authTokenListener = FirebaseAuth.IdTokenListener { auth ->
            trySend(auth.currentUser)
        }
        trySend(Firebase.auth.currentUser)
        Firebase.auth.addIdTokenListener(authTokenListener)
        awaitClose {
            Firebase.auth.removeIdTokenListener(authTokenListener)
        }
    }

    override suspend fun googleSignIn(idToken: String) {
        Firebase.auth.signInWithCredential(
            GoogleAuthProvider.getCredential(idToken, null)
        ).await()

    }

    override suspend fun googleLinkAccount(googleIdToken: String) {
        val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
        Firebase.auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun emailLinkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOutOfApp() {
        if(Firebase.auth.currentUser!!.isAnonymous) {
            Firebase.auth.currentUser!!.delete().await()
        }
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

    override suspend fun updateUsername(newUsername: String) {
        Firebase.auth.currentUser!!.updateProfile(
            userProfileChangeRequest {
                displayName = newUsername
            }
        ).await()
    }


    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}