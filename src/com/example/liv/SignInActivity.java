package com.example.liv;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SignInActivity extends Activity{
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
		mActionBar = getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
