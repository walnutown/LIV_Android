package com.example.liv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btnSignUp = (Button) findViewById(R.id.btn_sign);

		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toSignUpAct = new Intent(getApplicationContext(), SignUpActivity.class);
				startActivity(toSignUpAct);
			}
		});
	}

}
