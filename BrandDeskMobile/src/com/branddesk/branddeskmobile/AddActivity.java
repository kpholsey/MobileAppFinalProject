package com.branddesk.branddeskmobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.branddeskmobile.R;

public class AddActivity extends Activity implements
		GetTrademarkInfoAsyncTask.Trademark, GetDomainInfoAsyncTask.Domain,
		GetTwitterInfoAsyncTask.Twitter, GetFacebookInfoAsyncTask.Facebook {
	private String name;
	private String rationale;
	private Integer trademarkCount;
	private String dotComAvailability;
	private String dotNetAvailability;
	private boolean isTwitterAvailable = false;
	private boolean isFacebookAvailable = false;
	private boolean isTradeMarkDone = false;
	private boolean isDomainDone = false;
	private boolean isTwitterDone = false;
	private boolean isFacebookDone = false;
	ArrayList<Name> nameList = new ArrayList<Name>();
	final static String NAME_LIST_KEY = "Name List";
	protected static ProgressDialog dialog;
	EditText editTextName;
	EditText editTextRationale;
	Button screenButton;
	TextView clearFields;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		dialog = new ProgressDialog(AddActivity.this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.setMessage("Checking and Adding Your Name...");
		editTextName = (EditText) findViewById(R.id.editTextName);
		editTextRationale = (EditText) findViewById(R.id.editTextRationale);
		Button addNameButton = (Button) findViewById(R.id.addNameButton);
		clearFields = (TextView) findViewById(R.id.textViewClearFields);

		editTextName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				name = editTextName.getText().toString().trim();
			}
		});

		editTextRationale.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				rationale = editTextRationale.getText().toString().trim();
			}
		});

		addNameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editTextName.getText().toString().length() == 0) {
					Toast.makeText(AddActivity.this, "Please enter a name.",
							Toast.LENGTH_SHORT).show();
				} else {
					RestfulHandler searchUrl = new RestfulHandler(name);
					searchUrl.getMarkerUrl();
					dialog.show();
					new GetTrademarkInfoAsyncTask(AddActivity.this)
							.execute(searchUrl.getMarkerUrl());
					new GetDomainInfoAsyncTask(AddActivity.this)
							.execute(searchUrl.getDomainUrl());
					new GetTwitterInfoAsyncTask(AddActivity.this)
							.execute(searchUrl.getTwitterUrl());
					new GetFacebookInfoAsyncTask(AddActivity.this)
							.execute(searchUrl.getFacebookUrl());
				}
			}
		});

		screenButton = (Button) findViewById(R.id.screenButton);
		screenButton.setEnabled(false);
		screenButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Log.d("checks", nameList.toString());
				Intent screenIntent = new Intent(AddActivity.this,
						ScreenActivity.class);
				screenIntent.putExtra(NAME_LIST_KEY, nameList);
				startActivity(screenIntent);
			}
		});

		Button resetListButton = (Button) findViewById(R.id.clearFieldsButton);
		resetListButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (nameList.size() > 0) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getContext());
					alertDialogBuilder.setTitle("Erasing All Names!");
					alertDialogBuilder
							.setMessage(
									"Click Continue to erase all your current names. This cannot be undone!")
							.setCancelable(false)
							.setPositiveButton("Continue",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog, int id) {
											resetAllFields();
											screenButton.setEnabled(false);
											nameList.clear();
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				} else {
					Toast.makeText(getContext(), "There's nothing to clear.", Toast.LENGTH_SHORT).show();
				}
			}
		});

		clearFields.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editTextName.setText("");
				editTextRationale.setText("");
			}
		});
	}

	@Override
	public void setupTrademarkData(Integer result) {
		this.trademarkCount = result;
		isTradeMarkDone = true;
	}

	@Override
	public void setupDomainData(
			ArrayList<com.branddesk.branddeskmobile.Domain> result) {
		this.dotComAvailability = result.get(0).getAvailability();
		this.dotNetAvailability = result.get(1).getAvailability();
		isDomainDone = true;
	}

	public void setupTwitterData(Integer result) {
		if (result == 1) {
			this.isTwitterAvailable = false;
		} else {
			this.isTwitterAvailable = true;
		}
		isTwitterDone = true;
	}

	public void setupFacebookData(Integer result) {
		if (result == 1) {
			this.isFacebookAvailable = false;
		} else {
			this.isFacebookAvailable = true;
		}
		isFacebookDone = true;
	}

	public boolean checkAllFlags() {
		boolean flagCheck;
		if (isTradeMarkDone && isDomainDone && isTwitterDone && isFacebookDone) {
			flagCheck = true;
		} else {
			flagCheck = false;
		}
		return flagCheck;
	}

	public void resetAllFields() {
		this.trademarkCount = null;
		this.isTradeMarkDone = false;
		this.isDomainDone = false;
		this.isFacebookDone = false;
		this.isTwitterDone = false;
		this.isTwitterAvailable = false;
		this.isFacebookAvailable = false;
		this.dotComAvailability = null;
		this.dotNetAvailability = null;
		editTextName.setText("");
		editTextRationale.setText("");
	}

	public void addNameToList() {
		Name name = new Name();
		name.setName(this.name);
		name.setRationale(this.rationale);
		name.setTrademarkCount(this.trademarkCount);
		name.setDotComAvailability(this.dotComAvailability);
		name.setDotNetAvailability(this.dotNetAvailability);
		name.setTwitterUsernameAvailable(this.isTwitterAvailable);
		name.setFacebookUrlAvailable(this.isFacebookAvailable);
		nameList.add(name);
		screenButton.setEnabled(true);
		dialog.dismiss();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public Context getContext() {
		return this;
	}
	///////////////////////////////////////////////////////

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
