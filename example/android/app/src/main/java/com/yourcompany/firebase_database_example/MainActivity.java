package com.yourcompany.firebase_database_example;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import com.yourcompany.firebase_database.FirebaseDatabasePlugin;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabasePlugin.register(this);
    }
}
