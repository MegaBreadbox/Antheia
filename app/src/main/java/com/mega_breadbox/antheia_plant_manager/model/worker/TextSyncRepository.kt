package com.mega_breadbox.antheia_plant_manager.model.worker

interface TextSyncRepository {
    fun syncText(plantId: String)
}