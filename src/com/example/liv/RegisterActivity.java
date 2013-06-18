package com.example.liv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

public class RegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		// selecting default screen to register.xml
		setContentView(R.layout.register);
		TextView registerScreen = (TextView) findViewById(R.id.link_to_login);
		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Closing registration screen, and login screen will be shown
				finish();

			}
		});
	}
}
