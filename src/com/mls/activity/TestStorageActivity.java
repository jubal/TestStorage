package com.mls.activity;

import java.security.PublicKey;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestStorageActivity extends Activity {
    /** Called when the activity is first created. */
	private EditText nameText;
	private EditText ageText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        nameText=(EditText)findViewById(R.id.name);
        ageText=(EditText)findViewById(R.id.age);
        
        Button saveButton=(Button)findViewById(R.id.save);
        Button readButton=(Button)findViewById(R.id.read);
        
        saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences=getSharedPreferences("user", PreferenceActivity.MODE_WORLD_READABLE);
				Editor editor=preferences.edit();
				editor.putString("name", nameText.getText().toString());
				String ageString=ageText.getText().toString();
				if (ageString!=null ||"".equals(ageString.trim())) {
					editor.putInt("age", new Integer(ageString));
				}
				preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
					
					@Override
					public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
							String key) {
						// TODO Auto-generated method stub
						String string="Changed: "+key;
						showToast(string);
						if (key.equals("name")) {
							showToast("New name is: " + sharedPreferences.getString(key, ""));
						}
					}
				});
				editor.commit();
				
			}
		});
        
        readButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences=getSharedPreferences("user", PreferenceActivity.MODE_PRIVATE);
				String name=preferences.getString("name", "");
				Integer age=preferences.getInt("age", 0);
				nameText.setText(name);
				ageText.setText(age+"");
				Toast.makeText(getApplicationContext(), "Read Succeeded", Toast.LENGTH_SHORT).show();
			}
		});
        
    }
    public void showToast(String contentString){
    	Toast.makeText(getApplicationContext(), contentString, Toast.LENGTH_SHORT).show();
    }
}