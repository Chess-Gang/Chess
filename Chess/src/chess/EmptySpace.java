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
public class EmptySpace {
    public int xPos;
    public int yPos;
    public Rook rook;
    public int rookXPos;
    public int rookYPos;

    EmptySpace(int x, int y) {
        xPos = x;
        yPos = y;
    }
    EmptySpace(int x, int y, int x2, int y2, Rook _rook) {
        xPos = x;
        yPos = y;
        rookXPos = x2;
        rookYPos = y2;
        rook = _rook;
    }
}
