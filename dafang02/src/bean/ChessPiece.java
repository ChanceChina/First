package bean;

import com.chencheng.dafang.util.Constant;

public  class ChessPiece {
	private int rowIndex=0;
	private int colIndex =0;
	private String name="";


	public ChessPiece(String name,int rowIndex,int colIndex){
		this.name=name;
		this.rowIndex =rowIndex;
		this.colIndex = colIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	
	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	
	//得到左边的节点
	public ChessPiece getLeftChess(){
		ChessPiece[][] chesses = Constant.CURRENT_CHESSBOARD.getChesses();
		ChessPiece leftChess = null;
		if(!(rowIndex==0)){
			leftChess = chesses[rowIndex-1][colIndex];
		}
		return leftChess;
	}
	//得到右边的节点 
	public ChessPiece getRightChess(){
		
		ChessPiece[][] chesses = Constant.CURRENT_CHESSBOARD.getChesses();
		ChessPiece rightChess = null;
		if(!(rowIndex==5)){
			rightChess = chesses[rowIndex+1][colIndex];
		}
		return rightChess;
	}
	
	//得到上面的节点
	public ChessPiece getUpChess(){
		
		ChessPiece[][] chesses = Constant.CURRENT_CHESSBOARD.getChesses();
		ChessPiece upChess = null;
		if(!(colIndex==0)){
			upChess = chesses[rowIndex][colIndex-1];
		}
		return upChess;
	}
	//得到下面的节点
	public ChessPiece getDownChess(){
	
		ChessPiece[][] chesses = Constant.CURRENT_CHESSBOARD.getChesses();
		ChessPiece downChess = null;
		if(!(colIndex==5)){
			downChess = chesses[rowIndex][colIndex+1];
		}
		return downChess;
	}
	
	/**
	 * 当两个棋的坐标相同，又为同颜色时，刚视为同一个棋子
	 * @param chess
	 * @return
	 */
	public boolean equals(ChessPiece chess){
		boolean result = false;
		if(this.rowIndex == chess.getRowIndex() && this.colIndex == chess.getColIndex() && this.name.equals(chess.getName())){
			result = true;
		}
		return result;
	}

	public int getX(int tempX) {
		return Constant.ORIGIN_X+Constant.X_SPAN * tempX -Constant.BM_WHITE_CHESS.getWidth()/2; 
	}
	
	public int getY(int tempY) {
		return Constant.ORIGIN_Y+Constant.Y_SPAN * tempY -Constant.BM_WHITE_CHESS.getHeight()/2; 
	}
	
	
	
}
