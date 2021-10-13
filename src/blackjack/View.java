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
import javax.swing.JOptionPane;
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
    private boolean hasWinner = false;
    JLabel backCard;

    public View() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);

        this.setLocationRelativeTo(null);

        this.add(loginPanel);
        // pack();
        this.setVisible(true);

    }

    public void startGame() {

        betPanel.betButton.setEnabled(true);
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
        this.hitstand.balance.setText("Balance: $" + s);
        inputPanel.repaint();
    }

    public void drawPlayerCards(Player player) {
        int playerHandSize = player.getPlayerHand().getHand().size();
//        System.out.println("player hand size: " + playerHandSize);
        Card ca = null;
        // DRAW PLAYER CARDS
        if (playerHandSize == 2) { // draws first 2 cards from initial deal
            for (Card c : player.getPlayerHand().getHand()) {

                ImageIcon ii = new ImageIcon(c.getURL());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                playerCards.add(card);

            }
        } else { // after initial deal

            ca = player.getPlayerHand().getHand().get(playerHandSize - 1);

            ImageIcon ii = new ImageIcon(ca.getURL());
            Image img = ii.getImage();
            Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
            ii = new ImageIcon(newimg);  // transform it back
            JLabel card = new JLabel(ii);

            playerCards.add(card);

        }

        playerCards.valueLabel.setText(player.getPlayerName() + "'s Value: " + String.valueOf(player.getPlayerHand().getHandValue()));

        cardPanel.revalidate();
        repaint();
    }

    public void resetGame() {
        super.getContentPane().removeAll();
        cardPanel = new JPanel(new BorderLayout());
        playerCards = new CardsPanel();
        dealerCards = new CardsPanel();

        hitstand.hit.setEnabled(false);
        hitstand.stand.setEnabled(false);
        this.hasWinner = false;
        betPanel.betField.setText("");
        betPanel.betAmount.setText("Bet Amount:");

        
        startGame();
    }

    public void drawDealerCards(Player player, Dealer dealer) {
        // DRAW PLAYER CARDS
        int dealerHandSize = dealer.getDealerHand().getHand().size();
//        System.out.println("dealer hand size: " + dealerHandSize);
        Card dCa = null;
        
        if (dealerHandSize == 2 && player.getPlayerHand().getHand().size() == 2) { // draw initially dealt cards
//            for (Card c : dealer.getDealerHand().getHand()) {
String firstCardURL = dealer.getDealerHand().getHand().get(0).getURL();
                ImageIcon ii = new ImageIcon(firstCardURL);
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                ImageIcon jj = new ImageIcon("./resources/images/cards/back.png");
                Image img2 = jj.getImage();
                Image newimg2 = img2.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                jj = new ImageIcon(newimg2);  // transform it back
                backCard = new JLabel(jj);
                
                dealerCards.add(card);
                dealerCards.add(backCard);
                

//            }
        } else { // after initial deal
            if (player.getPlayerFinished() == true) { // dealers card drawn only after player finishes playing
                
                dealerCards.remove(backCard);
                String secondCardURL = dealer.getDealerHand().getHand().get(1).getURL();
                ImageIcon jj = new ImageIcon(secondCardURL);
                Image img2 = jj.getImage();
                Image newimg2 = img2.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                jj = new ImageIcon(newimg2);  // transform it back
                JLabel secondCard = new JLabel(jj);
                dealerCards.add(secondCard);
                
                dCa = dealer.getDealerHand().getHand().get(dealerHandSize - 1);

                ImageIcon ii = new ImageIcon(dCa.getURL());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                dealerCards.add(card);
            }
        }
        dealerCards.valueLabel.setText("Dealer's Value : " + String.valueOf(dealer.getDealerHand().getHandValue()));
        cardPanel.revalidate();
        repaint();
    }

    private void quitGame(Player player) {

        JPanel quitPanel = new JPanel();
        JLabel scoreLabel = new JLabel("Your balance: " + player.getPlayerBalance());
        JLabel ratioLabel = new JLabel("You have " + player.getPlayerWins() + " wins and " + player.getPlayerLoss() + " losses");
        quitPanel.add(scoreLabel);
        quitPanel.add(ratioLabel);
        this.getContentPane().removeAll();
        //calcPanel.setVisible(true);
        this.add(quitPanel);
        this.revalidate();
        this.repaint();
    }

    public void addActionListener(ActionListener listener) {
        loginPanel.loginButton.addActionListener(listener);
        betPanel.betButton.addActionListener(listener);
        hitstand.hit.addActionListener(listener);
        hitstand.stand.addActionListener(listener);
        rightPanel.quit.addActionListener(listener);
        rightPanel.reset.addActionListener(listener);
    }

    public void displayWinner(Data data) {
        System.out.println("DISPLAYING WINNER....");
        ImageIcon icon;

        switch (data.winner) {
            case (1): // tie
                icon = new ImageIcon("./resources/images/winner/tie.png");
                JOptionPane.showMessageDialog(this, "YOU HAVE TIED WITH THE HOUSE!", "TIE", JOptionPane.WARNING_MESSAGE, icon);
                break;
            case (2): // player natural bj
                icon = new ImageIcon("./resources/images/winner/bj.png");
                JOptionPane.showMessageDialog(this, "YOU GOT BLACKJACK!", "BLACKJACK", JOptionPane.WARNING_MESSAGE, icon);
                break;
            case (3): // player wins
                icon = new ImageIcon("./resources/images/winner/win.png");
                JOptionPane.showMessageDialog(this, "YOU HAVE WON!", "WIN", JOptionPane.WARNING_MESSAGE, icon);
                break;
            case (4): // player loses
                icon = new ImageIcon("./resources/images/winner/loss.png");
                JOptionPane.showMessageDialog(this, "YOU HAVE LOST!", "LOSS", JOptionPane.WARNING_MESSAGE, icon);
                break;
        }

        this.hasWinner = true;
    }

    // @Override
    public void update(Observable o, Object arg) {
        Data data = (Data) arg;

//        System.out.println("DEALER HAND TEST: " + dealer.getDealerHand().getHand());
        if (!data.player.isLoginFlag()) {
            loginPanel.unInput.setText("");
            loginPanel.pwInput.setText("");

        } else if (!this.started) {
            this.startGame();
            updateBalance(data.player);
            this.started = true;

        } else if (!this.betPanel.betField.getText().equals("")) {
            setBetLabel(Double.parseDouble(this.betPanel.betField.getText()));
            updateBalance(data.player);
        }
        if (data.player.getPlayerFinished()) {

            this.hitstand.hit.setEnabled(false); // enables user action after bet has been placed
            this.hitstand.stand.setEnabled(false);

        }
        if ((data.player.getPlayerHand().getHand().size() > 0) && data.player.hasStand == false) { //draw if 
            drawPlayerCards(data.player);
        }

        if (data.dealer.getDealerHand().getHand().size() > 0 && data.dealer.getDealerFinished() == false) {
            drawDealerCards(data.player, data.dealer);
        }
        if (data.quitFlag == true) {
            this.quitGame(data.player);

        }
        if (data.resetFlag == true) {
//            this.started = false;
//            this.hasWinner = false;
            data.resetFlag = false;

            resetGame();
        }
        if (data.winner != 0 && this.hasWinner == false) {
            displayWinner(data);
            System.out.println("HEREE");
        }
    }

    private static class CardsPanel extends JPanel {

        JLabel valueLabel;

        public CardsPanel() {
            super(new GridLayout());

            valueLabel = new JLabel();
            this.add(valueLabel, BorderLayout.EAST);
        }

    }

}
