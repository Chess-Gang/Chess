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
public class Rook extends Piece{
    Rook(int x, int y, Player play){
        super(x,y,play);
        myPieceType = Piece.pieceType.ROOK;
        if(Chess.normalMode){
            if(myPlayer.GetPlayerNumber().equals(0))
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_rook_1x.png");
            else
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_rook_1x.png");
        }
        else if(Chess.P4Mode){
            switch (myPlayer.GetPlayerNumber()) {
                case 0:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/blu_rook_1x.png");
                    break;
                case 1:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/r_rook_1x.png");
                    break;
                case 2:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/g_rook_1x.png");
                    break;
                case 3:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/y_rook_1x.png");
                    break;
                default:
                    break;
            }
        }
    }
    public Image GetImage(){
        return(pieceImage);
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        emptySpots.clear();
        fullSpots.clear();
        boolean stopCheck = false;
        
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
