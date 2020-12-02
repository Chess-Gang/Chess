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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Conner
 */
public class Bishop extends Piece{
    public enum Side{LEFT, RIGHT};
    Side startSpot;
    
    Bishop(int x, int y, Player play, Side type){
        super(x,y,play);
        startSpot = type;
        if(myPlayer.getColor().equals(Color.white))
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_bishop_1x.png");
        else
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_bishop_1x.png");
    }
    public Image GetImage(){
        return(pieceImage);
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        emptySpots.clear();
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
}
