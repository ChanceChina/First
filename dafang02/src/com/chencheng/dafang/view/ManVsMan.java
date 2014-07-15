package com.chencheng.dafang.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;
import bean.ChessPiece;

import com.chencheng.dafang.Main;
import com.chencheng.dafang.R;
import com.chencheng.dafang.util.Constant;
import com.chencheng.dafang.util.Util;

public class ManVsMan extends ChessBoard{
	
	public ManVsMan(Context context) {
		super(context);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d("surfaceCreated", "chessborad----surfaceCreated");
		// 棋盘背景图片
		Constant.BM_BACKGROUND = BitmapFactory.decodeResource(getResources(),R.drawable.dafang);
		// 棋盘左上角坐标
		Constant.ORIGIN_X = Constant.SCREEN_WIDTH * 40 / 480;
		Constant.ORIGIN_Y = Constant.SCREEN_HEIGHT * 200 / 800;
		//每个方格的宽度与高度
		Constant.X_SPAN = (Constant.SCREEN_WIDTH - 2 * Constant.ORIGIN_X) / 5;
		Constant.Y_SPAN = (Constant.SCREEN_HEIGHT - 2 * Constant.ORIGIN_Y) / 5;
		//泥巴棋子
		Constant.BM_BLACK_CHESS = BitmapFactory.decodeResource(getResources(), R.drawable.black_chess);
		Constant.BM_BLACK_CHESS_RED = BitmapFactory.decodeResource(getResources(), R.drawable.black_chess_red);
		//树叶棋子
		Constant.BM_WHITE_CHESS = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess);
		Constant.BM_WHITE_CHESS_RED = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess_red);
		//重玩,薅棋，悔棋按钮
		Constant.BM_RESTART_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
		Constant.BM_REGRET_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.regret);
		Constant.BM_REMOVE_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.bg_remove);
		btn_origin_x = Constant.SCREEN_WIDTH/6-Constant.BM_RESTART_BTN.getWidth()/2;
		btn_origin_y = Constant.SCREEN_HEIGHT*200/800/4-Constant.BM_RESTART_BTN.getHeight()/2;
		//根据设置获得 第一个是什么棋子
		currentIsLeaf = Constant.IS_LEAF_CHESS_FOR_PLAYER01 ? true:false;
		putTimes =1;		
		drawBackground();
	}


	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//得到触摸点的坐标
		int tempX=(int)event.getX();
		int tempY=(int)event.getY();
		
		//重玩
		if( tempX >btn_origin_x &&tempX < (btn_origin_x+Constant.BM_REGRET_BTN.getWidth()) 
				&& tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			reset();
		}
		//薅棋
		if( tempX >(btn_origin_x+Constant.SCREEN_WIDTH/3*2) && tempX < (btn_origin_x+Constant.SCREEN_WIDTH/3*2+Constant.BM_REGRET_BTN.getWidth()) &&
				tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			if(phase==1){
				Toast.makeText(getContext(), "~棋还没搁满，还不能薅棋~", Toast.LENGTH_SHORT).show();
				return  true;
			}else{
				remove();
			}
		}
		//悔棋
		if(tempX >(btn_origin_x+Constant.SCREEN_WIDTH/3) &&tempX < (btn_origin_x+Constant.SCREEN_WIDTH/3+Constant.BM_REGRET_BTN.getWidth()) 
				&& tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			if(Constant.CAN_REGRET){
				regret();
			}else{
				Toast.makeText(getContext(), "已选择 ~不带悔棋模式~", Toast.LENGTH_SHORT).show();
				return  true;
			}
		}
		
		//根据触摸事件得到落子坐标
		tempX = Util.getTempX(tempX);
		tempY = Util.getTempY(tempY);
		
		//走棋
		if(phase==3 && moveModel){
			move(tempX,tempY);
		}
		
		freshCanvas(tempX,tempY);
		
		return super.onTouchEvent(event);
	}

	private void freshCanvas(int tempX, int tempY) {
		Canvas canvas = holder.lockCanvas();
		_drawBackground(canvas);
		if(phase==1){
			drawChesses(tempX,tempY,canvas);
		}else if(phase ==2){
			drawChesses02(tempX,tempY,canvas);
		}else if(phase==3){
			drawChesses02(tempX,tempY,canvas);
		}
		
		showPlayerMessage(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	private void drawChesses(int tempX, int tempY, Canvas canvas) {
		if(tempX != -1 && tempY !=-1){
			if(chesses[tempX][tempY]==null){
				previousChess = currentChess;
				previousPutTimes  =putTimes;
				if(currentIsLeaf){
					chesses[tempX][tempY] = new ChessPiece(Constant.WHITE_CHESS,tempX,tempY);
					putTimes -=1;
					totalChesses +=1;
					whiteChessCounter +=1;
					//判断落子后是否 成方
					putTimes += isFang(chesses[tempX][tempY]);
					if(putTimes ==0){
						currentIsLeaf =false;
						putTimes =1;
					}
				}else{
					chesses[tempX][tempY] = new ChessPiece(Constant.BLACK_CHESS,tempX,tempY);
					putTimes -=1;
					totalChesses +=1;
					blackChessCounter +=1;
					//判断落子后是否 成方
					putTimes += isFang(chesses[tempX][tempY]);
					if(putTimes ==0){
						currentIsLeaf =true;
						putTimes =1;
					}
				}
				currentChess =chesses[tempX][tempY];
			}
		}
		//画所有的棋子
		for(ChessPiece chess[] : chesses){
			for(ChessPiece ch : chess){
				if(ch!=null){
					canvas.drawBitmap(getBitMap(ch),ch.getX(ch.getRowIndex()),ch.getY(ch.getColIndex()), null);
				}
			}
		}
		Log.v("total",totalChesses+"");
		if(totalChesses==36){//当棋搁满，开始第二阶段
			phase=2;
			previousChess=null;
			//后搁棋的人先薅棋
			currentIsLeaf = Constant.IS_LEAF_CHESS_FOR_PLAYER01? false:true;
			putTimes =2;
			Toast.makeText(getContext(), "棋搁满了，开始薅棋，走棋", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void drawChesses02(int tempX, int tempY, Canvas canvas){
		if(tempX != -1 && tempY !=-1){
			currentChess=chesses[tempX][tempY];

		}
		for(ChessPiece chess[] : chesses){
			for(ChessPiece ch : chess){
				if(ch!=null){
					canvas.drawBitmap(getBitMap(ch),ch.getX(ch.getRowIndex()),ch.getY(ch.getColIndex()), null);
				}
			}
		}
	}


	private Bitmap getBitMap(ChessPiece ch) {
		if(ch.getName().equals(Constant.BLACK_CHESS)){
			if(currentChess!=null){
				return (ch.getRowIndex()==currentChess.getRowIndex() && ch.getColIndex()==currentChess.getColIndex())? Constant.BM_BLACK_CHESS_RED:Constant.BM_BLACK_CHESS;
			}else{
				return  Constant.BM_BLACK_CHESS;
			}
		}else{
			if(currentChess !=null){
				return (ch.getRowIndex()==currentChess.getRowIndex() && ch.getColIndex()==currentChess.getColIndex())? Constant.BM_WHITE_CHESS_RED:Constant.BM_WHITE_CHESS;
			}else{
				return  Constant.BM_WHITE_CHESS;
			}
		}
		
	}

	/**
	 * 绘制游戏界面背景
	 */
	private void drawBackground() {
		Canvas canvas = holder.lockCanvas();
		_drawBackground(canvas);
		showPlayerMessage(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	/**
	 * 
	 * @param canvas
	 */
	private void _drawBackground(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		Rect bgRect = new Rect(0,0,Constant.BM_BACKGROUND.getWidth(),Constant.BM_BACKGROUND.getHeight());
		Rect dst = new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);
		//绘制背景图片
		canvas.drawBitmap(Constant.BM_BACKGROUND, bgRect,dst, null);
		//绘制重玩button 和悔棋 button 薅掉 button
		canvas.drawBitmap(Constant.BM_RESTART_BTN, btn_origin_x,btn_origin_y, null);
		canvas.drawBitmap(Constant.BM_REGRET_BTN, btn_origin_x+Constant.SCREEN_WIDTH/3,btn_origin_y, null);
		canvas.drawBitmap(Constant.BM_REMOVE_BTN, btn_origin_x+Constant.SCREEN_WIDTH/3*2,btn_origin_y,null);
	}
	
	/**
	 * 
	 * @param canvas
	 */
	private void showPlayerMessage(Canvas canvas) {
		String text = "该 ";
		Paint pen = new Paint();
		pen.setColor(Color.RED);
		pen.setTextSize((float) (Constant.BM_BLACK_CHESS.getWidth()*0.5));
		float width = Constant.SCREEN_WIDTH*120/480;
		float height = Constant.SCREEN_HEIGHT*131/800;
		canvas.drawText(text, width,height, pen);
		if(phase==1){
			String text02 = "  搁棋, 可搁  "+putTimes+" 子";
			canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
								(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
			canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
		}else if(phase==2){
			canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
					(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
			String text02="薅"+putTimes+"颗棋";
			canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
		}else if(phase==3){
			if(moveModel){
				String text02 = "  走一步棋";
				canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
									(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
				canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
			}else{
				canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
						(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
				String text02="薅"+putTimes+"颗棋";
				canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
			}
		}
	}

	private int isFang(ChessPiece chess) {
		int times = 0;
		times += Util.isDafang(chess);
		times += Util.isSanXie(chess);
		times += Util.isSiXie(chess);
		times += Util.isWuXie(chess);
		times += Util.isTongZhou(chess);
		times += Util.isDragon(chess);
		Log.d("isFang",times+"");
		return times;
	}
	
	private void reset() {
		previousPutTimes = 1;//当前执棋人可下几颗棋
		totalChesses = 0;   //棋盘上的总棋子总数
		whiteChessCounter = 0;//树叶棋子的总数
		blackChessCounter = 0;//泥巴棋子的总数
		putTimes = 1;
		phase =1;
		
		currentIsLeaf = Constant.IS_LEAF_CHESS_FOR_PLAYER01 ? true:false;
		chesses = new ChessPiece[6][6]; 
		drawBackground();
		
	}
	private void remove() {
		if(phase==1){
			Toast.makeText(getContext(), "~棋还没搁满类，不能薅~", Toast.LENGTH_SHORT).show();
			return;
		}else if(phase==2){
			if(currentChess != null){
				//不能薅自己的棋子
				if(currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					Toast.makeText(getContext(), "不能薅自己类棋 ~~ ？", Toast.LENGTH_SHORT).show();
					return;
				}
				if(isFang(currentChess)>=1){
					Toast.makeText(getContext(), "已经成方的棋不能薅掉 ~~", Toast.LENGTH_SHORT).show();
					//putTimes=1;
					return;
				}
				chesses[currentChess.getRowIndex()][currentChess.getColIndex()]=null;
				putTimes -=1;
				totalChesses -=1;
				if(currentChess.getName().equals(Constant.BLACK_CHESS)){
					blackChessCounter -=1;
					if(putTimes==0){
						putTimes=2;
						currentIsLeaf = false;//树叶棋 薅完 小棍棋 之后该 小棍棋薅棋
					}
				}else{
					whiteChessCounter -=1;
					if(putTimes==0){
						putTimes=2;
						currentIsLeaf = true;
					}
				}
				if(totalChesses==32){
					putTimes=1;
					phase = 3;
					moveModel=true;
					Toast.makeText(getContext(), "第三阶段，走棋", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getContext(), "没有棋选中，你想薅哪个？", Toast.LENGTH_SHORT).show();
				return;
			}
		}else if(phase ==3 && !moveModel){
			if(currentChess != null){
				//不能薅自己的棋子
				if(currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					Toast.makeText(getContext(), "no zuo no die, 不能薅自己的棋！~~~", Toast.LENGTH_SHORT).show();
					return;
				}
				if(isFang(currentChess)>=1){
					Toast.makeText(getContext(), "已经成方的棋不能薅掉 ~~~", Toast.LENGTH_SHORT).show();
					return;
				}
				chesses[currentChess.getRowIndex()][currentChess.getColIndex()]=null;
				putTimes -=1;
				totalChesses -=1;
				if(currentChess.getName().equals(Constant.BLACK_CHESS)){
					blackChessCounter -=1;
					//赢得比赛
					if(blackChessCounter<3){
						win();
					}
					if(putTimes==0){
						putTimes=1;
						currentIsLeaf = false;
						moveModel=true;
					}
				}else{
					whiteChessCounter -=1;
					//赢得比赛
					if(whiteChessCounter<3){
						win();
					}
					if(putTimes==0){
						putTimes=1;
						currentIsLeaf = true;
						moveModel=true;
					}
				}
			}else{
				Toast.makeText(getContext(), "没有棋选中，你想薅哪个？", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		freshCanvas(-1, -1);
	}

	private void regret() {
		if(phase==1){
			if(currentChess!=null && previousChess!=null){
				 if(!currentChess.equals(previousChess)){
					 chesses[currentChess.getRowIndex()][currentChess.getColIndex()]=null;
						totalChesses -=1;
						if(currentChess.getName().equals(Constant.BLACK_CHESS)){
								currentIsLeaf = false;
								this.blackChessCounter -=1;
						}else{
							currentIsLeaf = true;
							this.whiteChessCounter -=1;
						}
						putTimes  =previousPutTimes;
						currentChess = previousChess ;
						freshCanvas(-1, -1);
				 }else{
					 Toast.makeText(this.getContext(), "只能悔一步棋！",Toast.LENGTH_SHORT ).show();
				 }
			}else{
				reset();
			}
		}else if(phase==2){
			
		}else if(phase==3){
			
		}
		
	}
	

	private void move(int tempX,int tempY) {
		if(tempX!=-1&&tempY!=-1){
			Log.v("before move",putTimes+"");
			if(chesses[tempX][tempY]!=null){
				//当选 中的棋与应走的棋不一致时
				if(!chesses[tempX][tempY].getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					//Log.v("chosen &　infact",currentChess.getName() + ""+currentIsLeaf );
					Toast.makeText(getContext(), "拿人家棋乱走啥类？只能走自个的棋", Toast.LENGTH_SHORT).show();
					return ;
				}
			}else{
				if(currentChess!=null){
					if(!currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
						Toast.makeText(getContext(), "拿人家棋乱走啥类？只能走自个的棋~~", Toast.LENGTH_SHORT).show();
						return ;
					}
					boolean canMove =((Math.abs(currentChess.getRowIndex()-tempX)==1) && (currentChess.getColIndex()-tempY ==0))
							||((Math.abs(currentChess.getColIndex()-tempY)==1) && (currentChess.getRowIndex()-tempX ==0));
					Log.v("result",""+canMove);
					
					if(canMove){
						Log.v("chang position start","each other");
						chesses[tempX][tempY] = new ChessPiece(currentChess.getName(), tempX, tempY);
						chesses[currentChess.getRowIndex()][currentChess.getColIndex()] =null;
						putTimes += isFang(chesses[tempX][tempY]);
						Log.v("aftter move tiems",putTimes+"");
						if(putTimes > 1){
							putTimes -=1;//去掉原本的1
							//如果成方，帽进入薅棋模式
							moveModel =false;
						}else{
							//如果没有成方，则换由对方走棋
							currentIsLeaf= currentChess.getName().equals(Constant.BLACK_CHESS)? true:false;
							Log.v("aftter move currentIsLeaf",currentIsLeaf+"");
						}
						Log.v("chang position end","end");
						//chosenChess =chesses[tempX][tempY] ;
					}else{
						Toast.makeText(getContext(), "步子不能迈太大，容易扯着蛋，每次只能走一格！", Toast.LENGTH_SHORT).show();
						return;
					}
				}else{
					Toast.makeText(getContext(), "请选中你要走的棋", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}
	}

	public void win(){
		new AlertDialog.Builder(getContext()).setTitle("赢了")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setMessage("~~~"+(currentIsLeaf? "树叶棋":"小棍棋") +"赢了~~~")
		.setPositiveButton("~再玩一盘~", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				reset();
			}
		})
		.setNegativeButton("~休息一会~", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(getContext(),Main.class);
				getContext().startActivity(intent);
				((Activity) getContext()).finish();
			}
		})
		.show();
	}
	
	

	
}
