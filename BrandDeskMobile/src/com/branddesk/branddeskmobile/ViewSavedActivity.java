package com.branddesk.branddeskmobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.branddeskmobile.R;

public class ViewSavedActivity extends Activity {
	private static DatabaseDataManager dataManager;
	ArrayList<SavedName> savedNames = new ArrayList<SavedName>();
	final static String SAVED_DETAILED_NAME_KEY = "Saved Name Details";
	ListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_saved);

		dataManager = new DatabaseDataManager(getBaseContext());
		savedNames = (ArrayList<SavedName>) dataManager.getAllSavedNames();

		ListView listView = (ListView) findViewById(R.id.listViewSaved);
		listAdapter = new SavedNameAdapter(ViewSavedActivity.this, savedNames);
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent savedNameDetailIntent = new Intent(ViewSavedActivity.this, SavedNameDetailActivity.class);
				savedNameDetailIntent.putExtra(SAVED_DETAILED_NAME_KEY, savedNames.get(position));
				startActivity(savedNameDetailIntent);
			}
		});

		Button viewSavedBackButton = (Button) findViewById(R.id.savedBackButton);
		viewSavedBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button deleteAllButton = (Button) findViewById(R.id.savedDeleteAllButton);
		deleteAllButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ViewSavedActivity.this);
				alertDialogBuilder
						.setTitle("Erasing All Names From the Database!");
				alertDialogBuilder
						.setMessage(
								"Click Continue to erase all your saved names.\nThis cannot be undone! " +
								"\nUpon deletion you will be returned to the previous screen.")
						.setCancelable(false)
						.setPositiveButton("Continue",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dataManager.deleteAllSavedNames();
										finish();
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_saved, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		dataManager.close();
		super.onDestroy();
	}
	/////////////////////////////////////

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
