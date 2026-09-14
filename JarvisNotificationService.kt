package com.jarvis.voice

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class JarvisNotificationService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName
        if (pkg == "com.whatsapp") {
            // WhatsApp notification handling hook.
            // Full auto-reply depends on Android/WhatsApp notification actions and permissions.
        }
    }
}
