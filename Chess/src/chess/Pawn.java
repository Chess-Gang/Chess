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
import static chess.Board.graphic;
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
public class Pawn extends Piece{
    Pawn(int x, int y, Player play){
        super(x,y,play);
        myPieceType = Piece.pieceType.PAWN;
        if(Chess.normalMode){
            if(myPlayer.GetPlayerNumber().equals(0))
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_pawn_1x.png");
            else
                pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_pawn_1x.png");
        }
        else if(Chess.P4Mode){
            switch (myPlayer.GetPlayerNumber()) {
                case 0:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/blu_pawn_1x.png");
                    break;
                case 1:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/r_pawn_1x.png");
                    break;
                case 2:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/g_pawn_1x.png");
                    break;
                case 3:
                    pieceImage = Toolkit.getDefaultToolkit().getImage("./colored/y_pawn_1x.png");
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
        
        //Up direction
        if(myPlayer.GetPlayerNumber().equals(1)){
            if(Board.CheckifOpenSpot(xPos, yPos - 1)){
                emptySpots.add(new EmptySpace(xPos, yPos - 1));
            }
            if(Board.CheckifOpenSpot(xPos, yPos - 2) && Board.CheckifOpenSpot(xPos, yPos - 1) && firstUniqueMove){
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
        //Down direction
        if(myPlayer.GetPlayerNumber().equals(0)){
            if(Board.CheckifOpenSpot(xPos, yPos + 1)){
                emptySpots.add(new EmptySpace(xPos, yPos + 1));
            }
            if(Board.CheckifOpenSpot(xPos, yPos + 2) &&  Board.CheckifOpenSpot(xPos, yPos + 1) && firstUniqueMove){
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
        if(Chess.P4Mode){
            //Going Left direction
            if(myPlayer.GetPlayerNumber().equals(3)){
                if(Board.CheckifOpenSpot(xPos - 1, yPos)){
                    emptySpots.add(new EmptySpace(xPos - 1, yPos));
                }
                if(Board.CheckifOpenSpot(xPos - 2, yPos) && Board.CheckifOpenSpot(xPos - 1, yPos) && firstUniqueMove){
                    emptySpots.add(new EmptySpace(xPos - 2, yPos));
                }
                if(xPos - 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos + 1 >= 0){
                    if(!Board.CheckifOpenSpot(xPos - 1, yPos + 1) && Board.GetPieceBoard(xPos - 1, yPos + 1).myPlayer != myPlayer){
                        fullSpots.add(new FullSpace(xPos - 1, yPos + 1));
                    }
                }
                if(xPos - 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos - 1 >= 0 && yPos - 1 >= 0){
                    if(!Board.CheckifOpenSpot(xPos - 1, yPos - 1) && Board.GetPieceBoard(xPos - 1, yPos - 1).myPlayer != myPlayer){
                        fullSpots.add(new FullSpace(xPos - 1, yPos - 1));
                    }
                }
            }
            //Going Right direction
            if(myPlayer.GetPlayerNumber().equals(2)){
                if(Board.CheckifOpenSpot(xPos + 1, yPos)){
                    emptySpots.add(new EmptySpace(xPos + 1, yPos));
                }
                if(Board.CheckifOpenSpot(xPos + 2, yPos) &&  Board.CheckifOpenSpot(xPos + 1, yPos) && firstUniqueMove){
                    emptySpots.add(new EmptySpace(xPos + 2, yPos));
                }
                if(xPos + 1 < Board.BOARD_SIZE && yPos + 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos + 1 >= 0){
                    if(!Board.CheckifOpenSpot(xPos + 1, yPos + 1) && Board.GetPieceBoard(xPos + 1, yPos + 1).myPlayer != myPlayer){
                        fullSpots.add(new FullSpace(xPos + 1, yPos + 1));
                    }
                }
                if(xPos + 1 < Board.BOARD_SIZE && yPos - 1 < Board.BOARD_SIZE && xPos + 1 >= 0 && yPos - 1 >= 0){
                    if(!Board.CheckifOpenSpot(xPos + 1, yPos - 1) && Board.GetPieceBoard(xPos + 1, yPos - 1).myPlayer != myPlayer){
                        fullSpots.add(new FullSpace(xPos + 1, yPos - 1));
                    }
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
    public boolean MakeQueen(){
        if(myPlayer.GetPlayerNumber().equals(1)){
            if(yPos == 0)
                return(true);
        }
        else if(myPlayer.GetPlayerNumber().equals(0)){
            if(yPos == Board.BOARD_SIZE - 1)
                return(true);
        }
        else if(Chess.P4Mode){
            if(myPlayer.GetPlayerNumber().equals(2)){
                if(xPos == Board.NUM_COLUMNS - 1)
                    return(true);
                }
            else if(myPlayer.GetPlayerNumber().equals(3)){
                if(xPos == 0)
                    return(true);
            }
        }
        return(false);
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
