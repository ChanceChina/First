package com.chencheng.dafang;



import com.chencheng.dafang.util.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{
	private Button btnNew,btnConfig,btnSpecify,btnQuit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//播放背景音乐 
//		MediaPlayer myPlayer = MediaPlayer.create(this, R.raw.music_bg);
//		myPlayer.start();
//		myPlayer.setLooping(true);
		
		//读取sharedPreferences中的数据
		SharedPreferences sf = this.getSharedPreferences("chenguan_dafang_config", Context.MODE_PRIVATE);
		Constant.IS_MAN_VS_MACHINE = sf.getBoolean("IS_MAN_VS_MACHINE", false);
		Log.v("main sf--renji", ""+Constant.IS_MAN_VS_MACHINE);
		Constant.IS_PLAYER_FIRST = sf.getBoolean("isPlayerFirst", false);
		Log.v("main sf--isplafirst", ""+Constant.IS_PLAYER_FIRST);
		Constant.IS_MUSIC = sf.getBoolean("isMusic", false);
		Constant.CAN_REGRET = sf.getBoolean("CANREGRET", false);
		Log.v("main sf--CANREGRET", ""+Constant.CAN_REGRET);
		Constant.IS_LEAF_CHESS_FOR_PLAYER01 = sf.getBoolean("IS_LEAF_CHESS_FOR_PLAYER01", false);
		Log.v("main sf--IS_LEAF_CHESS_FOR_PLAYER01", ""+Constant.IS_LEAF_CHESS_FOR_PLAYER01);
		
		
		btnNew = (Button) findViewById(R.id.btn_new);
		btnConfig = (Button) findViewById(R.id.btn_config);
		btnSpecify = (Button) findViewById(R.id.btn_specify);
		btnQuit = (Button) findViewById(R.id.btn_quit);
		
		btnNew.setOnClickListener(this);
		btnConfig.setOnClickListener(this);
		btnSpecify.setOnClickListener(this);
		btnQuit.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View view) {
		if(view == btnNew){
			Intent intent = new Intent(Main.this,ChessActivity.class);
//			intent.putExtra("isNewGame","newgame");
			Main.this.startActivity(intent);
			Main.this.finish();
		}else if(view==btnConfig){
			Intent intent = new Intent(Main.this,ConfigActivity.class);
			Main.this.startActivity(intent);
			Main.this.finish();
		}else if(view==btnSpecify){
			Intent intent = new Intent(Main.this,SpecifyActivity.class);
			Main.this.startActivity(intent);
			Main.this.finish();
		}else if(view == btnQuit){
			quitMethod();
		}
	}
	/**
	 * 监听键盘的返回键
	 */
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.v("back click", "I'm clicked!");
			quitMethod();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void quitMethod(){
		new AlertDialog.Builder(Main.this).setTitle("退出")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setMessage("~~~再玩会儿中不中？~~~")
		.setPositiveButton("~不中,不玩了~", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Main.this.finish();
				System.exit(0);
			}
		})
		.setNegativeButton("~中，再玩会~", null)
		.show();
	}
	
	

}
