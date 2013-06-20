package com.example.liv;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.liv.library.DatabaseHandler;
import com.example.liv.library.UserFunctions;

public class LoginActivity extends Activity {

	Button btnLogin;
	TextView txtRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView txtErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Crashlytics.start(this);
		// selecting default screen to login.xml
		setContentView(R.layout.login);

		// Importing all assets like buttons, text, fields
		btnLogin = (Button) findViewById(R.id.btnLogin);
		txtRegister = (TextView) findViewById(R.id.link_to_register);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		txtErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.loginUser(email, password);

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						txtErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(
									getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME),
									json_user.getString(KEY_EMAIL),
									json_user.getString(KEY_UID),
									json_user.getString(KEY_CREATED_AT));

							// Launch Dashboard Screen
							Intent dashboard = new Intent(
									getApplicationContext(),
									DashboardActivity.class);
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);

							// Close Login Screen
							finish();
						} else {
							// Error in Login
							txtErrorMsg.setText("Incorrect username/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Listening to register new account link
		txtRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Switching to Register screen
				Intent register = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(register);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
