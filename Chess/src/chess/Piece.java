/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

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
    Piece(int x, int y, Player play){
        xPos = x;
        yPos =  y;
        myPlayer = play;
    }
    public void SetPossibleMoves(int xDelta, int yDelta){
        
    }
    public void DrawPossibleMoves(Graphics2D g, int xDelta, int yDelta){
        
    }
}
