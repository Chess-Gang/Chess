package chess;

/**
 *
 * @author Conner
 */
import static chess.Board.BOARD_SIZE;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class Chess extends JFrame implements Runnable {
    //UI / Frame Stuff
    static Button normalModeBut = new Button("Normal Chess");;
    static Button P4ModeBut = new Button("4 Player Chess");;
    static Button randomize = new Button("Randomize");
    static Chess frame;
    static JPanel buttonPanel;
    boolean animateFirstTime = true;   
    static boolean menuUp;
    
    //Game Mode Info
    public static boolean normalMode;
    public static boolean P4Mode;
    
    //Graphichs
    Image image;
    static Graphics2D g;
    
    //Arrays
    String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};   
 
    
    public static void main(String[] args) {
        frame = new Chess();
        
        //Create button to change board to brown
        Button brown = new Button("Brown");
        brown.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BROWN);
            //JOptionPane.showMessageDialog(frame, "You've clicked OK button");//this shows a little pop up window with that text
        });
        // Create button to change board to black
        Button black = new Button("Black");
        black.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BLACK);
            //JOptionPane.showMessageDialog(frame,"You've clicked Cancel button");//this shows a little pop up window with that text
        });
        randomize.addActionListener((ActionEvent e) -> {
            Board.RandomizeBackRow();
        });
        normalModeBut.addActionListener((ActionEvent e) -> {
            Chess.MenuChange();//without these two menuChanges the frame wont register keys for some reason?
            Chess.MenuChange();
            Board.BOARD_SIZE = 8;
            Board.NUM_ROWS = 8;
            Board.NUM_COLUMNS = 8;
            normalMode = true;
            //currentBoard = new Board();
            Board.NormalReset();
            P4ModeBut.disable();
        });
        P4ModeBut.addActionListener((ActionEvent e) -> {
            Chess.MenuChange();//without these two menuChanges the frame wont register keys for some reason?
            Chess.MenuChange();
            Board.BOARD_SIZE = 12;
            Board.NUM_ROWS = 12;
            Board.NUM_COLUMNS = 12;
            P4Mode = true;
            //currentBoard = new P4Board();
            Board.P4Reset();
            normalModeBut.disable();
        });
        // Add buttons to a panel
        buttonPanel = new JPanel( );
        buttonPanel.add(normalModeBut);
        buttonPanel.add(P4ModeBut);
        buttonPanel.add(brown);
        buttonPanel.add(black);
        buttonPanel.add(randomize);
        frame.getContentPane().add(buttonPanel,BorderLayout.NORTH);
        
        frame.setFocusable(true);//makes the screen take into a count key inputs adding the jPanel messes with that I think but this line prevents any problems with key inputs
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void MenuChange(){
        menuUp = !menuUp;
        if(menuUp)
            frame.getContentPane().remove(buttonPanel);
        else
            frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
    }
    public Chess() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton() ) {
                    int x = e.getX() - Window.getX(0);
                    int y = e.getY() - Window.getY(0);
                    Board.PickPiece(x,y);
                }
                if (e.BUTTON3 == e.getButton()) {
                    int x = e.getX() - Window.getX(0);
                    int y = e.getY() - Window.getY(0);
                    reset();
                }
                if (e.BUTTON2 == e.getButton()) {
                    Chess.MenuChange();
                    //reset();
                }
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {

            //repaint();
          }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {

            //repaint();
          }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    Chess.MenuChange();
                    //reset();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        
        g.setColor(Color.black);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.black);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        if(Chess.normalMode || Chess.P4Mode){
            Board.Draw(g);
            for (int zi = 0;zi<Board.BOARD_SIZE;zi++)
            {
                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.PLAIN,15));
                g.drawString(letters[zi], zi*(Window.getWidth2() / Board.BOARD_SIZE) + 40, Window.getY(-2));
                g.drawString(Integer.toString(zi + 1),Window.getX(-20), zi*(Window.getHeight2()/ Board.BOARD_SIZE) + 95);
            }
        }
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double fps = 150;//150
            double seconds = 1/fps;    //time that 1 frame takes. //.1 = 10fps
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
    
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        Chess.randomize.enable();
        //board[0] = new Board();
        //board[1] = new P4Board();
        Player.Reset();
        if(Chess.normalMode){
            Board.BOARD_SIZE = 8;
            Board.NormalReset();
        }
        else if(Chess.P4Mode){
            Board.BOARD_SIZE = 12;
            Board.P4Reset();
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }

            reset();

        }

        
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}