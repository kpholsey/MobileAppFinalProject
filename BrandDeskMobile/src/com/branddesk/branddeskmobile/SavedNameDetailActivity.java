package com.branddesk.branddeskmobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.branddeskmobile.R;

public class SavedNameDetailActivity extends Activity {
	SavedName savedName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_name_detail);
		
		Bundle savedExtras = getIntent().getExtras();
		if (getIntent().getExtras() != null) {
			savedName = (SavedName) savedExtras.getSerializable(ViewSavedActivity.SAVED_DETAILED_NAME_KEY);
		}
		
		Log.d("saved name detail", savedName.toString());
		
		TextView dbNameText = (TextView) findViewById(R.id.textViewDBNameText);
		TextView dbRationaleText = (TextView) findViewById(R.id.textViewDBRationaleText);
		TextView dbTrademarkText = (TextView) findViewById(R.id.textViewDBTMText);
		TextView dbDotComText = (TextView) findViewById(R.id.textViewComText);
		TextView dbDotNetText = (TextView) findViewById(R.id.textViewNetText);
		TextView dbTwitterText = (TextView) findViewById(R.id.textViewDBTwitterText);
		TextView dbFacebookText = (TextView) findViewById(R.id.textViewDBFacebookText);
		ImageView dbTMIcon = (ImageView) findViewById(R.id.imageViewDBTM);
		ImageView dbDomainIcon = (ImageView) findViewById(R.id.imageViewDBDomain);
		ImageView dbTwitterIcon = (ImageView) findViewById(R.id.imageViewDBTwitter);
		ImageView dbFacebookIcon = (ImageView) findViewById(R.id.imageViewDBFacebook);
		
		dbNameText.setText(savedName.getName());
		dbRationaleText.setText(savedName.getRationale());
		dbDotComText.setText(savedName.getDotComAvailability());
		dbDotNetText.setText(savedName.getDotNetAvailability());
		
		if (savedName.getTrademarkCount() == 1) {
			dbTrademarkText.setText(String.valueOf(savedName.getTrademarkCount()) + " possible conflict found.");
			dbTrademarkText.setTextColor(Color.parseColor("#ff6619"));
		} else if (savedName.getTrademarkCount() > 1) {
			dbTrademarkText.setText(String.valueOf(savedName.getTrademarkCount()) + " possible conflicts found.");
			dbTrademarkText.setTextColor(Color.RED);
		} else {
			dbTrademarkText.setText("The trademark appears to be available.");
			dbTrademarkText.setTextColor(Color.parseColor("#008000"));
		}
		
		if (savedName.getDotComAvailability().equals("available")) {
			dbDotComText.setTextColor(Color.parseColor("#008000"));
		} else if (savedName.getDotComAvailability().equals("taken")){
			dbDotComText.setTextColor(Color.parseColor("#FF0000"));
		} else {
			dbDotComText.setTextColor(Color.parseColor("#ff6619"));
		}
		
		if (savedName.getDotNetAvailability().equals("available")) {
			dbDotNetText.setTextColor(Color.parseColor("#008000"));
		} else if (savedName.getDotNetAvailability().equals("taken")){
			dbDotNetText.setTextColor(Color.parseColor("#FF0000"));
		} else {
			dbDotNetText.setTextColor(Color.parseColor("#ff6619"));
		}
		
		if (savedName.getIsTwitterUsernameAvailable().equals("true")) {
			dbTwitterText.setText("The Twitter screenname seems to be available.");
			dbTwitterText.setTextColor(Color.parseColor("#008000"));
		} else {
			dbTwitterText.setText("The Twitter screenname seems to be unavailable.");
			dbTwitterText.setTextColor(Color.parseColor("#FF0000"));
		}
		
		if (savedName.getIsFacebookUrlAvailable().equals("true")) {
			dbFacebookText.setText("The Twitter screenname seems to be available.");
			dbFacebookText.setTextColor(Color.parseColor("#008000"));
		} else {
			dbFacebookText.setText("The Facebook screenname seems to be unavailable.");
			dbFacebookText.setTextColor(Color.parseColor("#FF0000"));
		}
		
		Button savedBackButton = (Button) findViewById(R.id.buttonDBBack);
		savedBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		dbTMIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentTM = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uspto.gov"));
				startActivity(intentTM);
			}
		});
		
		dbDomainIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				RestfulHandler namecheap = new RestfulHandler(name.getName());
//				Intent intentDomain = new Intent(Intent.ACTION_VIEW, Uri.parse(namecheap.getNameCheapUrl()));
				Intent intentDomain = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.godaddy.com/domains/search-new.aspx?isc=gofd2001aj&ci=87929"));
				startActivity(intentDomain);
			}
		});
		
		dbTwitterIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/signup"));
				startActivity(intentTwitter);
			}
		});
		
		dbFacebookIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/help/211813265517027?sr=6&sid=0RexW1KVdne9XlBiG"));
				startActivity(intentFacebook);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_name_detail, menu);
		return true;
	}
	////////////////////////////////////////

	@Override
	public boolean onOptionsItemSelected(MenuItem item){//Overflow box menu items
	    switch(item.getItemId()) {// on click starts intents
	    case R.id.camera:
	        try {
				CameraIntent();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        break;
	    
	    case R.id.gallery:
	    	Intent intent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    	startActivity(intent);
	    	break;
	    }
	    return true;
	}
	static final int TAKEN_PHOTO =1;
	String mCurrentPhotoPath;
	static final int REQUEST_TAKE_PHOTO = 1;
	//creates file name
	 private File createImageFile() throws IOException {
	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    	String imageFileName = "JPEG_"+timeStamp+"_";
	    	File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    	File image = File.createTempFile(imageFileName, ".jpg" , storageDir);
	    	mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    	Log.i("path", mCurrentPhotoPath);
	    	return image;
	    }
	 //goes to camera intent
	 private void CameraIntent() throws FileNotFoundException{
		 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    // Ensure that there's a camera activity to handle the intent
		    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		        // Create the File where the photo should go
		        File photoFile = null;
		        try {
		            photoFile = createImageFile();
		           
		        } catch (IOException ex) { 
		        }
		        // Continue only if the File was successfully created
		        if (photoFile != null) {
		        	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			    	String imageFileName = "JPEG_"+timeStamp+"_";
		            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
		                    Uri.fromFile(photoFile));
		            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
		            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photoFile)));//lets gallery know about pic
		            }
		        }
		    }

}
