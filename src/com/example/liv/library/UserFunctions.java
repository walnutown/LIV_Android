package com.example.liv.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

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
		// BuiNameValuePairers
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		Log.d("Debug", params.toString());
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		//Log.e("JSON", json.toString());
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
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// Log.e("JSON", json.toString());
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
}


















