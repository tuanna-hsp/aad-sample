package me.tuanna.aadsample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends AppCompatActivity {

    private static String USE_LIGHT_THEME_KEY = "use_light_theme";

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
    }
}
