/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.localetextstarter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Help screen that provides a floating action button
 * to start dialing a phone number.
 */
public class HelpActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String preferenceKey = "localeapp";
    public static final String Currency = "currency";

    // TAG for the dial logging message.
    private static final String TAG = HelpActivity.class.getSimpleName();


    /**
     * Creates the view with a floating action button and click listener.
     *
     * @param savedInstanceState Bundle with activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        sharedPreferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getString(R.string.support_phone);
                callSupportCenter(phoneNumber);
            }
        });

        Button submitButton = (Button) findViewById(R.id.harga_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numberInput = (EditText) findViewById(R.id.input_harga);
                int hasil = Integer.parseInt(numberInput.getText().toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.contains(Currency)){
                    editor.putInt(Currency, hasil);
                    editor.commit();
                    Toast.makeText(HelpActivity.this, "Success", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(HelpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    /**
     * Sends an intent to dial the phone number using ACTION_DIAL.
     *
     * @param phoneNumber Phone number string
     */
    private void callSupportCenter(String phoneNumber) {
        // Format the phone number for dialing.
        String formattedNumber = String.format("tel: %s", phoneNumber);
        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the formatted phone number as data for the intent.
        dialIntent.setData(Uri.parse(formattedNumber));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            Log.e(TAG, getString(R.string.dial_log_message));
        }
    }
}
