package chess;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;

public class Board {
    public final static int BOARD_SIZE = 8;
    private final static int NUM_ROWS = BOARD_SIZE;
    private final static int NUM_COLUMNS = BOARD_SIZE;
    private static Piece board[][] = new Piece[NUM_ROWS][NUM_COLUMNS];
    private static ArrayList<Piece> allPieces = new ArrayList <Piece>();
    private enum WinState {NO_WIN,WIN_P1,WIN_P2,TIE};
    private static WinState winner;
    public static Image backroundSquare1;
    private static Image backroundSquare2;
    private static ImageObserver observe;
    private static Piece selectedPiece;
    private static int ydelta = Window.getHeight2()/NUM_ROWS;
    private static int xdelta = Window.getWidth2()/NUM_COLUMNS;
    
    public static void Reset() {
        backroundSquare1 = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown dark_1x.png");
        backroundSquare2 = Toolkit.getDefaultToolkit().getImage("./Chess Sprites/square brown light_1x.png");
        allPieces.clear();
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
        
        //player1's pieces (white)
        allPieces.add(new Queen(3, 0, Player.players[0]));
        allPieces.add(new King(4, 0, Player.players[0]));
        allPieces.add(new Rook(0, 0, Player.players[0]));
        allPieces.add(new Rook(7, 0, Player.players[0]));
        allPieces.add(new Knight(1, 0, Player.players[0]));
        allPieces.add(new Knight(6, 0, Player.players[0]));
        allPieces.add(new Bishop(5, 0, Player.players[0], Bishop.Side.RIGHT));
        allPieces.add(new Bishop(2, 0, Player.players[0], Bishop.Side.LEFT));
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, 1, Player.players[0]));
        
        //player2's pieces (black)
        allPieces.add(new Queen(4, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new King(3, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Rook(0, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Rook(7, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Knight(1, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Knight(6, NUM_ROWS - 1, Player.players[1]));
        allPieces.add(new Bishop(5, NUM_ROWS - 1, Player.players[1], Bishop.Side.LEFT));
        allPieces.add(new Bishop(2, NUM_ROWS - 1, Player.players[1], Bishop.Side.RIGHT));
        for(int i = 0; i < NUM_ROWS; i++)
            allPieces.add(new Pawn(i, NUM_ROWS - 2, Player.players[1]));
        
        UpdateBoard();
        winner = WinState.NO_WIN;
    }
    
    //move/select the piece the mouse clicked on
    public static void PickPiece(int x, int y){
        if(selectedPiece != null){
            selectedPiece.emptySpots.clear();
            selectedPiece.fullSpots.clear();
        }
        for(Piece pec : allPieces){
            if(Window.getX(x) > Window.getX(xdelta*pec.xPos) && Window.getX(x) < Window.getX(xdelta*pec.xPos + xdelta) &&
               Window.getY(y) > Window.getY(ydelta*pec.yPos) && Window.getY(y) < Window.getY(ydelta*pec.yPos + ydelta)){
                if(pec.myPlayer == Player.GetCurrentPlayer()){
                    selectedPiece = pec;
                    System.out.println(selectedPiece.getClass());
                }
            }
        }
        if(selectedPiece != null)
            selectedPiece.SetPossibleMoves(xdelta, ydelta);
        if(selectedPiece != null && selectedPiece.myPlayer == Player.GetCurrentPlayer()){
            for(EmptySpace space : selectedPiece.emptySpots){
                if(Window.getX(x) > Window.getX(xdelta*space.xPos) && Window.getX(x) < Window.getX(xdelta*space.xPos + xdelta) &&
                   Window.getY(y) > Window.getY(ydelta*space.yPos) && Window.getY(y) < Window.getY(ydelta*space.yPos + ydelta)){
                    board[selectedPiece.xPos][selectedPiece.yPos] = null;
                    board[space.xPos][space.yPos] = selectedPiece;
                    selectedPiece.xPos = space.xPos;
                    selectedPiece.yPos = space.yPos;
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
    //Remove a piece based off an instance
    public static void RemovePiece(Piece pec){
        System.out.println(pec.getClass().toString());
        if(pec.getClass().toString() == "class Chess.King" && pec.myPlayer.getColor().equals(Color.black)){
            System.out.println("chess.Board.RemovePiece()");
            winner = WinState.WIN_P1;
        }
        else if(pec.getClass().toString() == "class Chess.King" && pec.myPlayer.getColor().equals(Color.white)){
            System.out.println("chess.Board.RemovePiece()");
            winner = WinState.WIN_P2;
        }
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
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)        
            {
                if((zrow + zcol) % 2 == 0)
                    g.drawImage(backroundSquare1,Window.getX(xdelta*zcol),Window.getY(ydelta*zrow),xdelta + 4,ydelta + 4,observe);
                else
                    g.drawImage(backroundSquare2,Window.getX(xdelta*zcol),Window.getY(ydelta*zrow),xdelta + 4,ydelta + 4,observe);
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
            g.setColor(Color.red);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Player 1 Has Won",40,65);                
        } else if (winner == WinState.WIN_P2) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Player 2 Has Won",40,65);                
        } else if (winner == WinState.TIE) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("It is a tie",40,65);                
        }
    }
}