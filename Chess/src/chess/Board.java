package chess;


import static chess.Chess.normalMode;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Board{
    //Board Size
    public static int BOARD_SIZE;
    public static int NUM_ROWS = BOARD_SIZE;
    public static int NUM_COLUMNS = BOARD_SIZE;
    
    //Arrays
    private static Piece board[][];//keep track of pieces on the board
    private static ArrayList<Piece> allPieces = new ArrayList <Piece>();//keeps track of the pieces regardless of posistion
    
    //Enums
    private enum WinState {NO_WIN,TIE,WIN_P1,WIN_P2,WIN_P3,WIN_P4};
    private static WinState winner;
    
    //Audio
    private static Clip clip;//the chess sound
    
    //Backround Info
    public static enum BackroundType{BROWN, BLACK};
    private static BackroundType backType;
    private static Image backroundSquares[] = new Image[4];//1-2 brown, 3-4 black
    private static Image curBoardSquare1;//current sqaures on display
    private static Image curBoardSquare2;
    
    //Piece Instances / Info
    private static Piece selectedPiece;//curently selected piece
    private static Piece removePiece;// instance of the piece to remove
    public static boolean first;//after the first move this is false
    
    //Graphics
    private static ImageObserver observe;
    static Graphics2D graphic;
    static int counter;//these ints prevent a concurrent modification error that somtime happened at start
    static int allPiecesSize1;
    static int allPiecesSize2;

    //for the start & end screens
    private static boolean start;
    private static Color myColor = new Color(86, 87, 89, 50);

    
    public static void NormalReset() {
        board = new Piece[NUM_ROWS][NUM_COLUMNS];
        try {
	AudioInputStream input = AudioSystem.getAudioInputStream(new File("Chess Sound.wav"));
	clip = AudioSystem.getClip();
	clip.open(input);
        } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (LineUnavailableException e) {
                e.printStackTrace();
        }
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
        allPieces.add(new Queen(4, 0, Player.players[0]));
        Rook _wleftRook = new Rook(0, 0, Player.players[0]);
        allPieces.add(_wleftRook);
        Rook _wrightRook = new Rook(7, 0, Player.players[0]);
        allPieces.add(_wrightRook);
        allPieces.add(new King(3, 0, Player.players[0], _wleftRook, _wrightRook));
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
        winner = WinState.NO_WIN;
        selectedPiece = null;
        removePiece = null;
    }
    public static void P4Reset() {
        board = new Piece[NUM_ROWS][NUM_COLUMNS];
        try {
	AudioInputStream input = AudioSystem.getAudioInputStream(new File("Chess Sound.wav"));//sound not getting set 12-7
	clip = AudioSystem.getClip();
	clip.open(input);
        } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (LineUnavailableException e) {
                e.printStackTrace();
        }
        backroundSquares[1] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown dark_1x.png");
        backroundSquares[0] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown light_1x.png");
        backroundSquares[3] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray dark _1x.png");
        backroundSquares[2] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray light _1x.png");
        if(backType == null)
            SwitchBoardColor(Board.BackroundType.BROWN);
        allPieces.clear();
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
        
        //player0's pieces (white)
        allPieces.add(new Queen(6, 0, Player.players[0]));
        Rook _0leftRook = new Rook(2, 0, Player.players[0]);
        allPieces.add(_0leftRook);
        Rook _0rightRook = new Rook(9, 0, Player.players[0]);
        allPieces.add(_0rightRook);
        allPieces.add(new King(5, 0, Player.players[0], _0leftRook, _0rightRook));
        allPieces.add(new Knight(3, 0, Player.players[0]));
        allPieces.add(new Knight(8, 0, Player.players[0]));
        allPieces.add(new Bishop(7, 0, Player.players[0]));
        allPieces.add(new Bishop(4, 0, Player.players[0]));
        for(int i = 0; i < 8; i++)
            allPieces.add(new Pawn(i + 2, 1, Player.players[0]));

        //player1's pieces (black)
        allPieces.add(new Queen(6, NUM_ROWS - 1, Player.players[1]));
        Rook _1leftRook = new Rook(2, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_1leftRook);
        Rook _1rightRook = new Rook(9, NUM_ROWS - 1, Player.players[1]);
        allPieces.add(_1rightRook);
        allPieces.add(new King(5, NUM_ROWS - 1, Player.players[1], _1leftRook, _1rightRook));
        allPieces.add(new Knight(3, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Knight(8, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Bishop(7, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Bishop(4, NUM_ROWS - 1, Player.players[1]));
        for(int i = 0; i < 8; i++)
            allPieces.add(new Pawn(i + 2, NUM_ROWS - 2, Player.players[1]));

        //player2's pieces (black)
        allPieces.add(new Queen(0, 6, Player.players[2]));
        Rook _2leftRook = new Rook(0, 2, Player.players[2]);
        allPieces.add(_2leftRook);
        Rook _2rightRook = new Rook(0, 9, Player.players[2]);
        allPieces.add(_2rightRook);
        allPieces.add(new King(0, 5, Player.players[2], _2leftRook, _2rightRook));
        allPieces.add(new Knight(0, 3, Player.players[2]));
        allPieces.add(new Knight(0, 8, Player.players[2]));
        allPieces.add(new Bishop(0, 7, Player.players[2]));
        allPieces.add(new Bishop(0, 4, Player.players[2]));
        for(int i = 0; i < 8; i++)
            allPieces.add(new Pawn(1, i + 2, Player.players[2]));
        
        //player3's pieces (black)
        allPieces.add(new Queen(NUM_COLUMNS - 1, 6, Player.players[3]));
        Rook _3leftRook = new Rook(NUM_COLUMNS - 1, 2, Player.players[3]);
        allPieces.add(_3leftRook);
        Rook _3rightRook = new Rook(NUM_COLUMNS - 1, 9, Player.players[3]);
        allPieces.add(_3rightRook);
        allPieces.add(new King(NUM_COLUMNS - 1, 5, Player.players[3], _3leftRook, _3rightRook));
        allPieces.add(new Knight(NUM_COLUMNS - 1, 3, Player.players[3]));
        allPieces.add(new Knight(NUM_COLUMNS - 1, 8, Player.players[3]));
        allPieces.add(new Bishop(NUM_COLUMNS - 1, 7, Player.players[3]));
        allPieces.add(new Bishop(NUM_COLUMNS - 1, 4, Player.players[3]));
        for(int i = 0; i < 8; i++)
            allPieces.add(new Pawn(NUM_COLUMNS - 2, i + 2, Player.players[3]));
        
        UpdateBoard();
        Chess.randomize.disable();
        first = true;
        winner = Board.WinState.NO_WIN;
        selectedPiece = null;
        removePiece = null;
    }
    
    public static void RandomizeBackRow() {
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
        if(start){
            int ydelta = Window.getHeight2()/NUM_ROWS;
            int xdelta = Window.getWidth2()/NUM_COLUMNS;
            clip.drain();//this makes sure that the sound is done playing before the next move
            //System.out.println(x + ", " + y);
            for(Piece pec : allPieces){
                if(Window.getX(x) > Window.getX(xdelta*pec.xPos) && Window.getX(x) < Window.getX(xdelta*pec.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*pec.yPos) && Window.getY(y) < Window.getY(ydelta*pec.yPos + ydelta)){
                    if(pec.myPlayer == Player.GetCurrentPlayer() && winner.equals(WinState.NO_WIN)){

                        selectedPiece = pec;

                        if(Player.GetCurrentPlayer().inCheck){//might want to remove this, because if you want to move a differnt piece to save your king you wont be able to
                            if(!(selectedPiece instanceof King)){//makes it so the effected player can only chose thier king
                                selectedPiece = null;
                                return;
                            }
                            selectedPiece.SetPossibleMoves(xdelta, ydelta);
                            if(selectedPiece.takenPiece != null)
                                selectedPiece.SetPossibleMoves(xdelta, ydelta, selectedPiece.takenPiece); 
                        }
                        else{
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
                        selectedPiece.SetMove(space.xPos, space.yPos, xdelta, ydelta);
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
                        return;
                    }
                }
                for(FullSpace space : selectedPiece.fullSpots){
                    if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                       Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                        if(Chess.moveStealEnabled)
                            selectedPiece.takenPiece = GetPieceMouse(x, y).myPieceType;
                        removePiece = GetPieceMouse(x, y);
                        board[selectedPiece.xPos][selectedPiece.yPos] = null;//gets rid of current spot on board[][]
                        board[space.xPos][space.yPos] = selectedPiece;//adds the new space the piece is in on [][]
                        selectedPiece.SetMove(space.xPos, space.yPos, xdelta, ydelta);
                        return;
                    }
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
                if(pec instanceof King && pec.myPlayer.GetPlayerNumber().equals(1)){//Gives win to player1
                    winner = WinState.WIN_P1;
                    Player.AddPoint(0);
                }
                else if(pec instanceof King && pec.myPlayer.GetPlayerNumber().equals(0)){//gives win to player2
                    winner = WinState.WIN_P2;
                    Player.AddPoint(1);
                        }
            }
        }
        board[piecToRemove.xPos][piecToRemove.yPos] = null;
        allPieces.remove(piecToRemove);
    }

    //Remove a piece based off an instance also decided if game has been won
    public static void RemovePiece(Piece pec){
        if(pec instanceof King && pec.myPlayer.GetPlayerNumber().equals(1)){//Gives win to player1
            winner = WinState.WIN_P1;
            Player.AddPoint(0);
        }
        else if(pec instanceof King && pec.myPlayer.GetPlayerNumber().equals(0)){//gives win to player2
            winner = WinState.WIN_P2;
            Player.AddPoint(1);
                }
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
    public static void EndMovement(int i){
        int xdelta = Window.getWidth2() / NUM_COLUMNS;
        int ydelta = Window.getHeight2() / NUM_ROWS;
        if(first){
            first = false;
            clip.start();
        }
        else
            clip.loop(i);
        selectedPiece.moving = false;
        if(removePiece != null)
            RemovePiece(removePiece);
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
            }
        }
        CheckIfInCheck(xdelta, ydelta);
        selectedPiece.emptySpots.clear();
        selectedPiece.fullSpots.clear();
        removePiece = null;
        selectedPiece = null;
        Player.SwitchTurn();
    }
    //Checks to see if a king is in check
    public static void CheckIfInCheck(int xdelta, int ydelta){
        for(Player play : Player.players)
            play.inCheck = false;
        for(Piece piece : allPieces){
            piece.SetPossibleMoves(xdelta, ydelta);
            for(FullSpace space : piece.fullSpots){
                if(board[space.xPos][space.yPos] instanceof King){
                    board[space.xPos][space.yPos].myPlayer.inCheck = true;
                }
            }
        }
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
            counter++;
            if(counter % 2 == 0)// this prevents the concurrent modification error that happens at start somtimes
                allPiecesSize1 = allPieces.size();
            else if(counter % 2 == 1)
                allPiecesSize2 = allPieces.size();
            if(allPiecesSize1 == allPiecesSize2){
                for(Piece pec : allPieces){
                    if(pec != selectedPiece)
                        g.drawImage(pec.pieceImage,Window.getX(xdelta*pec.xPos),Window.getY(ydelta*pec.yPos),xdelta + 4,ydelta + 4,observe);
                    else if(!selectedPiece.moving)
                        g.drawImage(pec.pieceImage,Window.getX(xdelta*pec.xPos),Window.getY(ydelta*pec.yPos),xdelta + 4,ydelta + 4,observe);
                }
            }
        //Run the animation method
            if(activePiece && selectedPiece.moving)
                selectedPiece.Move(xdelta, ydelta, g);
        //Display in check or not
            if(Player.PlayerInCheck){
                String checkString = "";
                int playersInCheck = 0;
                for(Player play : Player.players){
                    if (play.inCheck) {
                        g.setFont(new Font("Arial",Font.PLAIN,25));
                        checkString += "|" + (play.GetPlayerNumber() + 1) + "| ";
                        playersInCheck++;
                    }
                }
                g.setColor(Color.black);
                g.fillRect(Window.getWidth2() / 2 - 65, Window.getHeight2() / 4, 210 + playersInCheck*32, 40);
                g.setColor(Color.white);
                g.drawString("Player " + checkString + " in Check",Window.getWidth2() / 2 - 60,Window.getHeight2() / 4 + 30);  
            }
        //Display if a player has won.
            if (winner == WinState.WIN_P1) {
                g.setColor(myColor);
                g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                g.setColor(Color.black);
                g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.PLAIN,50));
                g.drawString("Player 1 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);                
            } else if (winner == WinState.WIN_P2) {
                g.setColor(myColor);
                g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                g.setColor(Color.white);
                g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
                g.setColor(Color.black);
                g.setFont(new Font("Arial",Font.PLAIN,50));
                g.drawString("Player 2 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);                
            } else if (winner == WinState.TIE) {
                g.setColor(myColor);
                g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.PLAIN,20));
                g.drawString("It is a tie",40,65);                
            }

            
            
        }
///////////////////////////////////////////////////////////////////////////
        //draws the current player at the top for 2 player
        if(Chess.normalMode){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,20));
                if(Player.GetCurrentPlayer() == Player.players[0])
                    g.drawString("White's turn",40,65);
                else
                    g.drawString("Blacks's turn",40,65);
        }
            
        //draws the current player at the top for four player
        if(Chess.P4Mode){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,20));
                   
                if(Player.GetCurrentPlayer() == Player.players[0])
                    g.drawString("Blues's turn",40,65);
                else if (Player.GetCurrentPlayer() == Player.players[1])
                    g.drawString("Red's turn",40,65);
                else if(Player.GetCurrentPlayer() == Player.players[2])
                    g.drawString("Green's turn",40,65);
                else
                    g.drawString("Yellow's turn",40,65);
            }
    }
  //////////////////////////////////////////////////////////////////
    public static void DrawPiece() {
        if(start){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        Board.Draw(graphic);
        graphic.setColor(Color.red);
        graphic.fillRect(xdelta, ydelta, 200, 200);
        graphic.drawImage(selectedPiece.pieceImage,Window.getX(xdelta*selectedPiece.xPos),Window.getY(ydelta*selectedPiece.yPos),xdelta + 4,ydelta + 4,observe);
        //*/
        }
    }
    //sets start to true, activates the screen switching
    public static void start(){
        start = true;
    }
    //sets start to false, de-activates the screen switching
    public static void stop(){
        start = false;
    }
   
//////////////////////////// I will work on making this look nicer///////////////////////////
    //paints the first screen
    public static void firstScreen(Graphics2D g) {
        if(start == false)
        g.setColor(Color.GRAY);
        g.fillRect(Window.getX(0),Window.getY(0), Window.getWidth2(), Window.getHeight2());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.PLAIN,50));
        g.drawString("WELCOME TO CHESS!",75,150);
        g.setFont(new Font("Arial",Font.PLAIN,20));
        g.drawString("Click 4 player mode to play a 4-way chess game", 110, 200);
        g.drawString("or click normal mode to play a 2-player chess game", 110, 220);
        g.drawString("the 'black' & 'brown' buttons change the color of the board", 80, 340);
        g.drawString("the 'randomize' button change randomize the back row of pieces", 60, 360);
        g.drawString("If 'move steal' is on, the pieces can steal the moves of other pieces.", 50, 380);
        
    }
    

}