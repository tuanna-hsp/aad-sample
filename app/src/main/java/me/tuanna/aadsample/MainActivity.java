package me.tuanna.aadsample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static String USE_LIGHT_THEME_KEY = "use_light_theme";
    private static String CHANNEL_ID = "AAD Sample";

    @BindView(R.id.themeSelector)
    RadioGroup themeSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyAppTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        themeSelector.check(shouldUseLightTheme() ? R.id.lightThemeButton : R.id.darkThemeButton);
    }

    private void applyAppTheme() {
        setTheme(shouldUseLightTheme() ? R.style.AppTheme : R.style.AppThemeDark);
    }

    private boolean shouldUseLightTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean(USE_LIGHT_THEME_KEY, true);
    }

    @OnCheckedChanged({ R.id.lightThemeButton, R.id.darkThemeButton })
    public void onThemeSelectorChanged(CompoundButton button, boolean isChecked) {
        if (! isChecked) {
            return;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (button.getId()) {
            case R.id.lightThemeButton:
                preferences.edit().putBoolean(USE_LIGHT_THEME_KEY, true).apply();
                break;
            case R.id.darkThemeButton:
                preferences.edit().putBoolean(USE_LIGHT_THEME_KEY, false).apply();
                break;
        }
        showToastMessage();
    }

    @OnClick({ R.id.notificationButton, R.id.snackbarButton })
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.notificationButton:
                showNotification();
                break;

            case R.id.snackbarButton:
                showSnackbarMessage();
                break;
        }
    }

    private void showToastMessage() {
        Toast.makeText(this, R.string.restart, Toast.LENGTH_LONG).show();
    }

    private void showSnackbarMessage() {
        Snackbar.make(themeSelector, R.string.restart, Snackbar.LENGTH_LONG).show();
    }

    private void showNotification() {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.restart))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
