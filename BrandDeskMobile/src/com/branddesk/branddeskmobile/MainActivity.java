package com.branddesk.branddeskmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;

import com.example.branddeskmobile.R;
import com.parse.ParseAnalytics;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
	final CountDownTimer timer = new CountDownTimer(3000, 1000) {

		@Override
		public void onFinish() {
			Intent introIntent = new Intent(MainActivity.this, IntroActivity.class);
			startActivity(introIntent);
			finish();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
		
	};
	timer.start();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
