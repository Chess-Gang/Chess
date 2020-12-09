package chess;

import static chess.Player.players;
import static chess.Piece.observe;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class Board {
    public final static int BOARD_SIZE = 8;//12 for 4 player
    private final static int NUM_ROWS = BOARD_SIZE;
    private final static int NUM_COLUMNS = BOARD_SIZE;
    private static Piece board[][] = new Piece[NUM_ROWS][NUM_COLUMNS];
    private static ArrayList<Piece> allPieces = new ArrayList <Piece>();
    private enum WinState {NO_WIN,WIN_P1,WIN_P2,TIE};
    private static WinState winner;
    private static Image curBoardSquare1;//current sqaures on display
    private static Image curBoardSquare2;
    private static Image backroundSquares[] = new Image[4];//1-2 brown, 3-4 black
    public static enum BackroundType{BROWN, BLACK};
    private static BackroundType backType;
    private static ImageObserver observe;
    private static Piece selectedPiece;
    private static Piece removePiece;
    private static Clip clip;
    public static boolean first;
    public static boolean randomized;
    static Graphics2D graphic;
    private static boolean start;
    //used for the win screen overlay using RBGA values(the 4th value is opacity)
    private static Color myColor = new Color(86, 87, 89, 50);
    
    public static void Reset() {//fix double playing sound at first move
        
//        try {
//	AudioInputStream input = AudioSystem.getAudioInputStream(new File("Chess Sound.wav"));
//	clip=AudioSystem.getClip();
//	clip.open(input);
//        } catch (UnsupportedAudioFileException e) {
//                e.printStackTrace();
//        } catch (IOException e) {
//                e.printStackTrace();
//        } catch (LineUnavailableException e) {
//                e.printStackTrace();
//        }
        backroundSquares[1] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown dark_1x.png");
        backroundSquares[0] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown light_1x.png");
        backroundSquares[3] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray dark _1x.png");
        backroundSquares[2] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray light _1x.png");
        if(backType == null)
            SwitchBoardColor(BackroundType.BROWN);
        allPieces.clear();
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
        
        //player1's pieces (white)
        allPieces.add(new Queen(3, 0, Player.players[0]));
        Rook _wleftRook = new Rook(0, 0, Player.players[0]);
        allPieces.add(_wleftRook);
        Rook _wrightRook = new Rook(7, 0, Player.players[0]);
        allPieces.add(_wrightRook);
        allPieces.add(new King(4, 0, Player.players[0], _wleftRook, _wrightRook));
        allPieces.add(new Knight(1, 0, Player.players[0]));
        allPieces.add(new Knight(6, 0, Player.players[0]));
        allPieces.add(new Bishop(5, 0, Player.players[0]));
        allPieces.add(new Bishop(2, 0, Player.players[0]));
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, 1, Player.players[0]));
        
        //player2's pieces (black)
        allPieces.add(new Queen(4, NUM_ROWS - 1, Player.players[1]));
        Rook _bleftRook = new Rook(0, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_bleftRook);
        Rook _brightRook = new Rook(7, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_brightRook);
        allPieces.add(new King(3, NUM_ROWS - 1, Player.players[1], _bleftRook, _brightRook));
        allPieces.add(new Knight(1, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Knight(6, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Bishop(5, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Bishop(2, NUM_ROWS - 1, Player.players[1]));
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, NUM_ROWS - 2, Player.players[1]));
        
        UpdateBoard();
        first = true;
        randomized = false;
        winner = WinState.NO_WIN;
        selectedPiece = null;
        removePiece = null;
        start = false;
    }
    public static void RandomizeBackRow() {
        
        randomized = true;
        allPieces.clear();
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
        
        //player1's pieces (white)
        int randVal = (int)(Math.random()*NUM_COLUMNS);
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Queen(randVal, 0, Player.players[0]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        Rook _wleftRook = new Rook(randVal, 0, Player.players[0]);
        allPieces.add(_wleftRook);
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        Rook _wrightRook = new Rook(randVal, 0, Player.players[0]);
        allPieces.add(_wrightRook);
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new King(randVal, 0, Player.players[0], _wleftRook, _wrightRook));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Knight(randVal, 0, Player.players[0]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Knight(randVal, 0, Player.players[0]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Bishop(randVal, 0, Player.players[0]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, 0))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Bishop(randVal, 0, Player.players[0]));
        UpdateBoard();
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, 1, Player.players[0]));
        
        //player2's pieces (black)
        randVal = (int)(Math.random()*NUM_COLUMNS);
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Queen(randVal, NUM_ROWS - 1, Player.players[1]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        Rook _bleftRook = new Rook(randVal, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_bleftRook);
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        Rook _brightRook = new Rook(randVal, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_brightRook);
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new King(randVal, NUM_ROWS - 1, Player.players[1], _wleftRook, _wrightRook));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Knight(randVal, NUM_ROWS - 1, Player.players[1]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Knight(randVal, NUM_ROWS - 1, Player.players[1]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Bishop(randVal, NUM_ROWS - 1, Player.players[1]));
        UpdateBoard();
        while(!Board.CheckifOpenSpot(randVal, NUM_ROWS - 1))
            randVal = (int)(Math.random()*NUM_COLUMNS);
        allPieces.add(new Bishop(randVal, NUM_ROWS - 1, Player.players[1]));
        UpdateBoard();
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, NUM_ROWS - 2, Player.players[1]));
        
        UpdateBoard();
    }
    
    //move/select the piece the mouse clicked on
    public static void PickPiece(int x, int y){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        //clip.drain();
        //System.out.println(x + ", " + y);
        for(Piece pec : allPieces){
            if(Window.getX(x) > Window.getX(xdelta*pec.xPos) && Window.getX(x) < Window.getX(xdelta*pec.xPos + xdelta) &&
               Window.getY(y) > Window.getY(ydelta*pec.yPos) && Window.getY(y) < Window.getY(ydelta*pec.yPos + ydelta)){
                if(pec.myPlayer == Player.GetCurrentPlayer() && winner.equals(WinState.NO_WIN)){
                    if(selectedPiece != null)
                        if(!selectedPiece.moving){
                            selectedPiece = pec;
                            selectedPiece.emptySpots.clear();
                            selectedPiece.fullSpots.clear();
                            selectedPiece.SetPossibleMoves(xdelta, ydelta);
                            if(selectedPiece.takenPiece != null)
                                selectedPiece.SetPossibleMoves(xdelta, ydelta, selectedPiece.takenPiece);
                    }
                    if(selectedPiece == null){
                        selectedPiece = pec;
                        selectedPiece.emptySpots.clear();
                        selectedPiece.fullSpots.clear();
                        selectedPiece.SetPossibleMoves(xdelta, ydelta);
                        if(selectedPiece.takenPiece != null)
                            selectedPiece.SetPossibleMoves(xdelta, ydelta, selectedPiece.takenPiece);
                    }
                }
            }
        }
        if(selectedPiece != null && selectedPiece.myPlayer == Player.GetCurrentPlayer()){
            for(EmptySpace space : selectedPiece.emptySpots){
                if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                    board[selectedPiece.xPos][selectedPiece.yPos] = null;//gets rid of current spot on board[][]
                    board[space.xPos][space.yPos] = selectedPiece;//adds the new space the piece is in on [][]
                    if(selectedPiece instanceof Pawn){//if a pawn gets to the end a queen is made
                        Pawn pawn = (Pawn)selectedPiece;
                        if(pawn.MakeQueen()){
                            int _x = selectedPiece.xPos;
                            int _y = selectedPiece.yPos;
                            Player play = selectedPiece.myPlayer;
                            RemovePiece(selectedPiece);
                            RemovePiece(pawn);
                            Queen Q = new Queen(_x, _y, play);
                            allPieces.add(Q);
                            board[_x][_y] = Q;
                            //Player.SwitchTurn();
                            return;
                        }
                    }
                    selectedPiece.SetMove(space.xPos, space.yPos, xdelta, ydelta);
                    //selectedPiece.xPos = space.xPos;
                    //selectedPiece.yPos = space.yPos;
                    selectedPiece.firstUniqueMove = false;
                    selectedPiece.takenPiece = null;
                    Chess.randomize.disable();
                    if(space.rook != null){//moves the rook when casteling
                        space.rook.firstUniqueMove = false;
                        board[space.rook.xPos][space.rook.yPos] = null;
                        space.rook.xPos = space.rookXPos;
                        space.rook.yPos = space.rookYPos;
                        board[space.rook.xPos][space.rook.yPos] = space.rook;
                    }
                    //Player.SwitchTurn();
                    return;
                }
            }
            for(FullSpace space : selectedPiece.fullSpots){
                if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                    selectedPiece.takenPiece = GetPieceMouse(x, y).myPieceType;
                    removePiece = GetPieceMouse(x, y);
                    board[selectedPiece.xPos][selectedPiece.yPos] = null;//gets rid of current spot on board[][]
                    board[space.xPos][space.yPos] = selectedPiece;//adds the new space the piece is in on [][]
                    if(selectedPiece instanceof Pawn){
                        Pawn pawn = (Pawn)selectedPiece;
                        if(pawn.MakeQueen()){//if a pawn gets to the end a queen is made
                            int _x = selectedPiece.xPos;
                            int _y = selectedPiece.yPos;
                            Player play = selectedPiece.myPlayer;
                            RemovePiece(selectedPiece);
                            RemovePiece(pawn);
                            Queen Q = new Queen(_x, _y, play);
                            allPieces.add(Q);
                            board[_x][_y] = Q;
                            //Player.SwitchTurn();
                            return;
                        }
                    }
                    selectedPiece.SetMove(space.xPos, space.yPos, xdelta, ydelta);
                    //selectedPiece.xPos = space.xPos;
                    //selectedPiece.yPos = space.yPos;
                    //Player.SwitchTurn();
                    return;
                    //UpdateBoard();
                }
            }
        }
    } 
    
    //changes the board tiles
    public static void SwitchBoardColor(BackroundType type){
        
        if(type.equals(backType.BROWN)){//changes it to brown
            backType = BackroundType.BLACK;
            curBoardSquare1 = backroundSquares[0];
            curBoardSquare2 = backroundSquares[1];
        }
        else if(type.equals(backType.BLACK)){//cahnges it to grey
            backType = BackroundType.BLACK;
            curBoardSquare1 = backroundSquares[2];
            curBoardSquare2 = backroundSquares[3];
        }
        
    }
    
    //update the board array
    public static void UpdateBoard(){ 
        for(Piece pec : allPieces){
            board[pec.xPos][pec.yPos] = pec;
        }
    }
    
    //Remove a piece based off of x,y coords also decided if game has been won
    public static void RemovePiece(int x,int y){
        Piece piecToRemove = null;
        for(Piece pec : allPieces){
            if(pec.xPos == x && pec.yPos == y){
                piecToRemove = pec;
                if(pec instanceof King && pec.myPlayer.getColor().equals(Color.black))//Gives win to player1
                    winner = WinState.WIN_P1;
                else if(pec instanceof King && pec.myPlayer.getColor().equals(Color.white))//gives win to player2
                    winner = WinState.WIN_P2;
            }
        }
        board[piecToRemove.xPos][piecToRemove.yPos] = null;
        allPieces.remove(piecToRemove);
    }
    //Remove a piece based off an instance also decided if game has been won
    public static void RemovePiece(Piece pec){
        if(pec instanceof King && pec.myPlayer.getColor().equals(Color.black))//Gives win to player1
            winner = WinState.WIN_P1;
        else if(pec instanceof King && pec.myPlayer.getColor().equals(Color.white))//gives win to player2
            winner = WinState.WIN_P2;
        allPieces.remove(pec);
    }
    
    //return a piece based on x,y BOARD coords
    public static Piece GetPieceBoard(int x,int y){
        Piece piecToReturn = null;
        if(x < NUM_COLUMNS && y < NUM_ROWS && x >= 0 && y >= 0){
            for(Piece pec : allPieces){
                if(pec.xPos == x && y == pec.yPos)
                    piecToReturn = pec;
            }
        }
        return(piecToReturn);
    }
    //play the audio and reset the selected piece
    public static void EndMovement(int i ){
        if(first){
            first = false;
            clip.start();
        }
        else
            clip.loop(i);
        selectedPiece.moving = false;
        selectedPiece.emptySpots.clear();
        selectedPiece.fullSpots.clear();
        if(removePiece != null)
            RemovePiece(removePiece);
        removePiece = null;
        selectedPiece = null;
        Player.SwitchTurn();
    }
    //return a piece based on x,y MOUSE coords
    public static Piece GetPieceMouse(int x,int y){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        for(Piece pec : allPieces){
            if(Window.getX(x) > Window.getX(xdelta*pec.xPos) && Window.getX(x) < Window.getX(xdelta*pec.xPos + xdelta) &&
               Window.getY(y) > Window.getY(ydelta*pec.yPos) && Window.getY(y) < Window.getY(ydelta*pec.yPos + ydelta)){
                    return(pec);
            }
        }
        return(null);
    }
    //check if a place on the board is empty
    public static boolean CheckifOpenSpot(int x, int y){
        if(x < NUM_COLUMNS && y < NUM_ROWS && x >= 0 && y >= 0)
            if(board[x][y] == null)
                return(true);
        return(false);
    }
    ////////////////////////////////////////////////
    
    //sets start to true, activates the screen switching
    public static void start(){
        start = true;
    }
   
    //paints the first screen
    public static void firstScreen(Graphics2D g) {
        if(start == false)
        g.setColor(Color.GRAY);
        g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.PLAIN,50));
        g.drawString("WELCOME TO CHESS!",75,Window.WINDOW_HEIGHT/2);
        
        
    }
    
    /////////////////////////////////////////////////
    //Draw on the board.    
    public static void Draw(Graphics2D g) {
        if(start){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        graphic = g;
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)        
            {
                if((zrow + zcol) % 2 == 0)
                    g.drawImage(curBoardSquare1,Window.getX(xdelta*zcol),Window.getY(ydelta*zrow),xdelta + 4,ydelta + 4,observe);
                else
                    g.drawImage(curBoardSquare2,Window.getX(xdelta*zcol),Window.getY(ydelta*zrow),xdelta + 4,ydelta + 4,observe);
            }
        }
    //Draw the grid.        
        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(Window.getX(0),Window.getY(zi*ydelta),
                    Window.getX(Window.getWidth2()),Window.getY(zi*ydelta));
        }

        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(Window.getX(zi*xdelta),Window.getY(0),
                    Window.getX(zi*xdelta),Window.getY(Window.getHeight2()));
        }
    //draw possible moves
        boolean activePiece = selectedPiece != null;
        if(activePiece && selectedPiece.myPlayer == Player.GetCurrentPlayer() && !selectedPiece.moving)
            selectedPiece.DrawPossibleMoves(g,xdelta,ydelta);
    //draw the pieces
        for(Piece pec : allPieces){
            if(pec != selectedPiece)
                g.drawImage(pec.pieceImage,Window.getX(xdelta*pec.xPos),Window.getY(ydelta*pec.yPos),xdelta + 4,ydelta + 4,observe);
            else if(!selectedPiece.moving)
                g.drawImage(pec.pieceImage,Window.getX(xdelta*pec.xPos),Window.getY(ydelta*pec.yPos),xdelta + 4,ydelta + 4,observe);
        }
    //Run the animation method
        if(activePiece && selectedPiece.moving)
            selectedPiece.Move(xdelta, ydelta, g);
        if (null != winner) //Display if a player has won.
            switch (winner) {
                case WIN_P1:
                    g.setColor(myColor);
                    g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                    g.setColor(Color.black);
                    g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
                    g.setColor(Color.white);
                    g.setFont(new Font("Arial",Font.PLAIN,50));
                    g.drawString("Player 1 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);
                    break;
                case WIN_P2:
                    g.setColor(myColor);
                    g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                    g.setColor(Color.white);
                    g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
                    g.setColor(Color.black);
                    g.setFont(new Font("Arial",Font.PLAIN,50));
                    g.drawString("Player 2 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);
                    break;
                case TIE:
                    g.setColor(myColor);
                    g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                    g.setColor(Color.white);
                    g.setFont(new Font("Arial",Font.PLAIN,20));
                    g.drawString("It is a tie",40,65);
                    break;
                default:
                    break;
            }
        
        //adds current player to top
        if(Player.GetCurrentPlayer() == players[0]){
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.setColor(Color.white);
            g.drawString("White's turn",38,65);
        }
        else{
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Blacks's turn",38,65);
        }
        }
        
    }
    
    public static void DrawPiece() {
        if(start){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        
        Board.Draw(graphic);
        graphic.setColor(Color.red);
        graphic.fillRect(xdelta, ydelta, 200, 200);
        graphic.drawImage(selectedPiece.pieceImage,Window.getX(xdelta*selectedPiece.xPos),Window.getY(ydelta*selectedPiece.yPos),xdelta + 4,ydelta + 4,observe);
        }
        //*/
    }
}