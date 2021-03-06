package com.example.careerlauncher;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class Splash extends Activity {

	String isLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		getActionBar().hide();

		SharedPreferences preferences = getSharedPreferences("system",
				MODE_PRIVATE);
		isLogin = preferences.getString("login_status", "N/A");

		Thread timerThread = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (isLogin.equals("N/A")) {
						Intent loginIntent = new Intent(Splash.this,
								Login.class);
						startActivity(loginIntent);
					} else {
						Intent homeIntent = new Intent(Splash.this,
								MainActivity.class);
						startActivity(homeIntent);
					}
				}
			}
		};
		timerThread.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
