package com.example.liv.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class JSONParser {

	private InputStream is = null;
	private JSONObject jObj = null;
	private String json = "";
	private String urlStr;
	private List<NameValuePair> postParams;

	// Constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		urlStr = url;
		postParams = params;
		try {
			jObj = new makeRequest().execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		Log.d("JSON Object", jObj.toString());
		return jObj;
	}

	protected class makeRequest extends AsyncTask<Context, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(Context... params) {
			// Making HTTP POST request with HttpClient
			try {
				Log.d("Debug", "Execute makeRequest().");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(urlStr);
				Log.d("URL", urlStr);
				Log.d("postParams", postParams.toString());
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
				// Log.d("Debug", "mark 1");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				// Log.d("Debug", "mark 2");
				if (httpResponse != null) {
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// read the POST data
			Log.d("Debug", "Read the Post data");
			try {
				if (is != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					if (reader != null) {
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
					}
					is.close();
					json = sb.toString();
				}
			} catch (Exception e) {
				Log.e("Buffer Error",
						"Error reading the input: " + e.toString());
			}

			// try parse the string to a JSON object
			JSONObject json_obj = null;
			try {
				Log.d("JSON query string", json);
				if (json != "") {
					json_obj = new JSONObject(json);
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data: " + e.toString());
			}
			return json_obj;
		}
	}

}
