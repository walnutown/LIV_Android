package com.example.liv.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserFunctions {
	private JSONParser jsonParser;
	
	// Testing in local host
	// if you use the android emulator, use http://10.0.2.2/ to connect to your localhost
	// if you use the android device to develop, it's recommended to use the remote server
	private static String loginURL = "http://trojansite.com/liv_api/";
	private static String registerURL = "http://trojansite.com/liv_api/";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	// Constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/*
	 * function: make login request
	 */
	public JSONObject loginUser(String email, String password){
		// Build NameValuePairers
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		return json;
	}
	
	/*
	 * function: make Register Request
	 */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		//params.add(new BasicNameValuePair("sex", sex));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;	
	}
	
	/*
	 * Function: get Login status
	 */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if (count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	/*
	 * Function: logout user
	 */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	/*
	 * convert dp to px
	 */
	public static int dpToPx(int dp, Context ctx) {
		Resources r = ctx.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}
	
	public static int percentToPx(int p, Activity act){
		Display display = act.getWindowManager().getDefaultDisplay(); 
		Point size = new Point();
		display.getSize(size);
		return size.x * p/100;
	}

	/*
	 * Disable the touch events for all views
	 */
	public static void enableViewGroup(ViewGroup viewGroup, boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			//Log.d("Debug", "View name: " + view.toString());
			if (view.isFocusable()) {
				view.setEnabled(enabled);
			}
			if (view instanceof ViewGroup) {
				enableViewGroup((ViewGroup) view, enabled);
			} else if (view instanceof ListView) {
				if (view.isFocusable()) {
					view.setEnabled(enabled);
					ListView listView = (ListView) view;
					int listChildCount = listView.getChildCount();
					for (int j = 0; j < listChildCount; j++) {
						if (view.isFocusable()) {
							listView.getChildAt(j).setEnabled(enabled);
						}
					}
				}
			}
		}
	}
}


















