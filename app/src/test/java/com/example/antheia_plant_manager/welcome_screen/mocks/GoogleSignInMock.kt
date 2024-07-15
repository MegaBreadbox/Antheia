package com.example.antheia_plant_manager.welcome_screen.mocks

import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.model.service.GoogleSignIn

class GoogleSignInMock: GoogleSignIn {
    override fun getCredentialRequest(): GetCredentialRequest {
        TODO("Not yet implemented")
    }

    override fun credentialRequest() {
        TODO("Not yet implemented")
    }
}