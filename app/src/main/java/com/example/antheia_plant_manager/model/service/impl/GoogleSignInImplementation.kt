package com.example.antheia_plant_manager.model.service.impl

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.antheia_plant_manager.WEB_CLIENT_ID
import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleSignInImplementation: GoogleSignIn {
    private lateinit var credentialRequest: GetCredentialRequest

    override fun getCredentialRequest(): GetCredentialRequest {
        return credentialRequest
    }


    override fun credentialRequest() {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(
            serverClientId = WEB_CLIENT_ID
        ).build()

        credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
    }

}