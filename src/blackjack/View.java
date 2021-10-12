/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    public CardsPanel playerCards = new CardsPanel();
    public CardsPanel dealerCards = new CardsPanel();

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

        cardPanel.add(playerCards, BorderLayout.SOUTH);
        cardPanel.add(dealerCards, BorderLayout.NORTH);

        this.getContentPane().removeAll();
        this.setVisible(true);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.add(cardPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();

    }

    public void setBetLabel(Double betAmount) {
        this.betPanel.betAmount.setText("Bet Amount: $" + this.betPanel.betField.getText());
        this.betPanel.betButton.setEnabled(false); // disables preventing user from placing bets during game
        this.hitstand.hit.setEnabled(true); // enables user action after bet has been placed
        this.hitstand.stand.setEnabled(true);
        inputPanel.repaint();
    }

    public void updateBalance(Player player) {
        String s = String.valueOf(player.getPlayerBalance());
        this.hitstand.balance.setText(s);
        inputPanel.repaint();
    }

    public void drawCards(Player player) {
        int handSize = player.getPlayerHand().getHand().size();
        System.out.println("SDize: " + handSize);
        Card ca = null;
        if (handSize == 2) { // draw initially dealt cards
            for (Card c : player.getPlayerHand().getHand()) {

                ImageIcon ii = new ImageIcon(c.getURL());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                playerCards.add(card);
                dealerCards.add(new JLabel("HIIIII"));
            }
        } else { // after initial deal

            ca = player.getPlayerHand().getHand().get(handSize - 1);

            ImageIcon ii = new ImageIcon(ca.getURL());
            Image img = ii.getImage();
            Image newimg = img.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH); // scale image 
            ii = new ImageIcon(newimg);  // transform it back
            JLabel card = new JLabel(ii);

            playerCards.add(card);

        }
        cardPanel.revalidate();
        repaint();
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

//        System.out.println("DEALER HAND TEST: " + dealer.getDealerHand().getHand());
        if (!player.isLoginFlag()) {
            loginPanel.unInput.setText("");
            loginPanel.pwInput.setText("");

        } else if (!this.started) {
            this.startGame();
            updateBalance(player);
            this.started = true;

        } else if (!this.betPanel.betField.getText().equals("")) {
            setBetLabel(Double.parseDouble(this.betPanel.betField.getText()));
            updateBalance(player);
        }
        if (player.getPlayerFinished()) {

            this.hitstand.hit.setEnabled(false); // enables user action after bet has been placed
            this.hitstand.stand.setEnabled(false);

        }
        if ((player.getPlayerHand().getHand().size() > 0) && (player.getPlayerFinished() == false)) { //draw if 
            drawCards(player);
        }

    }

    private static class CardsPanel extends JPanel {

        JLabel valueLabel;

        public CardsPanel() {
            super(new GridLayout());

            valueLabel = new JLabel("Value: ");
            this.add(valueLabel, BorderLayout.EAST);
        }
//
//        public void add(Card... cards) {
//            for (Card c : cards) {
//                ImageIcon ii = new ImageIcon(c.getURL());
//                JLabel card = new JLabel(ii);
//                this.add(card);
//            }
//        }
    }

}
