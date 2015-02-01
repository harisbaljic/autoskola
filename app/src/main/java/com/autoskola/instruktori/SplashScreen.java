package com.autoskola.instruktori;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autoskola.instruktori.helpers.MemoryManager;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		final TextView progressText = (TextView) findViewById(R.id.progressText);
		new CountDownTimer(3000, 1500) {

			@Override
			public void onTick(long millisUntilFinished) {
				if (!isNetworkConnected()) {
					// pw.setBarColor(Color.RED);
					// pw.setText("Nema konekcije.");
					progressText.setTextColor(Color.RED);
					progressText.setText("Nema konekcije");
					this.cancel();
				}
			}

			@Override
			public void onFinish() {
				if (isNetworkConnected()) {
					MemoryManager memoryManager = new MemoryManager(
							SplashScreen.this);
					// pw.stopSpinning();
					finish();
//					if (memoryManager.isLoggedIn())
//						startActivity(new Intent(SplashScreen.this,
//								NaslovnicaActivity.class));
//					else
//						startActivity(new Intent(SplashScreen.this,
//								MainActivity.class));


				}
			}
		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
}
