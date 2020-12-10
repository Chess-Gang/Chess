/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 *
 * @author Conner
 */
public class Piece {
    //Posistion
    public int xPos;//these two will be in board square units
    public int yPos;//unless they are being used animate, in which they will be in normal x , y coords
    public int oldX;//these will hold the x / y pos Board Units
    public int oldY;
    public int desiredX;//these are to guide the piece on where to go in the Move method
    public int desiredY;
    
    //Possible Moves Info / storage
    public int possibleSpotSize = 15; // this is how big the red and blue circles are
    public ArrayList<EmptySpace> emptySpots = new ArrayList<EmptySpace>();//blue spots
    public ArrayList<FullSpace> fullSpots = new ArrayList<FullSpace>();//red spots, these apear when you can take a piece
    
    //enum
    public enum pieceType{KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN};
    public pieceType myPieceType;// this pieces types
    public pieceType takenPiece;// this is filled with the piece that was taken by this piece too use the taken piece movement as well
    
    //Player that this piece belongs to
    public Player myPlayer;
    
    //Move Info
    public boolean firstUniqueMove = true;//is false after first move
    public boolean moving;//if the move method is going this is true
    public int a = 0;//this is a counter to help prevent drifting off course in the move method
    
    //Graphics
    public Image pieceImage;
    static ImageObserver observe;
    
    Piece(int x, int y, Player play){
        xPos = x;
        yPos =  y;
        myPlayer = play;
        moving  = false;
    }
    public void DrawPossibleMoves(Graphics2D g, int xDelta, int yDelta){
    }
    public void SetMove(int _desiredX, int _desiredY, int xDelta, int yDelta){
        desiredX = _desiredX;
        desiredY = _desiredY;
        oldX = xPos;
        oldY = yPos;
        //System.out.println(xDelta + "," + yDelta);
        xPos *= xDelta;
        yPos *= yDelta;
        moving = true;
    }
    public void Move(int desiredX, int desiredY, Graphics2D g){
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
    }
    public void SetPossibleMoves(int xDelta, int yDelta, pieceType type){
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
                    if(myPlayer.GetPlayerNumber().equals(1)){
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
                    if(myPlayer.GetPlayerNumber().equals(0)){
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
