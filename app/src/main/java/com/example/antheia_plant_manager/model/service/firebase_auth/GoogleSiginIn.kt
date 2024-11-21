package com.example.antheia_plant_manager.model.service.firebase_auth

import androidx.credentials.GetCredentialRequest

interface GoogleSignIn {
    fun getCredentialRequest(): GetCredentialRequest

    fun credentialRequest()
}