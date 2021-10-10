/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ivan
 */
public class View extends JFrame implements Observer {

    public class userPanel extends JPanel {

        private BufferedImage img = null;

        public userPanel() {

            super(new BorderLayout());

            try {
                img = ImageIO.read(new File("./resources/images/background.jpg"));
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }

    }
    private userPanel userPanel = new userPanel();
    private JLabel uName = new JLabel("Username: ");
    private JLabel pWord = new JLabel("Password: ");
    public JTextField unInput = new JTextField(10);
    public JTextField pwInput = new JTextField(10);
    private JLabel wrongName = new JLabel("Wrong username or passwork!");
    public JButton loginButton = new JButton("Log in");

    private JPanel cardPanel = new JPanel(new BorderLayout());

    private JPanel inputPanel = new JPanel(new BorderLayout());
    private JButton hit = new JButton("HIT");
    private JButton stand = new JButton("STAND");
    private JLabel balance = new JLabel("Balance: ");
    private JButton reset = new JButton("Reset");
    private JButton quit = new JButton("Quit");
    private JLabel betAmount = new JLabel("Bet Amount: ");
    public JTextField betField = new JTextField();
    public JButton betButton = new JButton("Place bet");
    public JLabel message = new JLabel("");
    private boolean started = false;

    public View() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.userPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setLocationRelativeTo(null);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0, 10, 0, 10);  //top padding
        this.userPanel.add(quit, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);  //top padding
        this.userPanel.add(loginButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.userPanel.add(unInput, c);

        //this.userPanel.add(pwInput);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.userPanel.add(pwInput, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        uName.setForeground(Color.WHITE);
        this.userPanel.add(uName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pWord.setForeground(Color.WHITE);
        this.userPanel.add(pWord, c);

        this.add(userPanel);
       // pack();
        this.setVisible(true);

    }

    public void startGame() {
        inputPanel.add(hit, BorderLayout.SOUTH);
        inputPanel.add(stand);
        inputPanel.add(balance);
        inputPanel.add(reset, BorderLayout.NORTH);
        inputPanel.add(quit, BorderLayout.NORTH);
        inputPanel.add(betAmount);
        inputPanel.add(betField);
        inputPanel.add(betButton);
        
        
        
        

        
        this.getContentPane().removeAll();
        inputPanel.setVisible(true);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.add(cardPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();

    }

    
        public void addActionListener(ActionListener listener) {
        this.loginButton.addActionListener(listener);
//        this.nextButton.addActionListener(listener);
//        this.quitButton.addActionListener(listener);
    }
    
    
    // @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        if (!player.isLoginFlag()) {
            this.unInput.setText("");
            this.pwInput.setText("");
            this.message.setText("Invalid username or password.");
        } else if (!this.started) {
            this.startGame();
            this.started = true;

        }
    }

}
