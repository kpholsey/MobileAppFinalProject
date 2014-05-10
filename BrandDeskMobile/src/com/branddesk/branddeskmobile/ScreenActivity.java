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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.branddeskmobile.R;

public class ScreenActivity extends Activity {
	protected ArrayList<Name> names = new ArrayList<Name>();
	final static String DETAILED_NAME_KEY = "Name Details";
	ArrayList<SavedName> peek = new ArrayList<SavedName>();
	private static DatabaseDataManager dataManager;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen);
		
		Bundle extras = getIntent().getExtras();
		if(getIntent().getExtras() != null) {
				names = (ArrayList<Name>) extras.getSerializable(AddActivity.NAME_LIST_KEY);
		}
		dataManager = new DatabaseDataManager(getBaseContext());
		ListView listView = (ListView) findViewById(R.id.listViewSaved);
		ListAdapter listAdapter = new NameAdapter(ScreenActivity.this, names);
		listView.setAdapter(listAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent detailedNameIntent = new Intent(ScreenActivity.this, DetailedNameActivity.class);
				detailedNameIntent.putExtra(DETAILED_NAME_KEY, names.get(position));
				startActivity(detailedNameIntent);
			}
		});
		
		Button screenExitButton = (Button)findViewById(R.id.screenExitButton);
		screenExitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.action_saved:
			navigateToSavedNames();
			break;
		
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
	///////////////////////////////////////////////////////////////

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
