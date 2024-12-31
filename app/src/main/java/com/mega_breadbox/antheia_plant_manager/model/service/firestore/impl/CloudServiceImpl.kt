package com.mega_breadbox.antheia_plant_manager.model.service.firestore.impl

import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.PlantModel
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.UserModel
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudServiceImpl @Inject constructor() : CloudService {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val userIDFlow = flow {
        auth.currentUser?.uid?.let {
            emit(it)
        }
    }

    override fun userFlow(): Flow<UserModel> {
        return callbackFlow {
            userIDFlow.collect { id ->
                val snapshot = db.collection(USERS)
                    .document(id)
                val listener = snapshot.addSnapshotListener { value, error ->
                    error?.let {
                        this.close(it)
                        return@addSnapshotListener
                    }

                    value?.let {
                        val userData = value.toObject<UserModel>()
                        this.trySend(userData ?: UserModel())
                    }
                }
                awaitClose {
                    listener.remove()
                    this.cancel()
                }
            }
        }
    }

    override suspend fun generatePlantId(): String {
        return db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document().id
    }


    override suspend fun addPlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.id).set(plant)
    }

    override suspend fun deletePlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.id).delete()
    }

    override suspend fun updatePlant(plant: PlantModel) {
        db.collection(USERS).document(auth.currentUser!!.uid)
            .collection(PLANTS).document(plant.id).set(plant)
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