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
		// ���̱���ͼƬ
		Constant.BM_BACKGROUND = BitmapFactory.decodeResource(getResources(),R.drawable.dafang);
		// �������Ͻ�����
		Constant.ORIGIN_X = Constant.SCREEN_WIDTH * 40 / 480;
		Constant.ORIGIN_Y = Constant.SCREEN_HEIGHT * 200 / 800;
		//ÿ������Ŀ����߶�
		Constant.X_SPAN = (Constant.SCREEN_WIDTH - 2 * Constant.ORIGIN_X) / 5;
		Constant.Y_SPAN = (Constant.SCREEN_HEIGHT - 2 * Constant.ORIGIN_Y) / 5;
		//�������
		Constant.BM_BLACK_CHESS = BitmapFactory.decodeResource(getResources(), R.drawable.black_chess);
		Constant.BM_BLACK_CHESS_RED = BitmapFactory.decodeResource(getResources(), R.drawable.black_chess_red);
		//��Ҷ����
		Constant.BM_WHITE_CHESS = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess);
		Constant.BM_WHITE_CHESS_RED = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess_red);
		//����,޶�壬���尴ť
		Constant.BM_RESTART_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
		Constant.BM_REGRET_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.regret);
		Constant.BM_REMOVE_BTN = BitmapFactory.decodeResource(getResources(), R.drawable.bg_remove);
		btn_origin_x = Constant.SCREEN_WIDTH/6-Constant.BM_RESTART_BTN.getWidth()/2;
		btn_origin_y = Constant.SCREEN_HEIGHT*200/800/4-Constant.BM_RESTART_BTN.getHeight()/2;
		//�������û�� ��һ����ʲô����
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
		//�õ������������
		int tempX=(int)event.getX();
		int tempY=(int)event.getY();
		
		//����
		if( tempX >btn_origin_x &&tempX < (btn_origin_x+Constant.BM_REGRET_BTN.getWidth()) 
				&& tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			reset();
		}
		//޶��
		if( tempX >(btn_origin_x+Constant.SCREEN_WIDTH/3*2) && tempX < (btn_origin_x+Constant.SCREEN_WIDTH/3*2+Constant.BM_REGRET_BTN.getWidth()) &&
				tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			if(phase==1){
				Toast.makeText(getContext(), "~�廹û������������޶��~", Toast.LENGTH_SHORT).show();
				return  true;
			}else{
				remove();
			}
		}
		//����
		if(tempX >(btn_origin_x+Constant.SCREEN_WIDTH/3) &&tempX < (btn_origin_x+Constant.SCREEN_WIDTH/3+Constant.BM_REGRET_BTN.getWidth()) 
				&& tempY >btn_origin_y && tempY < (btn_origin_y+Constant.BM_REGRET_BTN.getHeight())){
			if(Constant.CAN_REGRET){
				regret();
			}else{
				Toast.makeText(getContext(), "��ѡ�� ~��������ģʽ~", Toast.LENGTH_SHORT).show();
				return  true;
			}
		}
		
		//���ݴ����¼��õ���������
		tempX = Util.getTempX(tempX);
		tempY = Util.getTempY(tempY);
		
		//����
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
					//�ж����Ӻ��Ƿ� �ɷ�
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
					//�ж����Ӻ��Ƿ� �ɷ�
					putTimes += isFang(chesses[tempX][tempY]);
					if(putTimes ==0){
						currentIsLeaf =true;
						putTimes =1;
					}
				}
				currentChess =chesses[tempX][tempY];
			}
		}
		//�����е�����
		for(ChessPiece chess[] : chesses){
			for(ChessPiece ch : chess){
				if(ch!=null){
					canvas.drawBitmap(getBitMap(ch),ch.getX(ch.getRowIndex()),ch.getY(ch.getColIndex()), null);
				}
			}
		}
		Log.v("total",totalChesses+"");
		if(totalChesses==36){//�����������ʼ�ڶ��׶�
			phase=2;
			previousChess=null;
			//����������޶��
			currentIsLeaf = Constant.IS_LEAF_CHESS_FOR_PLAYER01? false:true;
			putTimes =2;
			Toast.makeText(getContext(), "������ˣ���ʼ޶�壬����", Toast.LENGTH_SHORT).show();
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
	 * ������Ϸ���汳��
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
		//���Ʊ���ͼƬ
		canvas.drawBitmap(Constant.BM_BACKGROUND, bgRect,dst, null);
		//��������button �ͻ��� button ޶�� button
		canvas.drawBitmap(Constant.BM_RESTART_BTN, btn_origin_x,btn_origin_y, null);
		canvas.drawBitmap(Constant.BM_REGRET_BTN, btn_origin_x+Constant.SCREEN_WIDTH/3,btn_origin_y, null);
		canvas.drawBitmap(Constant.BM_REMOVE_BTN, btn_origin_x+Constant.SCREEN_WIDTH/3*2,btn_origin_y,null);
	}
	
	/**
	 * 
	 * @param canvas
	 */
	private void showPlayerMessage(Canvas canvas) {
		String text = "�� ";
		Paint pen = new Paint();
		pen.setColor(Color.RED);
		pen.setTextSize((float) (Constant.BM_BLACK_CHESS.getWidth()*0.5));
		float width = Constant.SCREEN_WIDTH*120/480;
		float height = Constant.SCREEN_HEIGHT*131/800;
		canvas.drawText(text, width,height, pen);
		if(phase==1){
			String text02 = "  ����, �ɸ�  "+putTimes+" ��";
			canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
								(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
			canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
		}else if(phase==2){
			canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
					(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
			String text02="޶"+putTimes+"����";
			canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
		}else if(phase==3){
			if(moveModel){
				String text02 = "  ��һ����";
				canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
									(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
				canvas.drawText(text02, (float) (width+Constant.BM_BLACK_CHESS.getWidth()*1.7), height, pen);
			}else{
				canvas.drawBitmap(currentIsLeaf? Constant.BM_WHITE_CHESS:Constant.BM_BLACK_CHESS,
						(float) (width+Constant.BM_BLACK_CHESS.getWidth()*0.7),height-Constant.BM_BLACK_CHESS.getHeight()/2,null);
				String text02="޶"+putTimes+"����";
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
		previousPutTimes = 1;//��ǰִ���˿��¼�����
		totalChesses = 0;   //�����ϵ�����������
		whiteChessCounter = 0;//��Ҷ���ӵ�����
		blackChessCounter = 0;//������ӵ�����
		putTimes = 1;
		phase =1;
		
		currentIsLeaf = Constant.IS_LEAF_CHESS_FOR_PLAYER01 ? true:false;
		chesses = new ChessPiece[6][6]; 
		drawBackground();
		
	}
	private void remove() {
		if(phase==1){
			Toast.makeText(getContext(), "~�廹û�����࣬����޶~", Toast.LENGTH_SHORT).show();
			return;
		}else if(phase==2){
			if(currentChess != null){
				//����޶�Լ�������
				if(currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					Toast.makeText(getContext(), "����޶�Լ����� ~~ ��", Toast.LENGTH_SHORT).show();
					return;
				}
				if(isFang(currentChess)>=1){
					Toast.makeText(getContext(), "�Ѿ��ɷ����岻��޶�� ~~", Toast.LENGTH_SHORT).show();
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
						currentIsLeaf = false;//��Ҷ�� ޶�� С���� ֮��� С����޶��
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
					Toast.makeText(getContext(), "�����׶Σ�����", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getContext(), "û����ѡ�У�����޶�ĸ���", Toast.LENGTH_SHORT).show();
				return;
			}
		}else if(phase ==3 && !moveModel){
			if(currentChess != null){
				//����޶�Լ�������
				if(currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					Toast.makeText(getContext(), "no zuo no die, ����޶�Լ����壡~~~", Toast.LENGTH_SHORT).show();
					return;
				}
				if(isFang(currentChess)>=1){
					Toast.makeText(getContext(), "�Ѿ��ɷ����岻��޶�� ~~~", Toast.LENGTH_SHORT).show();
					return;
				}
				chesses[currentChess.getRowIndex()][currentChess.getColIndex()]=null;
				putTimes -=1;
				totalChesses -=1;
				if(currentChess.getName().equals(Constant.BLACK_CHESS)){
					blackChessCounter -=1;
					//Ӯ�ñ���
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
					//Ӯ�ñ���
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
				Toast.makeText(getContext(), "û����ѡ�У�����޶�ĸ���", Toast.LENGTH_SHORT).show();
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
					 Toast.makeText(this.getContext(), "ֻ�ܻ�һ���壡",Toast.LENGTH_SHORT ).show();
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
				//��ѡ �е�����Ӧ�ߵ��岻һ��ʱ
				if(!chesses[tempX][tempY].getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
					//Log.v("chosen &��infact",currentChess.getName() + ""+currentIsLeaf );
					Toast.makeText(getContext(), "���˼�������ɶ�ֻࣿ�����Ը�����", Toast.LENGTH_SHORT).show();
					return ;
				}
			}else{
				if(currentChess!=null){
					if(!currentChess.getName().equals(currentIsLeaf? Constant.WHITE_CHESS:Constant.BLACK_CHESS)){
						Toast.makeText(getContext(), "���˼�������ɶ�ֻࣿ�����Ը�����~~", Toast.LENGTH_SHORT).show();
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
							putTimes -=1;//ȥ��ԭ����1
							//����ɷ���ñ����޶��ģʽ
							moveModel =false;
						}else{
							//���û�гɷ������ɶԷ�����
							currentIsLeaf= currentChess.getName().equals(Constant.BLACK_CHESS)? true:false;
							Log.v("aftter move currentIsLeaf",currentIsLeaf+"");
						}
						Log.v("chang position end","end");
						//chosenChess =chesses[tempX][tempY] ;
					}else{
						Toast.makeText(getContext(), "���Ӳ�����̫�����׳��ŵ���ÿ��ֻ����һ��", Toast.LENGTH_SHORT).show();
						return;
					}
				}else{
					Toast.makeText(getContext(), "��ѡ����Ҫ�ߵ���", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}
	}

	public void win(){
		new AlertDialog.Builder(getContext()).setTitle("Ӯ��")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setMessage("~~~"+(currentIsLeaf? "��Ҷ��":"С����") +"Ӯ��~~~")
		.setPositiveButton("~����һ��~", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				reset();
			}
		})
		.setNegativeButton("~��Ϣһ��~", new DialogInterface.OnClickListener() {
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
