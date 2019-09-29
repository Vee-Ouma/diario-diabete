package it.chiarani.diario_diabete.views;

import androidx.appcompat.app.AppCompatActivity;
import it.chiarani.diario_diabete.R;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Crashlytics.getInstance().crash(); // Force a crash
    }
}
