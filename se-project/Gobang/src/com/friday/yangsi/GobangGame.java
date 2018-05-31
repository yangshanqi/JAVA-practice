package com.friday.yangsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

//strategy pattern ?

public class GobangGame {

	private ChessBoard chessBoard;
	
	private int posX = 0;
	private int posY = 0;
	final private int WIN_SIZE = 5; 
	private String inputStr = null;
	private BufferedReader br = null;
	private String [][] board = null;
	
	
	public GobangGame (ChessBoard chessBoard){
		this.chessBoard=chessBoard;
	}
	
	public void start() throws IOException{
		chessBoard.initBoard();
		chessBoard.printBoard();
		System.out.println("please input the position according to the format x,y");
		br = new BufferedReader (new InputStreamReader(System.in));
		if ((inputStr=br.readLine( ))!=null){
			newRound();			
		}
	}
	
	private void newRound() throws IOException{
		while ( !isValid(inputStr) ){
			inputStr = br.readLine();
		}
		//System.out.println ("you successful input "+ posX + ", "+posY);
		
		chessBoard.setPoint(posX, posY, Chessman.BLACK.getChessman());
		//chessBoard.printBoard();
		if (isOver()){
			chessBoard.printBoard();
			System.out.println ("congragulations, you win");
			System.exit(0);
		}else{
			computerDo();
		}
		if (isOver()){
			System.out.println ("the computer wins");
			System.exit(0);
		}else{
			inputStr = br.readLine();
			newRound();
		}
	}
	
	public void computerDo (){
		Random r1 = new Random ();
		Random r2 = new Random ();
		
		while (chessBoard.getBoard()[posX][posY]!="+"){
			posX = r1.nextInt(15);
			posY = r2.nextInt(15);
		}
		chessBoard.setPoint(posX, posY, Chessman.WHITE.getChessman());
		chessBoard.printBoard();
		//System.out.println ("computer produce: "+comInStr);
		
	}
	
	public boolean isValid(String inputStr){
	    try {
			String[] tempInput = inputStr.split(",");
			posX = Integer.parseInt(tempInput[0]);
			posY = Integer.parseInt(tempInput[1]);
		} catch (NumberFormatException e) {
			System.out.println("please type in the correct format");
			return false;
		}		
		
	    if (posX > 14 | posX<0 |posY > 14 | posY <0){
	    	System.out.println("x and y shall range from 0 to 14");
	    	return false;
	    }
	    
	    if ((chessBoard.getBoard())[posX][posY]!="+"){
	    	System.out.println ("this position has already been taken, please choose another one");
	    	return false;
	    }
	    
	    return true ; 
	}
	

	
	public boolean isOver() {
		board = chessBoard.getBoard();
		int count = 0;
		int x = posX;
		int y = posY;
		
		// x direction
		while (board[x][y]==board[posX][posY]){
			count ++;
			if (count >= WIN_SIZE) return true;
			x++;
			if ( x >= 15 ) break;
		}
		
		if ((x = posX-1) >= 0){			
			while (board[x][y]==board[posX][posY]){
				count ++;
				if (count >= WIN_SIZE) return true;
				x--;
				if ( x < 0 ) break;
			}	
		}
		
		// y direction
		x = posX;
		count = 0;
		
		while (board[x][y]==board[posX][posY]){
			count ++;
			if (count >= WIN_SIZE) return true;
			y++;
			if ( y>= 15 ) break;
			
		}
		
		if ((y = posY-1) >= 0){			
			while (board[x][y]==board[posX][posY]){
				count ++;
				if (count >= WIN_SIZE) return true;
				y--;
				if ( y < 0 ) break;
				
			}	
		}
		
		// x,y direction
		y = posY;
		count = 0;
		
		while (board[x][y]==board[posX][posY]){
			count ++;
			if (count >= WIN_SIZE) return true;
			x++;
			y++;
			if ( y>= 15 | x >= 15 ) break;
			
		}
		
		if ((y = posY-1) >= 0 & (x = posX -1) >= 0){			
			while (board[x][y]==board[posX][posY]){
				count ++;
			    if (count >= WIN_SIZE) return true;
				y--;
				x--;
				if ( y < 0 | x<0) break;
				
			}	
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		GobangGame gobangGame = new GobangGame(new ChessBoard());
		gobangGame.start();

	}

}
