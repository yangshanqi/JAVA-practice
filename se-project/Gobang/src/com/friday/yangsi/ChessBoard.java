package com.friday.yangsi;

public class ChessBoard {
	final int BoardSize=15;
	private String[][] board;
	
	public String[][] getBoard() {
		return board;
	}

	public void initBoard(){
		board = new String [BoardSize][BoardSize];
		for (int i=0; i<BoardSize; i++){
			for (int j=0; j<BoardSize; j++){
				board[i][j]="+";
			}
		}
	}
	
	public void printBoard(){
		for (int i=0; i<BoardSize; i++){
			for (int j=0; j<BoardSize; j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println(" ");
		}
	}
	
	public void setPoint (int x, int y, String chessman){
		this.board [x][y] = chessman ; 
		
	}
	
	

}
