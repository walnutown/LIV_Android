package com.example.liv;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liv.library.DatabaseHandler;
import com.example.liv.library.UserFunctions;

public class LoginActivity extends Activity {

	Button btnLogin;
	TextView txtRegister;
	EditText inputEmail;
	EditText inputPassword;
	Dialog dialog;
	Button btnLoginError;	

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
						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							Log.d("Debug", "user successfully logged in");
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(
									getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							Log.d("Debug", "Clear all previous data in database");
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME),
									json_user.getString(KEY_EMAIL),
									json.getString(KEY_UID),
									json_user.getString(KEY_CREATED_AT));
							Log.d("Debug", "Launch Dashboard Screen");
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
							loginError_handler();
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
	
	/*
	 * Create Dialog to handle the Login Error
	 */
	public void loginError_handler(){
		dialog = new Dialog(this);
		dialog.setTitle("Login Error");
		dialog.setContentView(R.layout.login_error);
		dialog.show();
		btnLoginError = (Button) dialog.findViewById(R.id.btnDialog_loginError);
		btnLoginError.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				inputEmail.requestFocus();
			}
		});				
	}
	
	
	
}
