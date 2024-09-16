package com.example.antheia_plant_manager.model.service

interface AccountService {
    val currentUserId: String
    suspend fun googleSignIn(idToken: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOutOfApp()
    suspend fun anonymousSignIn()
    suspend fun createAccount(email: String, password: String)
    suspend fun deleteAccount()
    fun isSignedIn(): Boolean
}