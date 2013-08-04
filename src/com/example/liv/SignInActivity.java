package com.example.liv;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignInActivity extends Activity{
	private ActionBar mActionBar;
	private Button btnSign;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
		btnSign = (Button) findViewById(R.id.btn_sign);
		mActionBar = getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		btnSign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent toHomeAct = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(toHomeAct);	
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
