package chess;

/**
 *
 * @author Conner
 */
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;

public class Chess extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    static Graphics2D g;
    String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    static Button randomize = new Button("Randomize");
    static boolean menuUp;
    static Chess frame;
    static JPanel buttonPanel;
//    static JButton start;
//    static JButton brown;
//    static JButton black;
    static boolean checkStart = false;
    
    public static void main(String[] args) {
        frame = new Chess();
        frame.buttons();
        frame.setFocusable(true);//makes the screen take into a count key inputs adding the jPanel messes with that I think but this line prevents any problems with key inputs
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void buttons(){
        
        //Create button to change board to brown
        //buttons may cause screen to go white in the build version or in the editor IF THIS HAPPENS just comment out this chunk of code starting here ending at frame.getContentPane( ).add(buttonPanel,BorderLayout.SOUTH);
        //white screen is cause by the window.getwidth2() return the wrong nnumber because the buttons mess with it for some reason?
        
        //Create button to change board to brown
        Button brown = new Button("Brown");
        brown.setVisible(false);
        brown.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BROWN);
            
        });
        // Create button to change board to black
        Button black = new Button("Black");
        black.setVisible(false);
        black.addActionListener((ActionEvent e) -> {
            Board.SwitchBoardColor(Board.BackroundType.BLACK);
           
        });
        
        //Create button to randomize back row
        randomize.setVisible(false);
        randomize.addActionListener((ActionEvent e) -> {
            Board.RandomizeBackRow();
            
        });
        
        //create start button
        Button start = new Button("Start");
        start.setVisible(true);
        start.addActionListener((ActionEvent e) -> {          
            if(e.getSource()== start){
                //switches to the screen
                Board.start();
                
                //removes the start button, makes others visible
                start.setVisible(false);
                black.setVisible(true);
                brown.setVisible(true);
                randomize.setVisible(true);
                
                //repaints the panel, switches to the correct buttons
                buttonPanel.revalidate();
                buttonPanel.repaint();
               
            }
         });
        
        //adds buttons to the panel
        buttonPanel = new JPanel( );
        buttonPanel.add(start);
        buttonPanel.add(brown);
        buttonPanel.add(black);
        buttonPanel.add(randomize);
        frame.getContentPane().add(buttonPanel,BorderLayout.NORTH);
        
       
    }
    ////////////////////////////////////////////////////////////////
    // ignore these methods for now i was thinking of maybe adding in a menu that can be opened and closed but for now just leave them
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
        addKeyListener(new KeyAdapter() {//not registering keys, need to fix it
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
        
        Board.firstScreen(g);
        Board.Draw(g);
        
        for (int zi = 0;zi<Board.BOARD_SIZE;zi++)
        {
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.drawString(letters[zi], zi*(Window.getWidth2() / Board.BOARD_SIZE) + 40, Window.getY(-2));
            g.drawString(Integer.toString(zi + 1),Window.getX(-10), zi*(Window.getHeight2()/ Board.BOARD_SIZE) + 95);
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
        Player.Reset();
        Board.Reset();
        
        //when reset the board using buttons();, the randomize button is lost
        //my guess is because it is a global variable vs a local variable like the other buttons
        //also, the randomize button is lost, and when clicked reset, the blk & brn buttons do not change
        //buttons();
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