package com.thesocialcoin.networking.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;

/**
 * Created by identitat on 07/01/15.
 */
public class BaseService extends Service {

    protected static SessionData sessionData;

    public void onCreate() {
        sessionData = new SessionData(this);
        RequestManager.EventBus.register(this);
    }

    protected void postEvent(Object event){
        RequestManager.EventBus.post(event);
    }

    protected void showToast(String message)
    {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
