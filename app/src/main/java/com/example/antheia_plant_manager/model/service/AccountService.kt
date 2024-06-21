package com.example.antheia_plant_manager.model.service

interface AccountService {
    fun signIn(email: String, password: String)
    fun signOut()
    fun anonymousSignIn()
    fun createAccount(email: String, password: String)
    fun deleteAccount()
    fun isSignedIn(): Boolean
}