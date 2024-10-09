package com.example.antheia_plant_manager.model.worker.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.ComposeText

fun reminderNotification(plantName: String, context: Context) {
    val importance = NotificationManager.IMPORTANCE_LOW
    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        importance,
    ).apply { description = NOTIFICATION_DESCRIPTION}

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentText( context.getString(R.string.requires_attention, plantName))


    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }
}