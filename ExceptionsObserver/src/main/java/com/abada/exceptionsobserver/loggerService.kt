package com.abada.exceptionsobserver

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager
import com.abada.flyView.DraggableFlyView
import com.abada.flyView.FlyViewInfo
import com.abada.flyView.NoController
import com.abada.flyView.addFlyInfo

class loggerService : Service() {
    val flyViewKey = "logger view"
    override fun onBind(intent: Intent): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addFlyInfo(this, flyViewKey, ::stopSelf, FlyViewInfo(NoController) {
            DraggableFlyView {
                ExceptionObserverView()
            }
        })
    }

    companion object {
        fun startLogging(context: Context) {
            context.startService(Intent(context, loggerService::class.java))
        }
    }
}