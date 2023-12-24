package com.me.calendar.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

public abstract class WakeReminderIntentService extends Service {

    abstract void doReminderWork(Intent intent);

    public static final String LOCK_NAME_STATIC = "com.me.calendar:service.STATIC";
    private static PowerManager.WakeLock lockStatic = null;

    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    private static synchronized PowerManager.WakeLock getLock(Context context) {
        if (lockStatic == null) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            lockStatic = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME_STATIC);
            lockStatic.setReferenceCounted(true);
        }
        return lockStatic;
    }

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_SNOOZE_FOREGROUND_SERVICE = "ACTION_SNOOZE_FOREGROUND_SERVICE";

    public WakeReminderIntentService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null)
                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        try {
                            doReminderWork(intent);
                        } finally {
                            getLock(this).release();
                        }
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        stopForegroundService();
                        break;
                }
        }
//        return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    protected void stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(false);
//        stopSelf();
    }
}
