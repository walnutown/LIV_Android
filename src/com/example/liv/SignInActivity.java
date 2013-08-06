package com.example.liv;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.liv.library.DatabaseHandler;
import com.example.liv.library.UserFunctions;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {
	private ActionBar mActionBar;
	private Button btnSignin;
	private EditText editEmail;
	private EditText editPassword;
	private Dialog dialog;
	private Button btnLoginError;

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		init();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sign_in:
			// Toast.makeText(this, "Action bar icon selected",
			// Toast.LENGTH_SHORT).show();
			// slideAnimation();
			// sm.show();
			Intent toSignUpAct = new Intent(getApplicationContext(), SignUpActivity.class);
			startActivity(toSignUpAct);
			break;
		case android.R.id.home:
			Intent toWelcomeAct = new Intent(getApplicationContext(), WelcomeActivity.class);
			startActivity(toWelcomeAct);

		}
		return true;
	}
	
	
	public void init(){
		btnSignin = (Button) findViewById(R.id.btn_sign);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editPassword = (EditText) findViewById(R.id.edit_passwd);
		mActionBar = getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		btnSignin.setOnClickListener(signinListener);
				
					
	}
	
	private android.view.View.OnClickListener signinListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String email = editEmail.getText().toString();
			String password = editPassword.getText().toString();
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
						Intent toHomeAct = new Intent(
								getApplicationContext(),
								HomeActivity.class);
						// Close all views before launching home page
						toHomeAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(toHomeAct);
						// Close Login Screen
						finish();
					} else {
						// Error in Login
						loginErrorHandler();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	/*
	 * Create Dialog to handle the Login Error
	 */
	public void loginErrorHandler(){
		dialog = new Dialog(this);
		dialog.setTitle("Login Error");
		dialog.setContentView(R.layout.login_error);
		dialog.show();
		btnLoginError = (Button) dialog.findViewById(R.id.btnDialog_loginError);
		btnLoginError.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				editEmail.requestFocus();
			}
		});				
	}
	

}
