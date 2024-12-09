package com.example.antheia_plant_manager.model.service.firestore.impl

import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.PlantModel
import com.example.antheia_plant_manager.model.service.firestore.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CloudServiceImpl() : CloudService {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun userFlow(): Flow<UserModel> {
        return callbackFlow {
            Firebase.auth.currentUser?.let { user ->
                    val snapshot = db.collection(USERS).document(user.uid)
                    snapshot.addSnapshotListener { value, error ->
                        error?.let {
                            this.close(it)
                        }

                        value?.let {
                            val userData = value.toObject<UserModel>()
                            this.trySend(userData?: UserModel())
                        }
                    }
                }
            awaitClose { this.cancel() }
        }
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