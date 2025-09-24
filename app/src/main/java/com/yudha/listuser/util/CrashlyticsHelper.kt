package com.yudha.listuser.util

class CrashlyticsHelper {
    
    fun recordException(throwable: Throwable) {
        // For now, just log the exception
        // In a real app, you would integrate with Firebase Crashlytics
        throwable.printStackTrace()
    }
    
    fun log(message: String) {
        // For now, just print the message
        // In a real app, you would log to Crashlytics
        println("Crashlytics: $message")
    }
}