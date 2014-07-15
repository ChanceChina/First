package com.chencheng.dafang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SpecifyActivity extends Activity{
	Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.specification);
		btn_back = (Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View btn) {
				Intent intent = new Intent(SpecifyActivity.this,Main.class);
				SpecifyActivity.this.startActivity(intent);
				SpecifyActivity.this.finish();
				Log.v(" after intent","----**(*");
			}
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent(SpecifyActivity.this,Main.class);
			SpecifyActivity.this.startActivity(intent);
			SpecifyActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
