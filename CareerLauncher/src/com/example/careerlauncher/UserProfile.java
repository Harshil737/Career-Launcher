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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends Fragment {

	private View fragView;
	private TextView tv_name, tv_mail, tv_password;
	private Button btn_update;
	private String finalURL = "";
	private static String LOG_TAG = UserProfile.class.getSimpleName();
	private int uid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragView = inflater.inflate(R.layout.fragment_user_profile, container,
				false);
		initView();
		return fragView;
	}

	void initView() {
		tv_name = (TextView) fragView.findViewById(R.id.profile_tv_name);
		tv_mail = (TextView) fragView.findViewById(R.id.profile_tv_mail);
		tv_password = (TextView) fragView
				.findViewById(R.id.profile_tv_password);
		btn_update = (Button) fragView.findViewById(R.id.profile_btn_update);

		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		SharedPreferences mPreferences = getActivity().getSharedPreferences(
				"system", Context.MODE_PRIVATE);
		uid = mPreferences.getInt("UID", 0);

		finalURL = getResources().getString(R.string.SERVER_URL)
				+ "User.php?action=getUser&uid=" + uid + "";
		new UserTask().execute();
	}

	void updateUI(UserClass usr) {
		tv_name.setText(usr.getName());
		tv_mail.setText(usr.getEmail());
	}

	private class UserTask extends AsyncTask<URL, Object, UserClass> {

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

					// SharedPreferences mPreferences = getSharedPreferences(
					// "system", Context.MODE_PRIVATE);
					// SharedPreferences.Editor preEditor = mPreferences.edit();
					// preEditor.putInt("UID", user.getUid());
					// preEditor.putString("name", user.getName());
					// preEditor.putString("email", user.getEmail());
					// preEditor.putString("login_status", "DONE");
					// preEditor.apply();
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
				Toast.makeText(getActivity(), usr.getUid() + "",
						Toast.LENGTH_LONG).show();
				updateUI(usr);
			} else {
				Toast.makeText(getActivity(), "Email or password wrong",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
