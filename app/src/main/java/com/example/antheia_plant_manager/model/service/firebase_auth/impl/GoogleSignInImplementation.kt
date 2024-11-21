package com.example.antheia_plant_manager.model.service.firebase_auth.impl

import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.BuildConfig
import com.example.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

class GoogleSignInImplementation: GoogleSignIn {
    private lateinit var credentialRequest: GetCredentialRequest

    override fun getCredentialRequest(): GetCredentialRequest {
        return credentialRequest
    }


    override fun credentialRequest() {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(
            serverClientId = BuildConfig.WEB_CLIENT_ID
        ).build()

        credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
    }

}