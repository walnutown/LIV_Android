package com.example.liv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.liv.library.UserFunctions;

public class DashboardActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout;
	VideoView vv1;
	VideoView vv2;

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
			vv1 = (VideoView) findViewById(R.id.video1);
			String filename1 = "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te";
			vv1.setVideoURI(Uri.parse(filename1));
			vv1.start();
			vv1.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (vv1.isPlaying()){
						vv1.pause();
						Log.d("Video", "Pause");	
					}
					else{
						vv1.start();
						Log.d("Video", "Resume");
					}
					
					return true;
				}
			});
			
			vv2 = (VideoView) findViewById(R.id.video2);
			String filename2 = "https://v.cdn.vine.co/r/videos/37B926EBC4965748550245969920_17915f6d3d3.3_cHR4.8r6RDQXA_MwZRSvakvKuMdIV1XiHeI4Lq6_spR0SMqDo5zS5vTCuYI7BM7T.mp4?versionId=GofccS921hBgrP.f36TQpZpBJY_JLhgj";
			vv2.setVideoURI(Uri.parse(filename2));
			vv2.start();
			
			
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
