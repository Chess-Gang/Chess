package chess;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BorderLayout;


public class Player {
    //Players
    private static Player currentTurn;
    public static Player players[] = new Player[4];
    public static int turnTracker = 0;
    public static boolean PlayerInCheck = false;
    
    //Player Info
    public boolean hasKing = true;
    public boolean won = false;
    public Color myCol;
    private int playerNum;
    public boolean inCheck = false;
    public int points;
    private String name;
    
    
    Player(int num, Color col)
    {
        myCol = col;
        playerNum = num;
        points=0;
        name = PickName(playerNum);
    }
    public static void Reset()
    {
        players[0] = new Player(0, Color.red);//player 1 white / red 
        players[1] = new Player(1, Color.blue);//player 2 black /blue
        players[2] = new Player(2, Color.green);//player 1 green
        players[3] = new Player(3, Color.yellow);//player 2 yellow
        currentTurn = players[0];
        turnTracker = 0;
        PlayerInCheck = false;
    }
    public static void NewGameReset(){
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
            while(!players[turnTracker].hasKing){
                turnTracker++;
                if(turnTracker == 4)
                    turnTracker = 0;
            }
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
    public Integer GetPlayerPoints(){
        return(points);
    }
    public static void AddPoint(int x){
        players[x].points++;
    }
    public static String PickName(int _num){
        switch (_num) {
            case 0:
                return(Usernames.Player1);
            case 1:
                return(Usernames.Player2);
            case 2:
                return(Usernames.Player3);
            default:
                return(Usernames.Player4);
        }
    }
    public static void Draw(Graphics2D g){
            g.setColor(Color.blue);
            g.drawString(Player.PickName(0)+" : "+players[0].points,Window.getWidth2() / 4  - 100,Window.getHeight2() + 95);
            g.setColor(Color.red);            
            g.drawString(Player.PickName(1)+" : "+players[1].points,Window.getWidth2() / 2  - 100,Window.getHeight2() + 95);
            g.setColor(Color.green);
            g.drawString(Player.PickName(2)+" : "+players[2].points,Window.getWidth2() / 4 * 3  - 100,Window.getHeight2() + 95);
            g.setColor(Color.yellow);
            g.drawString(Player.PickName(3)+" : "+players[3].points,Window.getWidth2() - 100,Window.getHeight2() + 95);
    }

}