package com.tasanah.mygithubuserapp.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.receiver.UserReceiver;
import java.util.Calendar;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setActionBarTitle();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int notificationDaily = sharedPreferences.getInt("user_notification", 0);
        aSwitch = findViewById(R.id.switchNotification);
        if (notificationDaily == 1){
            aSwitch.setChecked(true);
        }else {
            aSwitch.setChecked(false);
        }
        onClickSwitch();

        ConstraintLayout constraintLayout = findViewById(R.id.setting_btn_changeLanguage);
        constraintLayout.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS)));

    }
    private void setActionBarTitle() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void onClickSwitch(){
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setReminder(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_notification",1);
                editor.apply();
                Toast.makeText(SettingActivity.this, "Notification Active", Toast.LENGTH_SHORT).show();
            }else {
                setReminderOff(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_notification",0);
                editor.apply();
                Toast.makeText(SettingActivity.this, "Notification Not Active", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setReminder(Context applicationContext) {
        Intent intent = new Intent(applicationContext, com.tasanah.mygithubuserapp.receiver.UserReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 27, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("aSwitch","aSwitch");
        if (alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }
    private void setReminderOff(Context applicationContext) {
        Log.d("aSwitch","aSwitch");
        Intent intent = new Intent(SettingActivity.this, UserReceiver.class);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 27, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }
}