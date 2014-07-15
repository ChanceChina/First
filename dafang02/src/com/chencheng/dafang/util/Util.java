package com.chencheng.dafang.util;

import bean.ChessPiece;

public class Util {
	
	/**
	 * get row number
	 * @param tempX
	 * @return
	 */
	public static int getTempX(int tempX) {
		int x = -1;
		//不在棋盘内
		if(tempX<=Constant.ORIGIN_X-Constant.X_SPAN/2
				|| tempX>=Constant.SCREEN_WIDTH-Constant.ORIGIN_X+Constant.X_SPAN/2){
			return x;
		//第一行
		}else if((tempX>Constant.ORIGIN_X-Constant.X_SPAN/2) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN/2)){
			x =0;// Constant.ORIGIN_X;
		//第二行
		}else if((tempX>(Constant.ORIGIN_X+Constant.X_SPAN-Constant.X_SPAN/2)) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN+Constant.X_SPAN/2)){
			x = 1;//Constant.ORIGIN_X+Constant.X_SPAN;
		//第三行
		}else if((tempX>(Constant.ORIGIN_X+Constant.X_SPAN*2-Constant.X_SPAN/2)) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN*2+Constant.X_SPAN/2)){
			x = 2;//Constant.ORIGIN_X+Constant.X_SPAN*2;
		//第四行
		}else if((tempX>(Constant.ORIGIN_X+Constant.X_SPAN*3-Constant.X_SPAN/2)) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN*3+Constant.X_SPAN/2)){
			x = 3;//Constant.ORIGIN_X+Constant.X_SPAN*3;
		//第五行
		}else if((tempX>(Constant.ORIGIN_X+Constant.X_SPAN*4-Constant.X_SPAN/2)) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN*4+Constant.X_SPAN/2)){
			x = 4;//Constant.ORIGIN_X+Constant.xSpan*4;
		//第六列
		}else if((tempX>(Constant.ORIGIN_X+Constant.X_SPAN*5-Constant.X_SPAN/2)) 
				&& (tempX<Constant.ORIGIN_X+Constant.X_SPAN*5+Constant.X_SPAN/2)){
			x = 5;//Constant.ORIGIN_X+Constant.xSpan*5;
		}
		
		return x;
	}
	
	/**
	 * get column number
	 * @param tempY
	 * @return
	 */
	public static int getTempY(int tempY) {
		int y = -1;
		//不在棋盘内
		if(tempY<=Constant.ORIGIN_Y-Constant.Y_SPAN/2
				|| tempY>=Constant.SCREEN_HEIGHT-Constant.ORIGIN_Y+Constant.Y_SPAN/2){
			return y;
		//第一行
		}else if((tempY>Constant.ORIGIN_Y-Constant.Y_SPAN/2) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN/2)){
			y = 0;//Constant.ORIGIN_Y;
		//第二列
		}else if((tempY>(Constant.ORIGIN_Y+Constant.Y_SPAN-Constant.Y_SPAN/2)) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN+Constant.Y_SPAN/2)){
			y = 1;//Constant.ORIGIN_Y+Constant.Y_SPAN;
		//第三列
		}else if((tempY>(Constant.ORIGIN_Y+Constant.Y_SPAN*2-Constant.Y_SPAN/2)) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN*2+Constant.Y_SPAN/2)){
			y = 2;//Constant.ORIGIN_Y+Constant.Y_SPAN*2;
		//第四列
		}else if((tempY>(Constant.ORIGIN_Y+Constant.Y_SPAN*3-Constant.Y_SPAN/2)) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN*3+Constant.Y_SPAN/2)){
			y = 3;//Constant.ORIGIN_Y+Constant.Y_SPAN*3;
		//第五列
		}else if((tempY>(Constant.ORIGIN_Y+Constant.Y_SPAN*4-Constant.Y_SPAN/2)) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN*4+Constant.Y_SPAN/2)){
			y = 4;//Constant.ORIGIN_Y+Constant.Y_SPAN*4;
		//第六列
		}else if((tempY>(Constant.ORIGIN_Y+Constant.Y_SPAN*5-Constant.Y_SPAN/2)) 
				&& (tempY<Constant.ORIGIN_Y+Constant.Y_SPAN*5+Constant.Y_SPAN/2)){
			y = 5;//Constant.ORIGIN_Y+Constant.Y_SPAN*5;
		}
		
		return y;
	}
	/**
	 * 判断是否为龙
	 * @param chess
	 * @return 
	 */
	public static int isDragon(ChessPiece chess) {
		ChessPiece[][] chesses =Constant.CURRENT_CHESSBOARD.getChesses();
		int result = 0;
		int rind=chess.getRowIndex();
		int cind = chess.getColIndex();
		String name = chess.getName();
		
		if(chesses[rind][0]!=null && chesses[rind][1]!=null && chesses[rind][2]!=null 
				&& chesses[rind][3]!=null&& chesses[rind][4]!=null && chesses[rind][5]!=null){
			if(chesses[rind][0].getName().equals(name)&&chesses[rind][1].getName().equals(name)&&chesses[rind][2].getName().equals(name)
				&&chesses[rind][3].getName().equals(name)&&chesses[rind][4].getName().equals(name)&&chesses[rind][5].getName().equals(name)){
				if(rind!=0 && rind !=5){//边龙虽然为龙，但不能获得再放三子的权利
					result +=3;
//					Log.d("is_dragon--rind-times",result+"");
				}
			}
		}
		if(chesses[0][cind]!=null && chesses[1][cind]!=null && chesses[2][cind]!=null 
				&& chesses[3][cind]!=null&& chesses[4][cind]!=null && chesses[5][cind]!=null){
			if(chesses[0][cind].getName().equals(name)&&chesses[1][cind].getName().equals(name)&&chesses[2][cind].getName().equals(name)
				&&chesses[3][cind].getName().equals(name)&&chesses[4][cind].getName().equals(name)&&chesses[5][cind].getName().equals(name)){
				if(cind!=0  &&  cind!=5) result +=3;
//				Log.d("is_dragon-cind--times",times+"");
//				result = true;
			}
		}
		
		
		return result;
	}
	/**
	 * 落子后检查是否为 “通州”
	 * @param chess
	 */
	public static int isTongZhou(ChessPiece chess) {
		ChessPiece[][] chesses =Constant.CURRENT_CHESSBOARD.getChesses();
		int result = 0;
//		if(this.whiteChessCounter<6 && this.blackChessCounter<6){
//			return result;
//		}
		int rind=chess.getRowIndex();
		int cind = chess.getColIndex();
		String name = chess.getName();
		
		//(0,0) (1,1) (2,2) (3,3) (4,4) (5,5)
		if(rind==cind){
			if(chesses[0][0]!=null && chesses[1][1]!=null && chesses[2][2]!=null && chesses[3][3]!=null 
					&& chesses[4][4]!=null && chesses[5][5]!=null){
				if(chesses[0][0].getName().equals(name) && chesses[1][1].getName().equals(name) 
					&& chesses[2][2].getName().equals(name) && chesses[3][3].getName().equals(name) 
					&& chesses[4][4].getName().equals(name) && chesses[5][5].getName().equals(name)){
					result +=3;
//					result = true;
				}
			}
		}
		else if(rind+cind ==5){
			if(chesses[0][5]!=null && chesses[1][4]!=null && chesses[2][3]!=null && chesses[3][2]!=null 
					&& chesses[4][1]!=null && chesses[5][0]!=null){
				if(chesses[0][5].getName().equals(name) && chesses[1][4].getName().equals(name) 
					&& chesses[2][3].getName().equals(name) && chesses[3][2].getName().equals(name) 
					&& chesses[4][1].getName().equals(name) && chesses[5][0].getName().equals(name)){
					result +=3;
//					result = true;
				}
			}
		}
		return result;
	}
	/**
	 * 落子后检查是否为 ：“五斜”
	 * @param chess
	 */
	public static int isWuXie(ChessPiece chess) {
		ChessPiece[][] chesses =Constant.CURRENT_CHESSBOARD.getChesses();
		int result = 0;
//		if(this.whiteChessCounter<5 && this.blackChessCounter<5){
//			return result;
//		}
		int rind=chess.getRowIndex();
		int cind = chess.getColIndex();
		String name = chess.getName();
		//(0,1) (1,2) (2,3) (3,4) (4,5)
		if((rind==0&&cind==1) || (rind==1&&cind==2) || (rind==2&&cind==3) || (rind==3&&cind==4) || (rind==4&&cind==5)){
			if(chesses[0][1]!=null && chesses[1][2]!=null && chesses[2][3]!=null && chesses[3][4]!=null && chesses[4][5]!=null){
				if(chesses[0][1].getName().equals(name) && chesses[1][2].getName().equals(name) 
					&& chesses[2][3].getName().equals(name) && chesses[3][4].getName().equals(name) 
					&& chesses[4][5].getName().equals(name)){
					result +=2;
					//result = true;
				}
			}
		}
		//(0,4) (1,3) (2,2) (3,1) (4,0)
		else if((rind==0&&cind==4) || (rind==1&&cind==3) || (rind==2&&cind==2) || (rind==3&&cind==1) || (rind==4&&cind==0)){
			if(chesses[0][4]!=null && chesses[1][3]!=null && chesses[2][2]!=null && chesses[3][1]!=null && chesses[4][0]!=null){
				if(chesses[0][4].getName().equals(name) && chesses[1][3].getName().equals(name) 
					&& chesses[2][2].getName().equals(name) && chesses[3][1].getName().equals(name) 
					&& chesses[4][0].getName().equals(name)){
					result +=2;
//					result = true;
				}
			}
		}
		//(1,5) (2,4) (3,3) (4,2) (5,1)
		else if((rind==1&&cind==5) || (rind==2&&cind==4) || (rind==3&&cind==3) || (rind==4&&cind==2) || (rind==5&&cind==1)){
			if(chesses[1][5]!=null && chesses[2][4]!=null && chesses[3][3]!=null && chesses[4][2]!=null && chesses[5][1]!=null){
				if(chesses[1][5].getName().equals(name) && chesses[2][4].getName().equals(name) 
					&& chesses[3][3].getName().equals(name) && chesses[4][2].getName().equals(name) 
					&& chesses[5][1].getName().equals(name)){
					result +=2;
//					result = true;
				}
			}
		}
		//(1,0) (2,1) (3,2) (4,3) (5,4)
		else if((rind==1&&cind==0) || (rind==2&&cind==1) || (rind==3&&cind==2) || (rind==4&&cind==3) || (rind==5&&cind==4)){
			if(chesses[1][0]!=null && chesses[2][1]!=null && chesses[3][2]!=null && chesses[4][3]!=null && chesses[5][4]!=null){
				if(chesses[1][0].getName().equals(name) && chesses[2][1].getName().equals(name) 
					&& chesses[3][2].getName().equals(name) && chesses[4][3].getName().equals(name) 
					&& chesses[5][4].getName().equals(name)){
					result +=2;
//					result = true;
				}
			}
		}
		return result;
	}
	/**
	 * 落子后检查是否为四斜
	 * @param chess
	 */
	public static int isSiXie(ChessPiece chess) {
		ChessPiece[][] chesses =Constant.CURRENT_CHESSBOARD.getChesses();
		int result = 0;
//		if(this.whiteChessCounter<4 && this.blackChessCounter<4){
//			return result;
//		}
		int rind=chess.getRowIndex();
		int cind = chess.getColIndex();
		String name = chess.getName();
		//(0,2) (1,3) (2,4) (3,5)
		if((rind==0&&cind==2) || (rind==1&&cind==3) || (rind==2&&cind==4) || (rind==3&&cind==5)){
			if(chesses[0][2]!=null && chesses[1][3]!=null && chesses[2][4]!=null && chesses[3][5]!=null){
				if(chesses[0][2].getName().equals(name) && chesses[1][3].getName().equals(name) 
					&& chesses[2][4].getName().equals(name) && chesses[3][5].getName().equals(name) ){
					result +=1;
//					result = true;
				}
			}
		}
		//(0,3) (1,2) (2,1) (3,0)
		else if((rind==0&&cind==3) || (rind==1&&cind==2) || (rind==2&&cind==1) || (rind==3&&cind==0)){
			if(chesses[0][3]!=null && chesses[1][2]!=null && chesses[2][1]!=null && chesses[3][0]!=null){
				if(chesses[0][3].getName().equals(name) && chesses[1][2].getName().equals(name) 
					&& chesses[2][1].getName().equals(name) && chesses[3][0].getName().equals(name) ){
					result +=1;
//					result = true;
				}
			}
		}
		//(2,0) (3,1) (4,2) (5,3)
		else if((rind==2&&cind==0) || (rind==3&&cind==1) || (rind==4&&cind==2) || (rind==5&&cind==3)){
			if(chesses[2][0]!=null && chesses[3][1]!=null && chesses[4][2]!=null && chesses[5][3]!=null){
				if(chesses[2][0].getName().equals(name) && chesses[3][1].getName().equals(name) 
					&& chesses[4][2].getName().equals(name) && chesses[5][3].getName().equals(name) ){
					result +=1;
//					result = true;
				}
			}
		}
		//(2,5) (3,4) (4,3) (5,2)
		else if((rind==2&&cind==5) || (rind==3&&cind==4) || (rind==4&&cind==3) || (rind==5&&cind==2)){
			if(chesses[2][5]!=null && chesses[3][4]!=null && chesses[4][3]!=null && chesses[5][2]!=null){
				if(chesses[2][5].getName().equals(name) && chesses[3][4].getName().equals(name) 
					&& chesses[4][3].getName().equals(name) && chesses[5][2].getName().equals(name) ){
					result +=1;
//					result = true;
				}
			}
		}
		return result;
	}
	/**
	 * 落子后检查是否为 “三斜”
	 * @param chess
	 */
	public static int isSanXie(ChessPiece chess) {
		ChessPiece[][] chesses =Constant.CURRENT_CHESSBOARD.getChesses();
		int result = 0;
		int rind=chess.getRowIndex();
		int cind = chess.getColIndex();
		String name = chess.getName();
		//(0,2) (1,1) (2,0)
		if((rind==0&&cind==2) || (rind==1&&cind==1) || (rind==2&&cind==0)){
			if(chesses[0][2]!=null && chesses[1][1]!=null && chesses[2][0]!=null){
				if(chesses[0][2].getName().equals(name) && chesses[1][1].getName().equals(name) && chesses[2][0].getName().equals(name)){
					result +=1;
					//result = true;
				}
			}
		}
		//(0,3) (1,4) (2,5)
		else if((rind==0&&cind==3) || (rind==1&&cind==4) || (rind==2&&cind==5)){
			if(chesses[0][3]!=null && chesses[1][4]!=null && chesses[2][5]!=null){
				if(chesses[0][3].getName().equals(name) && chesses[1][4].getName().equals(name) && chesses[2][5].getName().equals(name)){
					result +=1;
					//result = true;
				}
			}
		}
		//(3,0) (4,1) (5,2)
		else if((rind==3&&cind==0) || (rind==4&&cind==1) || (rind==5&&cind==2)){
			if(chesses[3][0]!=null && chesses[4][1]!=null && chesses[5][2]!=null){
				if(chesses[3][0].getName().equals(name) && chesses[4][1].getName().equals(name) && chesses[5][2].getName().equals(name)){
					//result = true;
					result +=1;
				}
			}
		}
		//(3,5) (4,4) (5,3)
		else if((rind==3&&cind==5) || (rind==4&&cind==4) || (rind==5&&cind==3)){
			if(chesses[3][5]!=null && chesses[4][4]!=null && chesses[5][3]!=null){
				if(chesses[3][5].getName().equals(name) && chesses[4][4].getName().equals(name) && chesses[5][3].getName().equals(name)){
					//result = true;
					result +=1;
				}
			}
		}
		return result;
	}
	/**
	 * 落子后检查是否 “成方”
	 * @param chess
	 * @return
	 */
	public static int isDafang(ChessPiece chess) {
		int result = 0;
//		if((chess.getName().equals(Constant.WHITE_CHESS) && this.whiteChessCounter<4)||
//		   (chess.getName().equals(Constant.BLACK_CHESS) && this.blackChessCounter<4)){
//			return result;
//		}
		//获取此棋子周围的八个棋子
		ChessPiece upChess = chess.getUpChess();
		ChessPiece downChess= chess.getDownChess();
		ChessPiece leftChess = chess.getLeftChess();
		ChessPiece rightChess = chess.getRightChess();
		ChessPiece leftTopDiagonalChess = leftChess==null? null:leftChess.getUpChess();
		ChessPiece leftBottomDiagonalChess = leftChess==null? null:leftChess.getDownChess();
		ChessPiece rightTopDiagonalChess = rightChess==null? null:rightChess.getUpChess();
		ChessPiece rightBottomDiagonalChess = rightChess==null? null:rightChess.getDownChess();
		//1. 当正左 左对角线 正上的棋子不为空，且为同一种棋子时 为 方
		String name = chess.getName();
		if(leftChess !=null && leftTopDiagonalChess !=null && upChess !=null ){
			if(leftChess.getName().equals(name) && leftTopDiagonalChess.getName().equals(name) && upChess.getName().equals(name)){
				result +=1;
//				result = true;
			}
		}
		if(leftChess !=null && leftBottomDiagonalChess != null && downChess != null){
			if(leftChess.getName().equals(name)&&leftBottomDiagonalChess.getName().equals(name) && downChess.getName().equals(name)){
				result +=1;
//				result = true;
			}
		}
		if(rightChess!=null && rightTopDiagonalChess!=null && upChess!=null){
			if(rightChess.getName().equals(name) && rightTopDiagonalChess.getName().equals(name) && upChess.getName().equals(name)){
				result +=1;
//				result = true;
			}
		}
		if(rightChess!=null && rightBottomDiagonalChess!=null && downChess !=null){
			if(rightChess.getName().equals(name)&&rightBottomDiagonalChess.getName().equals(name)&&downChess.getName().equals(name)){
				result +=1;
//				result = true;
			}
		}
		return result;
	}
	
	
}
