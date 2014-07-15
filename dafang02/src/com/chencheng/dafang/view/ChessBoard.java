package com.chencheng.dafang.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import bean.ChessPiece;

public class ChessBoard extends SurfaceView implements Callback{

	SurfaceHolder holder;
	int btn_origin_x;
	int btn_origin_y;
	ChessPiece[][] chesses = new ChessPiece[6][6];
	int phase =1;
	
	boolean currentIsLeaf = false;
	int putTimes=1;
	int previousPutTimes=0;
	ChessPiece currentChess =null;
	ChessPiece previousChess =null;
	int totalChesses = 0;
	int blackChessCounter =0;
	int whiteChessCounter=0;
	boolean moveModel= false;
	boolean choseModel = false;
	
	/**
	 * Constructor
	 * @param context
	 */
	public ChessBoard(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
	}

	/**
	 * ��ʼ������һЩ���ò���������
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece[][] getChesses() {
		return chesses;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_HOME ||keyCode==KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(getContext()).setTitle("֪ͨ")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setMessage("���е���Ϸ��Ϣ�����ᱻ����,��ȷ���˳���Ϸ?")
			.setPositiveButton("~������~", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					((Activity) getContext()).finish();
					System.exit(0);
				}
			})
			.setNegativeButton("~������~",null)
			.show();
		}
		
		return super.onKeyDown(keyCode, event);
	}

}
