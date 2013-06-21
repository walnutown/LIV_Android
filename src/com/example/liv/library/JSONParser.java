package com.example.liv.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
		new makeRequest().execute();
		// return JSON String
		return jObj;
	}

	protected class makeRequest extends AsyncTask<Context, Void, Void> {
		@Override
		protected Void doInBackground(Context... params) {
			// Making HTTP POST request with HttpClient
			try {
				Log.d("Debug", "Execute makeRequest().");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(urlStr);
				Log.d("URL", urlStr);
				Log.d("postParams", postParams.toString());
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
				Log.d("Debug", "mark 1");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.d("Debug", "mark 2");
				if (httpResponse != null) {
					Log.d("Debug", "httpResponse: " + httpResponse.toString());
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				}
				Log.d("is", is.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Log.d("Debug", "UnsupportedEncodeException");
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d("Debug", "ClientException");
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Debug", "IOException");
			}

			// read the POST data
			Log.d("Debug", "Read Post data 1");
			try {
				if (is != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"), 8);
					Log.d("Debug", "Read Post data 2");
					StringBuilder sb = new StringBuilder();
					String line = null;
					if (reader != null) {
						Log.d("Debug", "reader: " + reader.toString());
						while ((line = reader.readLine()) != null) {
							Log.d("Debug", "line " + line);
							sb.append(line + "\n");
						}
						Log.d("Debug", "sb: " + sb.toString());
					}
					Log.d("Debug", "Read Post data 3");
					is.close();
					Log.d("Debug", "Read Post data 4 ");
					json = sb.toString();
				}
			} catch (Exception e) {
				Log.e("Buffer Error",
						"Error reading the input: " + e.toString());
			}

			// try parse the string to a JSON object
			try {
				if (json != "") {
					jObj = new JSONObject(json);
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data: " + e.toString());
			}
			return null;
		}
	}

}
