package com.example.antheia_plant_manager.model.service

import android.content.Context
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse

interface GoogleSignIn {
    fun getCredentialRequest(): GetCredentialRequest

    fun credentialRequest()
}