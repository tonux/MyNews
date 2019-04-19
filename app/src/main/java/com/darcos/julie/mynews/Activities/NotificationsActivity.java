package com.darcos.julie.mynews.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.darcos.julie.mynews.R;
import com.darcos.julie.mynews.Utils.MyAlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private List<String> listCheckedNotification;
    private PendingIntent pendingIntent;
    private String button;

    @BindView(R.id.checkbox_arts)
    CheckBox checkBoxArts;
    @BindView(R.id.checkbox_politics)
    CheckBox checkBoxPolitics;
    @BindView(R.id.checkbox_business)
    CheckBox checkBoxBusiness;
    @BindView(R.id.checkbox_sports)
    CheckBox checkBoxSports;
    @BindView(R.id.checkbox_entrepreneurs)
    CheckBox checkBoxEntrepreneurs;
    @BindView(R.id.checkbox_travel)
    CheckBox checkBoxTravels;
    @BindView(R.id.toggle_button_notifications)
    ToggleButton toggle;
    @BindView(R.id.activity_notification_toolbar)
    Toolbar toolbarNotifications;
    @BindView(R.id.search_query_term_notification)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        this.configureToolBar();

        this.configureCheckbox();

        this.configureAlarmManager();

        this.toggle.setOnCheckedChangeListener(this);

        //initialize with the last backups
        this.editText.setText(getPreferences(MODE_PRIVATE).getString("edit", null));
        this.button = getPreferences(MODE_PRIVATE).getString("toggle", null);


        if (this.button != null) {
            if (this.button.equals("checked")) {
                toggle.setChecked(true);
            } else {
                toggle.setChecked(false);
            }
        }

    }

    //save information for the next launch of the activity
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit().putString("toggle", this.button).apply();
        preferences.edit().putString("edit", this.editText.getText().toString()).apply();
        for (int i = 0; i < 6; i++) {
            preferences.edit().putString("listCheckbox" + i, this.listCheckedNotification.get(i)).apply();
        }
    }


    //button for enable or disable notification
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startAlarm();
            this.button = "checked";
        } else {
            stopAlarm();
            this.button = "";
        }
    }

    //Configuring the AlarmManager and save inforpation in intent for notification message
    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(NotificationsActivity.this, MyAlarmReceiver.class);
        alarmIntent.putExtra("queryShearch", editText.getText().toString());
        alarmIntent.putExtra("newsDesk", newsDesk());

        pendingIntent = PendingIntent.getBroadcast(NotificationsActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // Start Alarm at 19:00 and repeat all day if actived if one category or more selected
    private void startAlarm() {
        if(buttonenable()) {
            configureAlarmManager();
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, times(20, 0), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Alarm set !", Toast.LENGTH_SHORT).show();
        }else{
            stopAlarm();
            Toast.makeText(this, "Select one category", Toast.LENGTH_SHORT).show();
            toggle.setChecked(false);
        }
    }

    // Stop Alarm
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        toggle.setChecked(false);
        Toast.makeText(this, "Alarm Canceled !", Toast.LENGTH_SHORT).show();
    }

    //configure toolbar
    private void configureToolBar() {
        setSupportActionBar(toolbarNotifications);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifications");
    }

    //configure button return of toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    //check or uncheck checkbox with last save
    private void configureCheckbox() {
        this.listCheckedNotification = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.listCheckedNotification.add(getPreferences(MODE_PRIVATE).getString("listCheckbox" + i, null));
        }


        if (this.listCheckedNotification.get(0) != null) {
            this.checkBoxArts.setChecked(true);
        }
        if (this.listCheckedNotification.get(1) != null) {
            this.checkBoxPolitics.setChecked(true);
        }
        if (this.listCheckedNotification.get(2) != null) {
            this.checkBoxBusiness.setChecked(true);
        }
        if (this.listCheckedNotification.get(3) != null) {
            this.checkBoxSports.setChecked(true);
        }
        if (this.listCheckedNotification.get(4) != null) {
            this.checkBoxEntrepreneurs.setChecked(true);
        }
        if (this.listCheckedNotification.get(5) != null) {
            this.checkBoxTravels.setChecked(true);
        }
    }


    //on click change value of listCheckedNotification and configure alarm
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked)
                    this.listCheckedNotification.set(0, "arts");
                else
                    this.listCheckedNotification.set(0, null);
                break;

            case R.id.checkbox_politics:
                if (checked)
                    this.listCheckedNotification.set(1, "politics");
                else
                    this.listCheckedNotification.set(1, null);
                break;

            case R.id.checkbox_business:
                if (checked)
                    this.listCheckedNotification.set(2, "business");
                else
                    this.listCheckedNotification.set(2, null);
                break;
            case R.id.checkbox_sports:
                if (checked)
                    this.listCheckedNotification.set(3, "sports");
                else
                    this.listCheckedNotification.set(3, null);
                break;
            case R.id.checkbox_entrepreneurs:
                if (checked)
                    this.listCheckedNotification.set(4, "entrepreneurs");
                else
                    this.listCheckedNotification.set(4, null);
                break;
            case R.id.checkbox_travel:
                if (checked)
                    this.listCheckedNotification.set(5, "travel");
                else
                    this.listCheckedNotification.set(5, null);
                break;
        }
        if(buttonenable() == false) {
            startAlarm();
        }
        this.configureAlarmManager();
    }

    /**
     * use for choose times of notification
     *
     * @param hours  chosse your hours
     * @param minute choose your minutes
     * @return time in millis
     */
    private long times(int hours, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * formating list for apiShearch
     *
     * @return newsDesk for apiShearch
     */
    public String newsDesk() {
        StringBuilder q;
        q = new StringBuilder("news_desk:(");

        for (int i = 0; i < this.listCheckedNotification.size(); i++) {
            if (this.listCheckedNotification.get(i) == null) {

            } else {
                q.append("\"").append(this.listCheckedNotification.get(i)).append("\" ");
            }
        }

        q.append(")");
        return q.toString();
    }

    /**
     * check if there is at least one category selected
     *
     * @return true if button enable or else if disable
     */
    public boolean buttonenable() {
        int j = 0;
        for (int i = 0; i < this.listCheckedNotification.size(); i++) {

            if (this.listCheckedNotification.get(i) == null) {
                j = j + 1;
            }
        }
        return j != 6;
    }
}
