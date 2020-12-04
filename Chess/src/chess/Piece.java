/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Conner
 */
public class Piece {
    public int xPos;//these two will be in board square units
    public int yPos;
    public int possibleSpotSize = 15;
    public ArrayList<EmptySpace> emptySpots = new ArrayList<EmptySpace>();
    public ArrayList<FullSpace> fullSpots = new ArrayList<FullSpace>();
    public Image pieceImage;
    public Player myPlayer;
    public boolean firstUniqueMove = true;
    public enum pieceType{KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN};
    public pieceType takenPiece;
    public pieceType myPieceType;
    Piece(int x, int y, Player play){
        xPos = x;
        yPos =  y;
        myPlayer = play;
    }
    public void DrawPossibleMoves(Graphics2D g, int xDelta, int yDelta){
        
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        
    }public void SetPossibleMoves(int xDelta, int yDelta, pieceType type){
        boolean stopCheck = false;
        int z = 0;
        switch (type) {
            case KING:
                    //going clockwise around the king to check open spots
                    if(xPos - 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos - 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos - 1, yPos - 1)){        
                            emptySpots.add(new EmptySpace(xPos - 1, yPos - 1));
                        }
                        else if(!Board.CheckifOpenSpot(xPos - 1, yPos - 1) && Board.GetPieceBoard(xPos - 1, yPos - 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos - 1, yPos - 1));
                        }
                    }

                    if(xPos < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos >= 0 && yPos - 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos, yPos - 1)){
                            emptySpots.add(new EmptySpace(xPos, yPos - 1));
                        }
                        else if(Board.GetPieceBoard(xPos, yPos - 1) != null && !Board.CheckifOpenSpot(xPos, yPos - 1) && Board.GetPieceBoard(xPos, yPos - 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, yPos - 1));
                        }
                    }

                    if(xPos + 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos - 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos + 1, yPos - 1)){
                            emptySpots.add(new EmptySpace(xPos + 1, yPos - 1));
                        }
                        else if(!Board.CheckifOpenSpot(xPos + 1, yPos - 1) && Board.GetPieceBoard(xPos + 1, yPos - 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos + 1, yPos - 1));
                        }
                    }

                    if(xPos + 1 < Board.BOARD_SIZE && yPos < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos >= 0){
                        if(Board.CheckifOpenSpot(xPos + 1, yPos)){
                            emptySpots.add(new EmptySpace(xPos + 1, yPos));
                        }
                        else if(!Board.CheckifOpenSpot(xPos + 1, yPos) && Board.GetPieceBoard(xPos + 1, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos + 1, yPos));
                        }
                    }

                    if(xPos + 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos + 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos + 1, yPos + 1)){
                            emptySpots.add(new EmptySpace(xPos + 1, yPos + 1));
                        }
                        else if(!Board.CheckifOpenSpot(xPos + 1, yPos + 1) && Board.GetPieceBoard(xPos + 1, yPos + 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos + 1, yPos + 1));
                        }
                    }

                    if(xPos < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos >= 0 && yPos + 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos, yPos + 1)){
                            emptySpots.add(new EmptySpace(xPos, yPos + 1));
                        }
                        else if(!Board.CheckifOpenSpot(xPos, yPos + 1) && Board.GetPieceBoard(xPos, yPos + 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, yPos + 1));
                        }
                    }

                    if(xPos - 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos + 1 >= 0){
                        if(Board.CheckifOpenSpot(xPos - 1, yPos + 1)){
                            emptySpots.add(new EmptySpace(xPos - 1, yPos + 1));
                        }
                        else if(!Board.CheckifOpenSpot(xPos - 1, yPos + 1) && Board.GetPieceBoard(xPos - 1, yPos + 1).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos - 1, yPos + 1));
                        }
                    }

                    if(xPos - 1 < Board.BOARD_SIZE && yPos < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos >= 0){
                        if(Board.CheckifOpenSpot(xPos - 1, yPos)){
                            emptySpots.add(new EmptySpace(xPos - 1, yPos));
                        }
                        else if(!Board.CheckifOpenSpot(xPos - 1, yPos) && Board.GetPieceBoard(xPos - 1, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos - 1, yPos));
                        }
                    }
                break;
            case QUEEN:
                    stopCheck = false;
                    z = 0;

                    for(int i = yPos - 1; i >= 0 && !stopCheck; i--){//vertical UP
                        if(Board.CheckifOpenSpot(xPos, i))
                            emptySpots.add(new EmptySpace(xPos, i));
                        else if(!Board.CheckifOpenSpot(xPos, i) && Board.GetPieceBoard(xPos, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, i));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    for(int i = yPos + 1; i < Board.BOARD_SIZE && !stopCheck; i++){//vertical DOWN
                        if(Board.CheckifOpenSpot(xPos, i))
                            emptySpots.add(new EmptySpace(xPos, i));
                        else if(!Board.CheckifOpenSpot(xPos, i) && Board.GetPieceBoard(xPos, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, i));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;

                    for(int i = xPos - 1; i >= 0 && !stopCheck; i--){//Horizontal LEFT
                        if(Board.CheckifOpenSpot(i, yPos))
                            emptySpots.add(new EmptySpace(i, yPos));
                        else if(!Board.CheckifOpenSpot(i, yPos) && Board.GetPieceBoard(i, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && !stopCheck; i++){//horizontal RIGHT
                        if(Board.CheckifOpenSpot(i, yPos))
                            emptySpots.add(new EmptySpace(i, yPos));
                        else if(!Board.CheckifOpenSpot(i, yPos) && Board.GetPieceBoard(i, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;

                    z = yPos + 1;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && z < Board.BOARD_SIZE && !stopCheck; i++, z++){//diagnal DOWN, RIGHT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos + 1;
                    for(int i = xPos - 1; i >= 0 && z < Board.BOARD_SIZE && !stopCheck; i--, z++){//diagnal DOWN, LEFT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos - 1;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && z >= 0 && !stopCheck; i++, z--){//diagnal UP, RIGHT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos - 1;
                    for(int i = xPos - 1; i >= 0 && z >= 0 && !stopCheck; i--, z--){//diagnal UP, LEFT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                break;
            case BISHOP:
                    stopCheck = false;
                    z = 0;

                    z = yPos + 1;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && z < Board.BOARD_SIZE && !stopCheck; i++, z++){//diagnal DOWN, RIGHT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos + 1;
                    for(int i = xPos - 1; i >= 0 && z < Board.BOARD_SIZE && !stopCheck; i--, z++){//diagnal DOWN, LEFT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos - 1;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && z >= 0 && !stopCheck; i++, z--){//diagnal UP, RIGHT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    z = yPos - 1;
                    for(int i = xPos - 1; i >= 0 && z >= 0 && !stopCheck; i--, z--){//diagnal UP, LEFT
                        if(Board.CheckifOpenSpot(i, z))
                            emptySpots.add(new EmptySpace(i, z));
                        else if(!Board.CheckifOpenSpot(i, z) && Board.GetPieceBoard(i, z).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, z));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                break;
            case KNIGHT:
                int stopCheckNum = 0;

                stopCheckNum = xPos + 1;
                for(int i = xPos - 1; i <= stopCheckNum && yPos - 2 >= 0; i += 2){//UP right and left
                    if(i < Board.BOARD_SIZE && i >= 0){
                        if(Board.CheckifOpenSpot(i, yPos - 2)){        
                            emptySpots.add(new EmptySpace(i, yPos - 2));
                        }
                        else if(!Board.CheckifOpenSpot(i, yPos - 2) && Board.GetPieceBoard(i, yPos - 2).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos - 2));
                        }
                    }
                }
                stopCheckNum = yPos + 1;
                for(int i = yPos - 1; i <= stopCheckNum && xPos + 2 < Board.BOARD_SIZE; i += 2){//RIGHT up and down
                    if(i < Board.BOARD_SIZE && i >= 0){
                        if(Board.CheckifOpenSpot(xPos + 2, i)){        
                            emptySpots.add(new EmptySpace(xPos + 2, i));
                        }
                        else if(!Board.CheckifOpenSpot(xPos + 2, i) && Board.GetPieceBoard(xPos + 2, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos + 2, i));
                        }
                    }
                }
                stopCheckNum = xPos + 1;
                for(int i = xPos - 1; i <= stopCheckNum && yPos + 2 < Board.BOARD_SIZE && i < Board.BOARD_SIZE  && i >= 0; i += 2){//DOWN right and left
                    if(i < Board.BOARD_SIZE && i >= 0){
                        if(Board.CheckifOpenSpot(i, yPos + 2)){        
                            emptySpots.add(new EmptySpace(i, yPos + 2));
                        }
                        else if(!Board.CheckifOpenSpot(i, yPos + 2) && Board.GetPieceBoard(i, yPos + 2).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos + 2));
                        }
                    }
                }
                stopCheckNum = yPos + 1;
                for(int i = yPos - 1; i <= stopCheckNum && xPos - 2 >= 0; i += 2){//LEFT up and down
                    if(i < Board.BOARD_SIZE && i >= 0){
                        if(Board.CheckifOpenSpot(xPos - 2, i)){        
                            emptySpots.add(new EmptySpace(xPos - 2, i));
                        }
                        else if(!Board.CheckifOpenSpot(xPos - 2, i) && Board.GetPieceBoard(xPos - 2, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos - 2, i));
                        }
                    }
                }
                break;
            case ROOK:
                    stopCheck = false;
                    for(int i = yPos - 1; i >= 0 && !stopCheck; i--){//vertical UP
                        if(Board.CheckifOpenSpot(xPos, i))
                            emptySpots.add(new EmptySpace(xPos, i));
                        else if(!Board.CheckifOpenSpot(xPos, i) && Board.GetPieceBoard(xPos, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, i));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    for(int i = yPos + 1; i < Board.BOARD_SIZE && !stopCheck; i++){//vertical DOWN
                        if(Board.CheckifOpenSpot(xPos, i))
                            emptySpots.add(new EmptySpace(xPos, i));
                        else if(!Board.CheckifOpenSpot(xPos, i) && Board.GetPieceBoard(xPos, i).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(xPos, i));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    for(int i = xPos - 1; i >= 0 && !stopCheck; i--){//Horizontal LEFT
                        if(Board.CheckifOpenSpot(i, yPos))
                            emptySpots.add(new EmptySpace(i, yPos));
                        else if(!Board.CheckifOpenSpot(i, yPos) && Board.GetPieceBoard(i, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                    stopCheck = false;
                    for(int i = xPos + 1; i < Board.BOARD_SIZE && !stopCheck; i++){//horizontal RIGHT
                        if(Board.CheckifOpenSpot(i, yPos))
                            emptySpots.add(new EmptySpace(i, yPos));
                        else if(!Board.CheckifOpenSpot(i, yPos) && Board.GetPieceBoard(i, yPos).myPlayer != myPlayer){
                            fullSpots.add(new FullSpace(i, yPos));
                            stopCheck = true;
                        }
                        else
                            stopCheck = true;
                    }
                break;
            case PAWN:
                    //black direction
                    if(myPlayer.getColor().equals(Color.black)){
                        if(Board.CheckifOpenSpot(xPos, yPos - 1)){
                            emptySpots.add(new EmptySpace(xPos, yPos - 1));
                        }
                        if(Board.CheckifOpenSpot(xPos, yPos - 2) && firstUniqueMove){
                            emptySpots.add(new EmptySpace(xPos, yPos - 2));
                        }
                        if(xPos + 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos - 1 >= 0){
                            if(!Board.CheckifOpenSpot(xPos + 1, yPos - 1) && Board.GetPieceBoard(xPos + 1, yPos - 1).myPlayer != myPlayer){
                                fullSpots.add(new FullSpace(xPos + 1, yPos - 1));
                            }
                        }
                        if(xPos - 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos - 1 >= 0){
                            if(!Board.CheckifOpenSpot(xPos - 1, yPos - 1) && Board.GetPieceBoard(xPos - 1, yPos - 1).myPlayer != myPlayer){
                                fullSpots.add(new FullSpace(xPos - 1, yPos - 1));
                            }
                        }
                    }
                    //white direction
                    if(myPlayer.getColor().equals(Color.white)){
                        if(Board.CheckifOpenSpot(xPos, yPos + 1)){
                            emptySpots.add(new EmptySpace(xPos, yPos + 1));
                        }
                        if(Board.CheckifOpenSpot(xPos, yPos + 2) && firstUniqueMove){
                            emptySpots.add(new EmptySpace(xPos, yPos + 2));
                        }
                        if(xPos - 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos + 1 >= 0){
                            if(!Board.CheckifOpenSpot(xPos - 1, yPos + 1) && Board.GetPieceBoard(xPos - 1, yPos + 1).myPlayer != myPlayer){
                                fullSpots.add(new FullSpace(xPos - 1, yPos + 1));
                            }
                        }
                        if(xPos + 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos + 1 >= 0){
                            if(!Board.CheckifOpenSpot(xPos + 1, yPos + 1) && Board.GetPieceBoard(xPos + 1, yPos + 1).myPlayer != myPlayer){
                                fullSpots.add(new FullSpace(xPos + 1, yPos + 1));
                            }
                        }
                    }
                break;
            default:
                break;
        }
    }
}
