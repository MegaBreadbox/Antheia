package service.firebase_auth.impl

import com.google.firebase.auth.FirebaseUser
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    override val currentUserId: String
        get() = ""

    override fun currentUser(): Flow<FirebaseUser?> {
        return emptyFlow()
    }

    override suspend fun googleSignIn(idToken: String) {
    }

    override suspend fun googleLinkAccount(googleIdToken: String) {
    }

    override suspend fun emailLinkAccount(email: String, password: String) {
    }

    override suspend fun signIn(email: String, password: String) {
    }

    override suspend fun signOutOfApp() {
    }

    override suspend fun anonymousSignIn() {
    }

    override suspend fun createAccount(email: String, password: String) {
    }

    override suspend fun deleteAccount() {
    }

    override suspend fun sendEmailVerification() {
    }

    override suspend fun sendPasswordReset() {
    }

    override suspend fun sendPasswordReset(email: String) {
    }

    override suspend fun updateEmail(newEmail: String) {
    }

    override suspend fun updateUsername(newUsername: String) {
    }

    override fun isSignedIn(): Boolean {
        return false
    }
}