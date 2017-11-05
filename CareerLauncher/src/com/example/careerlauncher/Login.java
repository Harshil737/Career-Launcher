package com.example.careerlauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	private EditText et_email, et_password;
	private Button btn_login;
	private TextView tv_register;
	private String email, password;
	private String finalURL = "";
	private static String LOG_TAG = Login.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		et_email = (EditText) findViewById(R.id.login_et_email);
		et_password = (EditText) findViewById(R.id.login_et_password);
		btn_login = (Button) findViewById(R.id.login_btn_login);
		tv_register = (TextView) findViewById(R.id.login_tv_register);

		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_tv_register:
			Intent registerIntent = new Intent(Login.this, Register.class);
			startActivity(registerIntent);
			break;
		case R.id.login_btn_login:
			checkLogin();
		default:
			break;
		}
	}

	void checkLogin() {
		email = et_email.getText().toString().trim();
		// Log.d(LOG_TAG, email);
		password = et_password.getText().toString().trim();
		// Log.d(LOG_TAG, password);
		if (TextUtils.isEmpty(email)) {
			et_email.setError("Required");
		} else if (TextUtils.isEmpty(password)) {
			et_password.setError("Required");
		} else if (!(email.contains("@") && email.contains(".com"))) {
			Toast.makeText(getApplicationContext(), "Invalid email",
					Toast.LENGTH_LONG).show();
		} else if (password.length() <= 4) {
			Toast.makeText(getApplicationContext(),
					"Password must be 5 characters long", Toast.LENGTH_LONG)
					.show();
		} else {
			finalURL = getResources().getString(R.string.SERVER_URL)
					+ "User.php?action=Login&mail=" + email + "&password="
					+ password + "";
			Log.d(LOG_TAG, finalURL);
			new LoginTask().execute();
		}
	}

	private class LoginTask extends AsyncTask<URL, Object, UserClass> {

		@Override
		protected UserClass doInBackground(URL... params) {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(finalURL);
			StringBuilder sb = new StringBuilder();
			try {
				HttpResponse response = client.execute(get);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} catch (ClientProtocolException e) {
				Log.e("WSDemo", "MessageDemo Message: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("WSDemo", "MessageDemo Message: " + e.getMessage());
				e.printStackTrace();
			}

			// Log.d(LOG_TAG, sb.toString());
			UserClass usr;
			usr = extractFeatureFromJson(sb.toString());
			return usr;
		}

		private UserClass extractFeatureFromJson(String userJSON) {
			if (TextUtils.isEmpty(userJSON)) {
				return null;
			}
			try {
				JSONObject baseJsonResponse = new JSONObject(userJSON);
				JSONArray featureArray;
				int success = baseJsonResponse.getInt("Status");
				if (success == 1) {
					featureArray = baseJsonResponse.getJSONArray("Records");
					JSONObject jsonObject = featureArray.getJSONObject(0);
					int uid = jsonObject.getInt("uid");
					// Log.d(LOG_TAG, uid + "");
					String name = jsonObject.getString("uname");
					// Log.d(LOG_TAG, name);
					String email = jsonObject.getString("email");
					// Log.d(LOG_TAG, email);
					UserClass user = new UserClass(uid, name, email);

					SharedPreferences mPreferences = getSharedPreferences(
							"system", Context.MODE_PRIVATE);
					SharedPreferences.Editor preEditor = mPreferences.edit();
					preEditor.putInt("UID", user.getUid());
					preEditor.putString("name", user.getName());
					preEditor.putString("email", user.getEmail());
					preEditor.putString("login_status", "DONE");
					preEditor.apply();
					return user;
				} else {
					Log.d(LOG_TAG, "User does not exist.");
				}
			} catch (JSONException e) {
				Log.e(LOG_TAG, "Problem parsing the user JSON results", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(UserClass usr) {
			if (usr != null) {
				Toast.makeText(Login.this, usr.getUid() + "", Toast.LENGTH_LONG)
						.show();
				Intent homeIntent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(homeIntent);
			} else {
				Toast.makeText(Login.this, "Email or password wrong",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
