package com.example.liv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.liv.library.UserFunctions;

public class DashboardActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout;
	VideoView vv;

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
			vv = (VideoView) findViewById(R.id.video);
			String filename = "https://vines.s3.amazonaws.com/videos/08C49094-DFB4-46DF-8110-EEEC7D4D6115-1133-000000B8AD9BE72C_1.0.1.mp4?versionId=TQGtC5O7G7H34TleFA2LF0Er9tI8VZUe";
			vv.setVideoURI(Uri.parse(filename));
			vv.start();
			
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
