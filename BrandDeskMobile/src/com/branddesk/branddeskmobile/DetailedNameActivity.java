package com.branddesk.branddeskmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.branddeskmobile.R;
import com.parse.ParseObject;

public class DetailedNameActivity extends Activity {
	Name name;
	ArrayList<SavedName> peek = new ArrayList<SavedName>();
	private static DatabaseDataManager dataManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_name);
		dataManager = new DatabaseDataManager(getBaseContext());
		Bundle extras = getIntent().getExtras();
		if (getIntent().getExtras() != null) {
			name = (Name) extras
					.getSerializable(ScreenActivity.DETAILED_NAME_KEY);
		}
		TextView nameText = (TextView) findViewById(R.id.textViewNameBody);
		TextView rationaleText = (TextView) findViewById(R.id.textViewRationaleBody);
		TextView trademarkHeader = (TextView) findViewById(R.id.textViewDetailTM);
		TextView domainHeader = (TextView) findViewById(R.id.textViewDetailDomain);
		TextView trademarkText = (TextView) findViewById(R.id.textViewTMBody);
		TextView dotComText = (TextView) findViewById(R.id.textViewBodyComResult);
		TextView dotNetText = (TextView) findViewById(R.id.textViewBodyNetResult);
		TextView twitterText = (TextView) findViewById(R.id.textViewTwitterBody);
		TextView facebookText = (TextView) findViewById(R.id.textViewFacebookBody);
		ImageView saveIcon = (ImageView) findViewById(R.id.imageViewSave);
		ImageView cloudIcon = (ImageView) findViewById(R.id.imageViewCloud);
		Button detailBackButton = (Button) findViewById(R.id.detailBackbutton);

		detailBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		trademarkHeader.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentTM = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.uspto.gov"));
				startActivity(intentTM);
			}
		});

		domainHeader.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// RestfulHandler namecheap = new
				// RestfulHandler(name.getName());
				// Intent intentDomain = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(namecheap.getNameCheapUrl()));
				Intent intentDomain = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://www.godaddy.com/domains/search-new.aspx?isc=gofd2001aj&ci=87929"));
				startActivity(intentDomain);
			}
		});

		saveIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SavedName mySavedName = new SavedName();
				mySavedName.setName(name.getName());
				mySavedName.setRationale(name.getRationale());
				mySavedName.setTrademarkCount(name.getTrademarkCount());
				mySavedName.setDotComAvailability(name.getDotComAvailability());
				mySavedName.setDotNetAvailability(name.getDotNetAvailability());
				mySavedName.setIsTwitterUsernameAvailable(String.valueOf(name
						.isTwitterUsernameAvailable()));
				mySavedName.setIsFacebookUrlAvailable(String.valueOf(name
						.isFacebookUrlAvailable()));
				dataManager.saveSavedName(mySavedName);
				Toast.makeText(getBaseContext(), "Saved To Database!",
						Toast.LENGTH_SHORT).show();
			}
		});

		cloudIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (name.getRationale() != null) {
					ParseObject cloudName = new ParseObject(
							ParseConstants.KEY_NAME_OBJECT);
					cloudName.put(ParseConstants.KEY_NAME, name.getName());
					cloudName.put(ParseConstants.KEY_RATIONALE,
							name.getRationale());
					cloudName.put(ParseConstants.KEY_TRADEMARK_COUNT,
							name.getTrademarkCount());
					cloudName.put(ParseConstants.KEY_DOT_COM_AVAILABILITY,
							name.getDotComAvailability());
					cloudName.put(ParseConstants.KEY_DOT_NET_AVAILABILITY,
							name.getDotNetAvailability());
					cloudName.put(ParseConstants.KEY_TWITTER_AVAILABILITY,
							name.isTwitterUsernameAvailable());
					cloudName.put(ParseConstants.KEY_FACEBOOK_AVAILABILITY,
							name.isFacebookUrlAvailable());
					cloudName.saveInBackground();
					Toast.makeText(getBaseContext(), "Backed-Up To the Cloud!",
							Toast.LENGTH_SHORT).show();
				} else {
					ParseObject cloudName = new ParseObject(
							ParseConstants.KEY_NAME_OBJECT);
					cloudName.put(ParseConstants.KEY_NAME, name.getName());
					cloudName
							.put(ParseConstants.KEY_RATIONALE, "not available");
					cloudName.put(ParseConstants.KEY_TRADEMARK_COUNT,
							name.getTrademarkCount());
					cloudName.put(ParseConstants.KEY_DOT_COM_AVAILABILITY,
							name.getDotComAvailability());
					cloudName.put(ParseConstants.KEY_DOT_NET_AVAILABILITY,
							name.getDotNetAvailability());
					cloudName.put(ParseConstants.KEY_TWITTER_AVAILABILITY,
							name.isTwitterUsernameAvailable());
					cloudName.put(ParseConstants.KEY_FACEBOOK_AVAILABILITY,
							name.isFacebookUrlAvailable());
					cloudName.saveInBackground();
					Toast.makeText(getBaseContext(), "Backed-Up To the Cloud!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		nameText.setText(name.getName());
		rationaleText.setText(name.getRationale());
		dotComText.setText(name.getDotComAvailability());
		dotNetText.setText(name.getDotNetAvailability());

		if (name.getTrademarkCount() == 1) {
			trademarkText.setText(String.valueOf(name.getTrademarkCount())
					+ " possible conflict found.");
			trademarkText.setTextColor(Color.parseColor("#ff6619"));
		} else if (name.getTrademarkCount() > 1) {
			trademarkText.setText(String.valueOf(name.getTrademarkCount())
					+ " possible conflicts found.");
			trademarkText.setTextColor(Color.RED);
		} else {
			trademarkText.setText("The trademark appears to be available.");
			trademarkText.setTextColor(Color.parseColor("#008000"));
		}

		if (name.getDotComAvailability().equals("available")) {
			dotComText.setTextColor(Color.parseColor("#008000"));
		} else if (name.getDotComAvailability().equals("taken")) {
			dotComText.setTextColor(Color.parseColor("#FF0000"));
		} else {
			dotComText.setTextColor(Color.parseColor("#ff6619"));
		}

		if (name.getDotNetAvailability().equals("available")) {
			dotNetText.setTextColor(Color.parseColor("#008000"));
		} else if (name.getDotNetAvailability().equals("taken")) {
			dotNetText.setTextColor(Color.parseColor("#FF0000"));
		} else {
			dotNetText.setTextColor(Color.parseColor("#ff6619"));
		}

		if (name.isTwitterUsernameAvailable()) {
			twitterText
					.setText("The Twitter screenname seems to be available.");
			twitterText.setTextColor(Color.parseColor("#008000"));
		} else {
			twitterText
					.setText("The Twitter screenname seems to be unavailable.");
			twitterText.setTextColor(Color.parseColor("#FF0000"));
		}

		if (name.isFacebookUrlAvailable()) {
			facebookText
					.setText("The Twitter screenname seems to be available.");
			facebookText.setTextColor(Color.parseColor("#008000"));
		} else {
			facebookText
					.setText("The Facebook screenname seems to be unavailable.");
			facebookText.setTextColor(Color.parseColor("#FF0000"));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_name, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.action_saved:
			navigateToSavedNames();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void navigateToSavedNames() {
		peek = (ArrayList<SavedName>) dataManager.getAllSavedNames();
		if (peek.size() > 0) {
			Intent intent = new Intent(this, ViewSavedActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(getBaseContext(),
					"You haven't saved any names yet.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onDestroy() {
		dataManager.close();
		super.onDestroy();
	}

}
