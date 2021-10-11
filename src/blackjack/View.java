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

    public LoginPanel loginPanel = new LoginPanel();

    public JPanel cardPanel = new JPanel(new BorderLayout());
    
    public JPanel inputPanel = new JPanel(new BorderLayout());
    public BetPanel betPanel = new BetPanel();
    public RightPanel rightPanel = new RightPanel();
    public HitStandPanel hitstand = new HitStandPanel();

    
    private boolean started = false;

    public View() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);

        this.setLocationRelativeTo(null);

        this.add(loginPanel);
        // pack();
        this.setVisible(true);

    }

    public void startGame() {

        inputPanel.add(betPanel, BorderLayout.WEST);
        inputPanel.add(hitstand, BorderLayout.CENTER);
        inputPanel.add(rightPanel, BorderLayout.EAST);

        this.getContentPane().removeAll();
        this.setVisible(true);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.add(cardPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();

    }
    
    
    public void setBetLabel(Double betAmount)
    {
        this.betPanel.betAmount.setText("Bet Amount: $" + this.betPanel.betField.getText());
        this.betPanel.betButton.setEnabled(false); // disables preventing user from placing bets during game
        this.hitstand.hit.setEnabled(true); // enables user action after bet has been placed
        this.hitstand.stand.setEnabled(true);
        inputPanel.repaint();
    }
    
    public void updateBalance(Player player)
    {
        String s = String.valueOf(player.getPlayerBalance());
        this.hitstand.balance.setText(s);
        inputPanel.repaint();
    }

    public void addActionListener(ActionListener listener) {
        loginPanel.loginButton.addActionListener(listener);
        betPanel.betButton.addActionListener(listener);
        hitstand.hit.addActionListener(listener);
        hitstand.stand.addActionListener(listener);
//        this.nextButton.addActionListener(listener);
//        this.quitButton.addActionListener(listener);
    }

    // @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        if (!player.isLoginFlag()) {
            loginPanel.unInput.setText("");
            loginPanel.pwInput.setText("");
            
        } else if (!this.started) {
            this.startGame();
            updateBalance(player);
            this.started = true;

        }else if (!this.betPanel.betField.getText().equals(""))
        {
            setBetLabel(Double.parseDouble(this.betPanel.betField.getText()));
            updateBalance(player);
        }
        if (player.getPlayerFinished())
        {
            System.out.println("HIIIIIIIIII");
            this.hitstand.hit.setEnabled(false); // enables user action after bet has been placed
            this.hitstand.stand.setEnabled(false);
        }
        
        System.out.println(player.getPlayerFinished());
        
    }

}
