package com.julie.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingActivity extends Activity implements OnItemSelectedListener  {
	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	EditText etSiteFilter;
	Button btnSave;
	Filter filter;
	private String color = "";
	private String size = "";
	private String site = "";
	private String type = "";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setupViews();
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.size_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
		        R.array.color_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.type_array, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner and set onClickListener
		spImageSize.setAdapter(adapter);
		spColorFilter.setAdapter(colorAdapter);
		spImageType.setAdapter(typeAdapter);
		spImageSize.setOnItemSelectedListener(this);
		spColorFilter.setOnItemSelectedListener(this);
		spImageType.setOnItemSelectedListener(this);
		
		//Save button Listener
		btnSave.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				site = etSiteFilter.getText().toString();
				filter = new Filter(size, color, type, site);
				//Log.d("DEBUG", filter.getColor().toString()+filter.getSize().toString());
				Intent i = new Intent();
				/**i.putExtra("color",color);
				i.putExtra("size",size);
				i.putExtra("type",type);
				i.putExtra("site",site);
				**/
				i.putExtra("filter", filter);
				setResult(RESULT_OK, i);
				finish();
			}
			
		});
	}

	private void setupViews() {
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		btnSave = (Button) findViewById(R.id.btnSave);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long rowId) {
		 color = String.valueOf(spColorFilter.getSelectedItem());
		 size = String.valueOf(spImageSize.getSelectedItem());
		 type = String.valueOf(spImageType.getSelectedItem());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
