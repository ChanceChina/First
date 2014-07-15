package com.chencheng.dafang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;

import com.chencheng.dafang.util.Constant;
import com.chencheng.dafang.view.ManVsMan;
import com.chencheng.dafang.view.MenVsComputer;

public class ChessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置为无标题栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* 设置为全屏模式（无状态栏） */
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		/* 窗口的宽度 */
		Constant.SCREEN_WIDTH = dm.widthPixels;
		/* 窗口的高度 */
		Constant.SCREEN_HEIGHT = dm.heightPixels;
		
		//设置view
		if(Constant.IS_MAN_VS_MACHINE){
			Constant.CURRENT_CHESSBOARD = new MenVsComputer(this);
		}else{
			Constant.CURRENT_CHESSBOARD = new ManVsMan(this); 
		}
		setContentView(Constant.CURRENT_CHESSBOARD);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent(ChessActivity.this,Main.class);
			ChessActivity.this.startActivity(intent);
			ChessActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
