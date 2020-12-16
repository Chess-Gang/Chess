/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Conner
 */
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
public class Knight extends Piece{
    Knight(int x, int y, Player play){
        super(x,y,play);
        myPieceType = Piece.pieceType.KNIGHT;
        if(Chess.normalMode){
            if(myPlayer.GetPlayerNumber().equals(0))
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_knight_1x.png");
            else
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_knight_1x.png");
        }
        else if(Chess.P4Mode){
            switch (myPlayer.GetPlayerNumber()) {
                case 0:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/blu_knight_1x.png");
                    break;
                case 1:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/r_knight_1x.png");
                    break;
                case 2:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/g_knight_1x.png");
                    break;
                case 3:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/y_knight_1x.png");
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
        int stopCheck;
        
        stopCheck = xPos + 1;
        for(int i = xPos - 1; i <= stopCheck && yPos - 2 >= 0; i += 2){//UP right and left
            if(i < Board.BOARD_SIZE && i >= 0){
                if(Board.CheckifOpenSpot(i, yPos - 2)){        
                    emptySpots.add(new EmptySpace(i, yPos - 2));
                }
                else if(!Board.CheckifOpenSpot(i, yPos - 2) && Board.GetPieceBoard(i, yPos - 2).myPlayer != myPlayer){
                    fullSpots.add(new FullSpace(i, yPos - 2));
                }
            }
        }
        stopCheck = yPos + 1;
        for(int i = yPos - 1; i <= stopCheck && xPos + 2 < Board.BOARD_SIZE; i += 2){//RIGHT up and down
            if(i < Board.BOARD_SIZE && i >= 0){
                if(Board.CheckifOpenSpot(xPos + 2, i)){        
                    emptySpots.add(new EmptySpace(xPos + 2, i));
                }
                else if(!Board.CheckifOpenSpot(xPos + 2, i) && Board.GetPieceBoard(xPos + 2, i).myPlayer != myPlayer){
                    fullSpots.add(new FullSpace(xPos + 2, i));
                }
            }
        }
        stopCheck = xPos + 1;
        for(int i = xPos - 1; i <= stopCheck && yPos + 2 < Board.BOARD_SIZE && i < Board.BOARD_SIZE  && i >= 0; i += 2){//DOWN right and left
            if(i < Board.BOARD_SIZE && i >= 0){
                if(Board.CheckifOpenSpot(i, yPos + 2)){        
                    emptySpots.add(new EmptySpace(i, yPos + 2));
                }
                else if(!Board.CheckifOpenSpot(i, yPos + 2) && Board.GetPieceBoard(i, yPos + 2).myPlayer != myPlayer){
                    fullSpots.add(new FullSpace(i, yPos + 2));
                }
            }
        }
        stopCheck = yPos + 1;
        for(int i = yPos - 1; i <= stopCheck && xPos - 2 >= 0; i += 2){//LEFT up and down
            if(i < Board.BOARD_SIZE && i >= 0){
                if(Board.CheckifOpenSpot(xPos - 2, i)){        
                    emptySpots.add(new EmptySpace(xPos - 2, i));
                }
                else if(!Board.CheckifOpenSpot(xPos - 2, i) && Board.GetPieceBoard(xPos - 2, i).myPlayer != myPlayer){
                    fullSpots.add(new FullSpace(xPos - 2, i));
                }
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
            if(yPos < desiredY * ydelta)
                yPos++;
            else if(yPos > desiredY * ydelta)
                yPos--;
            else if(xPos< desiredX * xdelta)
                xPos++;
            else if(xPos> desiredX * xdelta)
                xPos--;
        }
        else{
            yPos = desiredY;
            xPos = desiredX;
            Board.EndMovement(1);
        }
    }
}
