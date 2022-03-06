package com.vs.elearnquran.usage;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

public class App extends Application implements DefaultLifecycleObserver {

    public final String Tag = App.this.getClass().getCanonicalName();
    public static final String PUSH_NOTIFICATION_CHANNEL = "PUSH_NOTIFICATION";

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        createNotificationChannel();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            //Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.water_sound);

            /*.
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            */
            
            NotificationChannel pushNotificationChannel = new NotificationChannel(PUSH_NOTIFICATION_CHANNEL,
                    "Push Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            pushNotificationChannel.setDescription("Push notification.");

            NotificationManager manager = getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.createNotificationChannel(pushNotificationChannel);
            }
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        String state =  ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();

        Log.d(Tag,"onTerminate "+state);
    }



    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        String state =  ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();

        Log.d(Tag,"onEnterForeground "+state);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {


        String state =  ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();
        Log.d(Tag,"onEnterStop" + state );


    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {


        String state =  ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();
        Log.d(Tag,"onEnterDestroy" + state );
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        String state =  ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();
        Log.d(Tag,"onEnterBackground" + state );
    }


}

