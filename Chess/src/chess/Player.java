/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;

/**
 *
 * @author Conner
 */
public class Player {
    
    private static Player currentTurn;
    public static Player players[] = new Player[2];
    private Color color;   
    private int playerNum;
    
    
    Player(Color _color, int num)
    {
        color = _color;
        playerNum = num;
    }
    public static void Reset()
    {
        players[0] = new Player(Color.white, 1);//player 1
        players[1] = new Player(Color.black, 2);//player 2
        currentTurn = players[0];
    }
    public static Player GetCurrentPlayer()
    {
        return(currentTurn);
    }
    public static void SwitchTurn()
    {
        if (currentTurn == players[0])
            currentTurn = players[1];
        else
            currentTurn = players[0];
    }
    public Color getColor()
    {
        return (color);
    }
    public Integer GetPlayerNumber()
    {
        return (playerNum);
    }
}