package com.julie.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	Filter f;
	private String size=""; 
	private String color=""; 
	private String site="";
	private String type="";
	private String query="";
	private int start = 0;
	AsyncHttpClient Client;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		
		imageAdapter  = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		gvResults.setOnScrollListener(new EndlessScrollListener(){

			@Override
			public void loadMore(int page, int totalItemsCount) {
				//Log.d("DEBUG", "page "+page+" totalItemsCount "+totalItemsCount+" start "+start);
				start=(page*6)+1;
				connect();
			}
			
		});
		
		
		
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result",imageResult);
				startActivity(i);
			}
			
		});
		
		
	}


	public void onImageSearch(View v){
		query = etQuery.getText().toString();
        Toast.makeText(this, "Searching "+query, Toast.LENGTH_LONG).show();
        imageResults.clear();
        //Connection Client
		Client = new AsyncHttpClient();
		
		for(int i =0;i < 3; i++){
			start = start+(i*6);
		connect();
		
		}
	}


	private void connect() {
		Client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
				"start=" + start +"&v=1.0" +
				"&as_sitesearch="+  Uri.encode(site) +
				"&imgcolor="+ color +
				"&imgsz=" + size +
				"&imgtype=" + type +
				"&q=" + Uri.encode(query),
				//Response Handler
				new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					//Log.d("DEBUG", imageResults.toString());
							
				} catch(JSONException e) {
					e.printStackTrace();					
				}
				
			}
		});
		
	}


	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	//method for action bar setting action
	public void onComposeAction(MenuItem mi){
		Intent i = new Intent(getApplicationContext(),SettingActivity.class);
        startActivityForResult(i, 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		 
     		if (resultCode == RESULT_OK && requestCode == 0) {
     		 	Filter f = (Filter) i.getSerializableExtra("filter");
     		 	color = f.getColor();
     		 	size = f.getSize();
     		 	type = f.getType();
     		 	site = f.getSite();
		  }
		} 

}
