package chess;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
import javax.swing.JButton;

public class Board {
    public final static int BOARD_SIZE = 8;
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
    private static int ydelta = Window.getHeight2()/NUM_ROWS;
    private static int xdelta = Window.getWidth2()/NUM_COLUMNS;
    
    
    public static void Reset() {
        backroundSquares[0] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown dark_1x.png");
        backroundSquares[1] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown light_1x.png");
        backroundSquares[2] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray dark _1x.png");
        backroundSquares[3] = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square gray light _1x.png");
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
        winner = WinState.NO_WIN;
    }
    
    //move/select the piece the mouse clicked on
    public static void PickPiece(int x, int y){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        if(selectedPiece != null){
            selectedPiece.emptySpots.clear();
            selectedPiece.fullSpots.clear();
        }
        //System.out.println(x + ", " + y);
        for(Piece pec : allPieces){
            if(Window.getX(x) > Window.getX(xdelta*pec.xPos) && Window.getX(x) < Window.getX(xdelta*pec.xPos + xdelta) &&
               Window.getY(y) > Window.getY(ydelta*pec.yPos) && Window.getY(y) < Window.getY(ydelta*pec.yPos + ydelta)){
                if(pec.myPlayer == Player.GetCurrentPlayer()){
                    selectedPiece = pec;
                }
            }
        }
        if(selectedPiece != null)
            selectedPiece.SetPossibleMoves(xdelta, ydelta);
        if(selectedPiece != null && selectedPiece.myPlayer == Player.GetCurrentPlayer()){
            for(EmptySpace space : selectedPiece.emptySpots){
                if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                    board[selectedPiece.xPos][selectedPiece.yPos] = null;//gets rid of current spot on board[][]
                    board[space.xPos][space.yPos] = selectedPiece;//adds the new space the piece is in on [][]
                    selectedPiece.xPos = space.xPos;
                    selectedPiece.yPos = space.yPos;
                    selectedPiece.firstUniqueMove = false;
                    if(space.rook != null){
                        board[space.rook.xPos][space.rook.yPos] = null;
                        space.rook.xPos = space.rookXPos;
                        space.rook.yPos = space.rookYPos;
                        board[space.rook.xPos][space.rook.yPos] = space.rook;
                    }
                    if(selectedPiece instanceof Pawn){
                        Pawn pawn = (Pawn)selectedPiece;
                        if(pawn.MakeQueen()){//if a pawn gets to the end a queen is made
                            int _x = selectedPiece.xPos;
                            int _y = selectedPiece.yPos;
                            Player play = selectedPiece.myPlayer;
                            RemovePiece(selectedPiece);
                            RemovePiece(pawn);
                            allPieces.add(new Queen(_x, _y, play));
                        }
                    }
                    Player.SwitchTurn();
                }
            }
            for(FullSpace space : selectedPiece.fullSpots){
                if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                    RemovePiece(GetPieceMouse(x, y));
                    board[selectedPiece.xPos][selectedPiece.yPos] = null;
                    board[space.xPos][space.yPos] = selectedPiece;
                    selectedPiece.xPos = space.xPos;
                    selectedPiece.yPos = space.yPos;
                    selectedPiece.emptySpots.clear();
                    Player.SwitchTurn();
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
    
    //Remove a piece based off of x,y coords
    public static void RemovePiece(int x,int y){
        Piece piecToRemove = null;
        for(Piece pec : allPieces){
            if(pec.xPos == x && pec.yPos == y){
                piecToRemove = pec;
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
        board[pec.xPos][pec.yPos] = null;
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
    //return a piece based on x,y MOUSE coords
    public static Piece GetPieceMouse(int x,int y){
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
    //draw possible moves
        if(selectedPiece != null && selectedPiece.myPlayer == Player.GetCurrentPlayer())
            selectedPiece.DrawPossibleMoves(g,xdelta,ydelta);
    //draw the pieces
        for(Piece pec : allPieces){
            g.drawImage(pec.pieceImage,Window.getX(xdelta*pec.xPos),Window.getY(ydelta*pec.yPos),xdelta + 4,ydelta + 4,observe);
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
    //Display if a player has won.
        if (winner == WinState.WIN_P1) {
            g.setColor(Color.black);
            g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,50));
            g.drawString("Player 1 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);                
        } else if (winner == WinState.WIN_P2) {
            g.setColor(Color.white);
            g.fillRect(Window.getWidth2() / 2 - 130, Window.getHeight2() / 2, 325, 60);
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.PLAIN,50));
            g.drawString("Player 2 Wins",Window.getWidth2() / 2 - 125,Window.getHeight2() / 2 + 50);                
        } else if (winner == WinState.TIE) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("It is a tie",40,65);                
        }
    }
}