package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String preferenceKey = "localeapp";
    public static final String Currency = "currency";
    TextView currencyTextView;
    TextView multipleCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelp();
            }
        });
        Date date = new Date();
        long expirationDate = date.getTime() + TimeUnit.DAYS.toMillis(3);
        date.setTime(expirationDate);
        String formatDate = DateFormat.getDateInstance().format(date);
        TextView expiredTextView = findViewById(R.id.date);
        expiredTextView.setText(formatDate);


        currencyTextView = findViewById(R.id.price);
        multipleCurrency = findViewById(R.id.price_multiple);

        if (sharedPreferences.contains(Currency)) {
            int res = sharedPreferences.getInt(Currency, 0);
            currencyTextView.setText(Integer.toString(res));
            multipleCurrency.setText(Integer.toString(res * 100));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        currencyTextView = findViewById(R.id.price);
        multipleCurrency = findViewById(R.id.price_multiple);

        if (sharedPreferences.contains(Currency)) {
            int res = sharedPreferences.getInt(Currency, 0);
            currencyTextView.setText(Integer.toString(res));
            multipleCurrency.setText(Integer.toString(res * 100));
        }
    }

    /**
     * Shows the Help screen.
     */
    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}