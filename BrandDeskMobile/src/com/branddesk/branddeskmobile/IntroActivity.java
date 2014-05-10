package com.branddesk.branddeskmobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.branddeskmobile.R;

public class IntroActivity extends Activity {
	private static DatabaseDataManager dataManager;
	ArrayList<SavedName> peek = new ArrayList<SavedName>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		dataManager = new DatabaseDataManager(getBaseContext());
		Button addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent addActivityIntent = new Intent(IntroActivity.this,
						AddActivity.class);
				startActivity(addActivityIntent);
			}
		});

		Button viewSavedButton = (Button) findViewById(R.id.viewSavedButton);
		viewSavedButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				peek = (ArrayList<SavedName>) dataManager.getAllSavedNames();
				Log.d("intro", peek.size() + "");
				if (peek.size() > 0) {
					Intent viewSavedActivityIntent = new Intent(
							IntroActivity.this, ViewSavedActivity.class);
					startActivity(viewSavedActivityIntent);
				} else {
					Toast.makeText(getBaseContext(),
							"You haven't saved any names yet.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}
	///////////////////////////////////////
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
