package com.julie.gridimagesearch;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	private ImageResult result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		ivImage.setImageUrl(result.getFullUrl());
	}

	//method for action bar email action
		public void onEmail(MenuItem mi){
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.putExtra(Intent.EXTRA_TEXT, result.getFullUrl().toString());
			emailIntent.setType("text/plain");
			
			// Verify it resolves
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
			boolean isIntentSafe = activities.size() > 0;

			// Start an activity if it's safe
			if (isIntentSafe) {
			    startActivity(Intent.createChooser(emailIntent, "Send Image to..."));
			}
			
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

}
