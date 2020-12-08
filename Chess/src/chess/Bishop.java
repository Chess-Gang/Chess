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
public class Bishop extends Piece{
    Bishop(int x, int y, Player play){
        super(x,y,play);
        myPieceType = Piece.pieceType.BISHOP;
        if(myPlayer.GetPlayerNumber().equals(0))
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_bishop_1x.png");
        else
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_bishop_1x.png");
    }
    public Image GetImage(){
        return(pieceImage);
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        emptySpots.clear();
        fullSpots.clear();
        boolean stopCheck = false;
        int z = 0;
        
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
