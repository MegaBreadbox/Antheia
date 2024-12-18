package com.example.antheia_plant_manager.model.worker

interface TextSyncRepository {
    fun syncText(plantId: Int)
}