package com.example.crosses_zeros.app

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

const val ONESIGNAL_APP_ID = "8da8871f-f617-40f9-8b66-cac13336b1d3"

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications()
    }
}