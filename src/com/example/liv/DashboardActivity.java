package com.example.liv;

import com.crashlytics.android.Crashlytics;
import com.example.liv.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Crashlytics.start(this);
		// Check login status in database
		userFunctions = new UserFunctions();
		if (userFunctions.isUserLoggedIn(getApplicationContext())) {
			// user already logged in, display welcome
			setContentView(R.layout.dashboard);
			btnLogout = (Button) findViewById(R.id.btnLogout);

			btnLogout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					userFunctions.logoutUser(getApplicationContext());
					Intent login = new Intent(getApplicationContext(),
							LoginActivity.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					finish();
				}
			});
		} else {
			// user is logged in, display login screen
			Intent login = new Intent(getApplicationContext(),
					LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();
		}
	}
}
