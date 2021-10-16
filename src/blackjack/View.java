/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Ivan
 */
public class View extends JFrame implements Observer {

    public LoginPanel loginPanel = new LoginPanel();

    public JPanel cardPanel = new JPanel(new GridLayout(2, 1));
    public CardsPanel playerCards = new CardsPanel();
    public CardsPanel dealerCards = new CardsPanel();

    public JPanel inputPanel = new JPanel(new BorderLayout());
    public BetPanel betPanel = new BetPanel();
    public RightPanel rightPanel = new RightPanel();
    public HitStandPanel hitstand = new HitStandPanel();
    
    QuitPanel quitPanel = new QuitPanel();

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

    public void addActionListener(ActionListener listener) {
        loginPanel.loginButton.addActionListener(listener);
        betPanel.betButton.addActionListener(listener);
        hitstand.hit.addActionListener(listener);
        hitstand.stand.addActionListener(listener);
        rightPanel.quit.addActionListener(listener);
        rightPanel.reset.addActionListener(listener);
        rightPanel.help.addActionListener(listener);
        rightPanel.logout.addActionListener(listener);
        loginPanel.exitButton.addActionListener(listener);
        quitPanel.aboutButton.addActionListener(listener);
        

    }

    /**
     * adds JPanel components relating to game to JFrame
     */
    public void startGame() {

        betPanel.betButton.setEnabled(true);
        inputPanel.add(betPanel, BorderLayout.WEST);
        inputPanel.add(hitstand, BorderLayout.CENTER);
        inputPanel.add(rightPanel, BorderLayout.EAST);

        cardPanel.add(dealerCards);
        cardPanel.add(playerCards);

        this.getContentPane().removeAll();
        this.setVisible(true);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.add(cardPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();

    }

    /**
     * Updates the bet label to whatever the user input
     *
     * @param betAmount
     */
    public void setBetLabel(Double betAmount) {
        this.betPanel.betAmount.setText("Bet Amount: $" + this.betPanel.betField.getText());
        this.betPanel.betButton.setEnabled(false); // disables preventing user from placing bets during game
        this.hitstand.hit.setEnabled(true); // enables user action after bet has been placed
        this.hitstand.stand.setEnabled(true);
        this.revalidate();
        this.repaint();
    }

    /**
     * Updates the balance label to the players current balance
     *
     * @param player
     */
    public void updateBalance(Player player) {
        String s = String.valueOf(player.getPlayerBalance());
        this.hitstand.balance.setText("Balance: $" + s);
        this.revalidate();
        this.repaint();
    }

    /**
     * Draws players card onto playerCards JPanel
     *
     * @param player
     */
    public void drawPlayerCards(Player player) {
        int playerHandSize = player.getPlayerHand().getHand().size();
//        System.out.println("player hand size: " + playerHandSize);
        Card ca = null;
        // DRAW PLAYER CARDS
        if (playerHandSize == 2) { // draws first 2 cards from initial deal
            for (Card c : player.getPlayerHand().getHand()) {

                // Sets ImageIcon of JLabel to jpg of card. Adds JLabel to panel
                ImageIcon ii = new ImageIcon(c.getURL());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                playerCards.add(card);

            }
        } else { // after initial deal
//            if (player.isBust == false) { WONT DRAW FINAL CARD
            ca = player.getPlayerHand().getHand().get(playerHandSize - 1);

            ImageIcon ii = new ImageIcon(ca.getURL());
            Image img = ii.getImage();
            Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
            ii = new ImageIcon(newimg);  // transform it back
            JLabel card = new JLabel(ii);

            playerCards.add(card);

//            }
        }
        // Updates player hand value lAbel on left of drawn cards
        playerCards.valueLabel.setText(player.getPlayerName() + "'s Value: " + String.valueOf(player.getPlayerHand().getHandValue()));
//        playerCards.valueLabel.setForeground(Color.WHITE);
        this.revalidate();
        this.repaint();
    }

    // Reintialize panels and hasWinner variable for new game
    public void resetGame() {
        super.getContentPane().removeAll();
        cardPanel = new JPanel(new GridLayout(2, 1));
        playerCards = new CardsPanel();
        dealerCards = new CardsPanel();

        hitstand.hit.setEnabled(false);
        hitstand.stand.setEnabled(false);
        this.hasWinner = false;
        betPanel.betField.setText("");
        betPanel.betAmount.setText("Bet Amount:");
//        this.started = false;

        startGame();
    }

    /**
     * Draws dealers card onto dealerCards JPanel
     *
     * @param player
     * @param dealer
     */
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
            dealerCards.add(backCard); // During initial deal, second card is face down

            dealerCards.valueLabel.setText("Dealer's Value : " + String.valueOf(dealer.getDealerHand().getHand().get(0).getValue().getCardValue()));
//            dealerCards.valueLabel.setForeground(Color.WHITE);
//            }
        } else { // after initial deal
            if (player.getPlayerFinished() == true && player.isBust == false && player.getPlayerFinished() == true) { // dealers card drawn only after player finishes playing

                dealerCards.remove(backCard);
                String secondCardURL = dealer.getDealerHand().getHand().get(1).getURL();
                ImageIcon jj = new ImageIcon(secondCardURL);
                Image img2 = jj.getImage();
                Image newimg2 = img2.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                jj = new ImageIcon(newimg2);  // transform it back
                JLabel secondCard = new JLabel(jj);
                dealerCards.add(secondCard); // face down card is now face up after initial deal

                dCa = dealer.getDealerHand().getHand().get(dealerHandSize - 1);

                ImageIcon ii = new ImageIcon(dCa.getURL());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(150, 220, java.awt.Image.SCALE_SMOOTH); // scale image 
                ii = new ImageIcon(newimg);  // transform it back
                JLabel card = new JLabel(ii);

                dealerCards.add(card);
            }
            dealerCards.valueLabel.setText("Dealer's Value : " + String.valueOf(dealer.getDealerHand().getHandValue()));
//            dealerCards.valueLabel.setForeground(Color.WHITE);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Displays blackjack rules on a JOptionPane
     *
     * @param rules
     */
    public void help(String rules) {
        System.out.println(rules);
        JOptionPane.showMessageDialog(this, rules, "RULES", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays end screen with player stats and leaderboard
     *
     * @param data
     */
    private void quitGame(Data data) {

        System.out.println(data.leaderBoard);
        

        quitPanel.scoreLabel.setText("Your balance: " + data.player.getPlayerBalance());
        quitPanel.ratioLabel.setText("You have " + data.player.getPlayerWins() + " wins and " + data.player.getPlayerLoss() + " losses");
        quitPanel.leaderBoard.setText("\tLEADERBOARD\n " + data.leaderBoard);
        
        this.getContentPane().removeAll();
        //calcPanel.setVisible(true);
        this.add(quitPanel);

        this.revalidate();
        this.repaint();
    }

    /**
     * Displays invalid bet error message on JOptionPane
     */
    public void invalidBet() {
        JOptionPane.showMessageDialog(this, "Bet must be a number and valued below your balance!", "INVALID INPUT", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays winner message on JOptionPane depending on data winner value
     *
     * @param data
     */
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
    
    /**
     * Displays credits in JOptionPane
     */
    public void displayAbout(Data data)
    {
        JOptionPane.showMessageDialog(this, data.credits, "CREDITS" , JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays login panel and resets View class flags
     */
    public void logout() {
        this.getContentPane().removeAll();
        loginPanel.pwInput.setText("");
        loginPanel.unInput.setText("");
        started = false;
        hasWinner = false;
        this.add(loginPanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Called when Model calls notifyObservers()
     *
     * @param o
     * @param arg
     */
    // @Override
    public void update(Observable o, Object arg) {
        Data data = (Data) arg;

//        System.out.println("DEALER HAND TEST: " + dealer.getDealerHand().getHand());
        if (!data.player.isLoginFlag()) {
            loginPanel.unInput.setText("");
            loginPanel.pwInput.setText("");
            JOptionPane.showMessageDialog(this, "Wrong username or password!", "WRONG CREDENTIALS", JOptionPane.WARNING_MESSAGE);
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
            this.quitGame(data);

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

        JLabel valueLabel = new JLabel("");
        private BufferedImage img = null;

        public CardsPanel() {
            super(new GridLayout());
            try {
                img = ImageIO.read(new File("./resources/images/gameBackground.jpg"));
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
            valueLabel.setForeground(Color.WHITE);
//            valueLabel = new JLabel();
            this.add(valueLabel, BorderLayout.WEST);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }
    }

}
