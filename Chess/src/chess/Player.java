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
    //Players
    private static Player currentTurn;
    public static Player players[] = new Player[4];
    public static int turnTracker = 0;
    public static boolean PlayerInCheck = false;
    
    //Player Info  
    private int playerNum;
    public boolean inCheck = false;
    
    
    Player( int num)
    {
        playerNum = num;
    }
    public static void Reset()
    {
        players[0] = new Player(0);//player 1 white / red 
        players[1] = new Player(1);//player 2 black /blue
        players[2] = new Player(2);//player 1 green
        players[3] = new Player(3);//player 2 yell
        currentTurn = players[0];
        turnTracker = 0;
        PlayerInCheck = false;
    }
    public static Player GetCurrentPlayer()
    {
        return(currentTurn);
    }
    public static void SwitchTurn()
    {
        if(Chess.normalMode){
            if (currentTurn == players[0])
                currentTurn = players[1];
            else
                currentTurn = players[0];
        }
        else if(Chess.P4Mode){
            turnTracker++;
            if(turnTracker == 4)
                turnTracker = 0;
            currentTurn = players[turnTracker];
        }
        PlayerInCheck = false;
        for(int i = 0; i < players.length; i++){
            if(players[i].inCheck)
                PlayerInCheck = true;
        }
    }
    public Integer GetPlayerNumber()
    {
        return (playerNum);
    }
}