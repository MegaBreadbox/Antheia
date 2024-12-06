package com.example.antheia_plant_manager.model.service.firestore.impl

import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.PlantModel
import com.example.antheia_plant_manager.model.service.firestore.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudServiceImpl @Inject constructor(
   private val accountService: AccountService
) : CloudService {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userFlow: Flow<UserModel>
        get() =
            accountService.currentUser()
                .filter { it != null }
                .mapLatest { user ->
                db.collection(USERS).document(user?.uid?: "").get().await().toObject<UserModel>()?: UserModel()
            }

    override suspend fun addPlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.plantUserId).set(plant)
    }

    override suspend fun deletePlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.plantUserId).delete()
    }

    override suspend fun updatePlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.plantUserId).set(plant)
    }

    override suspend fun addUser() {
        val user  = UserModel(
            username = auth.currentUser!!.displayName?: "",
            email = auth.currentUser!!.email?: "",
            uid = auth.currentUser!!.uid
        )

        db.collection(USERS).document(auth.currentUser!!.uid).set(user, SetOptions.merge())
    }

    override suspend fun updateUser(user: UserModel) {
        db.collection(USERS).document(auth.currentUser!!.uid).set(user)
    }

    override suspend fun getUser(): UserModel? {
        return db.collection(USERS).document(auth.currentUser!!.uid).get().await().toObject<UserModel>()
    }

    override suspend fun getAllUserData(): List<PlantModel> {
        return db.collection(USERS)
            .document(auth.currentUser!!.uid)
            .collection(PLANTS)
            .get()
            .await()
            .toObjects<PlantModel>()
    }

    companion object {
        private const val USERS = "users"
        private const val PLANTS = "plants"
    }

}