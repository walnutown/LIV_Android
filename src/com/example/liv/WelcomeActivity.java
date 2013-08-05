package com.example.liv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends Activity{
	
	private Button btnContinue;
	private Button btnSkip;
	private EditText editZipcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		btnContinue = (Button) findViewById(R.id.btn_continue);
		btnSkip = (Button) findViewById(R.id.btn_skip);
		editZipcode = (EditText) findViewById(R.id.edit_zipcode);
		
		btnContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent toLoginAct = new Intent(getApplicationContext(), LoginActivity_new.class);
				startActivity(toLoginAct);
				
			}
		});
		
		
//		ActionBar actionbar = getActionBar();
//		actionbar.setDisplayShowHomeEnabled(false);
//		actionbar.setDisplayShowTitleEnabled(false);
//		//actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffffff")));
//		actionbar.hide();
		
	}
	
}
