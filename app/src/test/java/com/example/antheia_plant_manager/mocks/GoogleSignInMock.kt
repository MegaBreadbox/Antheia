package com.example.antheia_plant_manager.mocks

import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn

class GoogleSignInMock: GoogleSignIn {
    override fun getCredentialRequest(): GetCredentialRequest {
        TODO("Not yet implemented")
    }

    override fun credentialRequest() {
        TODO("Not yet implemented")
    }
}