package com.example.liv;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.liv.library.DatabaseHandler;
import com.example.liv.library.UserFunctions;

public class RegisterActivity extends Activity {

	Button btnRegister;
	TextView txtRegister;
	EditText inputEmail;
	EditText inputName;
	EditText inputPassword;
	
	Dialog dialog;
	Button btnRegisterError;
	TextView txtRegisterError;

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
		// selecting default screen to register.xml
		setContentView(R.layout.register);

		// Importing all assets like buttons, text, fields
		btnRegister = (Button) findViewById(R.id.btnRegister);
		txtRegister = (TextView) findViewById(R.id.link_to_login);
		inputName = (EditText) findViewById(R.id.inputName);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		// Register button Click Event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = inputName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, email,
						password);
				// check for register response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							// user successfully registered
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(
									getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME),
									json_user.getString(KEY_EMAIL),
									json.getString(KEY_UID),
									json_user.getString(KEY_CREATED_AT));
							// Launch Dashboard Screen
							Intent dashboard = new Intent(
									getApplicationContext(),
									DashboardActivity.class);
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							// Close Registration Screen
							finish();
						} else {
							// Error in Registration
							registerError_handler(json.getString(KEY_ERROR_MSG));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		// Listening to login link
		txtRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(login);
				// Closing registration screen, and login screen will be shown
				finish();
			}
		});
	}
	
	
	/*
	 * Dialog to handle the Register Error
	 */
	public void registerError_handler(String error_msg){
		dialog = new Dialog(this);
		dialog.setTitle("Registration Error");
		dialog.setContentView(R.layout.register_error);
		dialog.show();
		btnRegisterError = (Button) dialog.findViewById(R.id.btnDialog_registerError);
		txtRegisterError = (TextView) dialog.findViewById(R.id.txtDialog_registerError);
		txtRegisterError.setText(error_msg);
		btnRegisterError.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				inputName.requestFocus();
			}
		});	
	}
	
	
}
