package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Usernames {
    //static JLabel Player1;
    //final JFrame frame = new JFrame("Player Names");
//    static JTextField tfPlayer1 = new JTextField(20);
//    static JTextField tfPlayer2 = new JTextField(20);
    static String Player1 = "Player 1";
    static String Player2 = "Player 2";
    static String Player3 = "Player 3";
    static String Player4 = "Player 4";
    static JPanel buttonPanel;
    static Button change = new Button("Set Names");
    
    public static void MakeFields() {
      final JFrame Username = new JFrame("Player Names");
      Username.setBounds(200, 200, 300, 300);
      
      JPanel panel = new JPanel();
      JLabel lblPlayer1 = new JLabel("Player 1:");
      JTextField tfPlayer1 = new JTextField(20);
      lblPlayer1.setLabelFor(tfPlayer1);
      
      JLabel lblPlayer2 = new JLabel("Player 2:");
      JTextField tfPlayer2 = new JTextField(20);
      lblPlayer2.setLabelFor(tfPlayer2);
      
      JLabel lblPlayer3 = new JLabel("Player 3:");
      JTextField tfPlayer3 = new JTextField(20);
      lblPlayer3.setLabelFor(tfPlayer3);
      
      JLabel lblPlayer4 = new JLabel("Player 4:");
      JTextField tfPlayer4 = new JTextField(20);
      lblPlayer4.setLabelFor(tfPlayer4);
      //JPanel panel = new JPanel();
      change.addActionListener((ActionEvent e) -> {
            Player1=tfPlayer1.getText();     
            Player2=tfPlayer2.getText();
            Player3=tfPlayer3.getText();
            Player4=tfPlayer4.getText();
            System.out.println(Player1);
            System.out.println(Player2);
            System.out.println(Player3);
            System.out.println(Player4);
            Username.setVisible(false);
            //JOptionPane.showMessageDialog(frame, "You've clicked OK button");
        });
      panel.setLayout(new FlowLayout());
      panel.add(lblPlayer1);
      panel.add(tfPlayer1);
      panel.add(lblPlayer2);
      panel.add(tfPlayer2);
      panel.add(lblPlayer3);
      panel.add(tfPlayer3);
      panel.add(lblPlayer4);
      panel.add(tfPlayer4);
      panel.add(change);
      
      Username.setSize(300, 175);
      Username.getContentPane().add(panel, BorderLayout.CENTER);
      Username.setVisible(true);
//      Username.setVisible(true);
      //System.out.print(Player1);
   }
    //public static void 
    
}