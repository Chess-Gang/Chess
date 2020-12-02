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
public class Pawn extends Piece{
    
    Pawn(int x, int y, Player play){
        super(x,y,play);
        if(myPlayer.getColor().equals(Color.white))
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/w_pawn_1x.png");
        else
            pieceImage = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/b_pawn_1x.png");
    }
    public Image GetImage(){
        return(pieceImage);
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        emptySpots.clear();
        //black direction
        if(myPlayer.getColor().equals(Color.black)){
            if(Board.CheckifOpenSpot(xPos, yPos - 1)){
                emptySpots.add(new EmptySpace(xPos, yPos - 1));
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
