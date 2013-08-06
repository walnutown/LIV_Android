package com.example.liv;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.liv.library.DatabaseHandler;
import com.example.liv.library.UserFunctions;

public class SignUpActivity extends Activity{
	
	private ActionBar mActionBar;
	private EditText editName;
	private EditText editEmail;
	private EditText editPassword;
	private ToggleButton tbMale;
	private ToggleButton tbFemale;
	private Button btnSignup;
	
	private Dialog dialog;
	private Button btnRegisterError;
	private TextView txtRegisterError;
	
	// JSON Response node names
		private static String KEY_SUCCESS = "success";
		private static String KEY_ERROR = "error";
		private static String KEY_ERROR_MSG = "error_msg";
		private static String KEY_UID = "uid";
		private static String KEY_NAME = "name";
		private static String KEY_EMAIL = "email";
		private static String KEY_SEX = "sex";
		private static String KEY_CREATED_AT = "created_at";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
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
			Intent toSignInAct = new Intent(getApplicationContext(), SignInActivity.class);
			startActivity(toSignInAct);
			break;
		case android.R.id.home:
			Intent toWelcomeAct = new Intent(getApplicationContext(), WelcomeActivity.class);
			startActivity(toWelcomeAct);

		}
		return true;
	}
	
	public void init(){
		
		btnSignup = (Button) findViewById(R.id.btn_sign);
		editName = (EditText) findViewById(R.id.edit_name);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editPassword = (EditText) findViewById(R.id.edit_passwd);
		tbMale = (ToggleButton) findViewById(R.id.tb_male);
		tbFemale = (ToggleButton) findViewById(R.id.tb_female);
		
		tbSwitch();
		
		mActionBar = getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		
		btnSignup.setOnClickListener(signupListener);
	}
	
	public void tbSwitch(){
		tbFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					tbMale.setChecked(false);
				}
				else{
					tbMale.setChecked(true);
				}
				
			}
		});
		
		tbMale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					tbFemale.setChecked(false);
				}
				else{
					tbFemale.setChecked(true);
				}	
			}
		});
	}
	
	private OnClickListener signupListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			String name = editName.getText().toString();
			String email = editEmail.getText().toString();
			String password = editPassword.getText().toString();
			String sex = "male";
			if (tbFemale.isChecked()){
				sex = "female";
			}
			
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
						Intent toHomeAct = new Intent(
								getApplicationContext(),
								HomeActivity.class);
						// Close all views before launching home page
						toHomeAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(toHomeAct);
						// Close Registration Screen
						finish();
					} else {
						// Error in Registration
						registerErrorHandler(json.getString(KEY_ERROR_MSG));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
					
		
	/*
	 * Dialog to handle the Register Error
	 */
	public void registerErrorHandler(String error_msg) {
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
				editName.requestFocus();
			}
		});
	}
	
}
