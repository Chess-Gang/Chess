/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import static chess.Piece.observe;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Conner
 */
public class King extends Piece{
    Rook leftRook;
    Rook rightRook;
    
    King(int x, int y, Player play, Rook _rook1, Rook _rook2){
        super(x,y,play);
        myPieceType = Piece.pieceType.KING;
        if(myPlayer.GetPlayerNumber().equals(0))
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_king_1x.png");
        else
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_king_1x.png");
        leftRook = _rook1;
        rightRook = _rook2;
    }
    public Image GetImage(){
        return(pieceImage);
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        emptySpots.clear();
        fullSpots.clear();
        boolean canCastle = Chess.normalMode;
        //Casteling Checks
        if(leftRook.firstUniqueMove && firstUniqueMove && Chess.randomize.isEnabled()){
            for(int i =  xPos - 1; i > 0; i--){
                if(!Board.CheckifOpenSpot(i, yPos))
                    canCastle = false;
            }
            if(canCastle){
                emptySpots.add(new EmptySpace(xPos - 2, yPos, xPos - 1, yPos, leftRook));
            }
        }
        canCastle = true;
        if(rightRook.firstUniqueMove && firstUniqueMove){
            for(int i =  xPos + 1; i < 7; i++){
                if(!Board.CheckifOpenSpot(i, yPos))
                    canCastle = false;
            }
            if(canCastle){
                emptySpots.add(new EmptySpace(xPos + 2, yPos, xPos + 1, yPos, rightRook));
            }
        }
        
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
    }
    public void DrawPossibleMoves(Graphics2D g,int xDelta, int yDelta){
        for(EmptySpace empt : emptySpots){
            g.setColor(Color.blue);
            g.fillOval(Window.getX(xDelta*(empt.xPos) + possibleSpotSize/2),Window.getY(yDelta*(empt.yPos) + possibleSpotSize/2),xDelta - possibleSpotSize,yDelta - possibleSpotSize);
        }
        for(FullSpace full : fullSpots){
            g.setColor(Color.red);
            g.fillOval(Window.getX(xDelta*(full.xPos) + possibleSpotSize/2),Window.getY(yDelta*(full.yPos) + possibleSpotSize/2),xDelta - possibleSpotSize,yDelta - possibleSpotSize);
        }
    }
    public void Move(int xdelta,int ydelta, Graphics2D g){
        g.drawImage(pieceImage,Window.getX(xPos),Window.getY(yPos),xdelta + 4,ydelta + 4,observe);
        if(yPos != desiredY * ydelta || xPos != desiredX * xdelta){
            if(yPos < desiredY * ydelta && a % 9 != 8)
                yPos++;
            else if(yPos > desiredY * ydelta && a % 9 != 8)
                yPos--;
            if(xPos< desiredX * xdelta)
                xPos++;
            else if(xPos> desiredX * xdelta)
                xPos--;
            a++;
        }
        else{
            yPos = desiredY;
            xPos = desiredX;
            Board.EndMovement(1);
        }
    }
}
