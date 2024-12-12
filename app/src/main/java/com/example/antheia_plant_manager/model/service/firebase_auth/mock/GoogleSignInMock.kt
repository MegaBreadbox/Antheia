package com.example.antheia_plant_manager.model.service.firebase_auth.mock

import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn

class GoogleSignInMock: GoogleSignIn {
    override fun getCredentialRequest(): GetCredentialRequest {
        TODO("Not yet implemented")
    }

    override fun credentialRequest() {
    }
}